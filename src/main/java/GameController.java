package main.java;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import player.*;

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
                gl.player.moveUp();
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
                gl.player.moveDown();
        });

        s.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
                gl.player.moveStop();
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
                gl.player.moveStop();
        });
    }

}
