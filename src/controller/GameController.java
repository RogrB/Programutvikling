package controller;

import javafx.animation.AnimationTimer;
import model.GameModel;
import model.enemy.Enemy;
import model.levels.LevelData;
import model.levels.LevelLoader;
import model.weapons.Bullet;
import view.GameView;

import java.util.ArrayList;

public class GameController {

    // Singleton
    private static GameController inst = new GameController();
    private GameController(){}
    public static GameController getInstance(){ return inst; }

    // MVC-access
    GameModel gm;
    GameView gv;
    ArrayList<Enemy> enemies;
    LevelLoader level2;

    public void setup(){
        gm = GameModel.getInstance();
        gv = GameView.getInstance();
        level2 = new LevelLoader(LevelData.LEVEL2);
        enemies = level2.getEnemies();
    }

    public void start() {

        // ANIMATION TIMER, UPDATES VIEW
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gm.player.update();
                for(Enemy e : enemies){
                    e.update();
                }

                updateBullets();
                detectHit();
                detectCollision();
                detectDamage();
            }
        }; timer.start();

    }


    private void updateBullets(){
        for (Bullet bullet : gm.player.getBullets()){
            gv.renderBullet(bullet);
        }
    }
    
    public void detectHit() {
        // Metode for å sjekke om playerbullet traff enemy
        for (Bullet bullet : gm.player.getBullets()) {
            for (Enemy enemy : enemies) {
                if (enemy.getX() < bullet.getX() + bullet.getWidth() && enemy.getY() < bullet.getY() + bullet.getHeight()) {
                    if (bullet.getX() < enemy.getX() + enemy.getWidth() && bullet.getY() < enemy.getY() + enemy.getHeight()) {
                        // Enemy got hit
                        System.out.println("HIT!");
                        gm.player.takeDamage(); // Tester immunityframes her kun for trigger
                        // Do something
                    }
                }
            }
        }
    }
    
    public void detectCollision() {
        // Metode for å sjekke om player ble truffet av enemy - ikke prosjektil
        for (Enemy enemy: enemies) {
            if (enemy.getX() < gm.player.getX() + gm.player.getWidth() && enemy.getY() < gm.player.getY() + gm.player.getHeight()) {
                if (gm.player.getX() < enemy.getX() + enemy.getWidth() && gm.player.getY() < enemy.getY() + enemy.getHeight()) {
                    System.out.println("Player hit by enemy object");
                    if (!gm.player.getImmunity()) {
                        if (gm.player.getHealth() == 1) {
                            gameOver();
                        }
                        else {
                            gm.player.setImmunity(true);
                            gm.player.takeDamage();
                        }
                    }
                }
            }
        }
    }
    
    public void detectDamage() {
        // Metode for å sjekke om player ble truffet av enemy prosjektil
        // Må implementere enemy skytemekanisme
    }
    
    public void gameOver() {
        // Is ded!
        System.out.println("Game Over!");
    }
    
}
