package observer;

import model.Account;

public interface AccountObserver {
    void update(Account account);
}
