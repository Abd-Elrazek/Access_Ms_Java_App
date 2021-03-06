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
	private Stage Search_stage;
	private Stage Injection_stage;
	@FXML
	private AnchorPane Input_data_Anch;
	@FXML
	private AnchorPane Search_Anch;	
	@FXML
	private AnchorPane Injection_Anchor;
	@FXML
	private Pane searchPane;
	@FXML 
	private Pane inputPane;
	@FXML 
	private Pane injection;
	
	private Stage  primaryStage;
	//Functions
	// this function used to initialize my variables
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
		
        // initialize my data emails in stage and scence and its url 
        Input_data_stage = new Stage();
        Search_stage = new Stage();
		Injection_stage = new Stage();
		
        Input_data_stage.setResizable(false);
        Input_data_stage.setTitle("����� ������ ��������");  
		Input_data_stage.initStyle(StageStyle.UTILITY);

		//Input_data_stage.initModality(Modality.NONE);
		
		Search_stage.setResizable(false);
        Search_stage.setTitle("���");
	    Search_stage.initStyle(StageStyle.UTILITY);

		//Search_stage.initModality(Modality.APPLICATION_MODAL);
		
		Injection_stage.setResizable(false);
        Injection_stage.setTitle("���");
	    Injection_stage.initStyle(StageStyle.UTILITY);

		Injection_stage.initModality(Modality.APPLICATION_MODAL);
        try {
            Input_data_Anch = FXMLLoader.load(getClass().getResource("Input_data.fxml"));
            Search_Anch = FXMLLoader.load(getClass().getResource("Search.fxml"));
			Injection_Anchor = FXMLLoader.load(getClass().getResource("Input_data_injection.fxml"));
			
            Input_data_stage.setScene(new Scene(Input_data_Anch));
            Search_stage.setScene(new Scene(Search_Anch));
			Injection_stage.setScene(new Scene(Injection_Anchor));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("here wrong .....");
        }
        
      
    } // end Function initialize 

    //Show Input_data
	public void showInputData(){
	    primaryStage = (Stage) inputPane.getScene().getWindow();
		if (Input_data_stage.getOwner()== null){
		    Input_data_stage.initOwner(primaryStage);
		}
	    Input_data_stage.show();
		Input_data_stage.toFront();
		inputPane.setCursor(Cursor.WAIT);
	}
	//Show Search_data
	public void showSearch(){
	    primaryStage = (Stage) searchPane.getScene().getWindow();
		if (Search_stage.getOwner() == null){
	        Search_stage.initOwner(primaryStage);	
		}
		Search_stage.show();
		Search_stage.toFront();
		//Input_data_stage.setIconified(true);
		searchPane.setCursor(Cursor.WAIT);
	}
	
	//Show Injection_data
	public void showInjection(){
	    primaryStage = (Stage) injection.getScene().getWindow();
		if (Injection_stage.getOwner() == null){
	    Injection_stage.initOwner(primaryStage);	
		}
		Injection_stage.show();
		Injection_stage.toFront();
		//Input_data_stage.setIconified(true);
		injection.setCursor(Cursor.WAIT);
	}
	
	
	
	//Change Cursor from WAIT To DEFAULT
	public void defaultCursor(){
		inputPane.setCursor(Cursor.DEFAULT);
		searchPane.setCursor(Cursor.DEFAULT);
		injection.setCursor(Cursor.DEFAULT);
    }
}