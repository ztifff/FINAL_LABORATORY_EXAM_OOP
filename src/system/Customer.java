package system;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private String id;
	private static int idCount;
	private String name;
	private String dob;
	private String contactNumber;
	private String email;
	private String address;
	private String password;
	private List<Account> accounts;
	
	public Customer(String name, String dob, String contactNumber, String email, String address, String password) {
		id = String.format("%04d", ++idCount);
		this.name = name;
		this.dob = dob;
		this. contactNumber = contactNumber;
		this.email = email;
		this.address = address;
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public String getPassword() {
		return password;
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
