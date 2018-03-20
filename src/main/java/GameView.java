package main.java;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameView extends Application {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    GameController gc = GameController.getInstance();

    // Background Image
    String imgpath = "image/background.jpg";
    Image img = new Image(imgpath);
    BackgroundImage bg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Working Title: Pippi");
        Scene scene = new Scene(initGame());

        gc.setKeyListeners(scene);

        primaryStage.setScene(scene);
        primaryStage.show();


        // ANIMATION TIMER, UPDATES VIEW
        /*AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.player.update();

            }
        }; timer.start();*/
    }

    private Parent initGame() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);
        root.setBackground(new Background(bg));

        root.getChildren().addAll(gc.gl.player.getSprite());

        return root;
    }

}
