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
		account.deposit(2000);
		BankLedger bankLedger = BankLedger.getInstance();
        bankLedger.addAccount(account);
	}

}
