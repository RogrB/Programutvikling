package main.java;

import Player.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameController {

    public static final double SPEED_MODIFIER = 0.17;

    public Player player = new Player();


    public void keyPressedUp(){
        player.move("UP");
    }

    public void keyPressedDown(){
        player.move("DOWN");
    }

    public void keyReleasedUp(){
        player.move("STOP");
    }

    public void keyReleasedDown(){
        player.move("STOP");
    }

    public void keyPressedSpace(){
        player.shoot();
    }

    public void keyReleasedSpace(){

    }

}
