package system;

import java.util.UUID;

public class Admin {
	private String accountNumber;
	private String name;
	private String password;
	
	public Admin(String name, String password) {
		this.accountNumber = UUID.randomUUID().toString().substring(0, 8); 
		this.name = name;
		this. password = password;
	}
	

	public String getAccountNumber() {
		return accountNumber;
	}


	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContactNumber(String password) {
		this.password = password;
	}


}
