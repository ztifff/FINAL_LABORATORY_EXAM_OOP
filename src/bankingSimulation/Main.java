package bankingSimulation;

import gui.Login;
import system.Account;
import system.AccountFactory;
import system.BankLedger;
import system.Customer;

public class Main {

	public static void main(String[] args) {
		Login frame = new Login();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		Customer testCustomer = new Customer("sad", "2025/12/12", "09443434332", "sad@.com", "street", "tas");
		Account account = AccountFactory.createAccount("Checking", testCustomer);
		Account newAccount = AccountFactory.createAccount("Loan", testCustomer);
		account.deposit(2000);
		BankLedger bankLedger = BankLedger.getInstance();
        bankLedger.addAccount(account);
        testCustomer.addAccount(account);
        bankLedger.addAccount(newAccount);
        testCustomer.addAccount(newAccount); 
        
        Customer testCustomer11 = new Customer("wat", "2025/12/12", "09443434332", "sad@.com", "street", "yat");
		account = AccountFactory.createAccount("Checking", testCustomer11);
		account.deposit(2000);
		bankLedger = BankLedger.getInstance();
        bankLedger.addAccount(account);
        testCustomer11.addAccount(account);  
        
	}

}
