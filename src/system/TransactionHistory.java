package system;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
	private List<Transaction> transactions;

    public TransactionHistory() {
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
    
    public void removeTransaction(Transaction transaction) {
    	transactions.remove(transaction);
    }

    public Transaction findTransactionID(String transactionId) {
    	for (Transaction transaction : transactions) {
    		if (transaction.getId().equals(transactionId)) {
                return transaction;
            }
		}
        return null; 
    }
    
    public List<Transaction> getHistoryList() {
        return transactions;
    }
    


}
