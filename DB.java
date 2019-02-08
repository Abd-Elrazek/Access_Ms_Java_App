import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
public class DB {

    public static void main(String[] args) {
        // variables
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
		
	    // Variables of Inputs
        long Nbon = 457575757;
		String Dateexchange = "#02/04/2019#";
		String Typefuel = "”Ê·«—";
		long Quantitybon = 33;
		long Counter     = 33334;
		long Distance = 7855875;
		String Namedriver = "⁄»œ«·—«“ﬁ";
		long Nnote   = 34757534;
		String Nameresponsible = "⁄»œ«··Â";
	    String Codemachine = "„Ê·œ 33ﬂ";

        // Step 1: Loading or registering Oracle JDBC driver class
        try {

            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {

            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }

        // Step 2: Opening database connection
        try {
            Properties props = new Properties();
            String msAccDB = "db_main.accdb";
            String dbURL = "jdbc:ucanaccess://" + msAccDB; 

            // Step 2.A: Create and get connection using DriverManager class
            connection = DriverManager.getConnection(dbURL); 

            // Step 2.B: Creating JDBC Statement 
            statement = connection.createStatement();

            // Step 2.C: Executing SQL & retrieve data into ResultSet
            int result = statement.executeUpdate("insert into General_db (Nbon, Dateexchange, Typefuel, Quantitybon, Counter, Distance, Namedriver, Nnote, Nameresponsible, Codemachine)values("+Nbon+","+Dateexchange+",'"+Typefuel+"',"+Quantitybon+","+Counter+","+Distance+",'"+Namedriver+"',"+Nnote+",'"+Nameresponsible+"','"+Codemachine+"');");
			if (result != 0 ){
			    System.out.println("Inserted successfully and the result equal = "+ result);	
		    }
            System.out.println("ID\tfname\t\tlname\tAge\tmyphone");
            System.out.println("==\t========\t======\t===\t==========");

            // processing returned data and printing into console
			/*
            while(resultSet.next()) {
                System.out.println(resultSet.getInt(1) + "\t" + 
                        resultSet.getString(2)    + "\t" + 
                        resultSet.getString(3)    + "\t" +
                        resultSet.getString(4)    + "\t" +
			resultSet.getString(5)    + "\t" );
			}*/
		 
        }
        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {

            // Step 3: Closing database connection
            try {
                if(null != connection) {

                    // cleanup resources, once after processing
                    resultSet.close();
                    statement.close();

                    // and then finally close connection
                    connection.close();
                }
            }
            catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
    }
}