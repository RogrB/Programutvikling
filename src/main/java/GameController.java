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

    public static final double SPEED_MODIFIER = 0.17;

    public Player player = new Player();

    public void setKeyListeners(Scene s){
        s.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE)
                    keyPressedSpace();
                if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
                    keyPressedUp();
                if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
                    keyPressedDown();
            }
        });
        s.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
                    keyReleasedUp();
                if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
                    keyReleasedDown();
            }
        });
    }

    public void keyPressedUp(){
        player.moveUp();
    }

    public void keyPressedDown(){
        player.moveDown();
    }

    public void keyReleasedUp(){
        player.moveStop();
    }

    public void keyReleasedDown(){
        player.moveStop();
    }

    public void keyPressedSpace(){
        player.shoot();
    }

    public void keyReleasedSpace(){

    }

}
