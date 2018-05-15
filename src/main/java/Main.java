package main.java;

import assets.java.SoundManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.GameView;
import view.MenuView;

/**
 * The entry point for the application.
 *
 * @author Jonas Ege Carlsen
 * @author Åsmund Røst Wien
 * @author Roger Birkenes Solli
 */
public class Main extends Application {

    /**
     * Method to access the GameView singleton object.
     */
    private static GameView gv = GameView.getInstance();

    /**
     * Method to access the MenuView singleton object.
     */
    private static MenuView mv = MenuView.getInstance();

    /**
     * Method used to start the JavaFX application.
     * Also used to set stage settings and launching
     * the SoundManager.
     * @param primaryStage The stage used to launch the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        gv.mvcSetup();

        primaryStage.setTitle("Aero");
        primaryStage.getIcons().add(new Image("assets/image/player/ship/playerShip2_red.png"));
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
