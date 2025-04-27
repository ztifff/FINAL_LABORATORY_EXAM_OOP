package system;

import javax.swing.JOptionPane;

public class CheckingAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 500; 
    private double currentOverdraft = 0; 

    public CheckingAccount(Customer owner, String accountType, Bank bank) {
        super(owner, accountType, bank);
    }

    @Override
    public boolean withdraw(double amount) {
        double balance = getBalance();
        
        if (amount <= balance) {
            super.withdraw(amount);
            return true;
        } 
        // If the overdraft is still available
        else if (amount <= balance + OVERDRAFT_LIMIT - currentOverdraft) {
            double overdraftNeeded = amount - balance;
            currentOverdraft += overdraftNeeded; 
            
            super.withdraw(balance);
            
            
            getHistory().addTransaction(new Transaction("Overdraft Loan", overdraftNeeded, java.time.LocalDate.now()));
            
            
            JOptionPane.showMessageDialog(null, "Overdraft used! You borrowed: ₱" + overdraftNeeded,
                    "Overdraft Notice", JOptionPane.INFORMATION_MESSAGE);
            
            return true;
        } 
        
        else {
            JOptionPane.showMessageDialog(null, "Insufficient funds and overdraft limit exceeded.",
                    "Overdraft Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void showRemainingOverdraft() {
        double remainingOverdraft = OVERDRAFT_LIMIT - currentOverdraft;
        JOptionPane.showMessageDialog(null, "You can still borrow ₱" + remainingOverdraft + " via overdraft.",
                "Remaining Overdraft Limit", JOptionPane.INFORMATION_MESSAGE);
    }
}
