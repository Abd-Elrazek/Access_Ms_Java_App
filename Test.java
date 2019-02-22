import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Test{
	public static void main(String []args){
	Connection con_db = null;
	long t0 = System.nanoTime();
	DB db = new DB();
	con_db = db.getConnection_F_DB();
	try{ 
		ResultSet rs =con_db.createStatement().executeQuery("SELECT Serialn FROM General_db");
		int count = 0;
		while(rs.next()){
		    count++;
			System.out.println("count : " + count + " | SerialN "+rs.getLong("SerialN")+ " | Nbon : " + rs.getLong("Nbon"));
		}
		System.out.printf("Database opened in %.3f seconds%n",((System.nanoTime()-t0)/1000000000.0));
	}catch(SQLException e){
		e.printStackTrace();
	}
	}
}