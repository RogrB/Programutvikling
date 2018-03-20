package main.java;

import player.*;

public class GameController {

    // Singleton
    private static GameController inst = new GameController();
    private GameController(){}
    public static GameController getInstance() { return inst; }

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
