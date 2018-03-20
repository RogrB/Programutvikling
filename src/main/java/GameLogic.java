package main.java;

import enemy.Enemy;
import javafx.animation.AnimationTimer;
import player.Player;

import java.util.ArrayList;

public class GameLogic {

    // Singleton
    private static GameLogic inst = new GameLogic();
    private GameView gw = new GameView();
    private GameLogic(){ start(); }
    public static GameLogic getInstance() { return inst; }

    public static final double SPEED_MODIFIER = 0.17;
    public Player player = new Player();
    public ArrayList<Enemy> enemies = gw.getEnemies();




    public void start() {

        // ANIMATION TIMER, UPDATES VIEW
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.update();
                for(Enemy e : enemies){
                    e.update();
                    System.out.println(e.getX());
                }

            }
        }; timer.start();

    }

}
