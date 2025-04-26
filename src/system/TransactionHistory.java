package system;

import java.util.List;

public class TransactionHistory {
	private List<Transaction> transactions;
	
	
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	public void printHistory() {
		
	}

}
