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

    AnimationTimer gameMainTimer;

    public Iterator<Bullet> bulletIterator;
    public Iterator<Enemy> enemyIterator;
    public Iterator<PowerUp> powerUpIterator;

    public void setup(){
        gm = GameModel.getInstance();
        gv = GameView.getInstance();
        hud = HUD.getInstance();
        enemies = level2.getEnemies();
        powerups = level2.getPowerups();
    }

    public void start() {

        // ANIMATION TIMER, UPDATES VIEW
        gameMainTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                gm.player.update();
                if(gm.player.hasShield())
                    gv.renderShield();

                moveEnemies();
                movePowerups();
                moveAllBullets();

                detectPlayerCollidesWithEnemy();
                detectEnemyShotByPlayer();
                detectPlayerShotByEnemy();
                detectPlayerCollidesWithPowerUp();

                hud.renderHUD();

                detectGameOver();
            }
        }; gameMainTimer.start();
    }

    private void moveEnemies(){
        enemyIterator = enemies.iterator();
        while(enemyIterator.hasNext()){
            Enemy enemy = enemyIterator.next();
            enemy.update(enemyIterator);
            gv.render(enemy);
        }
    }

    private void movePowerups(){
        powerUpIterator = powerups.iterator();
        while (powerUpIterator.hasNext()){
            PowerUp powerUp = powerUpIterator.next();
            powerUp.update(-2, 0, powerUpIterator);
            gv.render(powerUp);
        }
    }

    private void moveAllBullets(){
        bulletIterator = gm.player.getBullets().iterator();
        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            bullet.update(20, 0, bulletIterator);
            gv.render(bullet);
        }

        bulletIterator = gm.getEnemyBullets().iterator();
        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            bullet.update(-12, 0, bulletIterator);
            gv.render(bullet);
        }
    }

    private void detectEnemyShotByPlayer(){
        for(Bullet bullet : gm.player.getBullets()){
            for(Enemy enemy : enemies){
                if(bullet.collidesWith(enemy)){
                    enemy.takeDamage(bullet.getDmg());
                    bullet.hasHit();
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
    
    private void detectPlayerCollidesWithPowerUp() {
        if (!powerups.isEmpty()) {
            for (PowerUp p : powerups) {
                if(p.collidesWith(gm.player)) {
                    p.powerUp();
                }
            }
        }
    }

    private void detectGameOver(){
        if (!gm.player.isAlive()) {
            gv.gameOver();
            gameMainTimer.stop();
        }
    }
    
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    
}
