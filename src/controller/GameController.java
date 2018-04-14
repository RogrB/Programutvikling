package controller;

import assets.java.Sprite;
import javafx.animation.AnimationTimer;
import model.GameModel;
import model.enemy.*;
import model.levels.LevelLoader;
import model.weapons.*;
import view.GameView;
import model.PowerUp;

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

    // Level data
    public ArrayList<Enemy> enemies;
    public ArrayList<PowerUp> powerups = new ArrayList();
    LevelLoader level2;

    public void setup(){
        gm = GameModel.getInstance();
        gv = GameView.getInstance();
        enemies = gv.getEnemies();
        powerups.add(new PowerUp(Sprite.WEAPON_POWERUP, 1220, 643));
        powerups.add(new PowerUp(Sprite.HEALTH_POWERUP, 1420, 557));
        powerups.add(new PowerUp(Sprite.SHIELD_POWERUP, 800, 427));
    }

    public void start() {

        // ANIMATION TIMER, UPDATES VIEW
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                purge();
                gm.player.update();
                if(gm.player.hasShield()) {
                    gv.renderShield();
                }
                for(Enemy e : enemies){
                    e.update();
                    gv.renderImage(e);
                    e.shoot();
                }
                if (!powerups.isEmpty()) {
                    for(PowerUp p : powerups) {
                        p.move();
                        gv.renderImage(p);
                    }
                }

                moveAllBullets();
                detectPlayerCollidesWithEnemy();
                detectEnemyShotByPlayer();
                detectPlayerShotByEnemy();
                detectPowerUp();
                if (!gm.player.isAlive()) {
                    gv.gameOver();
                    this.stop();
                }
            }
        }; timer.start();
    }


    private void moveAllBullets(){
        for (Bullet bullet : gm.player.getBullets()){
            gv.renderImage(bullet);
        }

        Iterator<Bullet> bulletIterator = gm.getEnemyBullets().iterator();
        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();

            if(bullet.isOffScreenLeft()) {
                bullet.purgeThis();
            } else {
                bullet.setX(bullet.getX() - 12);
                bullet.setY(bullet.getY());
                gv.renderImage(bullet);
            }
        }

        // Bytte ut med Iterator?
        /*for(Bullet bullet : gm.getEnemyBullets()) {
            if(bullet.isOffScreenLeft())
                bullet.purgeThis(); // Gir null pointer exception
            bullet.setX(bullet.getX() - 12);
            bullet.setY(bullet.getY());
            gv.renderImage(bullet);
        }*/
    }

    private void detectEnemyShotByPlayer(){
        for(Bullet bullet : gm.player.getBullets()){
            for(Enemy enemy : enemies){
                if(bullet.collidesWith(enemy)){
                    enemy.takeDamage(bullet.getDmg());
                    bullet.hasHit();
                    bullet.clearImage();
                    gv.renderImage(bullet);
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
    
    private void detectPowerUp() {
        if (!powerups.isEmpty()) {
            for (PowerUp p : powerups) {
                if(p.collidesWith(gm.player)) {
                    p.powerUp();
                }
            }
        }
    }

    private void purge(){
        Iterator<Enemy> enemyIterator = enemies.iterator();
        if(enemies.size() == 0){
            return;
        }
        if(enemies.size() == 1){
            Enemy e = enemyIterator.next();
            if(!e.isAlive()){
                System.out.println("Removing enemy");
                gv.clearLast(e);
                enemyIterator.remove();
            }
        }
        while(enemyIterator.hasNext()){
            Enemy e = enemyIterator.next();
            if(!e.isAlive()){
                System.out.println("Removing enemy");
                enemyIterator.remove();
                e = null;
            }
        }
    }
    
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    
}
