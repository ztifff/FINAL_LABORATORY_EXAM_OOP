package system;

public class CheckingAccount extends Account {

    public CheckingAccount(Customer owner) {
        super(owner);
    }

    @Override
    public boolean withdraw(double amount) {
        
        if (amount <= getBalance() + 500) {
            super.withdraw(amount);
            return true;
        } else {
            System.out.println("Overdraft limit reached.");
            return false;
        }
    }
}
