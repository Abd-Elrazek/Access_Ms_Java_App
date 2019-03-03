import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
// import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Properties;

public class DB {

//Variables of Inputs
	private long Nbon;
	private LocalDate Dateexchange;
	private Date date__;
	private String Typefuel;
	private long Quantitybon;
	private long Counter;
	private long Distance;
	private String Namedriver;
	private long Nnote;
	private String Nameresponsible;
	private String Codemachine;
    //Variables handle with database
	private Connection connection;
//Constructor
	public DB (){
	//variables of classes handle with database 
	  this.Nbon = 0;
      this.date__ = null;
	  this.Typefuel = "";
	  this.Quantitybon = 0;
	  this.Counter = 0;
	  this.Distance =0;
	  this.Namedriver = "";
	  this.Nnote = 0;
	  this.Nameresponsible =  "";
	  this.Codemachine = "";
	  this.connection = null;
	}

//Functions
 //funcs of set Vars
	public void setNbon(long nb){
	 this.Nbon = nb;
	}
	public void setDateexchange(Date da){
	 this.date__ =da;	
	}
	public void setTypefuel(String ty){
	 this.Typefuel = ty;	
	}
	public void setQuantitybon(long qu){
	 this.Quantitybon =  qu;	
	}
	public void setCounter(long co){
	 this.Counter = co;
	}
	public void setDistance(long co){
	 this.Distance = co;
	}
	public void setNamedriver(String na){
	 this.Namedriver = na;
	}
	public void setNnote(long nn){
	 this.Nnote = nn;
	}
	public void setNameresponsible(String naa){
	 this.Nameresponsible = naa;	
	}
	public void setCodemachine(String co){
	 this.Codemachine = co;	
	}
	//End funcs Set  
	
    //funcs of get Vars
	public long getNbon(){
	 return Nbon;
	}
	public Date getDateexchange(){
	 return date__;	
	}
	public String getTypefuel(){
	  return Typefuel;	
	}
	public long getQuantitybon(){
	  return Quantitybon;	
	}
	public long getCounter(){
	  return Counter;
	}
	public long getDistance(){
	  return Distance;
	}
	public String getNamedriver(){
	  return Namedriver;
	}
	public long getNnote(){
		return Nnote;
	}
	public String getNameresponsible(){
	 return Nameresponsible;	
	}
	public String getCodemachine(){
	 return Codemachine;	
	}
	//End funcs Get 
	
   //setConnection func
    public Connection getConnection_F_DB(){
   //Step 1: Loading or registering Oracle JDBC driver class with ucanaccess
	try {
	  Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	}
	catch(ClassNotFoundException cnfex) {
		System.out.println("Problem in loading or registering MS Access JDBC driver");
		cnfex.printStackTrace();
	}

    //Step 2: Opening database connection
    try {
		String msAccDB = "db_main.accdb";
		String dbURL = "jdbc:ucanaccess://" + msAccDB; 

       //Step 2.A: Create and get connection using DriverManager class
		connection = DriverManager.getConnection(dbURL);
		if (connection != null){
		 System.out.println("Connected to db...");
		}else{
		   System.out.println("Error with connectin with database");
		}

	}catch(SQLException e){
	  e.printStackTrace();
	}
	return this.connection;
	}	
}