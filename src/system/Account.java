package system;

import java.util.UUID;

public class Account {
    private String accountNumber;
    private double balance;
    private TransactionHistory history;

    public Account() {
        this.accountNumber = UUID.randomUUID().toString().substring(0, 8); // Random 8-char ID
        this.balance = 0.0;
        this.history = new TransactionHistory();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public TransactionHistory getHistory() {
        return history;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            history.addTransaction(new Transaction("Deposit", amount));
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            history.addTransaction(new Transaction("Withdraw", amount));
            return true;
        }
        return false;
    }

    public void printStatement() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
        history.printHistory();
    }
}
