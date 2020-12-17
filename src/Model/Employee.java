package Model;

public class Employee {
	/*client class that represents the employee, containing his/hers personal info and if he/she is a manager */
	private String name;
	private String userName;
	private String IDNumber;
	private String password;
	private String address;
	private String phoneNumber;
	private boolean manager;

	public Employee (String name, String IDNumber, String userName, String password, String address, String phoneNumber, boolean manager) {
		this.name = name;
		this.IDNumber = IDNumber;
		this.userName = userName;

		this.password = password;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.manager = manager;
	}

	/*setters*/
	public void setName(String name) {
		this.name = name;
	}
	public void setIDNumber(String id) {
		this.IDNumber = id;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhoneNumber (String pnum) {
		this.phoneNumber = pnum;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	/*getters*/
	public String getName() {
		return this.name;
	}
	public String getIDNumber() {
		return this.IDNumber;
	}
	public String getUserName() {
		return this.userName;
	}
	public String getPassword() {
		return this.password;
	}

	public String getPhoneNumber ( ) {
		return this.phoneNumber;
	}
	public String getAddress() {
		return this.address;
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

