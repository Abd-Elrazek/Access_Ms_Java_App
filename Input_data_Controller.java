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

import java.time.LocalDate;
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
	
//Variables
    @FXML
    private TableView<Table_View> viewtable;
	@FXML
    private TableColumn<Table_View,Integer> serialn_col;
	@FXML
    private TableColumn<Table_View,Integer> nbon_col;
	@FXML
    private TableColumn<Table_View,Date> dateexchane_col;
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
	//ChoiceBox var
	@FXML
    private ChoiceBox codemachine_choicebox;
	
	ObservableList<Table_View> table_view_list = FXCollections.observableArrayList();
	
	private DB db = new DB();
	
	
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
		//System.out.println(rs.getInt("Serialn")+" "+rs.getDate("Dateexchange"));
		}
		System.out.printf("Database opened in %.3f seconds%n",((System.nanoTime()-t0)/1000000000.0));
		
	  }catch(SQLException e){
		e.printStackTrace();
	  }
      serialn_col.setCellValueFactory(new PropertyValueFactory<>("Serialn"));
      nbon_col.setCellValueFactory(new PropertyValueFactory<>("Nbon"));
     // dateexchane_col.setCellValueFactory(new PropertyValueFactory<>("Dateexchange"));
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
	  LocalDate date_ = dateexchange_datepicker.getValue();
	  
	 // db = new DB(Integer.valueOf(nbon_txt.getText()),date_,"ÓæáÇÑ",Integer.valueOf(quantitybon_txt.getText()), Integer.valueOf(counter_txt.getText()), Integer.valueOf(counter_txt.getText()),namedriver_txt.getText(),Integer.valueOf(nnote_txt.getText()),nameresponsible_txt.getText(),codemachine_txt.getText());
	  db.setNbon(33333);
	  db.setDateexchange(date_);  
	  db.setTypefuel("solar");
	  db.setQuantitybon(333334);
	  db.setCounter(343444);
	  db.setDistance(34342);
	  db.setNamedriver("ali");
	  db.setNnote(334);
	  db.setNameresponsible("mahmoud");
	  db.setCodemachine("33k");
	 
	 System.err.println("Selected date: " + date_);
	  System.out.println("date_ from db.getDateexchange --> " + db.getDateexchange());
	  boolean db_db = db.insertData();
	  System.out.println("db.insertData return --> " + db_db);  
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