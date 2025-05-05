package system;

import javax.swing.JOptionPane;

public class LowBalanceNotifier implements AccountObserver {

    private static final double LOW_BALANCE_THRESHOLD = 1000.0;  // For Savings/Checking
    private static final double HIGH_LOAN_BALANCE_THRESHOLD = 50000.0; // Set based on your policy

    @Override
    public void update(Account account) {
        // Low balance for Savings or Checking
        if ((account instanceof SavingsAccount || account instanceof CheckingAccount)
                && account.getBalance() < LOW_BALANCE_THRESHOLD) {
            
            // Add notification instead of JOptionPane
            Notification notification = new Notification(
                "Low Balance Warning", 
                "⚠️ Balance below ₱1000: ₱" + account.getBalance(),
                java.time.LocalDate.now().toString()
            );
            account.addNotification(notification);  // Add to BankLedger notifications
        }

        // High loan balance alert
        if (account instanceof LoanAccount) {
            LoanAccount loanAccount = (LoanAccount) account;
            double loanBalance = loanAccount.getLoanBalance();

            if (loanBalance > HIGH_LOAN_BALANCE_THRESHOLD) {
                
                // Add notification instead of JOptionPane
                Notification notification = new Notification(
                    "High Loan Balance Alert",
                    "⚠️ Your loan balance is high! Current Loan Balance: ₱" + String.format("%.2f", loanBalance),
                    java.time.LocalDate.now().toString()
                );
                account.addNotification(notification);  // Add to BankLedger notifications
            }
        }
    }
}
