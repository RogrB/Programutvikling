package main.java;

import assets.java.SoundManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.GameView;
import view.MenuView;

public class Main extends Application {

    private static GameView gv = GameView.getInstance();
    private static MenuView mv = MenuView.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        gv.mvcSetup();

        primaryStage.setTitle("Working Title: Pippi");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            SoundManager.getInst().shutdown();
            Platform.exit();
            System.exit(0);});
        Scene scene = new Scene(mv.initScene());
        SoundManager.getInst().playMusic("music_menu");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
