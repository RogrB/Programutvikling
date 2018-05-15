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
 * Controller class to manipulate the model objects.
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

    /**
     * Used to reference the GameModel singleton object.
     */
    private GameModel gm;

    /**
     * Used to reference the GameView singleton object.
     */
    private GameView gv;

    /**
     * Used to reference the active GameState.
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
     * An class iterator used to iterate through enemies.
     */
    private Iterator<Enemy> enemyIterator;

    /**
     * Boolean that decides if the last game was lost or not.
     * Used for specific logical purposes.
     */
    private Boolean lastGameLost = false;

    /**
     * Method to initiate the model-view–controller references.
     */
    public void mvcSetup(){
        gm = GameModel.getInstance();
        gv = GameView.getInstance();
        gs = new GameState();

        levelLoader = LevelLoader.getInstance();
        hud = HUD.getInstance();
    }

    /**
     * Function to initiate a new game. Input parameterdefines
     * the name set by the user on a new game creation.
     * @param stateName The name for this game state.
     */
    public void newGame(String stateName){
        newGame();
        gs.setStateName(stateName);
    }

    /**
     * Function used to initiate a new multiplayer game.
     */
    public void newGame(){
        lastGameLost = false;
        gs.firstLevel();
        gs.player.init();
        gameRun();
        IdGen.getInstance().resetIDs();
    }

    /**
     * Iterates the game state to the next level.
     */
    public void nextGame(){
        lastGameLost = false;
        gs.nextLevel();
        gs.player.resume();
        gameRun();
        if (GameModel.getInstance().getMultiplayerStatus()) {
            MultiplayerHandler.getInstance().nextGame();
        }
    }

    /**
     * Loads an older game state.
     */
    public void loadGame(){
        gs.loadGameData();
        gs.player.resume();
        gameRun();
    }

    /**
     * Method to start the game logic loop.
     */
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

    /**
     * Method which defines the game logic loop.
     */
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

    /**
     * Pauses the game logic loop.
     * <b>Note: </b>also disconnects the other player if the game is multiplayer.
     */
    public void gamePause(){
        AutoSave.getInstance().stop();
        gameMainTimer.stop();
        gs.player.isNotPlaying();
        if(gm.getMultiplayerStatus()) {
            MultiplayerHandler.getInstance().sendDisconnect();
            ViewUtil.setError("Game Paused, disconnected from Multiplayer");
        }
    }

    /**
     * Iterates through all enemies on the game board,
     * moves them and triggers all functionality.
     * @see Enemy
     */
    private void moveEnemies(){
        enemyIterator = GameState.enemies.iterator();
        while(enemyIterator.hasNext()){
            Enemy enemy = enemyIterator.next();
            enemy.update(enemyIterator);
            gv.render(enemy);
        }
    }

    /**
     * Moves all PowerUps on the game board.
     * @see PowerUp
     */
    private void movePowerups(){
        Iterator<PowerUp> powerUpIterator = gs.powerups.iterator();
        while (powerUpIterator.hasNext()){
            PowerUp powerUp = powerUpIterator.next();
            powerUp.update(-2, 0, powerUpIterator);
            gv.render(powerUp);
        }
    }

    /**
     * Moves all bullets on the game board. Player and enemies.
     * @see Enemy
     * @see Basic
     */
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

    /**
     * Detects if an enemy was hit by a player bullet.
     * @see Enemy
     * @see Basic
     */
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

    /**
     * Detects of player was shot by an enemy bullet.
     * @see model.player.Player
     * @see Basic
     */
    private void detectPlayerShotByEnemy(){
        for(Basic bullet : gs.enemyBullets){
            if(bullet.collidesWith(gs.player) && !bullet.getHasHit()){
                gs.player.takeDamage();
                bullet.hasHit();
            }
        }
    }

    /**
     * Detects if player collides with an enemy.
     * @see model.player.Player
     * @see Enemy
     */
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

    /**
     * Detects if player collides with a powerup.
     * @see model.player.Player
     * @see PowerUp
     */
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

    /**
     * Random number generator to check if a powerup should spawn.
     * <i>Should spawn every 25 seconds on average.</i>
     */
    private void spawnPowerUps(){
        Random random = new Random();
        if(random.nextInt(1500) < 1) {
            gs.powerups.add(generateNewPowerUp());
        }
    }

    /**
     * Creates a new random powerup and returns it.
     * @return The new powerup object.
     * @see PowerUp
     */
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

    /**
     * Method to divide a large asteroid into two smaller ones.
     * @param x The initial X position of the new {@code SmallAsteroid} object.
     * @param y The initial Y position of the new {@code SmallAsteroid} object.
     * @see Asteroid
     * @see SmallAsteroid
     */
    public void spawnSmallAsteroids(int x, int y) {
        GameState.enemies.add(new SmallAsteroid(new EnemyMovementPattern("SIN"), x, y - 20));
        GameState.enemies.add(new SmallAsteroid(new EnemyMovementPattern("SIN_REVERSED"), x, y + 20));
    }

    /**
     * Detects if the conditions for losing the game has been met.
     */
    private void detectGameOver(){
        if (!gs.player.isAlive() && !gs.gameOver) {
            gs.player.isNotPlaying();
            gs.gameOver = true;
            lastGameLost = true;
            startGameOverTimer();
            AutoSave.getInstance().stop();
            if(gm.getMultiplayerStatus()) {
                MultiplayerHandler.getInstance().sendDisconnect();
                ViewUtil.setError("Disconnected from Multiplayer");
            }
        }
    }

    /**
     * Detects if the conditions for winning the game has been met.
     */
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
                    startGameOverTimer();
                    AutoSave.getInstance().stop();
                }
            }
        }
    }

    /**
     * Initiates a 2 second delay before the game logic ends and
     * the game is lost. This is to allow for animations to finish
     * before the game stops.
     */
    public void startGameOverTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gs.player.isNotPlaying();
                if(lastGameLost) {
                    gv.gameOver();
                } else {
                    gv.gameWon();
                }
                gameMainTimer.stop();
            }
        }, 2000);
    }

    /**
     * Returns if the previous game was lost or not.
     * @return {@code true} or {@code false}.
     */
    public Boolean getLastGameLost() {return this.lastGameLost; }

    //SLETT DENNE lol
    public void setLastGameLost(boolean input){this.lastGameLost = input;}

}