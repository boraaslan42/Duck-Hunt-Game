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

public class LevelSix extends Scene {
    Image crossHairImage;
    ImageView crossHair;
    Pane layout;

    int ammo = 9;
    double WIDTH;
    double HEIGHT;
    double SCALE;
    ImageView blueDuck;
    ImageView redDuck;
    ImageView blackDuck;
    DuckCoordinator duckIncubator;
    boolean blueDuckIsDead = false;
    boolean redDuckIsDead = false;
    boolean blackDuckIsDead = false;
    boolean lostLevel = false;
    boolean levelCompleted = false;

    public LevelSix(Stage primaryStage, double WIDTH, double HEIGHT, int prefCrossHair, int prefGround) {
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

        String gameCompletedFilePath = "assets/effects/GameCompleted.mp3";
        AudioClip gameCompletedClip = new AudioClip(Objects.requireNonNull(getClass().getResource(gameCompletedFilePath)).toExternalForm());

        String duckFallFilePath = "assets/effects/DuckFalls.mp3";
        AudioClip duckFallClip = new AudioClip(Objects.requireNonNull(getClass().getResource(duckFallFilePath)).toExternalForm());

        duckFallClip.setVolume(DuckHunt.VOLUME);
        gameOverClip.setVolume(DuckHunt.VOLUME);
        gameCompletedClip.setVolume(DuckHunt.VOLUME);
        gunShotClip.setVolume(DuckHunt.VOLUME);

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


        Text levelText = new Text("\nLevel 6/6");
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

        Timeline timelinePlayAgain = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> playAgainText.setVisible(false)),
                new KeyFrame(Duration.seconds(2), event -> playAgainText.setVisible(true))
        );
        playAgainText.setLayoutY(HEIGHT / 2 - playAgainText.getLayoutBounds().getHeight() / 2 + 22 * SCALE);
        playAgainText.setLayoutX(WIDTH / 2 - playAgainText.getLayoutBounds().getWidth() / 2);
        timelinePlayAgain.setCycleCount(Animation.INDEFINITE);

        Text gameWonText = new Text("\nYou have completed the game!");
        gameWonText.setFont(Font.font("Arial", FontWeight.BOLD, 16 * SCALE));
        gameWonText.setFill(Paint.valueOf("0xeb9a09"));
        gameWonText.setLayoutX(WIDTH / 2 - gameWonText.getLayoutBounds().getWidth() / 2);
        gameWonText.setLayoutY(HEIGHT / 2 - gameWonText.getLayoutBounds().getHeight() / 2);

        Text nextLevelText = new Text("Press ENTER to play again\n\tPress ESC to exit");
        nextLevelText.setFill(Paint.valueOf("0xeb9a09"));
        nextLevelText.setFont(Font.font("Arial", FontWeight.BOLD, 13 * SCALE));
        Timeline nextLevelTextTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> nextLevelText.setVisible(false)),
                new KeyFrame(Duration.seconds(2), event -> nextLevelText.setVisible(true))
        );
        nextLevelText.setLayoutY(HEIGHT / 2 - nextLevelText.getLayoutBounds().getHeight() / 2 + (26 * SCALE));
        nextLevelText.setLayoutX(WIDTH / 2 - nextLevelText.getLayoutBounds().getWidth() / 2);
        nextLevelTextTimeline.setCycleCount(Animation.INDEFINITE);


        primaryStage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE && (lostLevel || levelCompleted)) {
                gameOverClip.stop();
                layout.getChildren().clear();
                gunShotClip.stop();
                gameCompletedClip.stop();
                primaryStage.setScene(new StartScreen(primaryStage, WIDTH, HEIGHT,true)); // Set the scene to the StartGameScreen

            }
            else if ((event.getCode() == KeyCode.ENTER) &&(lostLevel || levelCompleted)){
                primaryStage.setScene(new LevelOne(primaryStage, WIDTH, HEIGHT, prefCrossHair, prefGround, false));
                gameCompletedClip.stop();
                gameOverClip.stop();
                gunShotClip.stop();
                duckFallClip.stop();
                duckFallClip.stop();
                duckFallClip.stop();

            }

        });
        duckIncubator = new DuckCoordinator();

        primaryStage.getScene().addEventHandler(MouseEvent.MOUSE_CLICKED, event2 -> {
            if (checkCoordinates(event2, blueDuck) || checkCoordinates(event2, redDuck) || checkCoordinates(event2, blackDuck)) {
                gunShotClip.play();
            }

            if (checkCoordinates(event2, blueDuck) && checkCoordinates(event2, redDuck) && checkCoordinates(event2, blackDuck)) {
                if (blueDuckIsDead || redDuckIsDead && blackDuckIsDead) {
                    return;
                }
                blueDuckIsDead = true;
                redDuckIsDead = true;
                blackDuckIsDead = true;
                duckFallClip.play();
                duckFallClip.play();
                duckFallClip.play();
                duckIncubator.animatingDeath(blueDuck, layout, SCALE);
                duckIncubator.animatingDeath(redDuck, layout, SCALE);
                duckIncubator.animatingDeath(blackDuck, layout, SCALE);
                layout.getChildren().removeAll(blueDuck, redDuck, blackDuck);//dead animation}

            } else if (checkCoordinates(event2, blueDuck) && checkCoordinates(event2, redDuck)) {
                blueDuckIsDead = true;
                duckFallClip.play();
                duckFallClip.play();
                redDuckIsDead = true;
                duckIncubator.animatingDeath(blueDuck, layout, SCALE);
                duckIncubator.animatingDeath(redDuck, layout, SCALE);
                layout.getChildren().removeAll(blueDuck, redDuck);//dead animation}

            } else if (checkCoordinates(event2, blueDuck) && checkCoordinates(event2, blackDuck)) {
                blueDuckIsDead = true;
                duckFallClip.play();
                duckFallClip.play();
                blackDuckIsDead = true;
                duckIncubator.animatingDeath(blueDuck, layout, SCALE);
                duckIncubator.animatingDeath(blackDuck, layout, SCALE);
                layout.getChildren().removeAll(blueDuck, blackDuck);//dead animation}

            } else if (checkCoordinates(event2, blackDuck) && checkCoordinates(event2, redDuck)) {
                blackDuckIsDead = true;
                redDuckIsDead = true;
                duckFallClip.play();
                duckFallClip.play();
                duckIncubator.animatingDeath(blackDuck, layout, SCALE);
                duckIncubator.animatingDeath(redDuck, layout, SCALE);
                layout.getChildren().removeAll(blackDuck, redDuck);//dead animation}

            } else if (checkCoordinates(event2, blueDuck)) {
                if (!blueDuckIsDead && layout.getChildren().size() == 8) {
                    blueDuckIsDead = true;
                    duckFallClip.play();
                    duckIncubator.animatingDeath(blueDuck, layout, SCALE);
                    layout.getChildren().remove(blueDuck);//dead animation}
                }
            } else if (checkCoordinates(event2, redDuck)) {
                if (!redDuckIsDead && layout.getChildren().size() == 8) {
                    redDuckIsDead = true;
                    duckFallClip.play();
                    duckIncubator.animatingDeath(redDuck, layout, SCALE);
                    layout.getChildren().remove(redDuck);//dead animation}
                }
            } else if (checkCoordinates(event2, blackDuck)) {
                if (!blackDuckIsDead && layout.getChildren().size() == 8) {
                    duckFallClip.play();
                    blackDuckIsDead = true;
                    duckIncubator.animatingDeath(blackDuck, layout, SCALE);
                    layout.getChildren().remove(blackDuck);//dead animation}
                }
            }



        });


        primaryStage.getScene().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (ammo == 1 && anyDuckAlive()) {
                ammo--;
                gunShotClip.play();
                ammoText.setText("\nAmmo Left: " + ammo);
                lostLevel = true;
                gameOverClip.play();
                layout.getChildren().addAll(gameOverText, playAgainText);
                timelinePlayAgain.play();
            } else if (anyDuckAlive() && ammo > 1) {//DIDNT KILL ALL BIRDS
                ammo--;
                gunShotClip.play();
                ammoText.setText("\nAmmo Left: " + ammo);
            }
        });


        layout.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                if (!anyDuckAlive() && layout.getChildren().size() == 7) {
                    layout.getChildren().removeListener(this);
                    ammoText.setText("\nAmmo Left: " + (ammo - 1));
                    gunShotClip.play();

                    gameCompletedClip.play();
                    levelCompleted = true;
                    layout.getChildren().addAll(gameWonText, nextLevelText);
                    nextLevelTextTimeline.play();
                }
            }
        });


        layout.getChildren().addAll(background);
        DuckCoordinator incubator = new DuckCoordinator();
        DuckCoordinator incubatorNo2 = new DuckCoordinator();
        DuckCoordinator incubatorNo3 = new DuckCoordinator();
        redDuck = incubator.hatchDiagonal(layout, SCALE, WIDTH, HEIGHT, -1, 1, "red", primaryStage);
        blueDuck = incubatorNo2.hatchDiagonal(layout, SCALE, WIDTH, HEIGHT, 1, 1, "blue", primaryStage);
        blackDuck = incubatorNo3.hatchDiagonal(layout, SCALE, WIDTH, HEIGHT, 1, -1, "black", primaryStage);
        layout.getChildren().add(blueDuck);
        layout.getChildren().add(redDuck);
        layout.getChildren().add(blackDuck);
        layout.getChildren().addAll(foreground, levelText, ammoText, crossHair);


    }

    private boolean anyDuckAlive() {
        return !blueDuckIsDead || !redDuckIsDead || !blackDuckIsDead;
    }

    private boolean checkCoordinates(MouseEvent event, ImageView duck) {
        double width = blueDuck.boundsInParentProperty().get().getWidth();
        double height = blueDuck.boundsInParentProperty().get().getHeight();
        double cursorX = event.getX();
        double cursorY = event.getY();

        return (cursorX >= duck.getX() - 2 * width / 5 && cursorX <= duck.getX() + 3 * width / 5 &&
                cursorY >= duck.getY() - 2 * height / 5 && cursorY <= duck.getY() + 3 * height / 5);
    }

    private void handleMouseMoved(MouseEvent event, ImageView customCursor) {
        customCursor.setLayoutX(event.getX() - customCursor.getFitWidth() / 2);
        customCursor.setLayoutY(event.getY() - customCursor.getFitHeight() / 2);
    }


}

