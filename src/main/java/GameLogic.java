package main.java;

import javafx.animation.AnimationTimer;
import player.Player;

public class GameLogic {

    // Singleton
    private static GameLogic inst = new GameLogic();
    private GameLogic(){ start(); }
    public static GameLogic getInstance(){ return inst; }

    public static final double SPEED_MODIFIER = 0.17;
    public Player player = new Player();

    public void start() {

        // ANIMATION TIMER, UPDATES VIEW
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.update();

            }
        }; timer.start();

    }

}
