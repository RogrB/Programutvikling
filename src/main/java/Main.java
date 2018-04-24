package main.java;

import controller.UserInputs;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.IdGen;
import view.GameView;
import view.MenuView;

public class Main extends Application {

    public static GameView gv = GameView.getInstance();
    public static MenuView mv = MenuView.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        gv.mvcSetup();

        primaryStage.setTitle("Working Title: Pippi");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            System.out.println(java.lang.Thread.activeCount());
            Platform.exit();
            System.exit(0);});
        //Scene scene = new Scene(gv.initGame());
        Scene scene = new Scene(mv.initScene());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
