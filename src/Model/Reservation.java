package Model;

import java.util.Date;

public class Reservation {
	/* class that represents any reservation, containing all reservation ifno*/
	private Date checkIn;
	private Date checkOut;
	private String clientID;
	private String roomID;
	private int totalPrice;
	private String ReservationID;
	private int guestNum;
	private boolean checkedIn;
	private boolean checkedOut;

	public Reservation(Date checkIn,Date checkOut, String clientID,String roomID, int guestNum) {
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.clientID =clientID;
		this.roomID = roomID;
		this.guestNum = guestNum;
		this.checkedIn = false;
		this.checkedOut = false;
	}
		
	
	/*setters*/
    public void setReservationID(String ReservationID) {
	this.ReservationID = ReservationID;
	}
	public void setCheckInDate(Date date) {
		this.checkIn = date;
	}
	
   public void setCheckoutDate(Date date) {
		this.checkOut = date;
	}
	public void setClient(String client) {
		this.clientID = client ;
	}
	public void setRoom(String room) {
		this.roomID = room;
	}
	
	public void setTotalPrice(int price) {
		this.totalPrice = price;
	}
	public void setGuestNumber(int guestNum) {
		this.guestNum = guestNum;
	}
	
	/*getters*/
	public Date getCheckInDate() {
		return this.checkIn;
	}
	public Date getCheckOutDate() {
		return this.checkOut;
	}
	public String getClient() {
		return this.clientID.toString();
	}
	public String getRoom() {
		return this.roomID.toString();
	}

	public int getTotalPrice() {
		return this.totalPrice;
	}
	public String getReservationID() {
		return this.ReservationID;
	}
	public int getGuestNum() {
		return this.guestNum; 
	}


	public boolean isCheckedIn() {
		return checkedIn;
	}


	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}


	public boolean isCheckedOut() {
		return checkedOut;
	}


	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}
	
	
}
