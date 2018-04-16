package controller;

import assets.java.Sprite;
import javafx.animation.AnimationTimer;
import model.GameModel;
import model.enemy.*;
import model.levels.LevelData;
import model.levels.LevelLoader;
import model.weapons.*;
import view.GameView;
import view.HUD;
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
    
    HUD hud;

    // Level data
    LevelLoader level2 = new LevelLoader(LevelData.LEVEL4);
    public ArrayList<PowerUp> powerups = new ArrayList();
    ArrayList<Enemy> enemies = level2.getEnemies();

    Iterator<Bullet> iterator;

    public void setup(){
        gm = GameModel.getInstance();
        gv = GameView.getInstance();
        hud = HUD.getInstance();
        enemies = level2.getEnemies();
        powerups = level2.getPowerups();
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
                    gv.render(e);
                    e.shoot();
                }
                if (!powerups.isEmpty()) {
                    for(PowerUp p : powerups) {
                        p.move();
                        gv.render(p);
                    }
                }

                moveAllBullets();
                detectPlayerCollidesWithEnemy();
                detectEnemyShotByPlayer();
                detectPlayerShotByEnemy();
                detectPowerUp();
                hud.renderHUD();
                if (!gm.player.isAlive()) {
                    gv.gameOver();
                    this.stop();
                }
            }
        }; timer.start();
    }


    private void moveAllBullets(){
        for (Bullet bullet : gm.player.getBullets()){
            gv.render(bullet);
        }

        iterator = gm.getEnemyBullets().iterator();
        while(iterator.hasNext()){
            Bullet bullet = iterator.next();
            bullet.setX(bullet.getX() - 12);
            bullet.setY(bullet.getY());
            if(bullet.isOffScreen() || bullet.isReadyToPurge()) { // Skal flyttes til Bullet.purgeThis()
                iterator.remove();
            }
            gv.render(bullet);
        }
    }

    private void detectEnemyShotByPlayer(){
        for(Bullet bullet : gm.player.getBullets()){
            for(Enemy enemy : enemies){
                if(bullet.collidesWith(enemy)){
                    enemy.takeDamage(bullet.getDmg());
                    bullet.hasHit();
                    bullet.clearImage();
                    gv.render(bullet);
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
