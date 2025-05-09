package system;

import java.time.LocalDate;

public class Transaction {
	private String action;
	private double amount;
	private LocalDate date;
	private Account account;

	public Transaction(String action, double amount, LocalDate date) {
		this.action = action;
		this.amount = amount;
		this.date = date;
	}
	
	public String getAction() {
		return action;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
        return date;
    }
	
	@Override
    public String toString() {
        return action + ": ₱" + amount;
    }
	
	public Account getAccount() {
		return account;
	}

}
