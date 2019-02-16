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
	private ToggleGroup group = new ToggleGroup();
	private String store_radio_val = "initialize";
	
	//ChoiceBox var
	@FXML
    private ChoiceBox codemachine_choicebox;
	
	ObservableList<Table_View> table_view_list = FXCollections.observableArrayList();
	Date date_ob = null;
	LocalDate date_ = null;
	private DB db = new DB();
	private PreparedStatement ps = null;
	
//Constructor
//funcions 
    // this function used to initialize my variables
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	  long t0 = System.nanoTime();
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
	  
	  //initialize of store_radio_val by Banzeeen
	  gas_radiobtn.setSelected(true);
	  store_radio_val = "»‰“Ì‰";
	}//end initialize variables
	
	//Save func
	@FXML
	public void saveData(){
	 con_db_savedata = db.getConnection_F_DB();
	 date_ = dateexchange_datepicker.getValue();
	 date_ob = new Date((date_.getYear()-1900), date_.getMonthValue()-1,date_.getDayOfMonth());
	 try{
        //Step 2.B: Creating JDBC PreparedStatement class 
		ps = con_db_savedata.prepareStatement("INSERT  INTO General_db (Nbon, Dateexchange, Typefuel, Quantitybon, Counter, Distance, Namedriver, Nnote, Nameresponsible, Codemachine)values(?,?,?,?,?,?,?,?,?,?)");
		ps.setLong(1, Integer.valueOf(nbon_txt.getText()));
		ps.setDate(2,Date.valueOf(date_));
		ps.setString(3, store_radio_val);
		ps.setLong(4, Integer.valueOf(quantitybon_txt.getText()));
		ps.setLong(5, Integer.valueOf(counter_txt.getText()));
		ps.setLong(6, Integer.valueOf(quantitybon_txt.getText()));
		ps.setString(7,namedriver_txt.getText());
		ps.setLong(8, Integer.valueOf(nnote_txt.getText()));
		ps.setString(9, nameresponsible_txt.getText());
		ps.setString(10, codemachine_txt.getText());
        // Step 2.C: Executing SQL 
		int result = ps.executeUpdate();
	    }catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {

            // Step 3: Closing database connection
            try {
                if(null != con_db_savedata) {

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
	
}