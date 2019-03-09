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

public class Input_data_injection_Controller implements Initializable{

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
    private TableColumn<Table_View,Integer> nbon_col;
	@FXML
    private TableColumn<Table_View,Integer> nnote_col;

	
	//Declaration of TextField vars only
		@FXML
		private TextField nbon_txt;
		@FXML
		private TextField nnote_txt;
		
	//These vars for getText from TextField and initialize some of them
		String nbon_txt_           = "";
		String nnote_txt_          = "";

		//set formErrors length
		String formErrors[] = new String[2];
		String collectErrors = "";
		private boolean check_update = true;
	    private boolean confirm_delete = false;
		private boolean update_distance = false;
		
		private TableViewSelectionModel<Table_View> selectedModel ;
//Constructor
public Input_data_injection_Controller(){
  
	
}
//funcions 
    // this function used to initialize my variables
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		//First view data from database
		setViewTable();
	}//end initialize variables Func
	
	
	//Set and get validation of inputs
    public boolean getValidation(){
        //Validation of inputs vars
		nbon_txt_ = nbon_txt.getText();
		nnote_txt_= nnote_txt.getText();
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
			    if (check_update){
					try{ 
					    Connection con_temp = db.getConnection_F_DB();
						BigInteger nbon_big = new BigInteger(nbon_txt_);
						long nbon_check = nbon_big.longValue();
						long nbon_distict = 0;

						ResultSet rs =con_temp.createStatement().executeQuery("SELECT Nnote, Nbon FROM Injection_db");
						while(rs.next()){
							nbon_distict = rs.getLong("Nbon");
							System.out.println("nbon_distict -> "+rs.getLong("Nbon"));
							if (nbon_distict == nbon_check){
								String concat = "—ﬁ„ «·»Ê‰ «·–Ï  Õ«Ê· «œŒ«·Â „ÊÃÊœ „”»ﬁ«...Õ«Ê· «œŒ«· —ﬁ„ «Œ—";
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

			if (!nnote_txt_.matches("[0-9]+")){
			 
			 formErrors[1] = " «œŒ· —ﬁ„ «·œ› — —ﬁ„ ›ﬁÿ ";
			 valid = false;
			}else{
			formErrors[1] = null;
			}
			break;
		} 
		//Concatenation every of name of machine and its number of code 
		if(valid && no_distinct){
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
			BigInteger nnote_big = new BigInteger(nnote_txt_);
			long nnote_from_big = nnote_big.longValue();
			//Creating JDBC PreparedStatement class 
			ps = con_db_savedata.prepareStatement("INSERT  INTO Injection_db (Nbon, Nnote)values(?,?)");
			ps.setLong(1, nbon_from_big);
			ps.setLong(2, nnote_from_big);
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
	 boolean getValid_Func = getValidation();
	 PreparedStatement ps_update = null;
	 try{
	     if (getValid_Func){
		 
		    BigInteger nbon_big = new BigInteger(nbon_txt_);
			long nbon_from_big = nbon_big.longValue();
			BigInteger nnote_big = new BigInteger(nnote_txt_);
			long nnote_from_big = nnote_big.longValue();
			
			//Creating JDBC PreparedStatement class 
			ps_update = con_db_update.prepareStatement("UPDATE General_db SET Nbon = ?, Nnote = ?  WHERE Nbon = ?;");
			ps_update.setLong(1, nbon_from_big);
			ps_update.setLong(2, nnote_from_big);
			ps_update.setLong(3,nbon_from_big);
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
		System.out.println("check_update -> " + check_update);
        }
	}
	
	@FXML
	//Delete func
	public void deleteData(){
	    String nbon = nbon_txt.getText();
	   if (nbon.matches("[0-9]+")){
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
				    BigInteger nbon_big = new BigInteger(nbon);
					long nbon_from_big = nbon_big.longValue();
					PreparedStatement ps_delete =con_db_delete.prepareStatement("DELETE FROM Injection_db WHERE Nbon = ?;");
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
		ResultSet rs =con_db.createStatement().executeQuery("SELECT * FROM Injection_db");
		while(rs.next()){
		table_view_list.add(new Table_View(rs.getLong("Nbon"),rs.getLong("Nnote")));
		}
		System.out.printf("Database opened in %.3f seconds%n",((System.nanoTime()-t0)/1000000000.0));
		//cut connect
		rs.close();
		con_db.close();
	  }catch(SQLException e){
		e.printStackTrace();
	  }
      nbon_col.setCellValueFactory(new PropertyValueFactory<>("Nbon"));
      nnote_col.setCellValueFactory(new PropertyValueFactory<>("Nnote"));
	  viewtable.setItems(table_view_list);
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
			.graphic(new ImageView(new Image("/images/inserted.PNG",true)))
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
			selectedModel = viewtable.getSelectionModel();
	    if (!selectedModel.isEmpty()){
			// nbon_txt.setDisable(true);
			ObservableList<Table_View>  list_view = selectedModel.getSelectedItems();
			nbon_txt.setText(""+list_view.get(0).getNbon());
			nnote_txt.setText(""+list_view.get(0).getNnote());
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
        nnote_txt.clear();
	}

}


