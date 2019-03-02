import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Test{
	public static void main(String []args){
	Connection con_db = null;
	long t0 = System.nanoTime();
	DB db = new DB();
	 // return counter and codemachine 
	    Connection con = db.getConnection_F_DB();
		ObservableList<Table_View> list = FXCollections.observableArrayList();
		long counter = 0;
		long serial  = 0;
		String code = "";
		String codemachine_val = "ÓíÇÑå 6ß";
		Table_View tv = null;
			System.out.printf("Serialn\t\tCounter\t\tcodemachine \n");
			System.out.printf("========\t=======\t\t=========== \n");
		try{
			ResultSet rs = con.createStatement().executeQuery("SELECT Serialn ,Counter ,Codemachine FROM General_db WHERE Codemachine =\""+codemachine_val+"\" AND Serialn = 192;");
			while(rs.next()){
				System.out.printf(rs.getLong("Serialn")+"\t\t"+rs.getLong("Counter")+"\t\t"+rs.getString("Codemachine")+"\n");
			}
			con.close();
			rs.close();
		}catch(SQLException e){
		  e.printStackTrace();
		}
		//check if not counter or = null so distance = zero 
		//check current counter small than in last row -> setNotification(create variable and set it in formErrors in getValidation) and saveData and updateData not running 
		//check counter current(inputs) larger than last row  -> create $distance = current - last
		System.out.printf("Database opened in %.3f seconds%n",((System.nanoTime()-t0)/1000000000.0));
	}
}

