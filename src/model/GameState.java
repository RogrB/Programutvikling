package model;

import assets.java.Sprite;
import model.enemy.Enemy;
import model.levels.LevelData;
import model.levels.LevelLoader;
import model.player.Player;
import model.player.Player2;
import model.weapons.Basic;

import java.util.ArrayList;

public class GameState implements java.io.Serializable {

    public ArrayList<PowerUp> powerups;
    public static ArrayList<Enemy> enemies;

    public ArrayList<Basic> enemyBullets;
    public ArrayList<Basic> playerBullets;
    public ArrayList<Basic> player2Bullets;

    public Player player = Player.getInst();
    public Player2 player2 = Player2.getInst();

    public static String bossType;
    public boolean gameOver;

    private String[][][] levelData;
    public int levelIncrementor;    //Increment within a level
    private int levelIterator;      //Iterate through levels

    public GameState(){
        powerups = new ArrayList();
        enemies = new ArrayList();

        enemyBullets = new ArrayList<>();
        playerBullets = new ArrayList<>();
        player2Bullets = new ArrayList<>();
        levelIterator = 0;
    }

    public void firstLevel(){
        levelIterator = 1;
        initLevel();
    }

    public void nextLevel(){
        levelIterator++;
        initLevel();
    }

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

    public void loadGameData(){
        LevelLoader.getInstance().setLevelData(levelData);
        player.newSprite(Sprite.PLAYER);
        player.getImageView().relocate(player.getX(), player.getY());
        player2.newSprite(Sprite.PLAYER2);
        player2.getImageView().relocate(player2.getX(), player2.getY());
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

    public int getLevelIterator(){return levelIterator; }

}
