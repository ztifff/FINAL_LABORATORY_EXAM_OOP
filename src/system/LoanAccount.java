package system;

public class LoanAccount extends Account {

    private double loanBalance;

    public LoanAccount(Customer owner, String accountType, Bank bank) {
        super(owner, accountType, bank);
        this.loanBalance = 0;
    }

    public double getLoanBalance() {
        return loanBalance;
    }

    public boolean borrow(double amount) {
        if (amount <= 0) {
            return false;
        }
        this.loanBalance += amount;
        return true;
    }

    public boolean repayLoan(double amount) {
        if (amount <= 0) {
            return false;
        }
        if (amount > loanBalance) {
            return false;
        }
        loanBalance -= amount;
        return true;
    }

    @Override
    public boolean withdraw(double amount) {
        return false;
    }

    @Override
    public boolean deposit(double amount) {
        if (amount > loanBalance) {
            amount -= loanBalance;
            loanBalance = 0;
        } else {
            loanBalance -= amount;
        }
        return true;
    }
}
