package system;

import java.util.ArrayList;
import java.util.List;

public class Notification implements AccountObserver {
    private String title;
    private String message;
    private String date;
    private String transactionId; 
    private List<AccountObserver> observers = new ArrayList<>();  // Added to store observers

    public Notification(String title, String message, String date, String transactionId) {
        this.title = title;
        this.message = message;
        this.date = date;
        this.transactionId = transactionId;
    }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getDate() { return date; }
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    // Adds an observer to this notification
    public void addObserver(AccountObserver observer) {
        observers.add(observer);
    }

    // Removes an observer
    public void removeObserver(AccountObserver observer) {
        observers.remove(observer);
    }

    // Notifies all observers of the current state (optional, depending on the use case)
    @Override
    public void update(Account account) {
        // Here you can define what happens when an observer is updated
        System.out.println("Notification updated for account: " + account.getAccountNumber());
    }
}

