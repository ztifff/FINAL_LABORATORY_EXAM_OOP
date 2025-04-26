package system;

import java.util.ArrayList;
import java.util.List;

public class AccountFactory {
	
	private static List<Account> accounts = new ArrayList<>();

    public static void registerAccount(Account account) {
        accounts.add(account);
    }

    public static List<Account> getAllAccounts() {
        return accounts;
    }
	
	
    public static Account findByAccountNumber(String accNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accNumber)) {
                return acc;
            }
        }
        return null;
    }
}
