import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The DuckHunt class is the entry point for the Duck Hunt game application.
 * It extends the Application class from JavaFX and provides the main method and the start method required for JavaFX applications.
 * SCALE and VOLUME static and final variables that does not change throughout the game.
 * They can be changed via hand before compiling and executing the code.
 */
public class DuckHunt extends Application {
    static  double SCALE = 3;
    static  double VOLUME = 0.025;
    Stage primaryStage;

    /**
     * The main method that launches the Duck Hunt game.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method of the DuckHunt application.
     * It sets up the primary stage, sets the title, loads the welcome image, and shows the start screen.
     *
     * @param primaryStage the primary stage of the JavaFX application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DuckHunt");
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);//done to prevent resizing of the screen which causes white blanks
        Image image = new Image("assets/welcome/1.png");
        //width and height of the tab because start-screen extends scene and
        // first line of code should be super, so we need to pass these values
        double WIDTH = image.getWidth() * SCALE;
        double HEIGHT = image.getHeight() * SCALE;
        primaryStage.setScene(new StartScreen(primaryStage, WIDTH, HEIGHT, true));
        primaryStage.show();
    }
}
