package controller;

import javafx.animation.AnimationTimer;
import model.GameModel;
import model.weapons.Bullet;
import view.GameView;

public class GameController {

    // Singleton
    private static GameController inst = new GameController();
    private GameController(){}
    public static GameController getInstance(){ return inst; }

    // MVC-access
    GameModel gm;
    GameView gv;

    public void setup(){
        gm = GameModel.getInstance();
        gv = GameView.getInstance();
        start();
        System.out.println("Controller sin View: " + gv);
        System.out.println("Controller sin Model: " + gm);
    }

    public void start() {

        // ANIMATION TIMER, UPDATES VIEW
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gm.player.update();
                //updateBullets();
            }
        }; timer.start();

    }

    private void updateBullets(){
        for (Bullet b : gm.player.getBullets()){
            System.out.println(b.getX() + " " + b.getY());
            // gv.renderBullet(b.getX(), b.getY());
            //System.out.println(gv.toString()); // prøver å finne årsaken til nullpointerexception gm.tostring funker mens gv.tostring gir feil
        }
    }
}
