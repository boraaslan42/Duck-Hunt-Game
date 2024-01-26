import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import java.util.Objects;

/**
 * The MusicPlayer class represents a player for background music in a JavaFX application.
 * It uses the AudioClip class from the JavaFX library to play the music.
 */
public class MusicPlayer {

    private final AudioClip audioClip;

    /**
     * Constructs a MusicPlayer object.
     *
     * @param primaryStage the primary stage of the JavaFX application
     */
    public MusicPlayer(Stage primaryStage) {
        String musicFilePath = "assets/effects/Title.mp3";

        audioClip = new AudioClip(Objects.requireNonNull(getClass().getResource(musicFilePath)).toExternalForm());
        audioClip.setCycleCount(AudioClip.INDEFINITE);
        audioClip.setVolume(DuckHunt.VOLUME);

        primaryStage.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (!(newScene instanceof SelectionScreen) && !(newScene instanceof StartScreen)) {
                audioClip.stop(); // Stop the music when the scene changes
            }
        });
    }

    /**
     * Plays the background music.
     */
    public void play() {
        audioClip.play();
    }
}
