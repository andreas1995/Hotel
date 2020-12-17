package Controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import Model.Client;

import Model.Reservation;
import Model.ReservationHandler;
import Model.Room;
import Model.Database;
import View.Alerts;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ReserveController {
	/*this is the class where the user fills in the clients personal info and confirms that the 
	 * reservation will happen */

    @FXML
    private TextField idNumber,name,creditCardNo,addres,telNumber;

    @FXML
    private DatePicker CreditCardExpDate;
   
    @FXML
    private Label checkinLabel,checkOutLabel,numOfRoomsLabel;

    @FXML
    private ChoiceBox<Integer> noOfGuestsCheckB;

    @FXML
    private TableView<Room> tableList;
    
    @FXML
    private TableColumn<Room, String> roomNo,roomPrice;

    
    private Database database ;
    private ReservationHandler reservationH ;
    private ObservableList<Integer> maxGuests;
    private ObservableList<Room> list;
	private Date checkIn;
	private Date checkOut;
	private Alerts alert;
	private Reservation reservation;
    private DateFormat df;
	@FXML
    void cancelReserve(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
	//reserve
    @FXML
    void reserve(ActionEvent event) throws Exception {
   
    if(name.getText().isEmpty()||idNumber.getText().isEmpty()||creditCardNo.getText().isEmpty()||telNumber.getText().isEmpty()||addres.getText().isEmpty()||CreditCardExpDate.getValue() == null)
    	alert.reportError("Please fill all the text fields!");
    else {
    //if the client does not already exists in the database.. add him/her.
     if(reservationH.getClient(name.getText().toString()) == null) {
    	Client client = new Client(name.getText().toString(),idNumber.getText().toString(),creditCardNo.getText().toString(),Date.from(CreditCardExpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),telNumber.getText().toString(),addres.getText().toString());
        database.addClient(client);
    }
   
     //every room we book we make a new reservation for it. It makes it easier to manage reservations.
     //even for adjoined rooms.
    for(Room room : list) {
    	database.addReservation(reservation =  new Reservation(checkIn,checkOut,idNumber.getText().toString(),room.getRoomID(),noOfGuestsCheckB.getValue()));
        goToConfirm(reservation);
    }
    ((Node) (event.getSource())).getScene().getWindow().hide();
     }}
    
    
    //confirm
    public void goToConfirm(Reservation res) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ConfirmationWindow.fxml"));     
        Parent root = (Parent)fxmlLoader.load();
        ConfirmationController controller = fxmlLoader.<ConfirmationController>getController();
    	controller.reservationConfirm(res);
    	Scene scene = new Scene(root); 
        Stage primaryStage = new Stage();
        Image anotherIcon = new Image("logo.png");
        primaryStage.getIcons().add(anotherIcon);
        primaryStage.setTitle("Linnaeus Hotel");
        primaryStage.setScene(scene);
		primaryStage.show();
		
	}
    
    
    //setting the tables when we start the class
    public void setRooms(ObservableList<Room> roomsForReserve, Date startDate, Date endDate) {
    this.df = new SimpleDateFormat("dd/MM/yyyy");
    this.checkIn= startDate;
	this.checkOut = endDate;
	this.checkinLabel.setText(df.format(checkIn));
	this.checkOutLabel.setText(df.format(checkOut));
	this.database = new Database();
	this.reservationH = new ReservationHandler();
	this.maxGuests  = reservationH.getNumOfGuests(roomsForReserve);
	this.alert = new Alerts();
	this.list = roomsForReserve;
	
	
	this.roomNo.setCellValueFactory(new PropertyValueFactory<Room, String>("RoomID"));
	this.roomPrice.setCellValueFactory(new PropertyValueFactory<Room, String>("Price"));
	this.tableList.setItems(list);
	this.numOfRoomsLabel.setText(Integer.toString(list.size()));
	
	
		
	this.noOfGuestsCheckB.setItems(maxGuests);
	noOfGuestsCheckB.setValue(1);
    }

}
