import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

class LevelOne extends Scene {
    Image crossHairImage;
    ImageView crossHair;
    Pane layout;
    DuckCoordinator duckIncubator = new DuckCoordinator();
    int ammo = 3;
    double WIDTH;
    double HEIGHT;
    double SCALE;
    ImageView blueDuck;
    boolean blueDuckIsDead = false;
    boolean lostLevel = false;
    boolean levelCompleted = false;
    /**
     * Constructs a LevelOne object with the specified stage, width, height, preferred crosshair, preferred ground,
     * and whether it is the first time playing the level.
     *
     * @param primaryStage  the primary stage of the application
     * @param WIDTH         the width of the scene
     * @param HEIGHT        the height of the scene
     * @param prefCrossHair the preferred crosshair
     * @param prefGround    the preferred ground
     * @param isFirstTime   indicates whether it is the first time playing the level
     */

    public LevelOne(Stage primaryStage, double WIDTH, double HEIGHT, int prefCrossHair, int prefGround, boolean isFirstTime) {
        super(new Pane(), WIDTH, HEIGHT);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.SCALE = DuckHunt.SCALE;
        layout = (Pane) getRoot(); // Cast the root to StackPane
        primaryStage.setScene(this);

        String gunShotFilePath = "assets/effects/Gunshot.mp3";
        AudioClip gunShotClip = new AudioClip(Objects.requireNonNull(getClass().getResource(gunShotFilePath)).toExternalForm());

        String gameOverFilePath = "assets/effects/GameOver.mp3";
        AudioClip gameOverClip = new AudioClip(Objects.requireNonNull(getClass().getResource(gameOverFilePath)).toExternalForm());

        String levelCompletedFilePath = "assets/effects/LevelCompleted.mp3";
        AudioClip levelCompletedClip = new AudioClip(Objects.requireNonNull(getClass().getResource(levelCompletedFilePath)).toExternalForm());

        String duckFallFilePath = "assets/effects/DuckFalls.mp3";
        AudioClip duckFallClip = new AudioClip(Objects.requireNonNull(getClass().getResource(duckFallFilePath)).toExternalForm());

        duckFallClip.setVolume(DuckHunt.VOLUME);
        gameOverClip.setVolume(DuckHunt.VOLUME);
        levelCompletedClip.setVolume(DuckHunt.VOLUME);
        gunShotClip.setVolume(DuckHunt.VOLUME);


        String introFilePath = "assets/effects/Intro.mp3";
        AudioClip introClip = new AudioClip(Objects.requireNonNull(getClass().getResource(introFilePath)).toExternalForm());
        // Play intro music if it is the first time playing the level
        if (isFirstTime) {
            introClip.play();
            while (introClip.isPlaying()) {
                // Wait for the intro music to finish playing to start the level
            }
        }


        Image backgroundImage = new Image("assets/background/" + prefGround + ".png");
        ImageView background = new ImageView(backgroundImage);
        background.setFitHeight(HEIGHT);
        background.setFitWidth(WIDTH);

        Image foregroundImage = new Image("assets/foreground/" + prefGround + ".png");
        ImageView foreground = new ImageView(foregroundImage);
        foreground.setFitHeight(HEIGHT);
        foreground.setFitWidth(WIDTH);

        primaryStage.getScene().setCursor(Cursor.NONE);
        crossHairImage = new Image("assets/crosshair/" + prefCrossHair + ".png");
        crossHair = new ImageView(new Image("assets/crosshair/" + prefCrossHair + ".png"));
        crossHair.setFitHeight(crossHairImage.getHeight() * SCALE);
        crossHair.setFitWidth(crossHairImage.getWidth() * SCALE);
        crossHair.setLayoutY(HEIGHT / 2 - crossHair.getFitHeight() / 2);
        crossHair.setLayoutX(WIDTH / 2 - crossHair.getFitWidth() / 2);
        primaryStage.getScene().setOnMouseMoved(event -> {
            handleMouseMoved(event, crossHair);
        });
        primaryStage.getScene().setOnMouseDragged(event -> {
            handleMouseMoved(event, crossHair);
        });


        Text levelText = new Text("\nLevel 1/6");
        levelText.setFont(Font.font("Arial", FontWeight.BOLD, 7 * SCALE));
        levelText.setFill(Paint.valueOf("0xeb9a09"));
        levelText.setLayoutX(WIDTH / 2 - levelText.getLayoutBounds().getWidth() / 2);

        Text ammoText = new Text("\nAmmo Left: " + ammo);
        ammoText.setFont(Font.font("Arial", FontWeight.BOLD, 7 * SCALE));
        ammoText.setFill(Paint.valueOf("0xeb9a09"));
        ammoText.setLayoutX(WIDTH - ammoText.getLayoutBounds().getWidth() * 1.1);


        Text gameOverText = new Text("\nGAME OVER!");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 16 * SCALE));
        gameOverText.setFill(Paint.valueOf("0xeb9a09"));
        gameOverText.setLayoutX(WIDTH / 2 - gameOverText.getLayoutBounds().getWidth() / 2);
        gameOverText.setLayoutY(HEIGHT / 2 - gameOverText.getLayoutBounds().getHeight() / 2);

        Text playAgainText = new Text("\n Press ENTER to play again\n\tPress ESC to exit");
        playAgainText.setFont(Font.font("Arial", FontWeight.BOLD, 16 * SCALE));
        playAgainText.setFill(Paint.valueOf("0xeb9a09"));
        playAgainText.setLayoutX(WIDTH / 2 - playAgainText.getLayoutBounds().getWidth() / 2);
        playAgainText.setLayoutY(HEIGHT / 2 - playAgainText.getLayoutBounds().getHeight() / 2 + 7 * SCALE);

        //use timeline to create flashing effect on text
        Timeline timelinePlayAgain = new Timeline(new KeyFrame(Duration.seconds(1), event -> playAgainText.setVisible(false)), new KeyFrame(Duration.seconds(2), event -> playAgainText.setVisible(true)));
        playAgainText.setLayoutY(HEIGHT / 2 - playAgainText.getLayoutBounds().getHeight() / 2 + 22 * SCALE);
        playAgainText.setLayoutX(WIDTH / 2 - playAgainText.getLayoutBounds().getWidth() / 2);
        timelinePlayAgain.setCycleCount(Animation.INDEFINITE);

        Text gameWonText = new Text("\nYOU WIN!");
        gameWonText.setFont(Font.font("Arial", FontWeight.BOLD, 16 * SCALE));
        gameWonText.setFill(Paint.valueOf("0xeb9a09"));
        gameWonText.setLayoutX(WIDTH / 2 - gameWonText.getLayoutBounds().getWidth() / 2);
        gameWonText.setLayoutY(HEIGHT / 2 - gameWonText.getLayoutBounds().getHeight() / 2);

        //use timeline to create flashing effect on text
        Text nextLevelText = new Text("Press ENTER to play next level");
        nextLevelText.setFill(Paint.valueOf("0xeb9a09"));
        nextLevelText.setFont(Font.font("Arial", FontWeight.BOLD, 16 * SCALE));
        Timeline nextLevelTextTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> nextLevelText.setVisible(false)), new KeyFrame(Duration.seconds(2), event -> nextLevelText.setVisible(true)));
        nextLevelText.setLayoutY(HEIGHT / 2 - nextLevelText.getLayoutBounds().getHeight() / 2 + 22 * SCALE);
        nextLevelText.setLayoutX(WIDTH / 2 - nextLevelText.getLayoutBounds().getWidth() / 2);
        nextLevelTextTimeline.setCycleCount(Animation.INDEFINITE);






        primaryStage.getScene().addEventHandler(MouseEvent.MOUSE_CLICKED, event2 -> {
            // Get the width and height of the blueDuck
            double width = blueDuck.boundsInParentProperty().get().getWidth();
            double height = blueDuck.boundsInParentProperty().get().getHeight();

            // Check if the mouse click event occurred within a certain range of the blueDuck's position
            if (event2.getX() >= blueDuck.getX() - 2 * width / 5 && event2.getX() <= blueDuck.getX() + 3 * width / 5 &&
                    event2.getY() >= blueDuck.getY() - 2 * height / 5 && event2.getY() <= blueDuck.getY() + 3 * height / 5) {

                // Check if the blueDuck is not dead and if layout have right number of children
                if (!blueDuckIsDead && layout.getChildren().size() == 6) {
                    duckFallClip.play(); // Play the duck fall sound effect
                    blueDuckIsDead = true;
                    duckIncubator.animatingDeath(blueDuck, layout, SCALE); // Start the death animation for the blueDuck
                    layout.getChildren().remove(blueDuck); // Remove the blueDuck to duckCoordinator to understand a duck died
                }
            }
        });

        primaryStage.getScene().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (!blueDuckIsDead && ammo > 1) { // If blueDuck is not dead and there is more than 1 ammo remaining
                ammo--;
                gunShotClip.play();
                ammoText.setText("\nAmmo Left: " + ammo);
            } else if (blueDuckIsDead && ammo > 0) { // If blueDuck is dead and there is at least 1 ammo remaining
                ammo--;
                gunShotClip.play();
                ammoText.setText("\nAmmo Left: " + ammo);
                ammo = -1;
            } else if (ammo == 1 && layout.getChildren().size() == 6) { // If there is only 1 ammo left and there are still birds remaining
                ammo--;
                gunShotClip.play();
                ammoText.setText("\nAmmo Left: " + ammo);
                lostLevel = true; // Set the lostLevel flag to true indicating that the game should be over
                layout.getChildren().addAll(gameOverText, playAgainText); // Add game over text and play again text to the layout
                timelinePlayAgain.play(); // Start the animation for the play again text
                gameOverClip.play(); // Play the game over sound effect
            }
        });





        layout.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                if (blueDuckIsDead) {
                    layout.getChildren().removeListener(this); // Remove the listener to stop further changes in the children list
                    ammoText.setText("\nAmmo Left: " + ammo);
                    layout.getChildren().addAll(gameWonText, nextLevelText); // Add game won text and next level text to the layout
                    levelCompletedClip.play(); // Play the level completed sound effect
                    nextLevelTextTimeline.play(); // Start the animation for the next level text
                    levelCompleted = true; // Set the levelCompleted flag to true indicating that the level is completed
                }
            }
        });


        primaryStage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {

            if (event.getCode() == KeyCode.ESCAPE && lostLevel) { // If the ESCAPE key is pressed and the level is lost
                layout.getChildren().clear();
                introClip.stop();
                gunShotClip.stop();
                gameOverClip.stop();
                primaryStage.setScene(new StartScreen(primaryStage, WIDTH, HEIGHT,true)); // Set the scene to the StartGameScreen

            }
            else if (event.getCode()== KeyCode.ENTER && lostLevel){
                gameOverClip.stop();
                gunShotClip.stop();
                primaryStage.setScene(new LevelOne(primaryStage, WIDTH, HEIGHT, prefCrossHair, prefGround,false));
            }
            else if (levelCompleted && event.getCode() == KeyCode.ENTER) { // If the ENTER key is pressed and the level is completed
                levelCompletedClip.stop();
                duckFallClip.stop();
                gunShotClip.stop();
                primaryStage.setScene(new LevelTwo(primaryStage, WIDTH, HEIGHT, prefCrossHair, prefGround)); // Set the scene to LevelTwo
            }
        });


        layout.getChildren().addAll(background); //add background first to make it in bottom of layout
        DuckCoordinator incubator = new DuckCoordinator(); // Create an instance of DuckCoordinator

        // Hatch a blue duck horizontally using the DuckCoordinator which is returned as ImageView
        // Add the blue duck as a ImageView to the layout
        blueDuck = incubator.hatchHorizontal(layout, SCALE, WIDTH, HEIGHT, 1, 1, "blue", primaryStage);
        layout.getChildren().add(blueDuck);

        // Add the foreground, levelText, ammoText, and crossHair to the layout
        //foreground should be on top of ducks and background
        //levelText, ammoText, and crossHair should be on top of foreground
        layout.getChildren().addAll(foreground, levelText, ammoText, crossHair);




    }

    private void handleMouseMoved(MouseEvent event, ImageView customCursor) {
        // Set the X position of the custom cursor based on the mouse X position and the width of the cursor
        customCursor.setLayoutX(event.getX() - customCursor.getFitWidth() / 2);

        // Set the Y position of the custom cursor based on the mouse Y position and the height of the cursor
        customCursor.setLayoutY(event.getY() - customCursor.getFitHeight() / 2);
    }



}

