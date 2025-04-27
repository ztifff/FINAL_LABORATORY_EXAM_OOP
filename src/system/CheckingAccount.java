package system;

public class CheckingAccount extends Account {

    public CheckingAccount(Customer owner, String accountType, Bank bank) {
        super(owner, accountType, bank);
    }

    @Override
    public boolean withdraw(double amount) {
        
        if (amount <= getBalance() + 500) {
            super.withdraw(amount);
            return true;
        } else {
            return false;
        }
    }
}
