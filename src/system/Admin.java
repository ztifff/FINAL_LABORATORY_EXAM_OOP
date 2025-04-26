package system;

public class Admin {
	private String id;
	private static int idCount;
	private String name;
	private String password;
	
	public Admin(String name, String password) {
		id = String.format("%04d", ++idCount);
		this.name = name;
		this. password = password;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
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

	public void setContactNumber(String password) {
		this.password = password;
	}


}
