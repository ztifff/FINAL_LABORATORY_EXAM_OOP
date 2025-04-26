package system;

import java.util.ArrayList;
import java.util.List;

public class Admin {
	private String id;
	private static int idCount;
	private String name;
	private String contactNumber;
	
	public Admin(String name, String contactNumber) {
		id = String.format("%04d", ++idCount);
		this.name = name;
		this. contactNumber = contactNumber;
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


}
