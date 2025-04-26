package system;

public class SavingsAccount extends Account {

    public SavingsAccount(Customer owner) {
        super(owner); // assuming Account class has a constructor that takes Customer
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        // You can add savings-specific logic here, like interest accrual
    }

    @Override
    public boolean withdraw(double amount) {
        // Example: savings might have a limit
        if (amount > 10000) {
            System.out.println("Savings account withdrawal limit exceeded.");
            return false;
        }
        return super.withdraw(amount);
    }
}
