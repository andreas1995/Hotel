package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReservationHandler {
	/*this class is responsible for handling and retrieving reservations and rooms lists, everything that needs to be 
	 * calculated for a reservation is in here*/
	private Database sq;
	
	
	public ReservationHandler() {
		this.sq = new Database();
	}
/*get all the reservations that have been made for a specific client*/
	public ObservableList<Reservation> getReservation(String clientID, ObservableList<Reservation> list) {
		ObservableList<Reservation> reservation = FXCollections.observableArrayList();
		for(Reservation n : list)
			if(n.getClient().contains(clientID))
				reservation.add(n);
		return reservation;
	}
	/*get all the coming reservations*/
	  public ObservableList<Reservation> getComingReservations() throws Exception {
	        ObservableList<Reservation> data = FXCollections.observableArrayList();
	        Connection con = sq.getConnection();
	        PreparedStatement pre = con.prepareStatement("SELECT * FROM Reservation WHERE CheckIn >= CURDATE()");
	        ResultSet rs = pre.executeQuery();
	        while (rs.next()) {

	        	Reservation res = new Reservation(rs.getDate("CheckIn"), rs.getDate("CheckOut"), rs.getString("ClientID"), rs.getString("RoomID"), rs.getInt("GuestsNumber"));
	            res.setReservationID(rs.getString("ReservationID"));
	        	data.add(res);       
	        	}
	        pre.close();
	        con.close();
	        return data;

	    }

/*get todays check in */
	    public ObservableList<Reservation> getTodayCheckIn() throws Exception {
	        ObservableList<Reservation> data = FXCollections.observableArrayList();
	        Connection con = sq.getConnection();
	        PreparedStatement pre = con.prepareStatement("SELECT * FROM Reservation WHERE CheckIn = CURDATE() AND checkedIn = 0");
	        ResultSet rs = pre.executeQuery();
	        while (rs.next()) {
	        	Reservation res = new Reservation(rs.getDate("CheckIn"), rs.getDate("CheckOut"), rs.getString("ClientID"), rs.getString("RoomID"),  rs.getInt("GuestsNumber"));
	            res.setReservationID(rs.getString("ReservationID"));
	        	data.add(res);
	        }//, rs.getString("ReservationID")

	        rs.close();
	        con.close();
	        return data;
	    }
/*get todays check outs*/
	    public  ObservableList<Reservation> getTodayCheckOut() throws Exception {
	        ObservableList<Reservation> data = FXCollections.observableArrayList();
	        Connection con = sq.getConnection();
	        PreparedStatement pre = con.prepareStatement("SELECT * FROM Reservation WHERE CheckOut = CURDATE() AND checkedOut = 0");
	        ResultSet rs = pre.executeQuery();
	     
	        while (rs.next()) {
	        	Reservation res = new Reservation(rs.getDate("CheckIn"), rs.getDate("CheckOut"), rs.getString("ClientID"), rs.getString("RoomID"),  rs.getInt("GuestsNumber"));
	            res.setReservationID(rs.getString("ReservationID"));
	        	data.add(res);
	        	
	        }//, rs.getString("ReservationID")
	        return data;
	    }
/*search for reservations that match with the given dates*/
	    protected ArrayList<Reservation> searchForDates(Date chIn, Date chOut) throws Exception {
	        String CheckIn = sq.convertDate(chIn);
	        String CheckOut = sq.convertDate(chOut);
	        System.out.println(CheckIn);
	        ArrayList<Reservation> data = new ArrayList<Reservation>();
	        Connection con = sq.getConnection();
	        PreparedStatement pre = con.prepareStatement("SELECT * FROM Reservation WHERE CheckIn >= '" + CheckIn + "' AND CheckOut <= '" + CheckOut + "'");
	        ResultSet rs = pre.executeQuery();
	        while (rs.next()) {
	        	Reservation res = new Reservation(rs.getDate("CheckIn"), rs.getDate("CheckOut"), rs.getString("ClientID"), rs.getString("RoomID"),rs.getInt("GuestsNumber"));
	            res.setReservationID(rs.getString("ReservationID"));
	        	data.add(res);
	        }
	        return data;
	    }
/*get the maximum number of guests that can fit in one reservation 
 * guests do not affect the price though*/
	  public ObservableList<Integer> getNumOfGuests(ObservableList<Room> list){
		  ObservableList<Integer>  maxGuests  = FXCollections.observableArrayList();
		  int maxNumOfGuests = 0;
			for(Room r : list) {
				maxNumOfGuests += r.getNumOfBed();
			}
			
			for(int i = 1;i <= maxNumOfGuests; i ++)
				maxGuests.add(i);
			
		return maxGuests;  
	  }
	    
	  /*get the client with the given id*/
	    public Client getClient(String clientID) throws Exception {
	        Connection con = sq.getConnection();
	        Client client = null;

	        PreparedStatement pre = con.prepareStatement("SELECT * FROM Client WHERE IDNumber = '" + clientID + "'  ");
	        ResultSet rs = pre.executeQuery();
	        while (rs.next()) {
	            
	            client = new Client(rs.getString("Name"), rs.getString("IDNumber"), rs.getString("CreditCardNumber"), rs.getDate("CreditCardExp"), rs.getString("PhoneNumber"), rs.getString("Address"));
	        }
	        rs.close();
	        con.close();

	        return client;
	    }

	
}
