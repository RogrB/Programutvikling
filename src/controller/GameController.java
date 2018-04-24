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
import view.ViewUtil;
import multiplayer.MultiplayerHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameController {

    // Singleton
    private static GameController inst = new GameController();
    private GameController(){}
    public static GameController getInstance(){ return inst; }

    // MVC-access
    GameModel gm;
    GameView gv;
    
    HUD hud;
    LevelLoader levelLoader;

    // Level data
    public ArrayList<PowerUp> powerups = new ArrayList();
    public static ArrayList<Enemy> enemies = new ArrayList();

    AnimationTimer gameMainTimer;

    public Iterator<Bullet> bulletIterator;
    public Iterator<Enemy> enemyIterator;
    public Iterator<PowerUp> powerUpIterator;

    public void mvcSetup(){
        gm = GameModel.getInstance();
        gv = GameView.getInstance();
    }

    public void gameStart() {

        hud = HUD.getInstance();
        levelLoader = LevelLoader.getInstance();
        levelLoader.setLevelData(LevelData.LEVEL4);

        // ANIMATION TIMER, UPDATES VIEW
        gameMainTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                levelLoader.increment();

                gm.player.update();
                gv.renderShield();

                spawnPowerUps();

                moveEnemies();
                movePowerups();
                moveAllBullets();

                detectPlayerCollidesWithEnemy();
                detectEnemyShotByPlayer();
                detectPlayerShotByEnemy();
                detectPlayerCollidesWithPowerUp();

                hud.renderHUD();

                detectGameOver();
                if(gm.getMultiplayerStatus()) {
                    gm.player2.update();
                    gm.getMP().send("Update", gm.player.getX(), gm.player.getY());
                }
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
        bulletIterator = gm.getPlayerBullets().iterator();
        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            bullet.update(20, 0, bulletIterator);
            gv.render(bullet);
        }

        bulletIterator = gm.getPlayer2Bullets().iterator();
        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            bullet.update(-12, 0, bulletIterator);
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
        ArrayList<Enemy> tempEnemies = new ArrayList<>();
        for(Bullet bullet : gm.getPlayerBullets()){
            for(enemyIterator = enemies.iterator(); enemyIterator.hasNext();){
                Enemy enemy = enemyIterator.next();
                if(bullet.collidesWith(enemy)){
                    enemy.takeDamage(bullet.getDmg());

                    if (!bullet.getHasHit()) {
                        gm.player.setScore(gm.player.getScore() + 10);
                    }

                    bullet.hasHit();

                    if (!enemy.isAlive() && enemy instanceof Asteroid && !((Asteroid)enemy).getSpawned()) {
                        ((Asteroid)enemy).setSpawned(true);
                        tempEnemies.add(enemy);
                    }

                    if(!enemy.isAlive() && enemy.getType().toString().equals("BOSS01")){
                        gameWin();
                    }

                    if(enemy.getType().toString().equals("BOSS01")){
                        gv.renderHealthBar();
                    }
                }
            }
        }
        for(Enemy e : tempEnemies){
            spawnSmallAsteroids(e.getX(), e.getY());
        }
    }
    
    private void spawnSmallAsteroids(int x, int y) {
        enemies.add(new SmallAsteroid(new EnemyMovementPattern("SIN"), x, y - 20));
        enemies.add(new SmallAsteroid(new EnemyMovementPattern("SIN_REVERSED"), x, y + 20));
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
                if(enemy.isAlive()) {
                    gm.player.takeDamage();
                }
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

    private void spawnPowerUps(){
        Random random = new Random();
        if(random.nextInt(1500) < 1) {
            powerups.add(generateNewPowerUp());
        }
    }

    private PowerUp generateNewPowerUp(){
        Random rand = new Random();
        int randNr = rand.nextInt(8);
        Sprite sprite = null;
        switch (randNr){
            case 0:
            case 1:
            case 2:
            case 3:
                sprite = Sprite.SHIELD_POWERUP;
                break;
            case 4:
            case 5:
                sprite = Sprite.HEALTH_POWERUP;
                break;
            case 6:
            case 7:
                sprite = Sprite.WEAPON_POWERUP;
                break;
            /*case 8:
                sprite = Sprite.DIE_POWERUP;
                break;
            case 9:
                sprite = Sprite.IMMUNE_POWERUP;
                break;*/
        }
        PowerUp res = new PowerUp(
                sprite,
                ViewUtil.VIEW_WIDTH - 1,
                rand.nextInt(ViewUtil.VIEW_HEIGHT - sprite.getHeight())
                );
        return res;
    }

    private void detectGameOver(){
        if (!gm.player.isAlive()) {
            gv.gameOver();
            gameMainTimer.stop();
        }
    }
    
    private void gameWin() {
        System.out.println("U totally won the game my g");
        gv.dialogBox.setOpacity(1);
        gameMainTimer.stop();
        gv.bossHealthBar.setOpacity(0);
    }
    
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
