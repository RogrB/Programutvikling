package main.java;

import javafx.animation.AnimationTimer;

public class GameLogic {

    // Singleton
    private static GameLogic inst = new GameLogic();
    private GameLogic(){}
    public static GameLogic getInstance() { return inst; }



    public void timer () {

        // ANIMATION TIMER, UPDATES VIEW
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

            }
        }; timer.start();

    }

}
