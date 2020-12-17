package Controller;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import Model.Client;
import Model.Reservation;
import Model.ReservationHandler;
import Model.Database;
import View.Alerts;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class EditReservationController implements Initializable{
  /*this class handles the inputs for edit reservation*/
    @FXML
    private TextField idSearch;

    @FXML
    private TableView<Reservation> reservationsTable;

    @FXML
    private TableColumn<Reservation, String> reservationNo;

    @FXML
    private TableColumn<Reservation, String> roomID;

    @FXML
    private TableColumn<Reservation, String> guestID;

    @FXML
    private TableColumn<Reservation, String> checkOut;

    @FXML
    private TableColumn<Reservation, String> checkIn;

    @FXML
    private TextField ID;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField phoneNum;

    @FXML
    private TextField creditCardNum;

    @FXML
    private DatePicker creditCardExpDate;
    
    private  SearchRoomController searcRoomC;
    private  Database database;
	private  ObservableList<Reservation> list ;
	private  ReservationHandler resHandler;
	private  Alerts alert;
	

    @FXML
    void editReservation(ActionEvent event) throws Exception {
    
    Client client = resHandler.getClient(reservationsTable.getSelectionModel().getSelectedItem().getClient());
    		
    ID.setText(client.getIDNumber());
    name.setText(client.getName());
    address.setText(client.getAddress());
    phoneNum.setText(client.getPhoneNumber());
    creditCardNum.setText(client.getCreditCardNum());
    
 
    }
//go back 
    @FXML
    public void back(ActionEvent event) throws IOException {
        searcRoomC.back(event);
    }
    //delete reservation if u want to change the dates
    @FXML
    void cancelReservation(ActionEvent event) throws Exception {
    	
    	
    	if(alert.responseAlert("Are you sure you want to cancel this reservation!?")) {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ConfirmationWindow.fxml"));     
        Parent root = (Parent)fxmlLoader.load();
        database.deleteReservation(reservationsTable.getSelectionModel().getSelectedItem());
    	ConfirmationController controller = fxmlLoader.<ConfirmationController>getController();
        controller.setCancel(reservationsTable.getSelectionModel().getSelectedItem());
    	Scene scene = new Scene(root); 
        Stage primaryStage = new Stage();
        Image anotherIcon = new Image("logo.png");
        primaryStage.getIcons().add(anotherIcon);
        primaryStage.setTitle("Linnaeus Hotel");
        primaryStage.setScene(scene);
		primaryStage.show();
    	}
    }
    
 //save all changes
    @FXML
    void saveChanges(ActionEvent event) throws Exception {
    if(name.getText().isEmpty()||ID.getText().isEmpty()||creditCardNum.getText().isEmpty()||phoneNum.getText().isEmpty()||address.getText().isEmpty()||creditCardExpDate.getValue() == null)
    		alert.reportError("Please fill everything before saving!");
    else
    	{
    Client client = new Client(name.getText().toString(),ID.getText().toString(),creditCardNum.getText().toString(),Date.from(creditCardExpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),phoneNum.getText().toString(),address.getText().toString());
    database.editClient(client);
    alert.reportInformation("Changes have been made!");
    	}
    }
    
    
    //search for specific reservation using clients id 
    @FXML
    void search(ActionEvent event) {
    if(idSearch.getText().isEmpty())
    	alert.reportError("Please fill in with clients id before searching!");
    else {
    	this.list = resHandler.getReservation(idSearch.getText().toString(),list);
        reservationsTable.setItems(list);
    }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    this.database = new Database();
	    this.resHandler = new ReservationHandler();
	    this.searcRoomC = new SearchRoomController();
	    this.alert = new Alerts();
		
			try {
				this.list = resHandler.getComingReservations();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		reservationNo.setCellValueFactory(new PropertyValueFactory<Reservation, String>("ReservationID"));
		roomID.setCellValueFactory(new PropertyValueFactory<Reservation, String>("Room"));
		guestID.setCellValueFactory(new PropertyValueFactory<Reservation, String>("Client"));
		checkIn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("CheckInDate"));
		checkOut.setCellValueFactory(new PropertyValueFactory<Reservation, String>("CheckOutDate"));
		reservationsTable.setItems(list);
	}
	

   

}
