import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import javafx.beans.property.ObjectProperty;




import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.SelectionModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


import javafx.scene.effect.Blend;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.control.ToggleGroup;


import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Date;
import javafx.scene.Cursor;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import java.sql.SQLException;
//import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import org.controlsfx.control.Notifications;

public class Input_data_Controller implements Initializable{
	private Connection con_db = null;
	private Connection con_db_savedata = null;
	
//Variables
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
	//TextField vars
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
	//DatePicker var
	@FXML
	private DatePicker dateexchange_datepicker;
	//Radio Button vars
	@FXML
	private RadioButton gas_radiobtn;
	@FXML
	private RadioButton solar_radiobtn;
	private String store_radio_val = "initialize";
	
	//Codemachine vars
	@FXML
	private TextField codemachine_txt;
	@FXML
    private ChoiceBox codemachine_choicebox;
	//number and number's code of  machine
	private String codemachine_val = "initialize";
	//name of machine 
	private String nmachine = "";
	//number's machine
	//private String n_machine = "";
	
	ObservableList<Table_View> table_view_list ;
	//Date date_ob = null;
	LocalDate date_ = null;
	private DB db = new DB();
	private PreparedStatement ps = null;
	
//Constructor
public Input_data_Controller(){
  
	
}
//funcions 
    // this function used to initialize my variables
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	  table_view_list = FXCollections.observableArrayList();
	  
	  //initialize of store_radio_val by Banzeeen
	  gas_radiobtn.setSelected(true);
	  solar_radiobtn.setSelected(false);
	  store_radio_val = "»‰“Ì‰";
	  
	  // initialize of codemachine_val
	  Tooltip tip = new Tooltip(" «Œ — «”„ «·√·… ");
	  tip.setFont(new Font(14));
	  //codemachine_choicebox.setValue("choose name of machine");
	  codemachine_choicebox.setTooltip(tip); 
	  final SelectionModel<String> sm = codemachine_choicebox.getSelectionModel();
        sm.selectedItemProperty().addListener(new InvalidationListener() {
            @Override public void invalidated(Observable o) {
			    int index = sm.getSelectedIndex();
				String cmachine_ = sm.getSelectedItem();
               if (index == 0){
					System.out.println("item -> " + index);
			        nmachine =cmachine_ ;
				}else if (index == 1){
				    System.out.println("item -> " + index);
				    nmachine = cmachine_;
				}else{
				    System.out.println("item -> " + index);
				    nmachine = cmachine_;
				}
            }
        });
	  
	  //getTimeCurrent with nanoTime
	  long t0 = System.nanoTime();
	  
	  //get connection of this.DB
	  con_db = db.getConnection_F_DB();
	  try{ 
		ResultSet rs =con_db.createStatement().executeQuery("SELECT * FROM General_db");
		while(rs.next()){
		table_view_list.add(new Table_View(rs.getInt("Serialn"), rs.getInt("Nbon"),rs.getDate("Dateexchange"),rs.getString("Typefuel"),rs.getInt("Quantitybon"),rs.getInt("Counter"),rs.getInt("Distance"),rs.getString("Namedriver"),rs.getInt("Nnote"),rs.getString("Nameresponsible"),rs.getString("Codemachine")));
		}
		System.out.printf("Database opened in %.3f seconds%n",((System.nanoTime()-t0)/1000000000.0));
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
	}//end initialize variables
	
	//Save func
	@FXML
	public void saveData(){
	 Notification noti = new Notification();
	 noti.create().title("Error").text("Hellor World abdelrazek nageh").showWarning();
	 con_db_savedata = db.getConnection_F_DB();
	 date_ = dateexchange_datepicker.getValue();
	 // date_ob= new Date((date_.getYear()-1900), date_.getMonthValue()-1,date_.getDayOfMonth());
	 
	 //Validation of inputs vars
		String nbon_txt_ = nbon_txt.getText();
		String quantitybon_txt_ = quantitybon_txt.getText();
		String counter_txt_ = counter_txt.getText();
		String namedriver_txt_= namedriver_txt.getText();
		String nnote_txt_= nnote_txt.getText();
		String nameresponsible_txt_= nameresponsible_txt.getText();
		String codemachine_txt_= codemachine_txt.getText();
		int y = 0;
	    String formErrors[] = new String[6];
		System.out.println(formErrors.length);
		while (true){
			if (!nbon_txt_.matches("[0-9]+")){
			 formErrors[y] = " «·—Ã«¡ «œŒ«· —ﬁ„ «·»Ê‰";
			}
			
			if (quantitybon_txt_.matches("[0-9]+")){
				if (Integer.valueOf(quantitybon_txt_) > 254 || Integer.valueOf(quantitybon_txt_) < 0){
				if (y == 0){
				y = 0;
				}else{
				y = y + 1;
				}
				formErrors[y] = "«œŒ· «—ﬁ«„ ›ﬁÿ Ê«·«—ﬁ«„ „‰ 1 «·Ï 254";	
				}
			}else{
			y = y +1;
			formErrors[y] = "«œŒ· «—ﬁ«„ ›ﬁÿ Ê«·«—ﬁ«„ „‰ 1 «·Ï 254";
			}
			
			if (!gas_radiobtn.isSelected() && !solar_radiobtn.isSelected()){
			 y = y +1;
			 formErrors[y] = "«Œ — ‰Ê⁄ «·ÊﬁÊœ";
			}
			
			if (!counter_txt_.matches("[0-9]+")){
			 y = y +1;
			 formErrors[y] = "«œŒ· —ﬁ„ «·⁄œ«œ «—ﬁ«„ ›ﬁÿ";
			}
			
			if (!nnote_txt_.matches("[0-9]+")){
			 y = y +1;
			 formErrors[y] = " «œŒ· —ﬁ„ «·œ› — —ﬁ„ ›ﬁÿ ";
			}
			
			if (!codemachine_txt_.matches("[0-9]+")){
			 y = y +1;
			 formErrors[y] = "«œŒ· ﬂÊœ «·√·… «—ﬁ«„ ›ﬁÿ";
			}
			break;
		} 
		//Concatenation every of name of machine and its number of code 
		codemachine_val = nmachine +" "+codemachine_txt_+"ﬂ";
	 
	 try{
	    if (formErrors[0] == null && formErrors[1] == null && formErrors[2] == null && formErrors[3] == null && formErrors[4] == null && formErrors[5] == null){
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
			}else{
				for (int i = 0; i < formErrors.length; i++){
					System.out.println(formErrors[i]);	
					if (formErrors[i] == null){break;}
				}
		    }
	    }catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {

            //Closing database connection
            try {
                if( con_db_savedata != null && formErrors[0] == null && formErrors[1] == null && formErrors[2] == null && formErrors[3] == null && formErrors[4] == null && formErrors[5] == null) {

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
}