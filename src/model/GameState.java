package model;

import assets.java.Sprite;
import model.enemy.Enemy;
import model.levels.LevelData;
import model.levels.LevelLoader;
import model.player.Player;
import model.player.Player2;
import model.weapons.Basic;

import java.util.ArrayList;

/**
 * <h1>Management of game variables</h1>
 * This class stores and allows access to game variables,
 * whom are manipulated from accessors. This is the main
 * main game class to be serialized and stored when the
 * game saves.
 *
 * @author Åsmund Røst Wien
 */
public class GameState implements java.io.Serializable {

    /**
     * {@code PowerUp} objects located in the game view.
     * @see PowerUp
     */
    public ArrayList<PowerUp> powerups;

    /**
     * {@code Enemy} objects located in the game view.
     * @see Enemy
     */
    public static ArrayList<Enemy> enemies;

    /**
     * Arraylists for bullets in the game view.
     */
    public ArrayList<Basic> enemyBullets, playerBullets, player2Bullets;

    /**
     * Access to the Player class.
     */
    public Player player = Player.getInst();

    /**
     * Access to the Player2 class.
     * <p><b>Note: </b>Only used in multiplayer.
     */
    public Player2 player2 = Player2.getInst();

    /**
     * Stores the boss' type when it spawns.
     * <p><b>Note: </b>used to set correct boss attributes
     * when the game loads.
     */
    public static String bossType;

    /**
     * Boolean to check if the game is over or not. Used
     * for specific logic purposes when the game loads.
     */
    public boolean gameOver;

    /**
     * Stores the {@code LevelData} for this level.
     * @see LevelData
     */
    private String[][][] levelData;

    /**
     * Stores level progression.
     */
    public int levelIncrementor;    //Increment within a level

    /**
     * Stores iteration through levels.
     */
    private int levelIterator;      //Iterate through levels

    /**
     * Stores the custom name for this save state.
     */
    private String stateName;

    /**
     * <b>Constructor</b> initializes game objects and fields.
     */
    public GameState(){
        powerups = new ArrayList();
        enemies = new ArrayList();

        enemyBullets = new ArrayList<>();
        playerBullets = new ArrayList<>();
        player2Bullets = new ArrayList<>();
        levelIterator = 0;
    }

    /**
     * Resets the GameState to level 1.
     * Called on the creation of a new game.
     */
    public void firstLevel(){
        levelIterator = 1;
        initLevel();
    }

    /**
     * Sets the GameState to the next level.
     * Called when the level is completed.
     */
    public void nextLevel(){
        levelIterator++;
        initLevel();
    }

    /**
     * Resets objects and fields in order to initialize a new level.
     */
    private void initLevel(){
        this.levelData = LevelData.getLevel("LEVEL"+levelIterator);

        levelIncrementor = 0;

        powerups = new ArrayList();
        enemies = new ArrayList();

        enemyBullets = new ArrayList<>();
        playerBullets = new ArrayList<>();
        player2Bullets = new ArrayList<>();

        gameOver = false;
        LevelLoader.getInstance().setLevelData(levelData);
        bossType = null;
    }

    /**
     * Updates objects and fields when a game is loaded from file.
     * {@code GameState} contains certain objects which are transient,
     * as these classes cannot be serialized. They must therefore be
     * reset manually when a game loads.
     */
    public void loadGameData(){
        LevelLoader.getInstance().setLevelData(levelData);
        player.newSprite(Sprite.PLAYER);
        player2.newSprite(Sprite.PLAYER2);
        if(player.hasShield()) {
            player.shield().newSprite(Sprite.SHIELD1);
        }

        for(Enemy enemy : enemies) { enemy.newSprite(enemy.sprite); }
        for(Basic bullet : enemyBullets){ bullet.newSprite(bullet.sprite); }
        for(Basic bullet : playerBullets){ bullet.newSprite(bullet.sprite); }
        player.canShoot = true;
        player.move("STOP");
        for(PowerUp powerUp : powerups){ powerUp.newSprite(powerUp.sprite); }

        for(Enemy enemy : enemies){
            if(enemy.getType().IS_BOSS){
                bossType = enemy.getType().name();
            }
        }
    }

    /**
     * Returns the current level iteration.
     * @return The current level iteration.
     */
    public int getLevelIterator(){return levelIterator; }

    /**
     * Sets a custom name of this {@code GameState}.
     * Called when a new game is created.
     * @param stateName The custom name for this {@code GameState}.
     */
    public void setStateName(String stateName){
        this.stateName = stateName;
    }

    /**
     * Returns the custom name for this {@code GameState}.
     * @return The custom name for this {@code GameState}.
     */
    public String getStateName() {
        return stateName;
    }
}
