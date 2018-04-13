package controller;

import assets.java.Sprite;
import javafx.animation.AnimationTimer;
import model.GameModel;
import model.enemy.*;
import model.levels.LevelData;
import model.levels.LevelLoader;
import model.weapons.*;
import model.weapons.damage.*;
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
        level2 = new LevelLoader(LevelData.LEVEL2);
        enemies = level2.getEnemies();
        powerups.add(new PowerUp(Sprite.POWERUP, 1220, 643));
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
                if (!powerups.isEmpty()) {
                    for(PowerUp p : powerups) {
                        p.move();
                        gv.renderPowerUp(p);
                    }
                }

                updateBullets();
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


    private void updateBullets(){
        for (Bullet bullet : gm.player.getBullets()){
            gv.renderBullet(bullet);
        }
        for(Bullet bullet : gm.getEnemyBullets()) {
            bullet.setX(bullet.getX() - 12);
            bullet.setY(bullet.getY());
            gv.renderBullet(bullet);
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
    
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    
}
