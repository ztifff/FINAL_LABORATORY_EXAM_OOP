package system;

import javax.swing.JOptionPane;

public class SavingsAccount extends Account {

    public SavingsAccount(Customer owner, String accountType, Bank bank) {
        super(owner, accountType, bank);
    }

    @Override
    public boolean deposit(double amount) {
        return super.deposit(amount);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 10000) {
            JOptionPane.showMessageDialog(null, "Savings account withdrawal limit exceeded (₱10,000 max per withdrawal).", 
            		"Withdrawal Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return super.withdraw(amount);
    }
}
