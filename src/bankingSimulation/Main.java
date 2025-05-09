package bankingSimulation;

import gui.Login;
import system.*;

public class Main {

    public static void main(String[] args) {
        Login frame = new Login();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        BankLedger bankLedger = BankLedger.getInstance();
        LowBalanceNotifier lowBalanceNotifier = new LowBalanceNotifier();

        // Test Customer 1: Loan + Checking
        Customer testCustomer = new Customer("sad", "2025/12/12", "09443434332", "sad@.com", "street", "tas");
        Account account = AccountFactory.createAccount("Checking", testCustomer);
        Account loanAccount = AccountFactory.createAccount("Loan", testCustomer);
        account.deposit(2000);
        ((LoanAccount) loanAccount).borrow(2000);
        bankLedger.addAccount(account);
        bankLedger.addAccount(loanAccount);
        testCustomer.addAccount(account);
        testCustomer.addAccount(loanAccount);
        account.addObserver(lowBalanceNotifier);
        loanAccount.addObserver(lowBalanceNotifier);
        account.addNotification(new Notification("Initial Deposit", "You deposited PHP 2000 to your Checking account.", java.time.LocalDate.now().toString()));
        loanAccount.addNotification(new Notification("Loan Disbursement", "You borrowed PHP 2000 on your Loan account.", java.time.LocalDate.now().toString()));

        // Test Customer 2: Only Checking
        Customer testCustomer11 = new Customer("wat", "2025/12/12", "09443434332", "sad@.com", "street", "yat");
        account = AccountFactory.createAccount("Checking", testCustomer11);
        account.deposit(2000);
        bankLedger.addAccount(account);
        testCustomer11.addAccount(account);
        account.addObserver(lowBalanceNotifier);
        account.addNotification(new Notification("Initial Deposit", "You deposited PHP 2000 to your Checking account.", java.time.LocalDate.now().toString()));

        // ➕ Add 15 more predefined customers
        for (int i = 1; i <= 15; i++) {
            String name = "Customer" + i;
            String dob = "2000/01/01";
            String contact = "0918123456" + i;
            String email = "customer" + i + "@mail.com";
            String address = "Street " + i;
            String password = "pass" + i;

            Customer customer = new Customer(name, dob, contact, email, address, password);

            // Alternate between account types for variety (or set fixed type if you prefer)
            String accountType;
            if (i % 3 == 0) {
                accountType = "Loan";
            } else if (i % 3 == 1) {
                accountType = "Checking";
            } else {
                accountType = "Savings";
            }

            Account acc = AccountFactory.createAccount(accountType, customer);

            // Perform a basic operation based on account type
            if (accountType.equals("Loan")) {
                ((LoanAccount) acc).borrow(1000 + i * 100);
                acc.addNotification(new Notification("Loan", "You borrowed PHP " + (1000 + i * 100) + " on your Loan account.", java.time.LocalDate.now().toString()));
            } else {
                acc.deposit(1000 + i * 100);
                acc.addNotification(new Notification("Deposit", "You deposited PHP " + (1000 + i * 100) + " to your " + accountType + " account.", java.time.LocalDate.now().toString()));
            }

            acc.addObserver(lowBalanceNotifier);
            bankLedger.addAccount(acc);
            customer.addAccount(acc);
        }
    }
}
