package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Database {
/*belongs database component, this class stores all the data to MYSQL and manages the data inside it
 * it (rooms,reservations,employees,clients)*/
//Room Management__________________________________________________________________________________________

    public void addRoom(Room room) throws Exception {
        Connection con = getConnection();
    
        PreparedStatement pre = con.prepareStatement("INSERT INTO Room (RoomID,Price,RoomSize,NumOfBeds,Location,RoomView,Smoking,Adjoint,AdjointRoomID) "
                + " VALUES ('" + room.getRoomID() + "','" + room.getPrice() + "','" + room.getRoomSize() + "','" + room.getNumOfBed() + "','" + room.getLocation() + "','" + getBoolean(room.getView()) + "','" + getBoolean(room.getSmoking()) + "','" + getBoolean(room.getAdjoint()) + "','" + room.getAdjoindsRoomID() + "');");
        pre.executeUpdate();
        pre.close();
        con.close();
    }

  

    public void deleteRoom(Room room) throws Exception {
        Connection con = getConnection();
        PreparedStatement pre = con.prepareStatement("DELETE FROM Room WHERE RoomID = '" + room.getRoomID() + "'");
        pre.executeUpdate();
    }

    public void editRoom(Room room) throws Exception {
        Connection con = getConnection();
        PreparedStatement pre = con.prepareStatement("UPDATE Room SET Price='" + room.getPrice() + "', RoomSize='" + room.getRoomSize() + "',NumOfBeds='" + room.getNumOfBed() +
                "',Location='" + room.getLocation() + "', RoomView='" + getBoolean(room.getView()) + "', Smoking='" + getBoolean(room.getSmoking()) + //"',Adjoint'" + getBoolean(room.getAdjoint()) +  "',AdjointRoomID='" + room.getAdjoindsRoomID() +"' " +
                "' WHERE RoomID='" + room.getRoomID() + "';");
        pre.executeUpdate();
        pre.close();
        con.close();
    }
//____________________________________________________________________________________________


//Employee Management____________________________________________________________________________

    public void addEmployee(Employee eployee) throws Exception {
        Connection con = getConnection();
        PreparedStatement pre = con.prepareStatement("INSERT INTO Employee (Name,IDNumber,UserName,Password,Adrress,PhoneNumber,Manager) "
                + " VALUES ('" + eployee.getName() + "','" + eployee.getIDNumber() + "','" + eployee.getUserName() + "','" + eployee.getPassword() + "','" + eployee.getAddress() + "','" + eployee.getPhoneNumber() + "','" + getBoolean(eployee.isManager()) + "');");
        pre.executeUpdate();
        pre.close();
        con.close();
    }

    

    public void deleteEmployee(Employee employee) throws Exception {
        Connection con = getConnection();
        PreparedStatement pre = con.prepareStatement("DELETE FROM Employee WHERE UserName = '" + employee.getUserName() + "';");
        pre.executeUpdate();
    }

    public void editEmployee(Employee employee) throws Exception {
        Connection con = getConnection();
        PreparedStatement pre = con.prepareStatement("UPDATE Employee SET Name='" + employee.getName() + "', IDNumber='" + employee.getIDNumber() + "',UserName='" + employee.getUserName() + "', Password='" + employee.getPassword() + "'"
                + ",Adrress='" + employee.getAddress() + "', PhoneNumber='" + employee.getPhoneNumber() +/* "',Manager'" + getBoolean(employee.isManager()) +*/ "' "
                + "WHERE IDNumber='" + employee.getIDNumber() + "';");
        pre.executeUpdate();
        pre.close();
        con.close();
    }

//__________________________________________________________________________________________________
    //Client  management

    
    public void addClient(Client client) throws Exception {
        Connection con = getConnection();
        
        convertDate(client.getCreditCardExpDate());
        PreparedStatement pre = con.prepareStatement("INSERT INTO Client (Name,IDNumber,CreditCardNumber,CreditCardExp,PhoneNumber,Address) "
                + " VALUES ('" + client.getName() + "','" + client.getIDNumber() + "','" + client.getCreditCardNum() + "','" +  convertDate(client.getCreditCardExpDate()) + "','" + client.getPhoneNumber() + "','" + client.getAddress() + "');");
        pre.executeUpdate();
        pre.close();
        con.close();
    }



    public void editClient(Client client) throws Exception {
        Connection con = getConnection();
        PreparedStatement pre = con.prepareStatement("UPDATE Client SET Name='" + client.getName() + "', IDNumber='" + client.getIDNumber() + "',CreditCardNumber='" + client.getCreditCardNum() + "', 	CreditCardExp='" + client.getCreditCardExpDate() + "'"
                + " PhoneNumber='" + client.getPhoneNumber() + "' ,Adrress='" + client.getAddress() + "'"
                + "WHERE IDNumber='" + client.getIDNumber() + "';");
        pre.close();
        con.close();
    }
  
    
   
    
    
  //__________________________________________________________________________________________________
    //Reservation management
    
    public void addReservation(Reservation reservation) throws Exception {

        Connection con = getConnection();
        PreparedStatement pre = con.prepareStatement("INSERT INTO Reservation (CheckIn, CheckOut, ClientID, RoomID, GuestsNumber,checkedIn,checkedOut) "                                    
                + "  VALUES ('" + convertDate(reservation.getCheckInDate()) + "','" + convertDate(reservation.getCheckOutDate()) + "','" + reservation.getClient() + "','" + reservation.getRoom() + "','" + reservation.getGuestNum() + "','" + getBoolean(reservation.isCheckedIn()) + "','" + getBoolean(reservation.isCheckedOut()) + "');");
        pre.executeUpdate();
        pre.close();
        con.close();
    }
 

    public void deleteReservation(Reservation reservation) throws Exception {
        Connection con = getConnection();

        PreparedStatement pre = con.prepareStatement("DELETE FROM Reservation WHERE ReservationID = '" + reservation.getReservationID() + "';");
        pre.executeUpdate();
        con.close();
    }


    public void editReservation(Reservation reservation) throws Exception {
        Connection con = getConnection();
        
        PreparedStatement pre = con.prepareStatement("UPDATE Reservation SET CheckIn='" + convertDate(reservation.getCheckInDate()) + "', CheckOut='" + convertDate(reservation.getCheckOutDate()) + "',ClientID='" + reservation.getClient() + "', RoomID='" + reservation.getRoom() + "'"
                + ",checkedIn='" + getBoolean(reservation.isCheckedIn()) + "', checkedOut='" + getBoolean(reservation.isCheckedOut()) + "'  WHERE ReservationID='" + reservation.getReservationID() + "';");

        pre.executeUpdate();
        pre.close();
        con.close();
    }

  //get date difference
  	protected int getDateDiff(Date date1, Date date2) {
  		TimeUnit timeUnit = TimeUnit.DAYS;
  	    long diffInMillies = date2.getTime() - date1.getTime();
  	    return (int) timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
  	}
   

    protected String convertDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    protected int getBoolean(boolean bol) {
        int i = bol ? 1 : 0;
        return i;
    }


    //Connection________________________________________________________________________________________
    protected  Connection getConnection() throws Exception {
        try {
            //sql7.freesqldatabase.com
            //sql7235306
            //ed5j4AGc2a
            Connection con = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com/sql7235306", "sql7235306", "ed5j4AGc2a");
            System.out.println("Connected");
            return con;
        } catch (Exception e) {
            System.out.println(e);
        }


        return null;
    }

}