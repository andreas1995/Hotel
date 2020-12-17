package Controller;



import Model.Room;
import Model.RoomHandler;
import Model.SearchManager;
import View.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;





public class SearchRoomController{
   /*shows the rooms that are available for booking and the rooms that we choose to book*/

    @FXML
    private AnchorPane anchor;

   
    @FXML
    private ChoiceBox<String> roomSizeChoice,bedTypeChoice,campusLoc;
   

    @FXML
    private DatePicker checkIn,checkOut;

    @FXML
    private TextField managerSearch;
   
    @FXML
    private CheckBox smokingBox,adjointBox,viewBox;

    @FXML
    private TableView<Room> tabView;

    @FXML
    private TableColumn<Room, String> tabCol_Id;

    @FXML
    private TableColumn<Room, Integer> tabCol_Price,tabCol_Size,tabCol_Beds;

    @FXML
    private TableColumn<Room, String> tabCol_Location,tabCol_Availble;

    @FXML
    private Label roomDetails;

    @FXML
    private AnchorPane managers;
    
    @FXML
    private ListView<String> roomList;
    

    @FXML
    private AnchorPane afterSearch;
    
    private	 ObservableList<Room> data;
    private  Alerts alert ;
    private  boolean isManager;
    private  ObservableList<String> roomSize;
    private  ObservableList<String> bedsNumber;
    private  ObservableList<Room> roomsForReserve;
    private  ObservableList<String> campusLocation;
    private  RoomHandler roomH ;
    private  Room room;
    private  SearchManager searchManager;
    
	//proceed to reserve the chosen room/s
	@FXML
    public void reservebtn(ActionEvent event) throws IOException {
		if(roomsForReserve.isEmpty())
			alert.reportError("Please choose a room before proceeding to make a reservation!");
		else {
			resetBtn(event);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Reserve.fxml"));     
        Parent root = (Parent)fxmlLoader.load();
    	ReserveController controller = fxmlLoader.<ReserveController>getController();
    	controller.setRooms(roomsForReserve,convertToDate(checkIn.getValue()),convertToDate(checkOut.getValue()));
    	Scene scene = new Scene(root); 
        Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		}
	}
	
	

    @FXML
    public void resetBtn(ActionEvent event) throws IOException {
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
    
    //add room to the rooms that would be reserved
    @FXML
    void addRoomToList(ActionEvent event) {
    	
    	if(checkIn.getValue() == null || checkOut.getValue() == null) 
    		alert.reportError("Please fill choose check in and check out date!");
    	else{
    	room = tabView.getSelectionModel().getSelectedItem();
    	//method that checks the list finds the other adjoining room and adds it to the lists.
    	roomList.getItems().add(room.getRoomID());
    	roomsForReserve.add(room);
    	tabView.getItems().remove(room);
    	
    	if(adjointBox.isSelected()) {
    		Room adjoined = roomH.adjoinedFind(room,data);
    		roomList.getItems().add(adjoined.getRoomID());
        	roomsForReserve.add(adjoined);
        	//remove needs fixing
        	tabView.getItems().remove(adjoined);
    	}
    	afterSearch.setVisible(false);
    	}	
    }
    
    
    
   
    
	@FXML
    public void back(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Menu.fxml"));     
    	Parent root = (Parent)fxmlLoader.load();
    	MenuController controller = fxmlLoader.<MenuController>getController();
    	controller.start(isManager);
    	Scene scene = new Scene(root); 
        Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
	
	
	
	public void start(boolean isManager) {
		this.afterSearch.setVisible(false);
		this.roomSize = FXCollections.observableArrayList("Small", "Medium","Suite");
	    this.bedsNumber = FXCollections.observableArrayList("Single", "Double","Double + Single");
		this.campusLocation = FXCollections.observableArrayList("Vaxjo" , "Kalmar");
		this.alert = new Alerts();
		this.roomsForReserve  = FXCollections.observableArrayList();
		this.roomSizeChoice.setItems(roomSize);
		this.bedTypeChoice.setItems(bedsNumber);
		this.campusLoc.setItems(campusLocation);
		
	
		this.bedTypeChoice.setValue("Single");
		this.roomSizeChoice.setValue("Small");
		this.campusLoc.setValue("Vaxjo");
		this.roomH = new RoomHandler();
		this.isManager = isManager;
		this.managers.setVisible(isManager);
		//make it to appear only for the manager
		
		
		try {
		this.data = roomH.getRooms();
		} catch (Exception e) {
		e.printStackTrace();
		}

		setTable();
	}
	
	
	//search for room
	@FXML
    void searchForRoom(ActionEvent event) throws Exception {
		if(isManager && !managerSearch.getText().isEmpty())
			managerSpecificRoom();
		else {
			
		
		int numOfBeds = roomH.getNumOfBeds(bedTypeChoice.getValue());
		int RoomSize = roomH.getRoomSize(roomSizeChoice.getValue());
        

		searchManager = new SearchManager(false,campusLoc.getValue(), convertToDate(checkIn.getValue()),convertToDate(checkOut.getValue()), viewBox.isSelected(), smokingBox.isSelected(), adjointBox.isSelected(), numOfBeds, RoomSize);

		if(!searchManager.datesAreCorrect())
			alert.reportError("Please fill the dates properly!");
		else {
		 data = searchManager.getAvailableRooms();	

		 /*if the list that comes back for the campus that we search for is empty
		  * it will check on the other campus if there are available rooms and offer only if they
		  * are available..*/
        if(data.isEmpty()) {
        String otherCampus = searchManager.offerRoomToOtherCampus(campusLoc.getValue());
        searchManager = new SearchManager(false,otherCampus,convertToDate(checkIn.getValue()),convertToDate(checkOut.getValue()), viewBox.isSelected(), smokingBox.isSelected(), adjointBox.isSelected(), numOfBeds, RoomSize); 
     
        if(!searchManager.getAvailableRooms().isEmpty())
        	alert.reportInformation("There are no available rooms in "+campusLoc.getValue()+" but there are in " +otherCampus);
        data = searchManager.getAvailableRooms();	
        }
       
       setTable();
       allowAdd();
		}
	
	}
    }
	
	//add button only appears after we click search
	private void allowAdd() {
		this.afterSearch.setVisible(true);
	}
	
	//only appears for manager and it searches for a specific room
	private void managerSpecificRoom() throws Exception {
		Room specificRoom = roomH.checkIfRoomExists(managerSearch.getText());
		if(specificRoom == null) {
			alert.reportError("Room with this Room ID does not exists!");
		}
		else {
		searchManager = new SearchManager(true,campusLoc.getValue(), convertToDate(checkIn.getValue()),convertToDate(checkOut.getValue()), viewBox.isSelected(), smokingBox.isSelected(), adjointBox.isSelected(), 0, 0);
		searchManager.setSpecificRoom(specificRoom);
		data = searchManager.getAvailableRooms();
		setTable();
		allowAdd();
		}
	}
	
	
	
	
	private void setTable() {
		    tabCol_Id.setCellValueFactory(new PropertyValueFactory<Room, String>("RoomID"));
	        tabCol_Price.setCellValueFactory(new PropertyValueFactory<Room, Integer>("Price"));
	        tabCol_Size.setCellValueFactory(new PropertyValueFactory<Room, Integer>("RoomSize"));
	        tabCol_Beds.setCellValueFactory(new PropertyValueFactory<Room, Integer>("NumOfBed"));
	        tabCol_Location.setCellValueFactory(new PropertyValueFactory<Room, String>("Location"));
	        tabView.setItems(data);
	}
	
	private Date convertToDate(LocalDate locaDate) {
    	return Date.from(locaDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
   
}
