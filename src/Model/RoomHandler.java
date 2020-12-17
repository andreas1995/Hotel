package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoomHandler {
private Database sq;

/*this class is responsible for handling and retrieving rooms and rooms lists, everything that needs to be 
 * calculated for a room is in here*/

	public RoomHandler() {
		this.sq = new Database();
	}
	
		
/*return how many beds are placed in the room*/
		public int getNumOfBeds(String bedType) {
		 switch (bedType) {
		 case "Single" :  return 1;
         case "Double":  return 2;
         case "Double + Single":  return 3;
	      }
				return 0;
			
		}
		/*return how room size*/
		public int getRoomSize(String roomType) {
			switch (roomType) {
			 case "Small" :  return 20;
	         case "Medium":  return 35;
	         case "Suite":   return 50;
	       
		      }
			return 0;		
		}
		
		
		
		
		
		
/*get a list with all the rooms*/
	  public ObservableList<Room> getRooms() throws Exception {
	        ObservableList<Room> data = FXCollections.observableArrayList();
	        Connection con = sq.getConnection();
	        PreparedStatement pre = con.prepareStatement("SELECT * FROM Room");
	        ResultSet rs = pre.executeQuery();
	        while (rs.next()) {
	            data.add(new Room(rs.getString("RoomID"), rs.getInt("Price"), rs.getInt("RoomSize"), rs.getInt("NumOfBeds"), rs.getString("Location"), rs.getBoolean("RoomView"), rs.getBoolean("Smoking"), rs.getBoolean("Adjoint"), rs.getString("AdjointRoomID")));
	        }
	        return data;
	    }
/*get a specific room for each reservation*/	
	  public Room getTheRoom(Reservation res) throws Exception {
	        Connection con = sq.getConnection();
	        Room room = null;

	        PreparedStatement pre = con.prepareStatement("SELECT * FROM Room WHERE RoomID = '" + res.getRoom() + "'");
	        ResultSet rs = pre.executeQuery();
	        while (rs.next()) {

	            room = new Room(rs.getString("RoomID"), rs.getInt("Price"), rs.getInt("RoomSize"), rs.getInt("NumOfBeds"), rs.getString("Location"), rs.getBoolean("RoomView"), rs.getBoolean("Smoking"), rs.getBoolean("Adjoint"), rs.getString("AdjointRoomID"));
	        }
	        rs.close();
	        con.close();

	        return room;
	    }
	/*when we create a room we check if it already exists in the database first */
	  public Room checkIfRoomExists(String idNumber) throws Exception {
			Connection con = sq.getConnection();

	        PreparedStatement pre = con.prepareStatement("SELECT * FROM Room WHERE RoomID = '" + idNumber + "'  ");
	        ResultSet rs = pre.executeQuery();
	        if (rs.next()) {
	         return new Room(rs.getString("RoomID"), rs.getInt("Price"), rs.getInt("RoomSize"), rs.getInt("NumOfBeds"), rs.getString("Location"), rs.getBoolean("RoomView"), rs.getBoolean("Smoking"), rs.getBoolean("Adjoint"), rs.getString("AdjointRoomID"));
	        }
	        rs.close();
	        con.close();

			return null;
		}
	  /*find the adjoined room */
	  public Room adjoinedFind(Room room,ObservableList<Room> data) {
	    	Room returnRoom = null;
	    	for(Room adRoom : data) {
	    	if(room.getAdjoindsRoomID().equals(adRoom.getRoomID())) {
	    			returnRoom = adRoom ;
	    		}
	    	}	
	    	return returnRoom;
	    }
	
}
