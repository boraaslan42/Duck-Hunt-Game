import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DuckCoordinator {
    int count;
    String color;
    private int duxDirection = 1;
    private int duyDirection = 1;
    private Timeline timelineDuck;

    public ImageView hatchDiagonal(Pane layout, double SCALE, double WIDTH, double HEIGHT, int duxDirection, int duyDirection, String color, Stage primaryStage) {
        this.color = color;
        this.count = 1;
        this.duyDirection = duyDirection;
        this.duxDirection = duxDirection;
        Image duck = new Image("assets/duck_" + color + "/3.png");
        ImageView duckView = new ImageView(duck);
        duckView.setX(WIDTH / 4);
        duckView.setY(HEIGHT / 2);
        duckView.scaleXProperty().set(SCALE * duxDirection);
        duckView.scaleYProperty().set(SCALE * duyDirection);

        timelineDuck = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (duckView.getScene() == null || duckView.getScene() instanceof StartScreen || duckView.getScene() instanceof SelectionScreen) {
                timelineDuck.stop();
                isDuckDead(duckView, SCALE, layout, primaryStage);

            } else {
                moveDuckDiagonal(duckView, WIDTH, HEIGHT);
                animateDuckDiagonal(duckView, layout, SCALE);
            }

        }));
        timelineDuck.setCycleCount(Timeline.INDEFINITE);
        timelineDuck.play();

        return duckView;
    }

    public void closeDuck() {
        timelineDuck.stop();
    }


    private void moveDuckDiagonal(ImageView duck, double WIDTH, double HEIGHT) {
        // Get the current position of the ball
        double x = duck.getX();
        double y = duck.getY();

        // Calculate the new position of the ball
        x += 50 * duxDirection;//speed
        y -= 50 * duyDirection;//speed
        Bounds bounds = duck.localToScene(duck.getBoundsInLocal());

        double width = bounds.getWidth();
        double height = bounds.getHeight();

        // Check if the ball hits the walls and change direction accordingly
        if (x <= width / 3 || x + width / 1.75 >= WIDTH) {
            duxDirection *= -1;
        }
        if (y <= height / 3 || y >= HEIGHT - height / 1.75) {

            duyDirection *= -1;
        }

        // Set the new position of the ball
        duck.setX(x);
        duck.setY(y);
    }

    private void animateDuckDiagonal(ImageView duckView, Pane root, double SCALE) {
        if (duxDirection == -1 && duyDirection == 1) {
            duckView.setImage(new Image("assets/duck_" + color + "/" + count + ".png"));
            duckView.setScaleX(-SCALE);
            duckView.setScaleY(SCALE);


        } else if (duxDirection == 1 && duyDirection == 1) {
            duckView.setImage(new Image("assets/duck_" + color + "/" + count + ".png"));
            duckView.setScaleX(SCALE);
            duckView.setScaleY(SCALE);

        } else if (duxDirection == -1 && duyDirection == -1) {
            duckView.setImage(new Image("assets/duck_" + color + "/" + count + ".png"));
            duckView.setScaleX(-SCALE);
            duckView.setScaleY(-SCALE);

        } else if (duxDirection == 1 && duyDirection == -1) {
            duckView.setImage(new Image("assets/duck_" + color + "/" + count + ".png"));
            duckView.setScaleX(SCALE);
            duckView.setScaleY(-SCALE);


        }
        count++;
        if (count == 3) {
            count = 1;
        }


    }

    public void isDuckDead(ImageView duckView, double SCALE, Pane layout, Stage primaryStage) {
        if (duckView.getScene() == null) {
            timelineDuck.getKeyFrames().clear();
            try {
                layout.getChildren().add(1, duckView);
            } catch (Exception e) {
                timelineDuck.stop();
            }
            duckView.setX(duckView.getX() - duckView.getFitWidth());
            timelineDuck.stop();


            Timeline deathTimeline = new Timeline();

            if (duxDirection == -1) {
                duckView.setImage(new Image("assets/duck_" + color + "/7.png"));
                duckView.setScaleX(-SCALE);
            } else {
                duckView.setImage(new Image("assets/duck_" + color + "/7.png"));
                duckView.setScaleX(SCALE);
            }

            deathTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), event -> {
                duckView.setY(duckView.getY() + 20 * SCALE);
                if (duxDirection == -1) {
                    duckView.setImage(new Image("assets/duck_" + color + "/8.png"));
                    duckView.setScaleY(SCALE);
                    duckView.setScaleX(-SCALE);
                } else {
                    duckView.setImage(new Image("assets/duck_" + color + "/8.png"));
                    duckView.setScaleY(SCALE);
                    duckView.setScaleX(SCALE);
                }

            }));
            deathTimeline.setCycleCount(10);


            deathTimeline.play();



        }

    }

    public void animatingDeath(ImageView duckView, Pane root, double SCALE) {

        if (duxDirection == -1 && duyDirection == 1) {
            duckView.setImage(new Image("assets/duck_" + color + "/7.png"));
            duckView.setScaleX(-SCALE);
            duckView.setScaleY(SCALE);
        }
    }

    public ImageView hatchHorizontal(Pane layout, double SCALE, double WIDTH, double HEIGHT, int duxDirection, int duyDirection, String color, Stage primaryStage) {
        this.color = color;
        this.count = 4;
        this.duyDirection = duyDirection;
        this.duxDirection = duxDirection;
        Image duck = new Image("assets/duck_" + color + "/6.png");
        ImageView duckView = new ImageView(duck);
        duckView.setX(WIDTH / 2);
        duckView.setY(HEIGHT / (3));
        duckView.scaleXProperty().set(SCALE * duxDirection);
        duckView.scaleYProperty().set(SCALE * duyDirection);

        timelineDuck = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (duckView.getScene() == null || duckView.getScene() instanceof StartScreen || duckView.getScene() instanceof SelectionScreen) {
                timelineDuck.stop();
                isDuckDead(duckView, SCALE, layout, primaryStage);

            } else {
                moveDuckHorizontal(duckView, WIDTH, HEIGHT);
                animateDuckHorizontal(duckView, layout, SCALE);
                isDuckDead(duckView, SCALE, layout, primaryStage);
            }

        }));

        timelineDuck.setCycleCount(Timeline.INDEFINITE);
        timelineDuck.play();
        return duckView;
    }

    private void animateDuckHorizontal(ImageView duckView, Pane root, double SCALE) {

        if (duxDirection == 1) {
            duckView.setImage(new Image("assets/duck_" + color + "/" + count + ".png"));
            duckView.setScaleX(SCALE);


        } else if (duxDirection == -1) {
            duckView.setImage(new Image("assets/duck_" + color + "/" + count + ".png"));
            duckView.setScaleX(-SCALE);

        }
        count++;
        if (count == 6) {
            count = 4;
        }


    }

    private void moveDuckHorizontal(ImageView duck, double WIDTH, double HEIGHT) {

        // Get the current position of the ball
        double x = duck.getX();

        // Calculate the new position of the ball
        x += 50 * duxDirection;//speed
        Bounds bounds = duck.localToScene(duck.getBoundsInLocal());

        double width = bounds.getWidth();

        // Check if the ball hits the walls and change direction accordingly
        if (x <= width / 3 || x + width / 1.75 >= WIDTH) {
            duxDirection *= -1;
        }

        // Set the new position of the ball
        duck.setX(x);
    }


}
