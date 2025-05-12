package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;

import data.Bank;
import data.BankLedger;
import observer.AccountObserver;
import observer.AccountSubject;
import service.Notification;

import java.time.LocalDate;

public class Account implements AccountSubject, AccountObserver {
    private String accountNumber;
    protected double balance;
    private String accountType;
    private List<AccountObserver> observers = new ArrayList<>();
    private List<Notification> notifications = new ArrayList<>();
    protected TransactionHistory history;
    private Customer owner;
    private Bank bank;

    public Account(Customer owner, String accountType, Bank bank) {
        this.accountNumber = UUID.randomUUID().toString().substring(0, 8); 
        this.balance = 0.0;
        this.history = new TransactionHistory();
        this.owner = owner;
        this.accountType = accountType; 
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }
    
    public Customer getOwner() {
        return owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
		this.balance = balance;
	}

	public TransactionHistory getHistory() {
        return history;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            Transaction txn = new Transaction("Deposit", amount, LocalDate.now());
            history.addTransaction(txn);
            BankLedger.getInstance().getTransactionHistory().addTransaction(txn);
            notifyObservers();
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            Transaction txn = new Transaction("Withdraw", amount, LocalDate.now());
            history.addTransaction(txn);
            BankLedger.getInstance().getTransactionHistory().addTransaction(txn);
            notifyObservers();
            return true;
        }
        return false;
    }

    public boolean transfer(Account recipient, Customer owner, double amount) {
        if (amount <= 0) {
            JOptionPane.showMessageDialog(null, "Transfer amount must be positive.", "Invalid Transfer", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (balance < amount) {
            JOptionPane.showMessageDialog(null, "Insufficient balance for transfer.", "Transfer Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Deduct from sender
        this.balance -= amount;

        if (recipient instanceof LoanAccount) {
            LoanAccount loan = (LoanAccount) recipient;
            double remainingLoan = loan.getLoanBalance();
            double repaymentAmount = Math.min(amount, remainingLoan);
            loan.setLoanBalance(remainingLoan - repaymentAmount);

            Transaction loanTxn = new Transaction("Loan Repayment from " + this.getOwner().getName(), repaymentAmount, LocalDate.now());
            loan.getHistory().addTransaction(loanTxn);
            BankLedger.getInstance().getTransactionHistory().addTransaction(loanTxn);
        } else {
            recipient.balance += amount;
            Transaction receiveTxn = new Transaction("Transfer from " + this.getOwner().getName(), amount, LocalDate.now());
            recipient.getHistory().addTransaction(receiveTxn);
            BankLedger.getInstance().getTransactionHistory().addTransaction(receiveTxn);
        }

        Transaction sendTxn = new Transaction("Transfer to " + recipient.getOwner().getName(), amount, LocalDate.now());
        history.addTransaction(sendTxn);
        BankLedger.getInstance().getTransactionHistory().addTransaction(sendTxn);

        notifyObservers();
        return true;
    }
    
    public void recalculateBalance() {
        double total = 0;

        for (Transaction t : history.getHistoryList()) {
            String action = t.getAction().toLowerCase();

            if (action.contains("deposit") || action.contains("transfer from")) {
                total += t.getAmount();
            } else if (action.contains("withdraw") || action.contains("transfer to")) {
                total -= t.getAmount();
            }
        }

        this.balance = total;
    }



    // Observer Methods
    @Override
    public void addObserver(AccountObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(AccountObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (AccountObserver observer : observers) {
            observer.update(this);
        }
    }
    
    public void addNotification(Notification notification) {
        notification.addObserver(this);  // The Account itself is an observer
        notifications.add(notification);
    }

    public void removeNotification(Notification notification) {
        notification.removeObserver(this);  // Removes the observer when removing the notification
        notifications.remove(notification);
    }
    
    public void removeNotificationRelatedTo(String transactionId) {
        notifications.removeIf(n -> transactionId.equals(n.getTransactionId()));
    }
    
    public Notification getLatestNotification() {
        if (!notifications.isEmpty()) {
            return notifications.get(notifications.size() - 1);
        }
        return null;
    }
    
    public Notification findNotificationByTransactionID(String transactionID) {
        for (Notification n : notifications) {
            if (n.getTransactionId().equals(transactionID)) {
                return n;
            }
        }
        return null;
    }
    
    public Transaction getLatestTransaction() {
        if (!history.getHistoryList().isEmpty()) {
            return history.getHistoryList().get(history.getHistoryList().size() - 1);
        }
        return null;
    }



    public List<Notification> getNotifications() {
        return notifications;
    }

	@Override
	public void update(Account account) {
		// TODO Auto-generated method stub
		
	}
}
