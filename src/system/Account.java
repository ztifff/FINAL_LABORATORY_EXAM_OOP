package system;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;

import java.time.LocalDate;

public class Account implements AccountSubject {
    private String accountNumber;
    private double balance;
    private String accountType;
    private List<AccountObserver> observers = new ArrayList<>();
    private List<Notification> notifications = new ArrayList<>();
    private TransactionHistory history;
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

	public TransactionHistory getHistory() {
        return history;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            history.addTransaction(new Transaction("Deposit", amount, LocalDate.now()));
            notifyObservers();  // Notify observers when balance changes
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            history.addTransaction(new Transaction("Withdraw", amount, LocalDate.now()));
            notifyObservers();  // Notify observers when balance changes
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

        // Check if recipient is a LoanAccount (repay loan instead of adding to balance)
        if (recipient instanceof LoanAccount) {
            LoanAccount loan = (LoanAccount) recipient;
            double remainingLoan = loan.getLoanBalance();
            double repaymentAmount = Math.min(amount, remainingLoan);
            loan.setLoanBalance(remainingLoan - repaymentAmount);

            // Add transaction for loan repayment
            loan.getHistory().addTransaction(
            	    new Transaction("Loan Repayment from " + this.getOwner().getName(), repaymentAmount, LocalDate.now())
            	);
            
        } else {
            // Regular transfer for Checking or Savings
            recipient.balance += amount;
            recipient.getHistory().addTransaction(new Transaction("Transfer from " + this.getOwner().getName(), amount, LocalDate.now()));
        }

        // Record transaction history
        history.addTransaction(new Transaction("Transfer to " + recipient.getOwner().getName(), amount, LocalDate.now()));
        

        notifyObservers();  // Notify sender observers
        recipient.notifyObservers();  // Also notify recipient observers

        return true;
    }


    // Observer Methods (AccountSubject Interface Implementation)
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
        notifications.add(notification);
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
}
