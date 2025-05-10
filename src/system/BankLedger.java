package system;

import java.util.ArrayList;
import java.util.List;

public class BankLedger {

    private static BankLedger instance;
    private Bank bank;
    private TransactionHistory transactionHistory;
    private List<Account> accounts;

    private BankLedger() {
        this.accounts = new ArrayList<>();
        this.bank = new Bank();
        this.transactionHistory = new TransactionHistory();
    }

    public static synchronized BankLedger getInstance() {
        if (instance == null) {
            instance = new BankLedger();
        }
        return instance;
    }

    // ----- Account Management -----

    public void addAccount(Account account) {
        accounts.add(account);
        bank.addAccount(account);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        bank.removeAccount(account);
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Account getAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().trim().equalsIgnoreCase(accountNumber.trim())) {
                return account;
            }
        }
        return null;
    }

    public Account findAccountByAccountNumber(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    public Account findAccountByName(String name) {
        for (Account acc : accounts) {
            if (acc.getOwner().getName().equals(name)) {
                return acc;
            }
        }
        return null;
    }

    // ----- Accessors -----

    public Bank getBank() {
        return bank;
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }
}
