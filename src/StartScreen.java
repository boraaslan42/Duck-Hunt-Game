import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * The StartScreen class represents the first screen user sees in Duck Hunt game.
 * It extends the Scene class from JavaFX and provides the user interface for starting the game.
 */
class StartScreen extends Scene {
    double SCALE;
    /**
     * Constructs a StartScreen object.
     *
     * @param primaryStage the primary stage of the JavaFX application
     * @param WIDTH        the width of the screen
     * @param HEIGHT       the height of the screen
     * @param isFirstTime  a flag indicating whether it is the first time showing the start screen
     *                     to understand if intro music is already played or not
     */

    public StartScreen(Stage primaryStage, double WIDTH, double HEIGHT, boolean isFirstTime) {
        super(new StackPane(), WIDTH, HEIGHT);

        primaryStage.setScene(this);
        primaryStage.getIcons().add(new Image("assets/favicon/1.png"));

        this.SCALE = DuckHunt.SCALE;

        if (isFirstTime) { //done to prevent music from playing from more than class
            MusicPlayer musicPlayer = new MusicPlayer(primaryStage);
            musicPlayer.play();
        }

        StackPane layout = (StackPane) getRoot(); // Cast the root to StackPane

        Image backgroundImage = new Image("assets\\welcome\\1.png");
        ImageView imageView = new ImageView(backgroundImage);

        imageView.scaleXProperty().set(SCALE);//SCALING IMAGES TO FIT THE SCREEN
        imageView.scaleYProperty().set(SCALE);

        Text flashingText = new Text("PRESS ENTER TO START GAME" + "\n\tPRESS ESC TO EXIT");

        flashingText.setFill(Paint.valueOf("0xeb9a09"));
        flashingText.setFont(Font.font("Arial", FontWeight.BOLD, 13 * SCALE));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> flashingText.setVisible(false)),
                new KeyFrame(Duration.seconds(1), event -> flashingText.setVisible(true))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        StackPane.setAlignment(flashingText, javafx.geometry.Pos.BOTTOM_CENTER);
        Translate translate = new Translate();
        translate.setY(-backgroundImage.getHeight() * SCALE / 4);
        flashingText.getTransforms().add(translate);


        primaryStage.getScene().setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.DIGIT1){
                primaryStage.setScene(new LevelSix(primaryStage, WIDTH, HEIGHT, 1, 1)); // Set the scene to LevelTwo

            }
            if(event.getCode()==KeyCode.DIGIT2){
                primaryStage.setScene(new LevelOne(primaryStage, WIDTH, HEIGHT, 1, 1,false)); // Set the scene to LevelTwo

            }
            if (event.getCode() == KeyCode.ENTER) {
                primaryStage.setScene(new SelectionScreen(primaryStage, WIDTH, HEIGHT));
            } else if (event.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            }
        });

        imageView.setScaleX(SCALE);
        imageView.setScaleY(SCALE);
        layout.getChildren().addAll(imageView, flashingText);

    }
}
