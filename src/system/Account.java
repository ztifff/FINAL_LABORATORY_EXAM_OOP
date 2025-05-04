package system;

import java.util.UUID;

import javax.swing.JOptionPane;

import java.time.LocalDate;

public class Account {
    private String accountNumber;
    private double balance;
    private String accountType;
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
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            history.addTransaction(new Transaction("Withdraw", amount, LocalDate.now()));
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

        this.balance -= amount;
        recipient.balance += amount;

        history.addTransaction(new Transaction("Transfer to " + recipient.getOwner().getName(), amount, LocalDate.now()));
        recipient.getHistory().addTransaction(new Transaction("Transfer from " + this.getOwner().getName(), amount, LocalDate.now()));
        return true;
    }

}
