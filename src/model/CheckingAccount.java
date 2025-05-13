package model;

import javax.swing.JOptionPane;
import data.Bank;

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
		} else if (amount <= balance + OVERDRAFT_LIMIT - currentOverdraft) {
			double overdraftNeeded = amount - balance;
			currentOverdraft += overdraftNeeded;

			super.withdraw(balance); // Deplete balance
			getHistory().addTransaction(new Transaction("Overdraft Loan", overdraftNeeded, java.time.LocalDate.now()));

			JOptionPane.showMessageDialog(null, "Overdraft used! You borrowed: ₱" + overdraftNeeded,
					"Overdraft Notice", JOptionPane.INFORMATION_MESSAGE);

			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Insufficient funds and overdraft limit exceeded.",
					"Overdraft Denied", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	@Override
	public boolean deposit(double amount) {
		if (amount <= 0) return false;

		if (currentOverdraft > 0) {
			if (amount >= currentOverdraft) {
				double remaining = amount - currentOverdraft;
				getHistory().addTransaction(new Transaction("Overdraft Repaid", currentOverdraft, java.time.LocalDate.now()));
				currentOverdraft = 0;
				super.deposit(remaining); // Deposit remainder
			} else {
				currentOverdraft -= amount;
				getHistory().addTransaction(new Transaction("Partial Overdraft Repayment", amount, java.time.LocalDate.now()));
				// Do not update actual balance
			}
		} else {
			super.deposit(amount); // Normal deposit
		}
		return true;
	}

	public void showRemainingOverdraft() {
		double remainingOverdraft = OVERDRAFT_LIMIT - currentOverdraft;
		JOptionPane.showMessageDialog(null, "You can still borrow ₱" + remainingOverdraft + " via overdraft.",
				"Remaining Overdraft Limit", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public String getAccountType() {
		return "Checking";
	}
}
