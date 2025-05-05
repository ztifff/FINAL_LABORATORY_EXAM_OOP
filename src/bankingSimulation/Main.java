package bankingSimulation;

import gui.Login;
import system.Account;
import system.AccountFactory;
import system.BankLedger;
import system.Customer;
import system.LoanAccount;
import system.LowBalanceNotifier;
import system.Notification; // Import Notification class

public class Main {

    public static void main(String[] args) {
        Login frame = new Login();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
        // Create and set up customer 1 with checking account
        Customer testCustomer = new Customer("sad", "2025/12/12", "09443434332", "sad@.com", "street", "tas");
        Account account = AccountFactory.createAccount("Checking", testCustomer);
        Account newAccount = AccountFactory.createAccount("Loan", testCustomer);
        
        // Perform initial deposit and loan
        account.deposit(2000);
        ((LoanAccount) newAccount).borrow(2000);
        
        // Add accounts to bank and customer
        BankLedger bankLedger = BankLedger.getInstance();
        bankLedger.addAccount(account);
        testCustomer.addAccount(account);
        bankLedger.addAccount(newAccount);
        testCustomer.addAccount(newAccount);
        
        // Register LowBalanceNotifier as an observer for the new account
        LowBalanceNotifier lowBalanceNotifier = new LowBalanceNotifier();
        account.addObserver(lowBalanceNotifier);
        newAccount.addObserver(lowBalanceNotifier);
        
        // Create initial deposit notification
        String depositMessage = "You deposited PHP 2000 to your " + account.getAccountType() + " account.";
        Notification initialDepositNotif = new Notification("Initial Deposit", depositMessage, java.time.LocalDate.now().toString());
        account.addNotification(initialDepositNotif);  // Add notification to customer
        
        // Create a notification for loan disbursement
        String loanMessage = "You borrowed PHP 2000 on your " + newAccount.getAccountType() + " account.";
        Notification loanNotif = new Notification("Loan Disbursement", loanMessage, java.time.LocalDate.now().toString());
        newAccount.addNotification(loanNotif);  // Add notification to customer

        // Create and set up customer 2 with checking account
        Customer testCustomer11 = new Customer("wat", "2025/12/12", "09443434332", "sad@.com", "street", "yat");
        account = AccountFactory.createAccount("Checking", testCustomer11);
        account.deposit(2000);

        // Add the new account to the bank and customer
        bankLedger.addAccount(account);
        testCustomer11.addAccount(account);
        
        account.addObserver(lowBalanceNotifier);

        // Add a notification for the initial deposit
        String depositMessage2 = "You deposited PHP 2000 to your " + account.getAccountType() + " account.";
        Notification initialDepositNotif2 = new Notification("Initial Deposit", depositMessage2, java.time.LocalDate.now().toString());
        account.addNotification(initialDepositNotif2);  // Add notification to customer
    }
}
