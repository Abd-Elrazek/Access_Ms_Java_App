import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Tooltip;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Blend;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.beans.property.ObjectProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.util.Duration;
import java.io.IOException;
//import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

public class Input_data_Controller implements Initializable{

//Global Variables
    //this Connection for View table
	private Connection con_db = null;
	private Connection con_db_savedata = null;
	private PreparedStatement ps = null;
	private DB db = new DB();
	//list of Table_View class 
	ObservableList<Table_View> table_view_list ;
	
	//variables of columns in TableView(1) and TableColumn( 11 cols )
    @FXML
    private TableView<Table_View> viewtable;
	@FXML
    private TableColumn<Table_View,Integer> serialn_col;
	@FXML
    private TableColumn<Table_View,Integer> nbon_col;
	@FXML
    private TableColumn<Table_View,Date> dateexchange_col;
	@FXML
    private TableColumn<Table_View,String> typefuel_col;
	@FXML
    private TableColumn<Table_View,Integer> quantitybon_col;
	@FXML
    private TableColumn<Table_View,Integer> counter_col;
	@FXML
    private TableColumn<Table_View,Integer> distance_col;
	@FXML
    private TableColumn<Table_View,String> namedriver_col;
	@FXML
    private TableColumn<Table_View,Integer> nnote_col;
	@FXML
    private TableColumn<Table_View,String> nameresponsible_col;
	@FXML
    private TableColumn<Table_View,String> codemachine_col;
	
	//Declaration of TextField vars only
		@FXML
		private TextField nbon_txt;
		@FXML
		private TextField quantitybon_txt;
		@FXML
		private TextField counter_txt;
		@FXML
		private TextField namedriver_txt;
		@FXML
		private TextField nnote_txt;
		@FXML
		private TextField nameresponsible_txt;
		@FXML
		private TextField codemachine_txt;
		//DatePicker var
		@FXML
		private DatePicker dateexchange_datepicker;
		//Radio Button vars
		@FXML
		private RadioButton gas_radiobtn;
		@FXML
		private RadioButton solar_radiobtn;
		//Codemachine ChoiceBox
		@FXML
		private ChoiceBox codemachine_choicebox;
	//These vars for getText from TextField and initialize some of them
		String nbon_txt_           = "";
		String quantitybon_txt_    = "";
		String counter_txt_        = "";
		String namedriver_txt_     = "";
		String nnote_txt_          = "";
		String nameresponsible_txt_= "";
		String codemachine_txt_    = "";
		//initialize of value of radio option
		private String store_radio_val = "initialize";
		//initialize and Concatenation of name and number's code of  machine from choice_box and textfield
		private String codemachine_val = "initialize";
		//name of machine_ChoiceBox
		private String nmachine = "";
		//data_ receive of Object from DatePicker its type is LocalDate
		LocalDate date_ = null;
		//set formErrors length
		String formErrors[] = new String[11];
		String collectErrors = "";
		
		private TableViewSelectionModel<Table_View> selectedModel ;
//Constructor
public Input_data_Controller(){
  
	
}
//funcions 
    // this function used to initialize my variables
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	  //initialize of DatePicker 
	  dateexchange_datepicker.setPromptText(" «Œ — «· «—ÌŒ „‰ Â‰« ");
	  //initialize of store_radio_val by Banzeeen
	  gas_radiobtn.setSelected(true);
	  solar_radiobtn.setSelected(false);
	  store_radio_val = "»‰“Ì‰";
	  
	  //Event of nmachine'ChoiceBox
	  Tooltip tip = new Tooltip(" «Œ — «”„ «·√·… ");
	  tip.setFont(new Font(14));
	  codemachine_choicebox.setTooltip(tip); 
	  final SelectionModel<String> sm = codemachine_choicebox.getSelectionModel();
        sm.selectedItemProperty().addListener(new InvalidationListener() {
            @Override public void invalidated(Observable o) {
				nmachine = sm.getSelectedItem();
				int index = sm.getSelectedIndex();
				System.out.println("item -> " + index + " : itmem -> "+ nmachine);
            }
        });
		//End Event
		
		//initialize of TableViewSelectionModel
	   // selectedModel = viewtable.getSelectionModel();
	   
       /* 	   
		 //getSelectedItem with Event
		 selectedModel = viewtable.getSelectionModel();
		 InvalidationListener listener = new InvalidationListener(){
		    @Override public void invalidated(Observable o) {
               System.out.println("row clicked..");
			   setTextFG();
            }
		};
		selectedModel.selectedItemProperty().addListener(listener); 
		
		 */
		
		//First view data from database
		setViewTable();
		
	}//end initialize variables Func
	
	
	//Set and get validation of inputs
    public boolean getValidation(){
        //Validation of inputs vars
	    date_ = dateexchange_datepicker.getValue();
		nbon_txt_ = nbon_txt.getText();
		quantitybon_txt_ = quantitybon_txt.getText();
		counter_txt_ = counter_txt.getText();
		namedriver_txt_= namedriver_txt.getText();
		nnote_txt_= nnote_txt.getText();
		nameresponsible_txt_= nameresponsible_txt.getText();
		codemachine_txt_= codemachine_txt.getText();
		System.out.println("length of formErrors are -> "+formErrors.length);
		
		collectErrors = "";
		boolean valid = true;
		boolean no_distinct = true;
		while (true){
			if (!nbon_txt_.matches("[0-9]+")){
			 formErrors[0] = " «·—Ã«¡ «œŒ«· —ﬁ„ «·»Ê‰";
			 System.out.println("please inter number of nbon");
			 valid = false;
			}else{
			    try{
					long nbon_check = Integer.valueOf(nbon_txt_);
					long nbon_distict = 0;
					long retrive_serialn_of_distinct = 0;
					ResultSet rs =con_db_savedata.createStatement().executeQuery("SELECT Serialn, Nbon FROM General_db");
					while(rs.next()){
						retrive_serialn_of_distinct = rs.getLong("Serialn");
						nbon_distict = rs.getLong("Nbon");
						System.out.println("nbon_distict -> "+rs.getLong("Nbon"));
						if (nbon_distict == nbon_check){
						    String concat = "  «·»Ê‰ «·–Ï  Õ«Ê· «œŒ«·Â „ﬂ—— ›Ï «·„”·”· —ﬁ„  " + retrive_serialn_of_distinct;
					        setAlert(AlertType.INFORMATION, "Œÿ√ ›«œÕ","—«Ã⁄ «·»Ì«‰«  ÃÌœ«",concat);
							no_distinct = false;
						} 
						
					}
			    }catch(SQLException e){
			        e.printStackTrace();
			    }
				formErrors[0] = null;
			}
			
			if (quantitybon_txt_.matches("[0-9]+")){
				if (Integer.valueOf(quantitybon_txt_) > 254 || Integer.valueOf(quantitybon_txt_) < 0){
					
					formErrors[1] = "«·«—ﬁ«„  ﬂÊ‰ „‰ 1 «·Ï 254 ›Ï «·”⁄Â";
					valid = false;
					
				}else{
				    formErrors[1] = null;
				}
				 formErrors[2] = null;
			}else{
			    formErrors[2] = "«—ﬁ«„ ›ﬁÿ ›Ï Œ«‰Â «·”⁄Â ";
			    valid = false;
			}
			
			if (!gas_radiobtn.isSelected() && !solar_radiobtn.isSelected()){
			 
			 formErrors[3] = "«Œ — ‰Ê⁄ «·ÊﬁÊœ";
			 valid = false;
			}else{
			    formErrors[3] = null;
			}
			
			if (!counter_txt_.matches("[0-9]+")){
			 
			 formErrors[4] = "«œŒ· —ﬁ„ «·⁄œ«œ «—ﬁ«„ ›ﬁÿ";
			 valid = false;
			}else{
			  formErrors[4] = null;
			}

			
			if (!nnote_txt_.matches("[0-9]+")){
			 
			 formErrors[5] = " «œŒ· —ﬁ„ «·œ› — —ﬁ„ ›ﬁÿ ";
			 valid = false;
			}else{
			formErrors[5] = null;
			}
			
			if (!codemachine_txt_.matches("[0-9]+")){
			 formErrors[6] = "«œŒ· ﬂÊœ «·√·… «—ﬁ«„ ›ﬁÿ";
			 valid = false;		
			}else{
			    formErrors[6] = null;
			}
			
			if (namedriver_txt_.isEmpty()){
			 formErrors[7] = "«œŒ· «”„ «·”«∆ﬁ";
			 valid = false;
			}else{
			    formErrors[7] = null;
			}
			
			if(nameresponsible_txt_.isEmpty()){
			  formErrors[8] = "«œŒ· «”„ «·„”∆Ê·";	
			  valid = false;
			}else{
			    formErrors[8] = null;
			}
			
			if (date_ == null){
			  formErrors[9] = "«Œ — «· «—ÌŒ „‰ «·ﬁ«∆„Â";
			  valid = false;
			}else{
			    formErrors[9] = null;
			}
			if (nmachine.equals("")){
				formErrors[10] =  "«Œ — ‰Ê⁄ «·√·…";
				valid = false;
			}else{
			   formErrors[10] = null;
			}
			break;
		} 
		//Concatenation every of name of machine and its number of code 
		codemachine_val = nmachine +" "+codemachine_txt_+"ﬂ";
		if (valid && no_distinct){
			return true;
		}
		return false;
	}	
	
    
	//Save func
	@FXML
	public void saveData(){
	 con_db_savedata = db.getConnection_F_DB();
	 boolean getValid_Func = getValidation();
	 try{
	    if (getValid_Func){
			//Creating JDBC PreparedStatement class 
			ps = con_db_savedata.prepareStatement("INSERT  INTO General_db (Nbon, Dateexchange, Typefuel, Quantitybon, Counter, Distance, Namedriver, Nnote, Nameresponsible, Codemachine)values(?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, Integer.valueOf(nbon_txt_));
			ps.setDate(2,Date.valueOf(date_));
			ps.setString(3, store_radio_val);
			ps.setLong(4, Integer.valueOf(quantitybon_txt_));
			ps.setLong(5, Integer.valueOf(counter_txt_));
			ps.setLong(6, Integer.valueOf(counter_txt_)); // this for distance calculator instead of counter_txt_
			ps.setString(7,namedriver_txt_);
			ps.setLong(8, Integer.valueOf(nnote_txt_));
			ps.setString(9, nameresponsible_txt_);
			ps.setString(10, codemachine_val);//codemachine_txt.getText());
			//Executing SQL 
			int result = ps.executeUpdate();
			if (result != 0 ){
				System.out.println("result of ps.executeUpdate -> "  + result);
				setViewTable();
				viewtable.refresh();
				//setNotification here pass "info" 
				setNotification("Info","no content");
				//clear TextField
				clear();
			}
			}else if (!getValid_Func){
			    //initialize of collectErrors here too
			    collectErrors = "";
				for (int i = 0; i < formErrors.length; i++){
				    if (formErrors[i] != null){
					collectErrors += formErrors[i]+"      \n\n"; 
					}else{
					System.out.println(formErrors[i]);	
					}
				}
				//setNotification here pass "Error" 
				if (!collectErrors.isEmpty()){
				setNotification("Error",collectErrors);
				}
		    }
	    }catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {
            //setViewTable();
            //Closing database connection
            try {
                if( con_db_savedata != null && getValid_Func) {

                    // cleanup resources, once after processing
                    ps.close();
                    // and then finally close connection
                    con_db_savedata.close();
                }
            }catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
	}
	
	@FXML
	//Update func
	public void updateData(){
		
	}
	
	@FXML
	//Delete func
	public void deleteData(){
		
	}
	
	//View Table in TableView
	public void setViewTable(){
	  //getTimeCurrent with nanoTime
	  long t0 = System.nanoTime();
	  table_view_list = FXCollections.observableArrayList();
	  //get connection of this.DB
	  con_db = db.getConnection_F_DB();
	  try{ 
		ResultSet rs =con_db.createStatement().executeQuery("SELECT * FROM General_db");
		while(rs.next()){
		table_view_list.add(new Table_View(rs.getInt("Serialn"), rs.getInt("Nbon"),rs.getDate("Dateexchange"),rs.getString("Typefuel"),rs.getInt("Quantitybon"),rs.getInt("Counter"),rs.getInt("Distance"),rs.getString("Namedriver"),rs.getInt("Nnote"),rs.getString("Nameresponsible"),rs.getString("Codemachine")));
		}
		System.out.printf("Database opened in %.3f seconds%n",((System.nanoTime()-t0)/1000000000.0));
		//cut connect
		rs.close();
		con_db.close();
	  }catch(SQLException e){
		e.printStackTrace();
	  }
	  
      serialn_col.setCellValueFactory(new PropertyValueFactory<>("Serialn"));
      nbon_col.setCellValueFactory(new PropertyValueFactory<>("Nbon"));
	  dateexchange_col.setCellValueFactory(new PropertyValueFactory<>("Dateexchange"));
      typefuel_col.setCellValueFactory(new PropertyValueFactory<>("Typefuel"));
      quantitybon_col.setCellValueFactory(new PropertyValueFactory<>("Quantitybon"));
      counter_col.setCellValueFactory(new PropertyValueFactory<>("Counter"));
      distance_col.setCellValueFactory(new PropertyValueFactory<>("Distance"));
      namedriver_col.setCellValueFactory(new PropertyValueFactory<>("Namedriver"));
      nnote_col.setCellValueFactory(new PropertyValueFactory<>("Nnote"));
      nameresponsible_col.setCellValueFactory(new PropertyValueFactory<>("Nameresponsible"));
      codemachine_col.setCellValueFactory(new PropertyValueFactory<>("Codemachine"));
	  viewtable.setItems(table_view_list);
	}
	
	@FXML //this way isn't correct way , the correct way using by Group and toggle classes
	public void setSelectedRadioBtnSolar(){
		if (gas_radiobtn.isSelected()){
		    gas_radiobtn.setSelected(false);
			store_radio_val = "”Ê·«—";
	    }
	}
	
   @FXML //this way isn't correct way , the correct way using by Group and toggle classes
	public void setSelectedRadioBtnGas(){
		if (solar_radiobtn.isSelected()){
		    solar_radiobtn.setSelected(false);
			store_radio_val = "»‰“Ì‰";
	    }
	}
	//type either "Info" or "Error"
	public void setNotification(String type,String content){
		if (type.equals("Info")){
			Notifications notificationBuilder = Notifications.create()
			.title(" „ »‰Ã«Õ")
			.text(" „ «÷«›Â «·»Ì«‰«  »‰Ã«Õ")
			.graphic(new ImageView(new Image("/images/inserted.PNG")))
			.hideAfter(Duration.seconds(2))
			.position(Pos.BOTTOM_LEFT)
			.onAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent arg0) {
					System.out.println("Notification clicked on!");
				}
			});
			//notificationBuilder.owner(stageOfThis);
			notificationBuilder.show();
		}else if (type.equals("Error")){
			Notifications notificationBuilder = Notifications.create()
			.title("  «œŒ· «·»Ì«‰«  ’ÕÌÕÂ ")
			.text(content)
			.graphic(new ImageView(new Image("/images/Error1.PNG")))
			.hideAfter(Duration.seconds(15))
			.position(Pos.BOTTOM_LEFT)
			.onAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent arg0) {
					System.out.println("Notification clicked on!");
				}
			});
			//notificationBuilder.owner(stageOfThis);
			notificationBuilder.darkStyle();
			notificationBuilder.show();
		}
	}
	
	//func to set Alert (AlertType , title , header , content)
	public void setAlert(Alert.AlertType alertType, String title , String header , String Content){
		Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(Content);
        alert.showAndWait();
	}
	
	//setText from getSelectionModel
	public void setTextFG(){
	    selectedModel = viewtable.getSelectionModel();
		ObservableList<Table_View>  list_view = selectedModel.getSelectedItems();
		nbon_txt.setText(""+list_view.get(0).getNbon());
		Date date = list_view.get(0).getDateexchange();
		dateexchange_datepicker.setValue(date.toLocalDate());
		String type_fuel = list_view.get(0).getTypefuel().toString();
		if (type_fuel.equals("»‰“Ì‰")){
			gas_radiobtn.setSelected(true);
			solar_radiobtn.setSelected(false);
		}else if (type_fuel.equals("”Ê·«—")){
		    solar_radiobtn.setSelected(true);
			gas_radiobtn.setSelected(false);
 	    }
		quantitybon_txt.setText(""+list_view.get(0).getQuantitybon());
		counter_txt.setText(""+list_view.get(0).getCounter());
		namedriver_txt.setText(list_view.get(0).getNamedriver().toString());
		nnote_txt.setText(""+list_view.get(0).getNnote());
		nameresponsible_txt.setText(list_view.get(0).getNameresponsible().toString());
		String code = list_view.get(0).getCodemachine().toString();
		codemachine_txt.setText(code.replaceAll("[^0-9]", ""));
	}
	//clear for Input TextFields
	public void clear(){
        nbon_txt.clear();	
        quantitybon_txt.clear();	
        counter_txt.clear();	
        dateexchange_datepicker.setValue(null);	
        namedriver_txt.clear();	
        nameresponsible_txt.clear();	
        nnote_txt.clear();	
        codemachine_txt.clear();	
	}
}


