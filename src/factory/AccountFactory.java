package factory;

import data.Bank;
import data.BankLedger;
import model.Account;
import model.CheckingAccount;
import model.Customer;
import model.LoanAccount;
import model.SavingsAccount;

public class AccountFactory {

    // Factory method for creating different types of accounts
    public static Account createAccount(String accountType, Customer owner) {
    	 Bank bank = BankLedger.getInstance().getBank();
        switch (accountType) {
            case "Checking":
                return new CheckingAccount(owner, accountType, bank);
            case "Savings":
                return new SavingsAccount(owner, accountType, bank);
            case "Loan":
                return new LoanAccount(owner, accountType, bank);
            default:
                throw new IllegalArgumentException("Invalid account type: " + accountType);
        }
    }
    
}
