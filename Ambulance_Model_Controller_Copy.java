import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
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



import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import javafx.scene.Node;
import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotParameters;
import java.io.File;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import java.awt.Desktop;
// import java.awt.Image;
import javafx.application.Platform;
import java.awt.Color;
import javafx.print.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.util.Duration;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.FileNotFoundException;
import javafx.scene.text.Font;
import javafx.scene.control.TableCell;

public class Ambulance_Model_Controller_Copy implements Initializable{ 
//Global Variables
	@FXML
	private Button backBtn;
	private Stage getAmbulance_Model_StageByBtn;
	private AnchorPane searchAnch;
	private Stage getSearchStage;  
	
	
	@FXML 
	private AnchorPane node_anch;

    //this Connection for View table
	private DB db = new DB();
	
	private Connection con_db = null;
	//list of Table_View class 
	ObservableList<Table_View> table_view_list ;
	
	//variables of columns in TableView(1) and TableColumn( 11 cols )
    @FXML
    private TableView<Table_View> viewtable;
	@FXML
    private TableColumn<Table_View,Long> serialn_col;
	@FXML
    private TableColumn<Table_View,Long> nbon_col;
	@FXML
    private TableColumn<Table_View,Date> dateexchange_col;
	@FXML
    private TableColumn<Table_View,String> typefuel_col;
	@FXML
    private TableColumn<Table_View,Long> quantitybon_col;
	@FXML
    private TableColumn<Table_View,Long> counter_col;
	@FXML
    private TableColumn<Table_View,Long> distance_col;
	@FXML
    private TableColumn<Table_View,String> namedriver_col;
	@FXML
    private TableColumn<Table_View,Long> nnote_col;
	@FXML
    private TableColumn<Table_View,String> nameresponsible_col;
	@FXML
    private TableColumn<Table_View,String> codemachine_col;
    @FXML
	private Label label_code;
	@FXML
	private Label label_month;
	@FXML
	private Label label_year;
	@FXML
	private Label label_typefuel;

	private String query_global = "SELECT * FROM General_db where Nbon = 30";
	List<String> all_name_codemachine = new ArrayList<String>();
	List<String> all_name_typefuel    = new ArrayList<String>();
	List<LocalDate> date              = new ArrayList<LocalDate>();
	private Document document = new Document();
    private Stage wait_stage;
	private AnchorPane wait_anch;
	private Font font_column = new Font(16);

//Functions
   //initialzable
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	
    }
	
    //View Table in TableView
	public void showTable(String query){
	  //getTimeCurrent with nanoTime
	  long t0 = System.nanoTime();
	  table_view_list = FXCollections.observableArrayList();
	  //get connection of this.DB
	  con_db = db.getConnection_F_DB();
	  try{ 
	    ResultSet rs =con_db.createStatement().executeQuery(query);
		while(rs.next()){
		    table_view_list.add(new Table_View(rs.getInt("Serialn"), rs.getLong("Nbon"),rs.getDate("Dateexchange"),rs.getString("Typefuel"),rs.getInt("Quantitybon"),rs.getLong("Counter"),rs.getInt("Distance"),rs.getString("Namedriver"),rs.getLong("Nnote"),rs.getString("Nameresponsible"),rs.getString("Codemachine")));
		   if(rs.isLast()){
		        LocalDate date = rs.getDate("Dateexchange").toLocalDate();
				String month = ""+ date.getMonthValue();
				String year  = "" + date.getYear();
				setLabel(rs.getString("Codemachine"), month, year, rs.getString("Typefuel"));
			}
		}
		
		System.out.printf("Database opened in %.3f seconds%n",((System.nanoTime()-t0)/1000000000.0));
		//cut connect
		rs.close();
		con_db.close();
	  }catch(SQLException e){
		e.printStackTrace();
	  }
      // nbon_col.setFont(font_column);
      passTableColumnInteger(serialn_col);
      passTableColumnInteger(nbon_col);
      passTableColumnDate(dateexchange_col);
      passTableColumnString(typefuel_col);
	  passTableColumnInteger(quantitybon_col);
      passTableColumnInteger(counter_col);
      passTableColumnInteger(distance_col);
      passTableColumnString(namedriver_col);
      passTableColumnInteger(nnote_col);
      passTableColumnString(nameresponsible_col);
      passTableColumnString(codemachine_col);
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
	
	public void setLabel(String code, String month, String year, String typefuel){
	    if (code != null){	
		label_code.setText(code);
		}
		if (month != null){
		label_month.setText(month);
		}
		if(!year.isEmpty()){
		label_year.setText( year);	
		}
		if (typefuel != null){
		label_typefuel.setText(typefuel);
		}
	}
	
	//print func 
	public void print (LocalDate from ,LocalDate to){
		distinct_codemachine(from, to);
	    String outputFile = "File.pdf";
		try{
		    PdfWriter.getInstance(document, new FileOutputStream(new File(".", outputFile)));
		}catch(Exception e){
		    e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Œÿ√");
			alert.setContentText("ÌÊÃœ Â‰«ﬂ „·› „› ÊÕ .. «·—Ã«¡ ﬁ›·Â ›Ï «·„—Â «·ﬁ«œ„Â ⁄‰œ ⁄„·ÌÂ «·ÿ»«⁄Â");
			alert.showAndWait();
		}  
		document.open();
		for(int i = 0; i < all_name_codemachine.size(); i++){
			String code = all_name_codemachine.get(i);
			System.out.println(code);
			showTable("SELECT * FROM General_db WHERE Codemachine = '" +code+ "' AND Dateexchange BETWEEN #" + from +"# AND #" + to + "# ");
			printNod(i);
		}
		document.close();
		
	    try{
			File file = new File(".\\File.pdf");
			Desktop desktop = Desktop.getDesktop();
			desktop.open(file);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Pdf created successfully"); 
	}
	
	//This func bring the all distinct codemachine and putted in List
	@FXML
	public void distinct_codemachine(LocalDate from , LocalDate to){
	    // LocalDate localDate_from_Date = null;
		//all_name_codemachine = null;
	    Connection con = db.getConnection_F_DB();
	    try{
		    ResultSet rs = con.createStatement().executeQuery("SELECT DISTINCT Codemachine FROM General_db WHERE Dateexchange BETWEEN #" + from +"# AND #" + to + "# ");
		    // ResultSet rs_ = con.createStatement().executeQuery("SELECT Typefuel, Dateexchange FROM General_db");
		    while(rs.next()){
			all_name_codemachine.add(rs.getString("Codemachine"));
		    }

		    rs.close();
		    con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}

	}
	
	//Print Node that i give it
	public void printNod(int i){
		System.out.println("node of AnchorPane return  => " + node_anch);
        WritableImage image = node_anch.snapshot(new SnapshotParameters(), null);
        ByteArrayOutputStream  byteOutput = new ByteArrayOutputStream();
		try{
			ImageIO.write( SwingFXUtils.fromFXImage( image, null ), "png", byteOutput );
			document.newPage();
			Image image_ = Image.getInstance(byteOutput.toByteArray());
			image_.setAbsolutePosition(0, 0);
			image_.setBorderWidth(0);
			image_.scaleAbsoluteHeight(PageSize.A4.getHeight());
			image_.scaleAbsoluteWidth(PageSize.A4.getWidth());   
			document.add(image_);
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	public void  passTableColumnInteger(TableColumn table_column){
		table_column.setCellFactory(callback -> {
			TableCell<Table_View, Long> cell =new TableCell<Table_View, Long>() {
				@Override
				protected void updateItem(Long item, boolean empty) {
					
					super.updateItem(item, empty);
					String item_ = "" + item;
					setText(empty ? "" : item_);
				}
			};
			// maybe choose a more suitable name Class here
			cell.setFont(font_column); 
			return cell ;
		}); 
	}
	
	public void  passTableColumnString(TableColumn table_column){
		table_column.setCellFactory(callback -> {
			TableCell<Table_View, String> cell =new TableCell<Table_View, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					
					super.updateItem(item, empty);
					// String item_ = "" + item;
					setText(empty ? "" : item);
				}
			};
			// maybe choose a more suitable name Class here
			cell.setFont(font_column); 
			return cell ;
		}); 
	}
	
	public void  passTableColumnDate(TableColumn table_column){
		table_column.setCellFactory(callback -> {
			TableCell<Table_View, Date> cell =new TableCell<Table_View, Date>() {
				@Override
				protected void updateItem(Date item, boolean empty) {
					
					super.updateItem(item, empty);
					String item_ = "" + item;
					setText(empty ? "" : item_);
				}
			};
			// maybe choose a more suitable name Class here
			cell.setFont(font_column); 
			return cell ;
		}); 
	}
	
}