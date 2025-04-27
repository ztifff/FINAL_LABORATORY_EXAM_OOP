package system;

import javax.swing.JOptionPane;

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
			JOptionPane.showMessageDialog(null, "Borrow amount must be positive.", "Invalid Borrow", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		this.loanBalance += amount;
		return true;
	}

	public boolean repayLoan(double amount) {
		if (amount <= 0) {
			JOptionPane.showMessageDialog(null, "Repayment amount must be positive.", "Invalid Repayment", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (amount > loanBalance) {
			JOptionPane.showMessageDialog(null, "Repayment exceeds loan balance.", "Repayment Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		loanBalance -= amount;
		return true;
	}

	@Override
	public boolean withdraw(double amount) {
		JOptionPane.showMessageDialog(null, "Withdrawals are not allowed from Loan Accounts.", 
				"Operation Not Allowed", JOptionPane.WARNING_MESSAGE);
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
