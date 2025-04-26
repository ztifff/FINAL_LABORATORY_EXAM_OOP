package system;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private String id;
	private static int idCount;
	private String name;
	private String contactNumber;
	private List<Account> accounts;
	
	public Customer(String name, String contactNumber) {
		id = String.format("%04d", ++idCount);
		this.name = name;
		this. contactNumber = contactNumber;
		this.accounts = new ArrayList<>();
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public List<Account> getAccount() {
		return accounts;
	}
	
	public void setAccount(Account account) {
		accounts.add(account);
	}
	
	

}
