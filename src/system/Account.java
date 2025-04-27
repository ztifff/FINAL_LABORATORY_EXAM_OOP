package system;

import java.util.UUID;
import java.time.LocalDate;

public class Account {
    private String accountNumber;
    private double balance;
    private String accountType;
    private TransactionHistory history;
    private Customer owner;
    private Admin admin;
    private Bank bank;

    public Account(Customer owner, String accountType, Bank bank) {
        this.accountNumber = UUID.randomUUID().toString().substring(0, 8); // Random 8-char ID
        this.balance = 0.0;
        this.history = new TransactionHistory();
        this.owner = owner;
        this.accountType = accountType; 
        this.bank = bank;
    }
    
    public Bank getBank() {
        return bank;
    }
    
    public Account(Admin admin) {
    	this.admin = admin;
	}
    
    public Customer getOwner() {
        return owner;
    }
    
    public Admin getAdmin() {
		return admin;
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
            System.out.println("Transfer amount must be positive.");
            return false;
        }
        
     // Withdraw the amount from the sender account
        if (!this.withdraw(amount)) {
            System.out.println("Insufficient balance for transfer.");
            return false;
        }

        // Deposit the amount into the recipient account
        if (!recipient.deposit(amount)) {
            this.deposit(amount);
            System.out.println("Transfer failed. Amount refunded.");
            return false;
        }

        // If everything is successful, log the transaction
        history.addTransaction(new Transaction("Transfer to " + recipient.getOwner().getName(), amount, LocalDate.now()));
        recipient.getHistory().addTransaction(new Transaction("Transfer from " + this.getOwner().getName(), amount, LocalDate.now()));
        return true;
    }

    public void printStatement() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
        history.printHistory();
    }
}
