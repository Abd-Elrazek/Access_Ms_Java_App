import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import javafx.scene.Cursor;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class Search_Controller implements Initializable{
	//Variables
	@FXML 
	private AnchorPane ambulance_Model_Anch;
	public Stage getSearchStage;
	private Stage ambulance_Model_Stage;
	@FXML
	private Button viewData;
	
	//Functions
	// this function used to initialize my variables
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	    ambulance_Model_Stage = new Stage();
		ambulance_Model_Stage.setTitle("‰„Ê“Ã ”Ì«—… «·«”⁄«›");
		ambulance_Model_Stage.setResizable(false);
		try {
            ambulance_Model_Anch = FXMLLoader.load(getClass().getResource("Ambulance_Model.fxml"));
            ambulance_Model_Stage.setScene(new Scene(ambulance_Model_Anch));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("here wrong .....");
        }
	}//end initialize variables
	
	//show Ambulance_Model
	public void showAmbulanceModel(){
	    getSearchStage = (Stage) viewData.getScene().getWindow();
		ambulance_Model_Stage.show();
		getSearchStage.hide();
		
	}
}