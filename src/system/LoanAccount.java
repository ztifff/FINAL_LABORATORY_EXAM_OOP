package system;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
		notifyObservers();
		return true;
	}
	
	public List<Transaction> getTransactionsBetween(LocalDate startDate, LocalDate endDate, Account account) {
	    List<Transaction> allTransactions = account.getHistory().getHistoryList();
	    List<Transaction> filtered = new ArrayList<>();

	    for (Transaction t : allTransactions) {
	        LocalDate date = t.getDate();
	        if ((date.isEqual(startDate) || date.isAfter(startDate)) &&
	            (date.isEqual(endDate) || date.isBefore(endDate))) {
	            filtered.add(t);
	        }
	    }

	    return filtered;
	}

	@Override
	public String getAccountType() {
		return "Loan";
	}

}
