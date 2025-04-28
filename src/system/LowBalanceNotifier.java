package system;

import javax.swing.JOptionPane;

public class LowBalanceNotifier {

    private static final double LOW_BALANCE_THRESHOLD = 1000.0; // You can adjust the threshold ₱

    public static void checkAndNotify(Account account) {
        if (account.getBalance() < LOW_BALANCE_THRESHOLD) {
            JOptionPane.showMessageDialog(null,
                    "Warning: Your account balance is low!\nCurrent Balance: ₱" + account.getBalance(),
                    "Low Balance Alert",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
