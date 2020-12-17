package Controller;


import View.Alerts;
import Model.Authentication;
import Model.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
/*belongs to the Authentication component, sends the user name and password to the authentication class*/

    @FXML
	private TextField UserName;

	@FXML
	private TextField Password;

    @FXML
    private Label WrongLogin;
    
    private Authentication authentication;
    private Alerts al;
    public void login(ActionEvent event) throws IOException {


        try {
			 this.authentication = new Authentication();
			 this.al = new Alerts();
			 Employee emp = authentication.authenticationControll(UserName.getText().toString(),Password.getText().toString());
			
			if(UserName.getText().isEmpty()||Password.getText().isEmpty())
				al.reportError("Please fill in user name and password!");
			else {
				/*if we get a null that means that the user name or password are not correct 
				 * or that the employee does not exist*/
			 if (emp == null)
                WrongLog();
            else if (emp.isManager())
                LogManager(event);
            else
                LogEmployee(event);
             }} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void WrongLog() throws IOException {
        UserName.setText("");
        Password.setText("");
        WrongLogin.setVisible(true);
    }

    public void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
        Stage primaryStage = new Stage();
        Image anotherIcon = new Image("logo.png");
        primaryStage.getIcons().add(anotherIcon);
        primaryStage.setTitle("Linnaeus Hotel");
        primaryStage.setScene(scene);
        primaryStage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
//when is an employee take him to the employee window
    private void LogEmployee(ActionEvent event) throws IOException {
        
       	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Menu.fxml"));     
       	Parent root = (Parent)fxmlLoader.load();
       	MenuController controller = fxmlLoader.<MenuController>getController();
       	controller.start(false);
       	Scene scene = new Scene(root); 
           Stage primaryStage = new Stage();
   		primaryStage.setScene(scene);
   		primaryStage.show();
           ((Node) (event.getSource())).getScene().getWindow().hide();

	}

  //when is an manager take him to the manager window
    private void LogManager(ActionEvent event) throws IOException {
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

	}


	

