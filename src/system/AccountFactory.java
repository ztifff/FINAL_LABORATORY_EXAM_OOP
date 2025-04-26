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
    
 // In AccountFactory.java
    public static Account findAccountByNameAndPassword(String name, String password) {
        for (Account acc : accounts) {
            if (acc.getOwner().getName().equals(name) && acc.getOwner().getPassword().equals(password)) {
                return acc;
            }
        }
        return null; // Not found
    }
    
    public static Account findAdminAccountByNameAndPassword(String name, String password) {
    	Admin admin = new Admin("admin", "admin123");
    	Account adminAccount = new Account(admin);
        accounts.add(adminAccount);
        for (Account acc : accounts) {
            if (acc.getAdmin().getName().equals(name) && acc.getAdmin().getPassword().equals(password)) {
                return acc;
            }
        }
        return null; // Not found
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
