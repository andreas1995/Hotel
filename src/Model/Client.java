package Model;

import java.util.Date;

public class Client {
	/*client class that represents the client, containing his/hers personal info */
	private String name;
	private String creditCardNumber;
	private Date creditCardExpDate;
	private String phoneNumber;
	private String IDNumber;
	private String address;
	
	
	public Client (String name,String IDNumber, String creditCardNumber,Date creditCardExpDate, String phoneNumber,String address) {
		this.name = name;
		this.IDNumber = IDNumber;
		this.creditCardNumber = creditCardNumber;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.creditCardExpDate =creditCardExpDate;
	
	}

	
	/*setters*/
	public void setName(String name) {
		this.name = name;
	}
	public void setIDNumber(String id) {
		this.IDNumber = id;
	}
	public void setCreditCardNum(String crn) {
		this.creditCardNumber = crn;
	}
	public void setCreditCardExpDate(Date creditCardExpDate) {
		this.creditCardExpDate =creditCardExpDate;
	}
	public void setPhoneNumber(String pnum) {
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
	public String getCreditCardNum() {
		return this.creditCardNumber;
	} 
	public Date getCreditCardExpDate() {
		return this.creditCardExpDate;
	}
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public String getAddress() {
		return this.address;
	} 
	
	
	
	
	
	
	
	
	
}
