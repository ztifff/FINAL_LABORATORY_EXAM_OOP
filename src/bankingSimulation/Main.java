package bankingSimulation;

import gui.Login;
import model.*;
import observer.LowBalanceNotifier;
import service.Notification;

import java.time.LocalDate;

import data.BankLedger;
import factory.AccountFactory;

public class Main {

    public static void main(String[] args) {
        Login frame = new Login();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        BankLedger bankLedger = BankLedger.getInstance();
        LowBalanceNotifier lowBalanceNotifier = new LowBalanceNotifier();

        // Test Customer 1: Loan + Checking
        Customer testCustomer = new Customer("Jane Doe", "2025/12/12", "09443434332", "sad@.com", "street", "joe");

        Account checking = AccountFactory.createAccount("Checking", testCustomer);
        checking.deposit(2000);
        String checkingTxId = null;
        for (Transaction t : checking.getHistory().getHistoryList()) {
            checkingTxId = t.getId();
        }
        checking.addNotification(new Notification("Initial Deposit", "You deposited PHP 2000 to your Checking account.", LocalDate.now().toString(), checkingTxId));

        Account loan = AccountFactory.createAccount("Loan", testCustomer);
        ((LoanAccount) loan).borrow(2000);
        String loanTxId = null;
        for (Transaction t : loan.getHistory().getHistoryList()) {
            loanTxId = t.getId();
        }
        loan.addNotification(new Notification("Loan Disbursement", "You borrowed PHP 2000 on your Loan account.", LocalDate.now().toString(), loanTxId));

        bankLedger.addAccount(checking);
        bankLedger.addAccount(loan);
        testCustomer.addAccount(checking);
        testCustomer.addAccount(loan);
        checking.addObserver(lowBalanceNotifier);
        loan.addObserver(lowBalanceNotifier);

        // Test Customer 2: Only Checking
        Customer testCustomer2 = new Customer("Jessa", "2025/12/12", "09443434332", "sad@.com", "street", "jes");
        Account checking2 = AccountFactory.createAccount("Checking", testCustomer2);
        checking2.deposit(2000);
        String checking2TxId = null;
        for (Transaction t : checking2.getHistory().getHistoryList()) {
            checking2TxId = t.getId();
        }
        checking2.addNotification(new Notification("Initial Deposit", "You deposited PHP 2000 to your Checking account.", LocalDate.now().toString(), checking2TxId));

        bankLedger.addAccount(checking2);
        testCustomer2.addAccount(checking2);
        checking2.addObserver(lowBalanceNotifier);

        // ➕ Add 15 more predefined customers
        for (int i = 1; i <= 15; i++) {
            String name = "Customer" + i;
            String dob = "2000/01/01";
            String contact = "0918123456" + i;
            String email = "customer" + i + "@mail.com";
            String address = "Street " + i;
            String password = "pass" + i;

            Customer customer = new Customer(name, dob, contact, email, address, password);

            String accountType = switch (i % 3) {
                case 0 -> "Loan";
                case 1 -> "Checking";
                default -> "Savings";
            };

            Account acc = AccountFactory.createAccount(accountType, customer);
            double amount = 1000 + i * 100;

            if (accountType.equals("Loan")) {
                ((LoanAccount) acc).borrow(amount);
                String txId = null;
                for (Transaction t : acc.getHistory().getHistoryList()) {
                    txId = t.getId();
                }
                acc.addNotification(new Notification("Loan", "You borrowed PHP " + amount + " on your Loan account.", LocalDate.now().toString(), txId));
            } else {
                acc.deposit(amount);
                String txId = null;
                for (Transaction t : acc.getHistory().getHistoryList()) {
                    txId = t.getId();
                }
                acc.addNotification(new Notification("Deposit", "You deposited PHP " + amount + " to your " + accountType + " account.", LocalDate.now().toString(), txId));
            }

            acc.addObserver(lowBalanceNotifier);
            bankLedger.addAccount(acc);
            customer.addAccount(acc);
        }
    }
}
