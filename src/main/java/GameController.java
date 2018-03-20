package main.java;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class GameController {
    // Singleton
    private static GameController inst = new GameController();
    private GameController(){}
    public static GameController getInstance(){ return inst; }

    GameLogic gl = GameLogic.getInstance();

    public void setKeyListeners(Scene s){
        s.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE)
                gl.player.shoot();
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
                gl.player.move("UP");
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
                gl.player.move("DOWN");
        });

        s.setOnMouseClicked(event ->{
           if(event.getButton() == MouseButton.PRIMARY){
               String msg =
                       "(x: "       + event.getX()      + ", y: "       + event.getY()       + ") -- " +
                               "(sceneX: "  + event.getSceneX() + ", sceneY: "  + event.getSceneY()  + ") -- " +
                               "(screenX: " + event.getScreenX()+ ", screenY: " + event.getScreenY() + ")";
               System.out.println(msg);
           }
        });

        s.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
                gl.player.move("STOP");
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
                gl.player.move("STOP");
        });
    }

}
