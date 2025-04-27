package system;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    
    private List<Account> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();  
    }

    
    public void addAccount(Account account) {
        accounts.add(account);
    }

    
    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    
    public List<Account> getAllAccounts() {
        return accounts;
    }

    // Find an account by account number
    public Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null; 
    }

    public boolean deposit(Account account, double amount) {
        return account.deposit(amount);
    }

    public boolean withdraw(Account account, double amount) {
        return account.withdraw(amount);
    }

    public boolean transfer(Account sender, Account recipient, double amount) {
        return sender.transfer(recipient, sender.getOwner(), amount);
    }
}
