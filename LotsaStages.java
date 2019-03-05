import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LotsaStages extends Application {
    private static final Color[] STAGE_COLORS = { 
        Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW 
    };
    private static final double  STAGE_OFFSET = 50;

    @Override
    public void start(Stage primaryStage) throws Exception {
        addContent(primaryStage, Color.LIGHTBLUE);
        primaryStage.show();

        double offset = STAGE_OFFSET;
        for (Color color: STAGE_COLORS) {
            Stage child = new Stage();
            child.initOwner(primaryStage);
            child.initStyle(StageStyle.UTILITY);

            child.setX(primaryStage.getX() + offset);
            child.setY(primaryStage.getY() + offset);

            addContent(child, color);

            child.show();

            offset += STAGE_OFFSET;
        }
    }

    private void addContent(Stage child, Color color) {
        child.setScene(
            new Scene(
                new Group(
                    new Rectangle(150, 70, color)
                )
            )
        );
    }

    public static void main(String[] args) { launch(args); }
}