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
import javafx.stage.StageStyle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.time.LocalDate;
import javafx.scene.control.Tooltip;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Window;

public class Search_Controller implements Initializable{
//Variables
	//public Stage getSearchStage;
	private Stage ambulance_Model_Stage;
	@FXML
	private Button viewData;
	@FXML
	private ChoiceBox choicebox;
	@FXML 
	private CheckBox checkbox_gas;
	@FXML 
	private CheckBox checkbox_solar;
	@FXML
	private DatePicker from;
	@FXML
	private DatePicker to;
	@FXML
	private TextField nbon_text;
	@FXML
	private TextField nmachine_text;
	@FXML
	private TextField namedriver_text;
	@FXML
	private TextField nnote_text;
	@FXML 
	private TextField nameresponsible_text;
	String nbon_text_            ="";   
	String nnote_text_           ="";
	String nmachine_text_        ="";
	String namedriver_text_      ="";    
	String nameresponsible_text_ ="";
	LocalDate date_from = null;
    LocalDate date_to   = null;
	private String query = "SELECT * FROM General_db WHERE";
	private String name_machine = "";	
	private Sound sound = new Sound();
	private String codemachine_val = "";
    private Stage getOwnerStage;
    private String typefuel = "";
//Functions
    //initialize function
	@Override
    public void initialize(URL url, ResourceBundle rb) {
	   //event select from choicebox
	   Tooltip tip = new Tooltip(" «Œ — «”„ «·√·… ");
	   tip.setFont(new Font(14));
	   choicebox.setTooltip(tip); 
	   final SelectionModel<String> sm = choicebox.getSelectionModel();
		sm.selectedItemProperty().addListener(new InvalidationListener() {
			@Override public void invalidated(Observable o) {
				name_machine = sm.getSelectedItem();
				int index = sm.getSelectedIndex();
				System.out.println("item -> " + index + " : itmem -> "+ name_machine);
			}
		});
	}
	
	//function validation 
	public boolean valid(){
		boolean check_code = false;
		namedriver_text_     = namedriver_text.getText();
		nameresponsible_text_= nameresponsible_text.getText();
	    nbon_text_     = nbon_text.getText();
		nnote_text_    = nnote_text.getText();
		nmachine_text_ = nmachine_text.getText();
		date_from = from.getValue();
		date_to   = to.getValue();
		if (!nbon_text_.isEmpty()){
		    if(!nbon_text_.matches("[0-9]+")){
			    if (sound.getSAE() != null){
			        sound.getSAE().play();
				}
			    setAlert(AlertType.ERROR, "Œÿ√", "«·»Ê‰ «—ﬁ«„ ›ﬁÿ");
				return false;
			}	
		}
		
		
		if (!nnote_text_.isEmpty()){
		    if(!nnote_text_.matches("[0-9]+")){
			    if (sound.getSAE() != null){
			        sound.getSAE().play();
				}
			    setAlert(AlertType.ERROR, "Œÿ√", "«·œ› — «—ﬁ«„ ›ﬁÿ");
				return false;
			}	
		}
		
		if (!nmachine_text_.isEmpty() || !name_machine.isEmpty()){
		    if(!nmachine_text_.matches("[0-9]+")){
			    if (sound.getSAE() != null){
			        sound.getSAE().play();
				}
			    setAlert(AlertType.ERROR, "Œÿ√", "«·—Ã«¡ «Œ Ì«— ﬂÊœ «·√·… «—ﬁ«„ ›ﬁÿ");
				return false;
			}else if (name_machine.isEmpty()){
			    if (sound.getSAE() != null){
			        sound.getSAE().play();
				}
				setAlert(AlertType.ERROR, "Œÿ√", "«·—Ã«¡ «Œ Ì«— «”„ «·√·…");
				return false;
			}
			codemachine_val = name_machine +" "+nmachine_text_+"ﬂ";
			System.out.println("codemachine_val => "+ codemachine_val);
		}else{
		    check_code = true;
		}
		
		if (date_from != null || date_to != null){
		    if (date_from  == null){			    	
			    if (sound.getSAE() != null){
			        sound.getSAE().play();
				}
				setAlert(AlertType.ERROR, "Œÿ√", "Õœœ »œ«Ì… «· «—ÌŒ");
				return false;
			}else if (date_to == null){
			    if (sound.getSAE() != null){
			        sound.getSAE().play();
				}
				setAlert(AlertType.ERROR, "Œÿ√", "Õœœ ‰Â«Ì… «· «—ÌŒ");
				return false;
			}
		}
		
		if (date_from == null && date_to == null && check_code && namedriver_text_.isEmpty() &&  nameresponsible_text_.isEmpty() && nbon_text_.isEmpty() && nnote_text_.isEmpty() && !checkbox_gas.isSelected() && !checkbox_solar.isSelected()){
			if (sound.getSAE() != null ){
			   sound.getSAE().play();
			}
			setAlert(AlertType.ERROR, "Œÿ√","«·—Ã«¡ «Œ Ì«— ⁄‰’— ··»ÕÀ ⁄‰Â");
			return false;
		}
		
		
		return true;
	}
	//return String Query
	public String getQuery(){
	    while(true){
		    if (!nbon_text_.isEmpty()){
				if (query.equals("SELECT * FROM General_db WHERE")){
					query += " Nbon = " +nbon_text_; 
				}else{
					if (!query.contains("Nbon")){
						query += " AND Nbon = " + nbon_text_;	
					}
				}
			}
			
			if (!nameresponsible_text_.isEmpty()){
				if (query.equals("SELECT * FROM General_db WHERE")){
					query += " Nameresponsible = '" +nameresponsible_text_+"'"; 
				}else{
					if (!query.contains("Nameresponsible")){
						query += " AND Nameresponsible = '" + nameresponsible_text_+"'";	
					}
			    }
			}
			
			
            if (!codemachine_val.isEmpty()){
				if (query.equals("SELECT * FROM General_db WHERE")){
					query += " Codemachine = '" +codemachine_val+"'"; 
				}else{
					if (!query.contains("Codemachine")){
						query += " AND Codemachine = '" + codemachine_val+"'";	
					}
			    }
			}
			
			if (date_from != null && date_to != null){
					if (query.equals("SELECT * FROM General_db WHERE")){
					query += " Dateexchange BETWEEN #" +date_from+"# AND #" + date_to + "# "; 
				}else{
					if (!query.contains("Dateexchange")){
						query += " AND Dateexchange BETWEEN #" +date_from+"# AND #" + date_to + "# ";	
					}
			    }
			}
			
			
			if (!namedriver_text_.isEmpty()){
				if (query.equals("SELECT * FROM General_db WHERE")){
					query += " Namedriver = '" +namedriver_text_+"'"; 
				}else{
					if (!query.contains("Namedriver")){
						query += " AND Namedriver = '" + namedriver_text_+"'";	
					}
			    }
			}
			
			if (!nnote_text_.isEmpty()){
				if (query.equals("SELECT * FROM General_db WHERE")){
					query += " Namedriver = " +nnote_text_; 
				}else{
					if (!query.contains("Nnote")){
						query += " AND Nnote = " + nnote_text_;	
					}
			    }
			}
			
			if (checkbox_gas.isSelected()){
				if (query.equals("SELECT * FROM General_db WHERE")){
					query += " Typefuel = '»‰“Ì‰'"; 
				}else{
					if (!query.contains("Typefuel")){
						query += " AND Typefuel = '»‰“Ì‰'";	
					}
			    }
			    typefuel = "»‰“Ì‰";
			}
			
			if (checkbox_solar.isSelected()){
				if (query.equals("SELECT * FROM General_db WHERE")){
					query += " Typefuel = '”Ê·«—'"; 
				}else{
					if (!query.contains("Typefuel")){
						query += " AND Typefuel = '”Ê·«—'";	
					}
			    }
				typefuel = "”Ê·«—";
			}
			
			
			break;
		}
	   return query;
	}
	
	//This function that make show Ambulance_Model and hide SearchStage
	public void showAmbulanceModel(){
	    //if valid true execute 
		boolean check_valid = valid();
	    if (check_valid){
		    String month = "";
			String year = "";
		if (date_from != null){
			month = ""+date_from.getMonthValue();
			year  = ""+date_from.getYear();;			
		}
		System.out.println("vlid() => "+ check_valid);
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ambulance_Model.fxml"));
				AnchorPane root1 = (AnchorPane) fxmlLoader.load();
				Ambulance_Model_Controller controller=fxmlLoader.<Ambulance_Model_Controller>getController();
				controller.showTable(getQuery());
				controller.setLabel(codemachine_val,month, year,typefuel);
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UTILITY);
				stage.setTitle("‰„Ê“Ã «·«Œ—«Ã");
				stage.setResizable(false);
				stage.setScene(new Scene(root1));
				getOwnerStage = (Stage)viewData.getScene().getWindow(); 
				stage.initOwner(getOwnerStage);
				//stage.initModality(Modality.WINDOW_MODAL);
				stage.show();
				System.out.println("Owner => " + stage.getOwner());
			} catch(Exception e) {
				e.printStackTrace();
			}
		}else{
		    choicebox.setValue(null);
		    name_machine = "";
			//System.out.println(getQuery());
		    System.out.println("vlid() => "+ check_valid);
		}
		typefuel = "";
		codemachine_val = "";
        query = "SELECT * FROM General_db WHERE";
	}
	
	//func to set Alert (AlertType , title  , content)
	public void setAlert(Alert.AlertType alertType, String title , String Content){
		Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(Content);
		alert.showAndWait();
	}
	
}