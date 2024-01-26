import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The StartGameScreen class represents the game screen where the crosshair and back and foregrounds can be chosen.
 * It extends the Scene class from JavaFX and provides the user interface for starting the game.
 */
class SelectionScreen extends Scene {
    ImageView crossHair;
    int prefCrossHair = 1;
    int prefGround = 1;

    Image crossHairImage;
    double SCALE;

    /**
     * Constructs a StartGameScreen object.
     *
     * @param primaryStage the primary stage of the JavaFX application
     * @param WIDTH        the width of the screen
     * @param HEIGHT       the height of the screen
     */


    public SelectionScreen(Stage primaryStage, double WIDTH, double HEIGHT) {
        super(new Pane(), WIDTH, HEIGHT);
        this.SCALE = DuckHunt.SCALE;


        primaryStage.setScene(this);


        Text screenText = new Text("\nUSE ARROW KEYS TO NAVIGATE\n" + "      \tPRESS ENTER TO START   \n" + "\t     PRESS ESC TO EXIT");
        // write this in upper case
        screenText.setFont(Font.font("Arial", FontWeight.BOLD, 7 * SCALE));
        screenText.setFill(Paint.valueOf("0xeb9a09"));
        screenText.setLayoutX(WIDTH / 2 - screenText.getLayoutBounds().getWidth() / 2);


        Image backgroundImage = new Image("assets/background/" + prefGround + ".png");
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(WIDTH);
        background.setFitHeight(HEIGHT);


        Image foregroundImage = new Image("assets/foreground/" + prefGround + ".png");
        ImageView foreground = new ImageView(foregroundImage);
        foreground.setFitHeight(HEIGHT);
        foreground.setFitWidth(WIDTH);

        primaryStage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            // Handle left and right arrow key presses to change the back and foreground image
            if (event.getCode() == KeyCode.LEFT) {
                prefGround -= 1;
            } else if (event.getCode() == KeyCode.RIGHT) {
                prefGround += 1;
            }
            if (prefGround == 0) {
                prefGround = 3;
            } else if (prefGround == 6) {
                prefGround = 1;
            }
            background.setImage(new Image("assets/background/" + prefGround + ".png"));
            foreground.setImage(new Image("assets/foreground/" + prefGround + ".png"));
        });


        crossHairImage = new Image("assets/crosshair/" + prefCrossHair + ".png");
        crossHair = new ImageView(crossHairImage);

        primaryStage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            // Handle down and up arrow key presses to change the crosshair image
            if (event.getCode() == KeyCode.UP) {
                prefCrossHair += 1;

            } else if (event.getCode() == KeyCode.DOWN) {
                prefCrossHair -= 1;

            }
            if (prefCrossHair == 0) {
                prefCrossHair = 7;
            } else if (prefCrossHair == 8) {
                prefCrossHair = 1;
            }
            crossHair.setImage(new Image("assets/crosshair/" + prefCrossHair + ".png"));
        });

        //create a layer of crosshair because built in javaFX crosshair is not scalable

        crossHair.setFitHeight(crossHairImage.getHeight() * SCALE);
        crossHair.setFitWidth(crossHairImage.getWidth() * SCALE);
        crossHair.setLayoutY(HEIGHT / 2 - crossHair.getFitHeight() / 2);
        crossHair.setLayoutX(WIDTH / 2 - crossHair.getFitWidth() / 2);

        Pane layout = (Pane) getRoot(); // Cast the root to StackPane


        layout.getChildren().addAll(background, screenText, foreground, crossHair);


        primaryStage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            // Handle key presses to change the scene or exit the game anytime
            if (event.getCode() == KeyCode.ESCAPE) {
                primaryStage.setScene(new StartScreen(primaryStage, WIDTH, HEIGHT, false));
            } else if (event.getCode() == KeyCode.ENTER) {
                primaryStage.setScene(new LevelOne(primaryStage, WIDTH, HEIGHT, prefCrossHair, prefGround, true));

            }
        });


    }


}
