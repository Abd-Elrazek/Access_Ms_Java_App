import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.net.URL;


public class Test{
	public static void main(String []args){
	Connection con_db = null;
	long t0 = System.nanoTime();
	DB db = new DB();
    LocalDate ld = LocalDate.now();
    LocalDate startDate = ld.minusDays(ld.getDayOfMonth() - 1);
	LocalDate endDate   = startDate.plusDays(ld.lengthOfMonth()-1);
	System.out.println("date now  => " + ld); //default >> 2019-03-01
	System.out.println("start  => " + startDate); //default >> 2019-03-01
	System.out.println("end => " + endDate);
	System.out.println("year => " + ld.getYear());
	System.out.println("year => " + ld.getMonthValue());
	Object ob = Thread.currentThread().getContextClassLoader().getResource("images/1.png");
	String obb = ob.toString();
	System.out.println("Class is => " + obb);
	
	
	String info_img  = "";
	String error_img = "";
	String update_img = "";
	try{
		URL info_imgg   = Thread.currentThread().getContextClassLoader().getResource("images/inserted.PNG");
	    URL error_imgg = Thread.currentThread().getContextClassLoader().getResource("images/Error1.PNG");
	    URL update_imgg = Thread.currentThread().getContextClassLoader().getResource("images/update.PNG");	
	    System.out.println(info_imgg.getFile());
	    System.out.println(error_imgg);
	    System.out.println(update_imgg);
	}catch(Exception e){
	    e.printStackTrace();
	}
	
	
	
	
    
	
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
			ResultSet rs = con.createStatement().executeQuery("SELECT DISTINCT Codemachine from General_db");
			System.out.println("size   : " + (rs.getInt("COUNT(Codemachine)")));
			// while(rs.next()){
				// System.out.println(rs.getString("Codemachine"));
			// }
			con.close();
			rs.close();
		}catch(SQLException e){
		  e.printStackTrace();
		} 
		//check if not counter or = null so distance = zero 
		//check current counter small than in last row -> setNotification(create variable and set it in formErrors in getValidation) and saveData and updateData not running 
		//check counter current(inputs) larger than last row  -> create $distance = current - last
		
		System.out.printf("Database opened in %.3f seconds%n",((System.nanoTime()-t0)/1000000000.0));
	    System.out.println(Integer.MAX_VALUE);
	    System.out.println(Long.MAX_VALUE);
		System.out.println("" + (1 + 2));
	}
	
}

