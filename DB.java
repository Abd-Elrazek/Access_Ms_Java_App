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
//Variables handle with database
	private Connection connection = null;
	private PreparedStatement ps = null;
//Variables of Inputs
	private long Nbon = 2;
	private LocalDate Dateexchange = null;
	private Date date__;
	private String Typefuel = "dffdff";
	private long Quantitybon =3;
	private long Counter     = 4;
	private long Distance = 5;
	private String Namedriver = "dfdf";
	private long Nnote  = 6;
	private String Nameresponsible = "daaaaa";
	private String Codemachine = "dfdf33434";

    
	
//Constructor
	public DB (){
	//variables of classes handle with database 
        date__ = new Date(2019, 2, 10);
	}
	//we can use either Constructor or set function for insert dat in this class 
    // public DB(long Nbon,Date Dateexchange,String Typefuel, long Quantitybon,long Counter,long Distance, String Namedriver,long Nnote, String Nameresponsible,String Codemachine)
    // {
	 // this.Nbon = Nbon;
	 // this.Dateexchange = Dateexchange;
	 // this.Typefuel = Typefuel;
	 // this.Quantitybon = Quantitybon;
	 // this.Counter = Counter;
	 // this.Distance = Distance;
	 // this.Namedriver = Namedriver;
	 // this.Nnote = Nnote;
	 // this.Nameresponsible = Nameresponsible;
	 // this.Codemachine = Codemachine;
	// }
//Functions
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
		}

	}catch(SQLException e){
	  e.printStackTrace();
	}
	return connection;
	}
	
//func InsertDate and retrieve True if data is inserted False if data not inserted 
	public boolean insertData(){
        try{
        //Step 2.B: Creating JDBC PreparedStatement class 
		ps = connection.prepareStatement("INSERT  INTO General_db (Nbon, Dateexchange, Typefuel, Quantitybon, Counter, Distance, Namedriver, Nnote, Nameresponsible, Codemachine)values(?,?,?,?,?,?,?,?,?,?)");
		ps.setLong(1, Nbon);
		ps.setDate(2, new Date(2000, 02,05));
		ps.setString(3, Typefuel);
		ps.setLong(4, Quantitybon);
		ps.setLong(5, Counter);
		ps.setLong(6, Distance);
		ps.setString(7, Namedriver);
		ps.setLong(8, Nnote);
		ps.setString(9, Nameresponsible);
		ps.setString(10, Codemachine);
        // Step 2.C: Executing SQL 
		int result = ps.executeUpdate();
		if (result != 0){
		 return true;
		}
	    }catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {

            // Step 3: Closing database connection
            try {
                if(null != connection) {

                    // cleanup resources, once after processing
                    ps.close();
                    System.out.println("Data inserted ...");
                    // and then finally close connection
                    connection.close();
                    System.out.println("Connection closed");
                }
            }catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
		return false;
	}
	
	 //funcs of set Vars
	public void setNbon(long nb){
	 this.Nbon = nb;
	}
	public void setDateexchange(LocalDate da){
	 this.Dateexchange =da;	
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
	public LocalDate getDateexchange(){
	 return Dateexchange;	
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
	
	// public static void main(String args []){
	   // DB db = new DB();
	   // if (db.getConnection_F_DB() != null && db.insertData()){
		 
		// }	
	// }
	
}