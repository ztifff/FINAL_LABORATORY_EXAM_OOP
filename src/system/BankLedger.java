package system;

import java.util.ArrayList;
import java.util.List;


public class BankLedger {
    
    private static BankLedger instance;
    private Bank bank;
    private List<Account> accounts;
    
    private BankLedger() {
        accounts = new ArrayList<>();
        this.bank = new Bank();
    }
    
    public static synchronized BankLedger getInstance() {
        if (instance == null) {
            instance = new BankLedger();
        }
        return instance;
    }
    
    public void addAccount(Account account) {
        accounts.add(account);
        bank.addAccount(account);
    }
    
    public void removeAccount(Account account) {
        accounts.remove(account);
    }
    
    public List<Account> getAllAccounts() {
        return accounts;
    }
    
    public Bank getBank() {
        return bank;
    }
    
    public Account getAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().trim().equalsIgnoreCase(accountNumber.trim())) {
                return account;
            }
        }
        return null; 
    }
    
    public void generateMonthlyTransactionSummary() {
        System.out.println("Monthly Transaction Summary:");
        for (Account account : accounts) {
            System.out.println("Account: " + account.getAccountNumber());
            account.getHistory().getHistoryList().forEach(transaction -> {
                System.out.println(transaction);
            });
        }
    }
    
    public void generateAccountBalanceReport() {
        System.out.println("Account Balance Report:");
        for (Account account : accounts) {
            System.out.println("Account: " + account.getAccountNumber() + " | Balance: ₱" + account.getBalance());
        }
    }
    
    public void generateDailyDepositsWithdrawalsReport() {
        System.out.println("Daily Deposits and Withdrawals Report:");
        for (Account account : accounts) {
            System.out.println("Account: " + account.getAccountNumber());
            account.getHistory().getHistoryList().forEach(transaction -> {
                if (transaction.getDate().equals(java.time.LocalDate.now())) {
                    System.out.println(transaction);
                }
            });
        }
    }
    
    public Account findAccountByAccountNumber(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null; // Not found
    }
    
    public Account findAccountByName(String Name) {
        for (Account acc : accounts) {
            if (acc.getOwner().getName().equals(Name)) {
                return acc;
            }
        }
        return null; // Not found
    }
    
}
