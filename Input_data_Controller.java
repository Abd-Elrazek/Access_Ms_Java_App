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
import javafx.scene.control.ButtonType;
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

//import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.util.Duration;
import java.io.IOException;
//import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;
import java.net.URL;
import java.math.BigInteger;

public class Input_data_Controller implements Initializable{

//Global Variables
    //this Connection for View table
	private Connection con_db = null;
	private Connection con_db_savedata = null;
	private Connection con_db_update = null;
	private Connection con_db_delete = null;
	private DB db = new DB();
	private Sound sound = new Sound();
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
		private boolean check_update = true;
	    private boolean confirm_delete = false;
		private long Distance = 0;
		private boolean update_distance = false;
		//this Nbon for delete row from Injection_db 
		private long Nbon = 0;
		
		private TableViewSelectionModel<Table_View> selectedModel ;
//Constructor
public Input_data_Controller(){
  
	
}
//funcions 
    // this function used to initialize my variables
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	   // counter_txt.setPrefColumnCount(9);
	    // counter_txt.selectRange(0,8);
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
			}else {
			    if (check_update){
					try{ 
					    Connection con_temp = db.getConnection_F_DB();
						BigInteger nbon_big = new BigInteger(nbon_txt_);
						long nbon_check = nbon_big.longValue();
						long nbon_distict = 0;
						long retrive_serialn_of_distinct = 0;
						ResultSet rs =con_temp.createStatement().executeQuery("SELECT Serialn, Nbon FROM General_db");
						while(rs.next()){
							retrive_serialn_of_distinct = rs.getLong("Serialn");
							nbon_distict = rs.getLong("Nbon");
							System.out.println("nbon_distict -> "+rs.getLong("Nbon"));
							if (nbon_distict == nbon_check){
								String concat = "  «·»Ê‰ «·–Ï  Õ«Ê· «œŒ«·Â „ﬂ—— ›Ï «·„”·”· —ﬁ„  " + retrive_serialn_of_distinct;
								if (sound.getSNI() != null){
								    sound.getSAI().play();
								}
								setAlert(AlertType.INFORMATION, "Œÿ√ ›«œÕ","—«Ã⁄ «·»Ì«‰«  ÃÌœ«",concat);
								no_distinct = false;
							} 
							
						}
						rs.close();
						con_temp.close();
					}catch(SQLException e){
						e.printStackTrace();
					}
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
		    codemachine_val = nmachine +" "+codemachine_txt_+"ﬂ";
			// here is place of calcDistance which nmachine and codemachine_txt_ are valids
			//In Future if Want customize for car only check nmachine for ”Ì«—Â only
			if (!update_distance){
				if(!nmachine.isEmpty() && !codemachine_txt_.isEmpty()){
					calcDistance();
				}
		    }
			break;
		} 
		//Concatenation every of name of machine and its number of code 
		if (valid && no_distinct){
			return true;
		}
		return false;
	}	
	
    
	//Save func
	@FXML
	public void saveData(){
	 con_db_savedata = db.getConnection_F_DB();
	 PreparedStatement ps = null;
	 boolean getValid_Func = getValidation();
	 try{
	    if (getValid_Func){
		
		    BigInteger nbon_big = new BigInteger(nbon_txt_);
			long nbon_from_big = nbon_big.longValue();
			
			BigInteger conter_big = new BigInteger(counter_txt_);
			long counter_from_big = conter_big.longValue();
			
			BigInteger nnote_big = new BigInteger(nnote_txt_);
			long nnote_from_big = nnote_big.longValue();
			
			//Creating JDBC PreparedStatement class 
			ps = con_db_savedata.prepareStatement("INSERT  INTO General_db (Nbon, Dateexchange, Typefuel, Quantitybon, Counter, Distance, Namedriver, Nnote, Nameresponsible, Codemachine)values(?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, nbon_from_big);
			ps.setDate(2,Date.valueOf(date_));
			ps.setString(3, store_radio_val);
			ps.setLong(4, Integer.valueOf(quantitybon_txt_));
			ps.setLong(5, counter_from_big);
			ps.setLong(6, Distance); // this for distance calculator instead of counter_txt_
			ps.setString(7,namedriver_txt_);
			ps.setLong(8,nnote_from_big);
			ps.setString(9, nameresponsible_txt_);
			ps.setString(10, codemachine_val);//codemachine_txt.getText());
			//Executing SQL 
			int result = ps.executeUpdate();
			if (result != 0 ){
				System.out.println("result of ps.executeUpdate -> "  + result);
				setViewTable();
				//setNotification here pass "info" 
				if (sound.getSNI() != null){
				   sound.getSNI().play();
				}
				setNotification("Info","no content");
				System.out.println("Nobon before delete from injection_db is " + nbon_from_big);
				
				//delete row of nbon and nnote  from Injection_db where Nbon = ?
			    boolean result_ = con_db_savedata.createStatement().execute("delete from Injection_db where Nbon = " + nbon_from_big+";");
			    if (result_){
				    System.out.println("Nbon deleted from Injection_db correctly : " + nbon_from_big);
				}
				//clear TextField
				clear();
				//getNbonFrom Nnote in injection_db
				getDefaultNbon();
				//in the end after savedata correctly return Nbon var to zero 
				Nbon = 0;
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
				if(sound.getSNE() != null){
				    sound.getSNE().play();
				}
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
	 con_db_update = db.getConnection_F_DB();
	 check_update = false;
	 update_distance = true;
	 boolean getValid_Func = getValidation();
	 PreparedStatement ps_update = null;
	 try{
	    if (getValid_Func){
		    
			BigInteger nbon_big = new BigInteger(nbon_txt_);
			long nbon_from_big = nbon_big.longValue();
			
			BigInteger conter_big = new BigInteger(quantitybon_txt_);
			long counter_from_big = conter_big.longValue();
			
			BigInteger nnote_big = new BigInteger(nnote_txt_);
			long nnote_from_big = nnote_big.longValue();
			
			//Creating JDBC PreparedStatement class 
			ps_update = con_db_update.prepareStatement("UPDATE General_db SET Nbon = ?, Dateexchange = ?, Typefuel = ?, Quantitybon = ?, Counter = ?, Namedriver = ?, Nnote = ?, Nameresponsible = ?, Codemachine = ? WHERE Nbon = ?;");
			ps_update.setLong(1, nbon_from_big);
			ps_update.setDate(2,Date.valueOf(date_));
			ps_update.setString(3, store_radio_val);
			ps_update.setLong(4, Integer.valueOf(quantitybon_txt_));
			ps_update.setLong(5, counter_from_big);
			//ps_update.setLong(6, Distance); // this for distance calculator instead of counter_txt_
			ps_update.setString(6,namedriver_txt_);
			ps_update.setLong(7, nnote_from_big);
			ps_update.setString(8, nameresponsible_txt_);
			ps_update.setString(9, codemachine_val);//codemachine_txt.getText());
			ps_update.setLong(10,nbon_from_big);
			//Executing SQL 
			    int result = ps_update.executeUpdate();
				System.out.println("result of ps_update.executeUpdate -> "  + result);
			if (result != 0 ){
				System.out.println("result of ps_update.executeUpdate -> "  + result);
				setViewTable();
				viewtable.refresh();
				//setNotification here pass "info" 
				if(sound.getSNI() != null){
				    sound.getSNI().play();
				}
				setNotification("Info_update"," „ «· ⁄œÌ· »‰Ã«Õ");
				//clear TextField
				clear();
			}else{
			    if(sound.getSAE() != null){
					sound.getSAE().play();
				}
			    setAlert(AlertType.ERROR, "Œÿ√","«· ÕœÌÀ ›‘·","«·’› «·–Ï  Õ«Ê·  ÕœÌÀÂ €Ì— „ÊÃÊœ .. √ﬂœ „‰ ÊÃÊœ —ﬁ„ «·»Ê‰ ›Ï «·ÃœÊ·");
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
				if (sound.getSNE() != null){
				    sound.getSNE().play();
				}
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
                if( con_db_update != null && getValid_Func) {

                    // cleanup resources, once after processing
                    ps_update.close();
                    // and then finally close connection
                    con_db_update.close();
                }
            }catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
		check_update = true;
		update_distance = false;
		System.out.println("check_update -> " + check_update);
        }
	}
	
	@FXML
	//Delete func
	public void deleteData(){
	    String nbon = nbon_txt.getText();
	   if (nbon.matches("[0-9]+")){
		BigInteger nbon_big = new BigInteger(nbon);
		long nbon_from_big = nbon_big.longValue();
		   //setAlert
		   //when I call setAlert confirm_delete var will become true if response == ButtonType.OK
		   if (sound.getSAI() != null){
       		   sound.getSAI().play();	   
			}
		   setAlert(AlertType.CONFIRMATION, " Õ“Ì—", "«‰  ⁄·Ï Ê‘ﬂ Õ–› ’›¯"," : Â· «‰  „ √ﬂœ „‰ Õ–› «·»Ê‰ —ﬁ„ "+nbon_txt.getText());
		   if (confirm_delete){
				long t0 = System.nanoTime();
				con_db_delete =  db.getConnection_F_DB();
				try{ 
					PreparedStatement ps_delete =con_db_delete.prepareStatement("DELETE FROM General_db WHERE Nbon = ?;");
					ps_delete.setLong(1, nbon_from_big);
					int reslut = ps_delete.executeUpdate(); 
					System.out.printf("Database opened in %.3f seconds%n",((System.nanoTime()-t0)/1000000000.0));
					if (reslut != 0){
					   //setNotification
					   setViewTable();
					   if(sound.getSNI() != null){
					       sound.getSNI().play();
					   }
					   setNotification("delete", "  „ «·Õ–›                  ");
					}else{
					    if (sound.getSAE() != null){	
					    sound.getSAE().play();
						}
						setAlert(AlertType.ERROR, "Œÿ√", " ·ﬁœ ÕœÀ Œÿ√ „« ", "«‰   Õ«Ê· Õ–› ’› €Ì— „ÊÃÊœ");
					}
					//cut connect
					ps_delete.close();
					con_db_delete.close();
			   }catch(SQLException e){
				e.printStackTrace();
			   }
			   clear();
			}
	    }else{
		    if (sound.getSAE() != null){
		        sound.getSAE().play();
			}
		    setAlert(AlertType.ERROR, "Œÿ√","·ﬁœ ÕœÀ Œÿ√ „« ","«·—Ã«¡ «· √ﬂœ „‰ —ﬁ„ «·»Ê‰..«—ﬁ«„ ›ﬁÿ");
		}
		confirm_delete = false;
	}
	
	//View Table in TableView
	public void setViewTable(){
	  //getTimeCurrent with nanoTime
	  long t0 = System.nanoTime();
	  table_view_list = FXCollections.observableArrayList();
	  //get connection of this.DB
	  con_db = db.getConnection_F_DB();
	  try{ 
		ResultSet rs =con_db.createStatement().executeQuery("SELECT * FROM General_db WHERE Dateexchange BETWEEN #"+getDayOfMonth("start")+"# AND #"+getDayOfMonth("end")+"#");
		while(rs.next()){
		table_view_list.add(new Table_View(rs.getInt("Serialn"), rs.getLong("Nbon"),rs.getDate("Dateexchange"),rs.getString("Typefuel"),rs.getLong("Quantitybon"),rs.getLong("Counter"),rs.getInt("Distance"),rs.getString("Namedriver"),rs.getLong("Nnote"),rs.getString("Nameresponsible"),rs.getString("Codemachine")));
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
	//func calculate distance
	public void calcDistance(){
	    // return counter and codemachine 
	    Connection con = db.getConnection_F_DB();
		ObservableList<Table_View> list = FXCollections.observableArrayList();
		long counter = 0;
		long serial  = 0;
		String code = "";
		Table_View tv = null;
		//String codemachine_val = "·‰‘ 10ﬂ";
		try{
			ResultSet rs = con.createStatement().executeQuery("SELECT Serialn ,Counter ,Codemachine FROM General_db WHERE Codemachine =\""+codemachine_val+"\";");
			while(rs.next()){
		    tv = new Table_View(rs.getLong("Serialn"),rs.getInt("Counter"),rs.getString("Codemachine"));
			   list.add(tv); 
			}
			//Cut con, rs
			con.close();
			rs.close();
			
			System.out.printf("Serialn\t\tCounter\t\tcodemachine \n");
			System.out.printf("========\t=======\t\t=========== \n");
			 int lastIndex = list.lastIndexOf(tv);
		    //check if not counter or = null so distance = zero 
			if (lastIndex != -1){
			    serial  = list.get(lastIndex).getSerialn();
				counter = list.get(lastIndex).getCounter();
				code    = list.get(lastIndex).getCodemachine();
				System.out.printf(serial+"\t\t"+counter + "\t\t" + code + "\n");
				System.out.println("lastIndex = " + lastIndex);
		        //check current counter small than in last row -> setNotification(create variable and set it in formErrors in getValidation) and saveData and updateData not running 
				if (counter_txt_.matches("[0-9]+")){
				int current_counter = Integer.valueOf(counter_txt_);
					if (current_counter > counter){
						//distance = currentCounter - lastCounter for this Code
						Distance =  current_counter - counter;
					}else{
					    
					    if(sound.getSAI() != null){
				          sound.getSAI().play();
				        }
					    setAlert(AlertType.INFORMATION,"„·«ÕŸÂ","⁄œ«œ Â–Â «·«·Â «’€— „‰ «Œ— „—Â ”Ã·  ›ÌÂ«","«–« ﬂ«‰ «·⁄œ«œ «·Õ«·Ï «ﬂ»— „‰ «·”«»ﬁ »«·‰”»Â ·Â–Â «·√·… ”Ê›  Ì „  ”ÃÌ· «·„”«›Â »’›— «– ‰ÃÕ  «·⁄„·ÌÂ");
					    Distance = 0;
					}
				}
			}else{
			    //I don't know if alert stop thread or not
				if(sound.getSAI() != null){
				sound.getSAI().play();
				}
				setAlert(AlertType.INFORMATION,"„·«ÕŸÂ","ﬂÊœÂ–Â «·«·Â €Ì— „ÊÃÊœ „‰ ﬁ»·","«‰   Õ«Ê· Ê÷⁄ ﬂÊœ «·√·… €Ì— „ÊÃÊœ ·–« ”Ê› Ì „ Ê÷⁄ «·„”«›Â » ’›— ﬂ„—Ã⁄ «–« ‰ÃÕ  «·⁄„·ÌÂ");
				Distance = 0;
			}
			/* for (int i = 0; i < list.size(); i++){
				counter = list.get(i).getCounter();
				code = list.get(i).getCodemachine();
				serial = list.get(i).getSerialn();
				System.out.printf(serial+"\t\t"+counter + "\t\t" + code + "\n");
			}  */
			
		}catch(SQLException e){
		  e.printStackTrace();
		}

		
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
	//this way isn't good way to 
	public void setNotification(String type,String content){
        URL info_img   = Thread.currentThread().getContextClassLoader().getResource("images/inserted.PNG");
	    URL error_img  = Thread.currentThread().getContextClassLoader().getResource("images/Error1.PNG");
	    URL update_img = Thread.currentThread().getContextClassLoader().getResource("images/update.PNG");	
		if (type.equals("Info")){
			Notifications notificationBuilder = Notifications.create()
			.title(" „ »‰Ã«Õ")
			.text(" „ «÷«›Â «·»Ì«‰«  »‰Ã«Õ")
			.graphic(new ImageView(new Image("/images/inserted.PNG",true)))
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
			.graphic(new ImageView(new Image("/images/Error1.PNG",true)))
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
		}else if (type.equals("Info_update")){
		    Notifications notificationBuilder = Notifications.create()
			.title(" ⁄œÌ·  «·ÃœÊ·")
			.text(content)
			.graphic(new ImageView(new Image("/images/update.PNG",true)))
			.hideAfter(Duration.seconds(3))
			.position(Pos.BOTTOM_LEFT)
			.onAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent arg0) {
					System.out.println("Notification clicked on!");
				}
			});
			//notificationBuilder.owner(stageOfThis);
			notificationBuilder.show();
		}else if (type.equals("delete")){
		      Notifications notificationBuilder = Notifications.create()
			.title("Õ–›")
			.text(content)
			.graphic(new ImageView(new Image("/images/delete.PNG",true)))
			.hideAfter(Duration.seconds(3))
			.position(Pos.BOTTOM_LEFT)
			.onAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent arg0) {
					System.out.println("Notification clicked on!");
				}
			});
			//notificationBuilder.owner(stageOfThis);
			notificationBuilder.show();
		}
	}
	
	//func to set Alert (AlertType , title , header , content)
	public void setAlert(Alert.AlertType alertType, String title , String header , String Content){
		Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(Content);
		if (alertType == AlertType.CONFIRMATION){
			alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
			   confirm_delete = true;
			  }
			});
		}else{
		    alert.showAndWait();
		}
		
	}
	
	//setText from getSelectionModel
	public void setTextFG(){
	        counter_txt.setDisable(true);
			selectedModel = viewtable.getSelectionModel();
	    if (!selectedModel.isEmpty()){
			// nbon_txt.setDisable(true);
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
	    }else{
		if(sound.getSAE() != null){
		    sound.getSAE().play();
		}
		    
		setAlert(AlertType.ERROR, "Œÿ√", "ÌÃ»  ÕœÌœ ’› ﬁ»·  Õ—Ì—Â","«·—Ã«¡ «Œ Ì«— ’› „‰ «·ÃœÊ·  ﬁ»· «· ⁄œÌ· ⁄·ÌÂ");
		}
	}
	//clear for Input TextFields
	public void clear(){
        nbon_txt.clear();	
        quantitybon_txt.clear();	
        counter_txt.clear();	
        counter_txt.setDisable(false);	
        dateexchange_datepicker.setValue(null);	
        namedriver_txt.clear();	
        nameresponsible_txt.clear();	
        // nnote_txt.clear();	
        codemachine_txt.clear();	
		/*  if (nbon_txt.isDisable()){
			nbon_txt.setDisable(false);
		}  */
	}
	//return start and end of month 
	public LocalDate getDayOfMonth(String se){
		LocalDate ld = LocalDate.now();
		LocalDate startDate = ld.minusDays(ld.getDayOfMonth() - 1);
		LocalDate endDate   = startDate.plusDays(ld.lengthOfMonth()-1);
	    if (se.equals("start")){
			return startDate;
		}
		return endDate;
	}
	
	public void getDefaultNbon(){
	    //trim()  for delete spaces in first number and last
	    String nnote_txt__ = nnote_txt.getText().trim(); 
		
	    if (nnote_txt__.matches("[0-9]+")){
		    BigInteger big = new BigInteger(nnote_txt__);
		    long nnote = big.longValue();
			Nbon = 0;
            int index = 0;
			Connection con = null;
			Table_View tv = null;
			con = db.getConnection_F_DB();
			ObservableList<Table_View> list = FXCollections.observableArrayList();
			//select nbon and nnote from Injection_db where nnote equal ... nnote_txt.getText();
			try{
				PreparedStatement ps = con.prepareStatement("SELECT * From Injection_db WHERE Nnote = ?");
				ps.setLong(1, nnote);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
				tv = new Table_View(rs.getLong("Nbon"),rs.getLong("Nnote"));
			      //insert it nbon and nnote in list 
				   list.add(tv);
				   index = rs.getRow();
				   break;
				}
			      //return the first item(nnbon) in the list and store in variable Nbon
				  if (index != 0){
			            //nbon_text.setText(Nbon);
						Nbon = list.get(0).getNbon();
						nbon_txt.setText(String.valueOf(Nbon));
					}
				//Cut con, rs
				rs.close();
				ps.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			index = 0;
			list = null;
		}
	}

//functions for check the length of TextField not more than of max_length
	public void veryfiedNbon(){
		int maxChar = 14;
		if(nbon_txt.getText().length() > maxChar) {
			String s = nbon_txt.getText().substring(0, maxChar);
            nbon_txt.setText(s);
		}	
	}
	
	public void veryfiedCounter(){
		int maxChar = 8;
		if(counter_txt.getText().length() > maxChar) {
			String s = counter_txt.getText().substring(0, maxChar);
            counter_txt.setText(s);
		}	
	}
	
	public void veryfiedQuantityNbon(){
		int maxChar = 2;
		if(quantitybon_txt.getText().length() > maxChar) {
			String s = quantitybon_txt.getText().substring(0, maxChar);
            quantitybon_txt.setText(s);
		}	
	}
	
	public void veryfiedNnote(){
		int maxChar = 14;
		if(nnote_txt.getText().length() > maxChar) {
			String s = nnote_txt.getText().substring(0, maxChar);
            nnote_txt.setText(s);
		}	
	}
	
	public void veryfiedNcode(){
		int maxChar = 8;
		if(codemachine_txt.getText().length() > maxChar) {
			String s = codemachine_txt.getText().substring(0, maxChar);
			codemachine_txt.setText(s);
		}	
	}

}


