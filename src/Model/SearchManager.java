package Model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SearchManager {
/*this class will compare all the rooms with the search choices make a room list out of it
 * then it would compare this room list with upcoming reservations to see if there are available rooms
 * it would create and return a list of the available rooms.*/
	private Database database;
	private ArrayList<Room> roomList;
	private ArrayList<Reservation> ColapingRess;
	private ReservationHandler reservationH ;
	private ArrayList<Room> NOTavailable;
	private ObservableList<Room> available;
	private boolean datesValid;
	private Date startDate;
	private Date endDate;

	public SearchManager (boolean managerSearch,String campusLoc, Date startDate, Date endDate, boolean view, boolean smoking, boolean adjoined, int numOfBeds, int RoomSize) throws Exception {
		 this.reservationH = new ReservationHandler();
		 this.database = new Database();
		 this.startDate = startDate;
		 this.endDate = endDate;
		 this.available = FXCollections.observableArrayList();
		 this.NOTavailable = new ArrayList<Room>();
		 
		 checkDates(startDate,endDate);
		 if(datesValid) {
	    if(!managerSearch) {
		this.roomList = getRoomChoices(campusLoc, view, smoking, adjoined, numOfBeds, RoomSize);
		//finding reservations that conflict with your dates  
		this.ColapingRess = reservationH.searchForDates(startDate,endDate);
		setNOTavailable();
		setAvailableRooms();
			 }
		 }
	}
	public void setSpecificRoom(Room room) throws Exception {
	   this.roomList = new ArrayList<Room>();
	   roomList.add(room);
	   this.ColapingRess = reservationH.searchForDates(startDate,endDate);
	   setNOTavailable();
	   setAvailableRooms();
	}
	
	
	
	public boolean datesAreCorrect() {
		return datesValid;
	}
	private void checkDates(Date startDate,Date endDate) {
		Date today = new Date();
		
		if(database.getDateDiff(startDate, today) > 0)
			this.datesValid = false;
		else if(database.getDateDiff(startDate, endDate) <= 0)
			this.datesValid = false;
		else if(database.getDateDiff(endDate, today) > 0)
			this.datesValid = false;
		else
			this.datesValid = true;
	}
	
	public String offerRoomToOtherCampus(String campusLocation) {
		if(campusLocation.equals("Kalmar"))
			return "Vaxjo";
		else
			return "Kalmar";
	}
	private void setNOTavailable() {
		for(Room r : roomList) {
			for(Reservation res : ColapingRess) {
				if(res.getRoom().equals(r.getRoomID()))
					NOTavailable.add(r);
			}
				
		}
	}
	private void setAvailableRooms(){
		
		for(Room r : roomList) {
			if(!NOTavailable.contains(r)) {
				available.add(r);
			}
		}
	}
	
	public ObservableList<Room> getAvailableRooms(){
		return this.available;
	}

	private ArrayList <Room> getRoomChoices (String campusLoc, boolean view, boolean smoking, boolean adjoined, int numOfBeds, int RoomSize) throws Exception {

		ArrayList<Room> data = new ArrayList<Room>();
	        Connection con = database.getConnection();
	        PreparedStatement pre;
	   if(adjoined)
		   pre = con.prepareStatement("SELECT * FROM Room WHERE Location='" + campusLoc + "' AND RoomView='" + database.getBoolean(view) + "'   AND Smoking='" + database.getBoolean(smoking) + "' AND Adjoint='" + database.getBoolean(adjoined) + "' AND NumOfBeds='" + numOfBeds + "' AND RoomSize='" + RoomSize + "'");
	   else
		  pre = con.prepareStatement("SELECT * FROM Room WHERE Location='" + campusLoc + "' AND RoomView='" + database.getBoolean(view) + "'   AND Smoking='" + database.getBoolean(smoking) + "' AND NumOfBeds='" + numOfBeds + "' AND RoomSize='" + RoomSize + "'");
	        
	   ResultSet rs = pre.executeQuery();
	        while (rs.next()) {

	            data.add(new Room(rs.getString("RoomID"), rs.getInt("Price"), rs.getInt("RoomSize"), rs.getInt("NumOfBeds"), rs.getString("Location"), rs.getBoolean("RoomView"), rs.getBoolean("Smoking"), rs.getBoolean("Adjoint"), rs.getString("AdjointRoomID")));
	        }
	        return data;
	    }

	
	
}
