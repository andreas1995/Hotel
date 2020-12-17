package View;


import java.util.Optional;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Alerts {
	/*this class will be called when an alert needs to be showed the methods inside the class need a string
	 * that will print what we gave on the string inside the alert box. */
	public Alerts() {}

	
	//report errors
    public void reportError(String str) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setContentText(str);
    	alert.showAndWait();
    }
    //inform when something happens
    public void reportInformation(String str) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Error Dialog");
    	alert.setContentText(str);
    	alert.showAndWait();
    }
    //report what will happen if we proceed and wait for confirmation answer 
    public boolean responseAlert(String str) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setContentText(str);
		ButtonType yes = new ButtonType("Yes");
    	ButtonType no = new ButtonType("No");
    	alert.getButtonTypes().setAll(yes,no);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yes)
			return true;
		else
			return false;
	
	 }
    
}