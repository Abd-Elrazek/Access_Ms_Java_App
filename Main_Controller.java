import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import javafx.scene.Cursor;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class Main_Controller implements Initializable{
	
	//Variables
	private Stage Input_data_stage;
	public Stage Search_stage;
	@FXML
	private AnchorPane Input_data_Anch;
	@FXML
	private AnchorPane Search_Anch;
	@FXML
	private Pane searchPane;
	@FXML 
	private Pane inputPane;
	
	//Functions
	// this function used to initialize my variables
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
        // initialize my data emails in stage and scence and its url 
        Input_data_stage = new Stage();
        Search_stage = new Stage();
		
        Input_data_stage.setResizable(false);
        Input_data_stage.setTitle("«œŒ«· Ê Õ—Ì— «·»Ì«‰« ");  
		Input_data_stage.initStyle(StageStyle.UTILITY);
		//Input_data_stage.initModality(Modality.APPLICATION_MODAL);
		
		Search_stage.setResizable(false);
        Search_stage.setTitle("»ÕÀ");
	    Search_stage.initStyle(StageStyle.UTILITY);
        try {
            Input_data_Anch = FXMLLoader.load(getClass().getResource("Input_data.fxml"));
            Search_Anch = FXMLLoader.load(getClass().getResource("Search.fxml"));
			
            Input_data_stage.setScene(new Scene(Input_data_Anch));
            Search_stage.setScene(new Scene(Search_Anch));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("here wrong .....");
        }
        
      
    } // end Function initialize 

    //Show Input_data
	public void showInputData(){
	
	    Input_data_stage.show();
		inputPane.setCursor(Cursor.WAIT);
	}
	//Show Search_data
	public void showSearch(){
		Search_stage.show();
		searchPane.setCursor(Cursor.WAIT);
	}
	//Change Cursor from WAIT To DEFAULT
	public void defaultCursor(){
		inputPane.setCursor(Cursor.DEFAULT);
		searchPane.setCursor(Cursor.DEFAULT);
    }
}