package model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private String name;
	private String dob;
	private String contactNumber;
	private String email;
	private String address;
	private String password;
	private List<Account> accounts;
	
	public Customer(String name, String dob, String contactNumber, String email, String address, String password) {
		
		this.name = name;
		this.setDob(dob);
		this. contactNumber = contactNumber;
		this.email = email;
		this.address = address;
		this.password = password;
		this.accounts = new ArrayList<>();
	}
	
	public void addAccount(Account account) {
        accounts.add(account);
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

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
	
	

}
