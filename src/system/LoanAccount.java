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


	public void setLoanBalance(double loanBalance) {
		this.loanBalance = loanBalance;
	}

	public boolean borrow(double amount) {
		if (amount <= 0) {
			JOptionPane.showMessageDialog(null, "Borrow amount must be positive.", "Invalid Borrow", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		this.loanBalance += amount;
		
		getHistory().addTransaction(new Transaction("Loan Disbursement", amount, java.time.LocalDate.now()));
		notifyObservers();
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

		getHistory().addTransaction(new Transaction("Loan Repayment", amount, java.time.LocalDate.now()));
		
		// Add notification
	    Notification repaymentNotification = new Notification(
	        "Loan Repayment Received",
	        "Your loan account received a repayment of PHP " + String.format("%.2f", amount),
	        java.time.LocalDate.now().toString()
	    );
	    this.addNotification(repaymentNotification);
		notifyObservers();
		return true;
	}

	@Override
	public String getAccountType() {
		return "Loan";
	}

}
