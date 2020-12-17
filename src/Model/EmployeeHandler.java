package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeHandler {
	/*this class is responsible for handling and retrieving Employees and Employees lists, everything that needs to be 
	 * handled for an Employee is in here*/
   private Database sq;
    public EmployeeHandler() {
    	this.sq = new Database();
    }
    
	
	/*get a list with all the employees*/
	public ObservableList<Employee> getEmployyesList() throws Exception {
        ObservableList<Employee> data = FXCollections.observableArrayList();
        Connection con = sq.getConnection();
        PreparedStatement pre = con.prepareStatement("SELECT * FROM Employee");
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
           data.add(new Employee(rs.getString("Name"), rs.getString("IDNumber"), rs.getString("UserName"), rs.getString("Password"), rs.getString("Adrress"), rs.getString("PhoneNumber"), rs.getBoolean("Manager")));
        }
        return data;
    }

   /*check if someone with this id or user name already exists*/
	public boolean checkIfEmployeeExists(String idNumber,String userName) throws Exception {
		Connection con = sq.getConnection();

        PreparedStatement pre = con.prepareStatement("SELECT * FROM Employee WHERE IDNumber = '" + idNumber + "' AND userName = '" + userName + "' ");
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
         return false;
        }
        rs.close();
        con.close();

		return true;
	}
	
	
	
}
