package observer;

import model.Account;
import model.CheckingAccount;
import model.LoanAccount;
import model.SavingsAccount;
import model.Transaction;
import service.Notification;

public class LowBalanceNotifier implements AccountObserver {

    private static final double LOW_BALANCE_THRESHOLD = 1000.0;  // For Savings/Checking
    private static final double HIGH_LOAN_BALANCE_THRESHOLD = 50000.0; // Set based on your policy

    @Override
    public void update(Account account) {
        // Low balance for Savings or Checking
    	String transaction_1 = null;
		for (Transaction transaction1 : account.getHistory().getHistoryList()) {
			transaction_1 = transaction1.getId();
		}
        if ((account instanceof SavingsAccount || account instanceof CheckingAccount)
                && account.getBalance() < LOW_BALANCE_THRESHOLD) {
            
            // Add notification instead of JOptionPane
            Notification notification = new Notification(
                "Low Balance Warning", 
                "⚠️ Balance below ₱1000: ₱" + account.getBalance(),
                java.time.LocalDate.now().toString(), transaction_1
            );
            account.addNotification(notification);  
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
                    java.time.LocalDate.now().toString(), transaction_1
                );
                account.addNotification(notification);  
            }
        }
    }
}
