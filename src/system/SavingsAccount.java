package system;

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
            System.out.println("Savings account withdrawal limit exceeded.");
            return false;
        }
        return super.withdraw(amount);
    }
}
