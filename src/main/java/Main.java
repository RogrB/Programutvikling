package main.java;

import controller.GameController;
import controller.UserInputs;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.GameModel;
import view.GameView;

public class Main extends Application {

    public static GameView gv = GameView.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        gv.setup();

        primaryStage.setTitle("Working Title: Pippi");
        Scene scene = new Scene(gv.initGame());

        UserInputs userInputs = new UserInputs(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
