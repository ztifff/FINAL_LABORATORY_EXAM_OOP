package system;

public interface AccountSubject {
    void addObserver(AccountObserver observer);
    void removeObserver(AccountObserver observer);
    void notifyObservers();
}
