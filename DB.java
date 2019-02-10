import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Properties;

public class DB {
//Variables of Inputs
	private long Nbon = 457575757l;
	private Date Dateexchange = null;
	private String Typefuel = "”Ê·«—";
	private long Quantitybon = 33l;
	private long Counter     = 33334l;
	private long Distance = 7855875l;
	private String Namedriver = "⁄»œ«·—«“ﬁ";
	private long Nnote  = 34757534l;
	private String Nameresponsible = "⁄»œ«··Â";
	private String Codemachine = "„Ê·œ 33ﬂ";
	
//Constructor
	public DB (){
	//variables of classes handle with database 
	Dateexchange = new Date(2019, 2, 10);
	}
    public DB(long Nbon,Date Dateexchange,String Typefuel, long Quantitybon,long Counter,long Distance, String Namedriver,long Nnote, String Nameresponsible,String Codemachine)
    {
	 this.Nbon = Nbon;
	 this.Dateexchange = Dateexchange;
	 this.Typefuel = Typefuel;
	 this.Quantitybon = Quantitybon;
	 this.Counter = Counter;
	 this.Distance = Distance;
	 this.Namedriver = Namedriver;
	 this.Nnote = Nnote;
	 this.Nameresponsible = Nameresponsible;
	 this.Codemachine = Codemachine;
	}
//Functions
   //setConnection func
    public void setConnection(){
	Connection connection = null;
	PreparedStatement ps = null;
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

        //Step 2.B: Creating JDBC PreparedStatement class 
		ps = connection.prepareStatement("INSERT  INTO General_db (Nbon, Dateexchange, Typefuel, Quantitybon, Counter, Distance, Namedriver, Nnote, Nameresponsible, Codemachine)values(?,?,?,?,?,?,?,?,?,?)");
		ps.setLong(1, Nbon);
		ps.setDate(2, Dateexchange);
		ps.setString(3, Typefuel);
		ps.setLong(4, Quantitybon);
		ps.setLong(5, Counter);
		ps.setLong(6, Distance);
		ps.setString(7, Namedriver);
		ps.setLong(8, Nnote);
		ps.setString(9, Nameresponsible);
		ps.setString(10, Codemachine);
        // Step 2.C: Executing SQL & retrieve data into ResultSet
		int result = ps.executeUpdate();
		if (result != 0){
		 System.out.println("Result is " + result );
		 System.out.println("Database inserted");
		}
        }
        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {

            // Step 3: Closing database connection
            try {
                if(null != connection) {

                    // cleanup resources, once after processing
                    ps.close();

                    // and then finally close connection
                    connection.close();
                }
            }
            catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
	}//End setConnection func
 
    public static void main(String []para){
	 DB db = new DB();
	 db.setConnection();
	}
}