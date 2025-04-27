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
    
    public List<Transaction> getHistoryList() {
        return transactions;
    }

    public void printHistory() {
        System.out.println("Transaction History:");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

}
