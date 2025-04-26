package system;

public class Transaction {
	private String action;
	private double amount;
	
	public Transaction(String action, double amount) {
		this.action = action;
		this.amount = amount;
	}
	
	@Override
    public String toString() {
        return action + ": ₱" + amount;
    }

}
