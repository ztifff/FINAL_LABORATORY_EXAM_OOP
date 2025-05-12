package service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import model.Account;
import model.LoanAccount;
import model.Transaction;

public class ReportGenerator {

    public static String generateMonthlyTransactionSummary(Account account, int month, int year) {
        StringBuilder report = new StringBuilder();
        report.append("Monthly Transaction Summary for ")
              .append(account.getOwner().getName())
              .append("\n")
              .append("Month: ").append(month)
              .append(", Year: ").append(year)
              .append("\n");

        List<Transaction> monthlyTransactions = account.getHistory().getHistoryList().stream()
                .filter(transaction -> transaction.getDate().getMonthValue() == month && 
                                       transaction.getDate().getYear() == year)
                .collect(Collectors.toList());

        double totalDeposits = 0;
        double totalWithdrawals = 0;
        double totalTransfers = 0;
        for (Transaction transaction : monthlyTransactions) {
            switch (transaction.getAction()) {
                case "Deposit":
                    totalDeposits += transaction.getAmount();
                    break;
                case "Withdraw":
                    totalWithdrawals += transaction.getAmount();
                    break;
                case "Transfer":
                    totalTransfers += transaction.getAmount();
                    break;
            }
        }

        report.append("Total Deposits: ₱").append(totalDeposits)
              .append("\n")
              .append("Total Withdrawals: ₱").append(totalWithdrawals)
              .append("\n")
              .append("Total Transfers: ₱").append(totalTransfers)
              .append("\n");

        return report.toString();
    }
    
    public static String generateLoanTransactionSummary(Account account, LocalDate startDate, LocalDate endDate) {
        if (!(account instanceof LoanAccount)) {
            return "Not a loan account.";
        }

        LoanAccount loan = (LoanAccount) account;
        List<Transaction> transactions = loan.getTransactionsBetween(startDate, endDate, account);

        StringBuilder report = new StringBuilder("Loan Account Summary:\n");
        report.append("Account Number: ").append(account.getAccountNumber()).append("\n");
        report.append("Owner: ").append(account.getOwner().getName()).append("\n");
        report.append("Date Range: ").append(startDate).append(" to ").append(endDate).append("\n\n");

        if (transactions.isEmpty()) {
            report.append("No loan transactions (borrow or repay) in this period.");
        } else {
            for (Transaction tx : transactions) {
                report.append(tx.getDate()).append(" - ").append(tx.getAction()).append(": PHP ").append(tx.getAmount()).append("\n");
            }
        }

        return report.toString();
    }


    public static String generateAccountBalanceReport(Account account) {
        StringBuilder report = new StringBuilder();
        report.append("📄 Account Balance Report for ")
              .append(account.getOwner().getName())
              .append("\n")
              .append("🔢 Account Number: ").append(account.getAccountNumber())
              .append("\n");

        if (account instanceof LoanAccount) {
            double loanBalance = ((LoanAccount) account).getLoanBalance();
            report.append("💰 Loan Balance: ").append(String.format("%,.2f", loanBalance)).append("\n");
        } else {
            report.append("💰 Current Balance: ").append(String.format("%,.2f", account.getBalance())).append("\n");
        }

        return report.toString();
    }

 // For Daily Deposits and Withdrawals Report
    public static String generateDailyDepositsAndWithdrawalsReport(Account account, String date) {
        StringBuilder report = new StringBuilder();
        report.append("📅 DAILY TRANSACTION SUMMARY\n")
              .append("👤 Account Holder: ").append(account.getOwner().getName()).append("\n")
              .append("🏦 Account Number: ").append(account.getAccountNumber()).append("\n")
              .append("📆 Date: ").append(date).append("\n\n");

        List<Transaction> dailyTransactions = account.getHistory().getHistoryList();
        List<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction : dailyTransactions) {
            if (transaction.getDate().toString().equals(date)) {
                filteredTransactions.add(transaction);
            }
        }

        if (filteredTransactions.isEmpty()) {
            report.append("✅ No transactions found for this date.\n");
            return report.toString();
        }

        double totalDeposits = 0;
        double totalWithdrawals = 0;

        for (Transaction transaction : filteredTransactions) {
            String timeOnly = transaction.getDate() != null ? transaction.getDate().toString() : "N/A";
            String action = transaction.getAction();
            double amount = transaction.getAmount();

            if ("Deposit".equalsIgnoreCase(action)) {
                totalDeposits += amount;
            } else if ("Withdrawal".equalsIgnoreCase(action)) {
                totalWithdrawals += amount;
            }

            // Report each transaction on separate lines
            report.append("Date: ").append(timeOnly).append("\n")
                  .append("Action: ").append(action).append("\n")
                  .append("Amount: ").append(amount).append("\n\n");
        }

        // Summary
        report.append("Total Deposits: ").append(totalDeposits).append("\n")
              .append("Total Withdrawals: ").append(totalWithdrawals).append("\n");

        return report.toString();
    }

    // For Account Activity Report
    public static String generateAccountActivityReport(Account account) {
        StringBuilder report = new StringBuilder();

        report.append("📄 ACCOUNT ACTIVITY REPORT\n")
              .append("👤 Account Holder: ").append(account.getOwner().getName()).append("\n")
              .append("🏦 Account Number: ").append(account.getAccountNumber()).append("\n")
              .append("📅 Report Date: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
              .append("\n\n");

        List<Transaction> transactions = account.getHistory().getHistoryList();

        if (transactions.isEmpty()) {
            report.append("✅ No transaction history available.\n");
            return report.toString();
        }

        for (Transaction transaction : transactions) {
            String date = transaction.getDate() != null ? transaction.getDate().toString() : "N/A";
            String action = transaction.getAction();
            double amount = transaction.getAmount();

            // Report each transaction on separate lines
            report.append("Date: ").append(date).append("\n")
                  .append("Action: ").append(action).append("\n")
                  .append("Amount: ").append(amount).append("\n\n");
        }

        return report.toString();
    }



    public static String generateLowBalanceAlertReport(List<Account> accounts, double threshold) {
        StringBuilder report = new StringBuilder("🔻 LOW BALANCE ALERT REPORT 🔻\n\n");
        boolean found = false;

        for (Account account : accounts) {
            if (!(account instanceof LoanAccount) && account.getBalance() < threshold) {
                found = true;
                report.append("👤 Name       : ").append(account.getOwner().getName()).append("\n")
                      .append("🔢 Account No : ").append(account.getAccountNumber()).append("\n")
                      .append("🏦 Type       : ").append(account.getAccountType()).append("\n")
                      .append("💰 Balance    : ").append(String.format("%,.2f", account.getBalance())).append("\n")
                      .append("--------------------------------------------------\n");
            }
        }

        if (!found) {
            report.append("✅ All applicable accounts have a balance above ")
                  .append(String.format("%,.2f", threshold)).append(".");
        }

        return report.toString();
    }
    
    public static String generateHighLoanBalanceReport(List<Account> accounts, double threshold) {
        StringBuilder report = new StringBuilder("💸 HIGH LOAN BALANCE ALERT REPORT 💸\n\n");
        boolean found = false;

        for (Account account : accounts) {
            if (account instanceof LoanAccount) {
                LoanAccount loan = (LoanAccount) account;
                if (loan.getLoanBalance() > threshold) {
                    found = true;
                    report.append("👤 Name       : ").append(loan.getOwner().getName()).append("\n")
                          .append("🔢 Account No : ").append(loan.getAccountNumber()).append("\n")
                          .append("🏦 Type       : ").append(loan.getAccountType()).append("\n")
                          .append("💰 Loan Amt   : ").append(String.format("%,.2f", loan.getLoanBalance())).append("\n")
                          .append("--------------------------------------------------\n");
                }
            }
        }

        if (!found) {
            report.append("✅ No loan accounts exceed ")
                  .append(String.format("%,.2f", threshold)).append(".");
        }

        return report.toString();
    }




}
