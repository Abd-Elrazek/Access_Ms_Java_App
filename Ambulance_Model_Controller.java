import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class Ambulance_Model_Controller  implements Initializable{
//Variables
	@FXML
	private Button backBtn;
	private Stage getAmbulance_Model_StageByBtn;
	private AnchorPane searchAnch;
	private Stage getSearchStage;

	
//funcions 
    // this function used to initialize my variables
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		// try {
            // searchAnch = FXMLLoader.load(getClass().getResource("Search.fxml"));
            // getSearchStage.setScene(new Scene(searchAnch));
        // } catch (IOException e) {
            // e.printStackTrace();
            // System.out.println("here wrong .....");
        // }
	}//end initialize variables
	
	@FXML
	//showBackSearch
	public void showBackSearch(){
	 getSearchStage = new Stage();
	 getAmbulance_Model_StageByBtn = (Stage) backBtn.getScene().getWindow();
	 getAmbulance_Model_StageByBtn.close();
	 try{
	      searchAnch= FXMLLoader.load(getClass().getResource("Search.fxml"));
          getSearchStage.setScene(new Scene(searchAnch));
	      getSearchStage.show();
		}catch(IOException e){
		 e.printStackTrace();
		}
	}
}