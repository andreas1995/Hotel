package Controller;

import Model.Reservation;
import Model.ReservationHandler;
import Model.Database;
import View.Alerts;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MenuController {
    /*menu is where we can check in out client, go to edit reservation and search room 
     * is the main work window for user */
    @FXML
    private TableView<Reservation> CheckInTable;

    @FXML
    private TableColumn<Reservation, String> CIRoomNumber;

    @FXML
    private TableColumn<Reservation, String> CIGuestName;
    
    @FXML
    private TableColumn<Reservation, String> CICheckInDate;

    @FXML
    private TableColumn<Reservation, String> CICheckOutDate;



    @FXML
    private TableView<Reservation> CheckOutTable;

    @FXML
    private TableColumn<Reservation, String> CORoomNum;

    @FXML
    private TableColumn<Reservation, String> COGuestName;
    
    @FXML
    private TableColumn<Reservation, String> COCheckInDate;

    @FXML
    private TableColumn<Reservation, String> COCheckOutDate;
    
    @FXML
    private AnchorPane managerPane;
   
    private Alerts al;
    private LoginController lo;
    private ReservationHandler rl;
	private ObservableList<Reservation> checkInList;
    private ObservableList<Reservation> checkOutList;
    private boolean isManager;
    private Database sq;
    
    public void logout(ActionEvent event) throws IOException {
    	
    	if (al.responseAlert("Are you sure you want to log out?"))
    		lo.logout(event);
    	
    	
    }
//check the client in also send it to the database
	public void CheckIn(ActionEvent event) throws Exception {
		
        Reservation res = CheckInTable.getSelectionModel().getSelectedItem();
        res.setCheckedIn(true);
        sq = new Database();
		sq.editReservation(res);
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ConfirmationWindow.fxml"));     
        Parent root = (Parent)fxmlLoader.load();
    	ConfirmationController controller = fxmlLoader.<ConfirmationController>getController();
    	controller.setCheckIn(res);
    	Scene scene = new Scene(root); 
        Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.show();
		CheckInTable.getItems().remove(res);
		
	}
	//check out the client 
	public void CheckOut(ActionEvent event) throws Exception {
		Reservation res = CheckOutTable.getSelectionModel().getSelectedItem();
		res.setCheckedOut(true);
		sq = new Database();
		sq.editReservation(res);
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ConfirmationWindow.fxml"));     
        Parent root = (Parent)fxmlLoader.load();
    	ConfirmationController controller = fxmlLoader.<ConfirmationController>getController();
    	controller.setCheckOut(CheckOutTable.getSelectionModel().getSelectedItem());
    	Scene scene = new Scene(root); 
        Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.show();
		CheckOutTable.getItems().remove(res);
	}
	//go to search
	public void Search(ActionEvent event) throws IOException {
	
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/SearchRoom.fxml"));     
    	Parent root = (Parent)fxmlLoader.load();
    	SearchRoomController controller = fxmlLoader.<SearchRoomController>getController();
    	controller.start(isManager);
    	Scene scene = new Scene(root); 
        Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    public void EditReservations(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/View/EditReservation.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Image anotherIcon = new Image("logo.png");
		window.getIcons().add(anotherIcon);
		window.setTitle("Linnaeus Hotel");
		window.setScene(scene);
		window.show();
		
	}

    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Menu.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Image anotherIcon = new Image("logo.png");
		window.getIcons().add(anotherIcon);
		window.setTitle("Linnaeus Hotel");
		window.setScene(scene);
        window.show();
    }
    
    //only appears for a manager and it leads to the manager window
    @FXML
    void goToManager(ActionEvent event) throws IOException {
    	   Parent root = FXMLLoader.load(getClass().getResource("/View/ManagerWindow.fxml"));
   		   Scene scene = new Scene(root);
   		  scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
           Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
           Image anotherIcon = new Image("logo.png");
           window.getIcons().add(anotherIcon);
           window.setTitle("Linnaeus Hotel");
           window.setScene(scene);
           window.show();

    }
	
	public void start(boolean isManager) {
		this.isManager = isManager;
		this.al = new Alerts();
	    this.lo = new LoginController();
	    this.rl = new ReservationHandler();
	    this.managerPane.setVisible(isManager);
		 

		try {
			this.checkInList = rl.getTodayCheckIn();
			System.out.println(checkInList.size());
			this.checkOutList = rl.getTodayCheckOut();
			System.out.println(checkOutList.size());
        } catch (Exception e) {
			e.printStackTrace();
		}


		CICheckInDate.setCellValueFactory(new PropertyValueFactory<>("CheckInDate"));
		CICheckOutDate.setCellValueFactory(new PropertyValueFactory<Reservation, String>("CheckOutDate"));
		CIRoomNumber.setCellValueFactory(new PropertyValueFactory<Reservation, String>("Room"));
		CIGuestName.setCellValueFactory(new PropertyValueFactory<>("Client"));
		CheckInTable.setItems(checkInList);
        
		COCheckInDate.setCellValueFactory(new PropertyValueFactory<>("CheckInDate"));
		COCheckOutDate.setCellValueFactory(new PropertyValueFactory<Reservation, String>("CheckOutDate"));
		CORoomNum.setCellValueFactory(new PropertyValueFactory<Reservation, String>("Room"));
		COGuestName.setCellValueFactory(new PropertyValueFactory<>("Client"));
		CheckOutTable.setItems(checkOutList);

    }
	


}
