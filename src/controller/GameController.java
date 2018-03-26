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
    ArrayList<Enemy> enemies;
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
                gm.player.update();
                for(Enemy e : enemies){
                    e.update();
                }

                updateBullets();
                detectHit();
                detectCollision();
                detectDamage();
                clearDamage();
            }
        }; timer.start();

    }


    private void updateBullets(){
        for (Bullet bullet : gm.player.getBullets()){
            gv.renderBullet(bullet);
        }
        for (Enemy enemy : enemies){
            for(EnemyBulletBasic b : enemy.getBullet()) {
                gv.renderEnemyBullet(b);
            }
        }
        for (Damage damage : damage) {
            gv.renderDamage(damage);
        }
    }
    
    public void detectHit() {
        // Metode for å sjekke om playerbullet traff enemy

        Iterator<Bullet> bulletIterator = gm.player.getBullets().iterator();
        Iterator<Enemy> enemyIterator = enemies.iterator();

        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            while(enemyIterator.hasNext()){
                Enemy enemy = enemyIterator.next();
                if (enemy.getX() < bullet.getX() + bullet.getWidth() && enemy.getY() < bullet.getY() + bullet.getHeight()) {
                    if (bullet.getX() < enemy.getX() + enemy.getWidth() && bullet.getY() < enemy.getY() + enemy.getHeight()) {
                        // Enemy got hit
                        System.out.println("HIT!");
                        enemy.takeDamage();
                        if(enemy.getHealth() == 0){
                            enemyIterator.remove();
                            enemy.getSprite().setImage(null);
                        }
                        bullet.clearImage();
                        gv.renderBullet(bullet);
                        damage.add(new Damage((int)bullet.getX(), (int)bullet.getY()));
                        bulletIterator.remove();
                        // Do something
                    }
                }
            }

        }

//        for (Bullet bullet : gm.player.getBullets()) {
//
//        }
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
                            gm.player.getSprite().setImage(null);
                        }
                        else {
                            gm.player.takeDamage();
                        }
                    }
                }
            }
        }
    }
    
    public void detectDamage() {
        // Metode for å sjekke om player ble truffet av enemybullet
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while(enemyIterator.hasNext()){
            Enemy enemy = enemyIterator.next();
            Iterator<EnemyBulletBasic> eBulletIterator = enemy.getBullet().iterator();
            while(eBulletIterator.hasNext()){
                EnemyBulletBasic ebullet = eBulletIterator.next();
                if (gm.player.getX() < ebullet.getX() + ebullet.getWidth() && gm.player.getY() < ebullet.getY() + ebullet.getHeight()) {
                    if (ebullet.getX() < gm.player.getX() + gm.player.getWidth() && ebullet.getY() < gm.player.getY() + gm.player.getHeight()) {
                        if (!gm.player.getImmunity()) {
                            if (gm.player.getHealth() == 1) {
                                gameOver();
                                gm.player.getSprite().setImage(null);
                            }
                            else {
                                gm.player.takeDamage();
                            }
                        }
                        ebullet.clearImage();
                        gv.renderEnemyBullet(ebullet);
                        eBulletIterator.remove();
                    }
                }
            }
        }
    }
    
    public void gameOver() {
        // Is ded!
        System.out.println("Game Over!");
    }
    
    public void clearDamage() {
        // Fjerner damage animasjon etter den er ferdig
        for (Damage damage : damage) {
            if (damage.getFinished()) {
                damage.clearImage();
                damage = null;
            }
        }
    }
    
}
