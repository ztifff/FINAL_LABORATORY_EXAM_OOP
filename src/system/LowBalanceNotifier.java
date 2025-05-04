package system;

import javax.swing.JOptionPane;

public class LowBalanceNotifier {

    private static final double LOW_BALANCE_THRESHOLD = 1000.0;  // For Savings/Checking
    private static final double HIGH_LOAN_BALANCE_THRESHOLD = 50000.0; // Set based on your policy

    public static void checkAndNotify(Account account) {
        // Low balance for Savings or Checking
        if ((account instanceof SavingsAccount || account instanceof CheckingAccount)
                && account.getBalance() < LOW_BALANCE_THRESHOLD) {
            JOptionPane.showMessageDialog(null,
                    "⚠️ Warning: Your account balance is low!\nCurrent Balance: ₱" + String.format("%.2f", account.getBalance()),
                    "Low Balance Alert",
                    JOptionPane.WARNING_MESSAGE);
        }

        // High loan balance alert
        if (account instanceof LoanAccount) {
            LoanAccount loanAccount = (LoanAccount) account;
            double loanBalance = loanAccount.getLoanBalance();

            if (loanBalance > HIGH_LOAN_BALANCE_THRESHOLD) {
                JOptionPane.showMessageDialog(null,
                        "⚠️ Alert: Your loan balance is high!\nCurrent Loan Balance: ₱" + String.format("%.2f", loanBalance),
                        "High Loan Balance Alert",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
