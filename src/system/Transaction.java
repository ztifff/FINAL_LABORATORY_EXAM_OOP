package system;

import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
	private final String id;
	private String action;
	private double amount;
	private LocalDate date;

	public Transaction(String action, double amount, LocalDate date) {
		this.id = UUID.randomUUID().toString().substring(0, 8); // 8-character ID
		this.action = action;
		this.amount = amount;
		this.date = date;
	}
	
	public String getId() {
        return id;
    }
	
	public String getAction() {
		return action;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
        return date;
    }
	
	@Override
    public String toString() {
        return action + ": ₱" + amount;
    }

}
