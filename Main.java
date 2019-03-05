import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
// import java.sql.PreparedStatement;
import java.awt.SplashScreen;
import javafx.event.EventHandler;
import javax.swing.JOptionPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;



/**
 *
 * @author Abdelrazek
 */
public class Main extends Application {
    public Stage primaryStage = null;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(" Œ“Ì‰ «·»Ì«‰« ");
		stage.setResizable(false);
		stage.sizeToScene();
		//stage.setOpacity(0.9);
		//stage.initStyle(StageStyle.UTILITY);
		//stage.initStyle(StageStyle.TRANSPARENT);
	    //stage.initModality(Modality.WINDOW_MODAL);
		stage.getIcons().add(new Image("images/icon.png"));
        stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        @Override
			public void handle(WindowEvent event) {	
			System.exit(0); 
			}
        });
    }


    public static void main(String[] args){
/* 	LocalDate ld = LocalDate.now();
	int day = ld.getDayOfMonth();
	if(day == 3){
		System.out.println("initialize Serialn count 1");
		DB db = new DB();
		Connection con = db.getConnection_F_DB();
		try{
			int  ps1 = con.createStatement().executeUpdate("ALTER TABLE [General_db] ALTER COLUMN [Serialn] COUNTER(1,1);");
			//int  ps2 = con.createStatement().executeUpdate("ALTER TABLE General_db ADD COLUMN Serialn AutoNumber;");
			if (ps1 == 0){
				System.out.println("Table altered successfully.");
			}else{
				System.out.println("Table altered not Success");
			}
		}catch(SQLException sql){
		    sql.printStackTrace();
		}
	} */
	 
	 
	  // if SplashScreen is visible broke it
        SplashScreen splash = SplashScreen.getSplashScreen();
		if (splash != null){
			if (splash.isVisible()) {
				System.out.println("Is visible");
				splash.close();
			}
		}
	//GUI Run 
	 java.awt.EventQueue.invokeLater(() -> {
		 launch(args);
	  });
	  System.out.println("Gui running...");
	 //DB  Run
    }
    
}
