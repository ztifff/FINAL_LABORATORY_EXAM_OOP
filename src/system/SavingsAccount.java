package system;

public class SavingsAccount extends Account {

    public SavingsAccount(Customer owner) {
        super(owner); 
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        
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
