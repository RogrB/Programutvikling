package controller;

import assets.java.SoundManager;
import assets.java.Sprite;
import javafx.animation.AnimationTimer;
import io.AutoSave;
import model.GameModel;
import model.GameState;
import model.enemy.*;
import model.levels.LevelLoader;
import model.weapons.*;
import multiplayer.MultiplayerHandler;
import view.GameView;
import view.HUD;
import model.powerups.PowerUp;
import view.ViewUtil;

import java.util.*;

import static model.GameState.bossType;

/**
 * Class used to control the Game.
 *
 * @author Åsmund Røst Wien
 * @author Jonas Ege Carlsen
 * @author Roger Birkenes Solli
 */
public class GameController {

    /**
     * The singleton object.
     */
    private static GameController inst = new GameController();

    /**
     * Method to access the singleton object.
     * @return Returns a reference to the singleton object.
     */
    public static GameController getInstance(){ return inst; }

    /**
     * Private constructor.
     */
    private GameController(){}

    // MVC-access

    /**
     * Used to reference the GameModel singleton object.
     */
    private GameModel gm;

    /**
     * Used to reference the GameView singleton object.
     */
    private GameView gv;

    /**
     * Used to reference GameState.
     */
    public static GameState gs;

    /**
     * Used to display the Heads Up Display.
     */
    private HUD hud;

    /**
     * Used to load levels.
     */
    private LevelLoader levelLoader;

    /**
     * Timer used to update the game.
     */
    private AnimationTimer gameMainTimer;

    /**
     * An iterator used to iterate through enemies.
     */
    private Iterator<Enemy> enemyIterator;

    /**
     * Boolean that decides if the last game was lost or not.
     */
    private Boolean lastGameLost = false;

    public void mvcSetup(){
        gm = GameModel.getInstance();
        gv = GameView.getInstance();
        gs = new GameState();

        levelLoader = LevelLoader.getInstance();
        hud = HUD.getInstance();
    }

    public void newGame(String stateName){
        newGame();
        gs.setStateName(stateName);
    }

    public void newGame(){
        lastGameLost = false;
        gs.firstLevel();
        gs.player.init();
        gameRun();
    }

    public void nextGame(){
        lastGameLost = false;
        gs.nextLevel();
        gs.player.resume();
        gameRun();
        if (GameModel.getInstance().getMultiplayerStatus()) {
            MultiplayerHandler.getInstance().nextGame();
        }
    }

    public void loadGame(){
        gs.loadGameData();
        gs.player.resume();
        gameRun();
    }

    private void gameRun(){
        gs.player.isPlaying();
        gv.clearAllGraphics();
        gameTimerStart();
        if(!gm.getMultiplayerStatus()){
            AutoSave.getInstance().start();
        }
        if(SoundManager.getInst().getPlayer() != null){
            SoundManager.getInst().playMusic("stop");
        }
            SoundManager.getInst().playMusic("music_battle");
    }

    private void gameTimerStart() {
        if(gameMainTimer == null)
            gameMainTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {

                    gs.levelIncrementor = levelLoader.increment(gs.levelIncrementor);

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
            };
        gameMainTimer.start();
    }


    public void gamePause(){
        AutoSave.getInstance().stop();
        gameMainTimer.stop();
        gs.player.isNotPlaying();
        if(gm.getMultiplayerStatus()) {
            MultiplayerHandler.getInstance().sendDisconnect();
            ViewUtil.setError("Game Paused, disconnected from Multiplayer");
        }
    }

    private void moveEnemies(){
        enemyIterator = GameState.enemies.iterator();
        while(enemyIterator.hasNext()){
            Enemy enemy = enemyIterator.next();
            enemy.update(enemyIterator);
            gv.render(enemy);
        }
    }

    private void movePowerups(){
        Iterator<PowerUp> powerUpIterator = gs.powerups.iterator();
        while (powerUpIterator.hasNext()){
            PowerUp powerUp = powerUpIterator.next();
            powerUp.update(-2, 0, powerUpIterator);
            gv.render(powerUp);
        }
    }

    private void moveAllBullets(){
        Iterator<Basic> bulletIterator = gs.playerBullets.iterator();
        while(bulletIterator.hasNext()){
            Basic bullet = bulletIterator.next();
            bullet.update(20, 0, bulletIterator);
            gv.render(bullet);
        }

        bulletIterator = gs.player2Bullets.iterator();
        while(bulletIterator.hasNext()){
            Basic bullet = bulletIterator.next();
            bullet.update(20, 0, bulletIterator);
            gv.render(bullet);
        }

        bulletIterator = gs.enemyBullets.iterator();
        while(bulletIterator.hasNext()){
            Basic bullet = bulletIterator.next();
            bullet.update(-12, 0, bulletIterator);
            gv.render(bullet);
        }
    }

    private void detectEnemyShotByPlayer(){
        ArrayList<Enemy> tempEnemies = new ArrayList<>();
        for(Basic bullet : gs.playerBullets){
            for(enemyIterator = GameState.enemies.iterator(); enemyIterator.hasNext();){
                Enemy enemy = enemyIterator.next();
                if(bullet.collidesWith(enemy)){
                    enemy.takeDamage(bullet.getDmg());
                    if(gm.getMultiplayerStatus()) {
                        MultiplayerHandler.getInstance().send("EnemyUpdate", enemy.getID(), enemy.getHealth(), enemy.isAlive());
                    }
                    if (!bullet.getHasHit()) {
                        gs.player.setScore(gs.player.getScore() + 10);
                        SoundManager.getInst().impactBullets();
                    }

                    bullet.hasHit();

                    if (!enemy.isAlive() && enemy instanceof Asteroid && !((Asteroid)enemy).getSpawned()) {
                        ((Asteroid)enemy).setSpawned(true);
                        tempEnemies.add(enemy);
                    }
                }
            }
        }

        for(Basic bullet : gs.player2Bullets){
            for(Enemy enemy : GameState.enemies){
                if(bullet.collidesWith(enemy))
                    bullet.hasHit();
            }
        }

        for(Enemy e : tempEnemies){
            spawnSmallAsteroids(e.getX(), e.getY());
        }
    }

    public void spawnSmallAsteroids(int x, int y) {
        GameState.enemies.add(new SmallAsteroid(new EnemyMovementPattern("SIN"), x, y - 20));
        GameState.enemies.add(new SmallAsteroid(new EnemyMovementPattern("SIN_REVERSED"), x, y + 20));
    }

    private void detectPlayerShotByEnemy(){
        for(Basic bullet : gs.enemyBullets){
            if(bullet.collidesWith(gs.player) && !bullet.getHasHit()){
                gs.player.takeDamage();
                bullet.hasHit();
            }
        }
    }

    private void detectPlayerCollidesWithEnemy(){
        for (Enemy enemy: GameState.enemies) {
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
        return new PowerUp(
                sprite,
                ViewUtil.VIEW_WIDTH - 1,
                rand.nextInt(ViewUtil.VIEW_HEIGHT - sprite.getHeight())
        );
    }

    private void detectGameOver(){
        if (!gs.player.isAlive() && !gs.gameOver) {
            gs.gameOver = true;
            gs.player.isNotPlaying();
            lastGameLost = true;
            startLossTimer();
            gv.gameOver();
            gameMainTimer.stop();
            AutoSave.getInstance().stop();
            if(gm.getMultiplayerStatus()) {
                MultiplayerHandler.getInstance().sendDisconnect();
                ViewUtil.setError("Disconnected from Multiplayer");
            }
        }
    }

    private void startLossTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {}
        }, 2000);
    }

    private void detectGameWin() {
        if (GameModel.getInstance().getMultiplayerStatus()) {
            if(MultiplayerHandler.getInstance().getNextGameRequest()) {
                MultiplayerHandler.getInstance().setNextGameRequest(false);
            }
        }
        if(bossType != null){
            EnemyType boss = EnemyType.valueOf(bossType);
            for(Enemy enemy : GameState.enemies){
                if(enemy.getType() == boss && !enemy.isAlive() && !gs.gameOver){
                    gs.gameOver = true;
                    lastGameLost = false;
                    startGameWinTimer();
                    AutoSave.getInstance().stop();
                }
            }
        }
    }

    private void startGameWinTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gs.player.isNotPlaying();
                gv.gameWon();
                gameMainTimer.stop();
            }
        }, 2000);
    }

    public Boolean getLastGameLost() {return this.lastGameLost; }
}