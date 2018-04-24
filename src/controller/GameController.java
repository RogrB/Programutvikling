package controller;

import assets.java.Sprite;
import javafx.animation.AnimationTimer;
import io.AutoSave;
import model.GameModel;
import model.GameState;
import model.enemy.*;
import model.levels.LevelData;
import model.levels.LevelLoader;
import model.weapons.*;
import multiplayer.MultiplayerHandler;
import view.GameView;
import view.HUD;
import model.PowerUp;
import view.ViewUtil;

import java.util.*;

import static model.GameState.bossType;

public class GameController {

    // Singleton
    private static GameController inst = new GameController();
    private GameController(){}
    public static GameController getInstance(){ return inst; }

    // MVC-access
    GameModel gm;
    GameView gv;
    GameState gs;
    
    HUD hud;
    LevelLoader levelLoader;

    AnimationTimer gameMainTimer;

    public Iterator<Bullet> bulletIterator;
    public Iterator<Enemy> enemyIterator;
    public Iterator<PowerUp> powerUpIterator;

    public void mvcSetup(){
        gm = GameModel.getInstance();
        gv = GameView.getInstance();
        gs = GameState.getInstance();

        levelLoader = LevelLoader.getInstance();
        hud = HUD.getInstance();
    }

    public void newGame(){
        gs.newGameState(LevelData.LEVEL4);
        gameStart();
    }

    private void gameStart() {
        AutoSave.getInstance().start();
        gameMainTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                gs.levelIncrement = levelLoader.increment(gs.levelIncrement);

                gs.player.update();
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
                detectGameWin();
                if(gm.getMultiplayerStatus()) {
                    gs.player2.update();
                    gm.getMP().send("Update", gs.player.getX(), gs.player.getY());
                }
            }
        }; gameMainTimer.start();
    }

    public void gamePause(){
        AutoSave.getInstance().stop();
        gameMainTimer.stop();
    }

    private void moveEnemies(){
        enemyIterator = gs.enemies.iterator();
        while(enemyIterator.hasNext()){
            Enemy enemy = enemyIterator.next();
            enemy.update(enemyIterator);
            gv.render(enemy);
        }
    }

    private void movePowerups(){
        powerUpIterator = gs.powerups.iterator();
        while (powerUpIterator.hasNext()){
            PowerUp powerUp = powerUpIterator.next();
            powerUp.update(-2, 0, powerUpIterator);
            gv.render(powerUp);
        }
    }

    private void moveAllBullets(){
        bulletIterator = gs.playerBullets.iterator();
        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            bullet.update(20, 0, bulletIterator);
            gv.render(bullet);
        }

        bulletIterator = gs.player2Bullets.iterator();
        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            bullet.update(20, 0, bulletIterator);
            gv.render(bullet);
        }

        bulletIterator = gs.enemyBullets.iterator();
        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            bullet.update(-12, 0, bulletIterator);
            gv.render(bullet);
        }
    }

    private void detectEnemyShotByPlayer(){
        ArrayList<Enemy> tempEnemies = new ArrayList<>();
        for(Bullet bullet : gs.playerBullets){
            for(enemyIterator = gs.enemies.iterator(); enemyIterator.hasNext();){
                Enemy enemy = enemyIterator.next();
                if(bullet.collidesWith(enemy)){
                    enemy.takeDamage(bullet.getDmg());
                    if(gm.getMultiplayerStatus()) {
                        MultiplayerHandler.getInstance().send("EnemyUpdate", enemy.getID(), enemy.getHealth(), enemy.isAlive());
                    }
                    if (!bullet.getHasHit()) {
                        gs.player.setScore(gs.player.getScore() + 10);
                    }

                    bullet.hasHit();

                    if (!enemy.isAlive() && enemy instanceof Asteroid && !((Asteroid)enemy).getSpawned()) {
                        ((Asteroid)enemy).setSpawned(true);
                        tempEnemies.add(enemy);
                    }
                }
            }
        }

        for(Bullet bullet : gs.player2Bullets){
            for(Enemy enemy : gs.enemies){
                if(bullet.collidesWith(enemy))
                    bullet.hasHit();
            }
        }

        for(Enemy e : tempEnemies){
            spawnSmallAsteroids(e.getX(), e.getY());
        }
    }
    
    private void spawnSmallAsteroids(int x, int y) {
        gs.enemies.add(new SmallAsteroid(new EnemyMovementPattern("SIN"), x, y - 20));
        gs.enemies.add(new SmallAsteroid(new EnemyMovementPattern("SIN_REVERSED"), x, y + 20));
    }

    private void detectPlayerShotByEnemy(){
        for(Bullet bullet : gs.enemyBullets){
            if(bullet.collidesWith(gs.player)){
                gs.player.takeDamage();
                bullet.hasHit();
            }
        }
    }

    private void detectPlayerCollidesWithEnemy(){
        for (Enemy enemy: gs.enemies) {
            if(enemy.collidesWith(gs.player)){
                if(enemy.isAlive()) {
                    gs.player.takeDamage();
                }
                enemy.takeDamage();
            }
        }
    }
    
    private void detectPlayerCollidesWithPowerUp() {
        if (!gs.powerups.isEmpty()) {
            for (PowerUp powerUp : gs.powerups) {
                if(powerUp.collidesWith(gs.player) && !powerUp.isPickedUp()) {
                    powerUp.setPickedUp();
                    gs.player.powerUp(powerUp);
                }
            }
        }
    }

    private void spawnPowerUps(){
        Random random = new Random();
        if(random.nextInt(1500) < 1) {
            gs.powerups.add(generateNewPowerUp());
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
        if (!gs.player.isAlive()) {
            gv.gameOver();
            gameMainTimer.stop();
        }
    }
    
    private void detectGameWin() {
        if(bossType != null){
            EnemyType boss = EnemyType.valueOf(bossType);
            for(Enemy enemy : gs.enemies){
                if(enemy.getType() == boss && !enemy.isAlive()){
                    startGameWinTimer();
                }
            }
        }
    }

    private void startGameWinTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Game Won!");
            }
        }, 3000);
    }
}
