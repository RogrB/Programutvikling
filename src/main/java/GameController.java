package main.java;

import Player.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameController {

    public static final double SPEED_MODIFIER = 0.17;

    public Player player = new Player();


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
