package controller;

import javafx.animation.AnimationTimer;
import model.GameModel;
import model.enemy.Enemy;
import model.levels.LevelData;
import model.levels.LevelLoader;
import model.weapons.*;
import model.weapons.damage.*;
import view.GameView;


import java.util.ArrayList;
import java.util.Iterator;

public class GameController {

    // Singleton
    private static GameController inst = new GameController();
    private GameController(){}
    public static GameController getInstance(){ return inst; }

    // MVC-access
    GameModel gm;
    GameView gv;
    
    private boolean tickLeft = true; // Om enemies skal ticke mot venstre

    // Level data
    public ArrayList<Enemy> enemies;
    LevelLoader level2;
    ArrayList<Damage> damage = new ArrayList<>();

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
                purge();
                gm.player.update();
                for(Enemy e : enemies){
                    e.update();
                    e.shoot();
                }

                updateBullets();
                detectPlayerCollidesWithEnemy();
                detectEnemyShotByPlayer();
                detectPlayerShotByEnemy();
                if (tickLeft) {
                    tickLeft();
                }
                if (!gm.player.isAlive()) {
                    gv.gameOver();
                    this.stop();
                }
            }
        }; timer.start();

    }


    private void updateBullets(){
        for (Bullet bullet : gm.player.getBullets()){
            gv.renderBullet(bullet);
        }
        for(Bullet bullet : gm.getEnemyBullets()) {
            bullet.setX(bullet.getX() - 12);
            gv.renderEnemyBullet(bullet);
        }
    }

    private void detectEnemyShotByPlayer(){
        for(Bullet bullet : gm.player.getBullets()){
            for(Enemy enemy : enemies){
                if(bullet.collidesWith(enemy)){
                    enemy.takeDamage(bullet.getDmg());
                    bullet.hasHit();
                    bullet.clearImage();
                    gv.renderBullet(bullet);
                }
            }
        }
    }

    private void detectPlayerShotByEnemy(){
        for(Bullet bullet : gm.getEnemyBullets()){
            if(bullet.collidesWith(gm.player)){
                gm.player.takeDamage();
                bullet.hasHit();
            }
        }
    }

    private void detectPlayerCollidesWithEnemy(){
        for (Enemy enemy: enemies) {
            if(enemy.collidesWith(gm.player)){
                gm.player.takeDamage();
                enemy.takeDamage();
            }
        }
    }

    // Metode for å sjekke om player ble truffet av enemy - ikke prosjektil
    /*public void detectCollision() {
        for (Enemy enemy: enemies) {
            if (enemy.getX() < gm.player.getX() + gm.player.getWidth() && enemy.getY() < gm.player.getY() + gm.player.getHeight()) {
                if (gm.player.getX() < enemy.getX() + enemy.getWidth() && gm.player.getY() < enemy.getY() + enemy.getHeight()) {
                    System.out.println("Player hit by enemy object");
                    if (!gm.player.isImmune()) {
                        if (gm.player.getHealth() == 1) {
                            gm.player.getSprite().setImage(null);
                        }
                        else {
                            gm.player.takeDamage();
                        }
                    }
                }
            }
        }
    }*/

    private void purge(){
        Iterator<Enemy> enemyIterator = enemies.iterator();
        if(enemies.size() == 0){
            return;
        }
        Enemy e = enemyIterator.next();
        if(enemies.size() == 1){
            if(!e.isAlive()){
                System.out.println("Removing enemy");
                enemyIterator.remove();
            }
        }
        while(enemyIterator.hasNext()){
            System.out.println("Hey there");
            if(!e.isAlive()){
                System.out.println("Removing enemy");
                enemyIterator.remove();
                e = null;

            }
        }
    }
    
    private void tickLeft() {
        for (Enemy enemy: enemies) {
            enemy.setX(enemy.getX() - enemy.getLeftTick());
        }
    }
    
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    
}
