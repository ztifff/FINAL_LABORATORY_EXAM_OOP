package system;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
                case "Withdrawal":
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
        report.append("Account Balance Report for ")
              .append(account.getOwner().getName())
              .append("\n")
              .append("Account Number: ").append(account.getAccountNumber())
              .append("\n")
              .append("Current Balance: ₱").append(account.getBalance())
              .append("\n");
        return report.toString();
    }

    public static String generateDailyDepositsAndWithdrawalsReport(Account account, String date) {
        StringBuilder report = new StringBuilder();
        report.append("Daily Transactions for ").append(date)
              .append("\n");

        List<Transaction> dailyTransactions = account.getHistory().getHistoryList().stream()
                .filter(transaction -> transaction.getDate().toString().equals(date))
                .collect(Collectors.toList());

        double totalDeposits = 0;
        double totalWithdrawals = 0;

        for (Transaction transaction : dailyTransactions) {
            if (transaction.getAction().equals("Deposit")) {
                totalDeposits += transaction.getAmount();
            } else if (transaction.getAction().equals("Withdrawal")) {
                totalWithdrawals += transaction.getAmount();
            }
        }

        report.append("Total Deposits: ₱").append(totalDeposits)
              .append("\n")
              .append("Total Withdrawals: ₱").append(totalWithdrawals)
              .append("\n");

        return report.toString();
    }

    public static String generateAccountActivityReport(Account account) {
        StringBuilder report = new StringBuilder();
        report.append("Account Activity Report for ")
              .append(account.getOwner().getName())
              .append("\n")
              .append("Account Number: ").append(account.getAccountNumber())
              .append("\n");

        List<Transaction> transactions = account.getHistory().getHistoryList();

        for (Transaction transaction : transactions) {
            report.append(transaction.getDate())
                  .append(" - ")
                  .append(transaction.getAction())
                  .append(": ₱").append(transaction.getAmount())
                  .append("\n");
        }

        return report.toString();
    }

    public static String generateLowBalanceAlertReport(List<Account> accounts, double threshold) {
        StringBuilder report = new StringBuilder();
        report.append("Low Balance Alert Report\n");

        for (Account account : accounts) {
            if (account.getBalance() < threshold) {
                report.append("Account Number: ").append(account.getAccountNumber())
                      .append(" | Holder: ").append(account.getOwner().getName())
                      .append(" | Balance: ₱").append(account.getBalance())
                      .append("\n");
            }
        }

        return report.toString();
    }

}
