import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
// import javafx.stage.StageStyle;

/**
 *
 * @author Abdelrazek
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("ÊÎÒíä ÇáÈíÇäÇÊ");
		stage.setResizable(false);
		stage.sizeToScene();
		// stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
	 DB db = new DB();
     //GUI Run 
	 java.awt.EventQueue.invokeLater(() -> {
		 launch(args);
	  });
	  System.out.println("Gui running...");
	 //DB  Run
	 db.setConnection();
	 boolean dbr =  db.insertData();
	if (dbr){
		System.out.println(dbr);
	}
    }
    
}
