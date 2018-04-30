package model;

import assets.java.Sprite;
import model.enemy.Enemy;
import model.levels.LevelLoader;
import model.player.Player;
import model.player.Player2;
import model.weapons.Basic;

import java.io.ObjectStreamException;
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
    private boolean multiplayer = false;
    public boolean gameOver;

    public String[][][] levelData;
    public int levelIncrement;

    public GameState(){
        powerups = new ArrayList();
        enemies = new ArrayList();

        enemyBullets = new ArrayList<>();
        playerBullets = new ArrayList<>();
        player2Bullets = new ArrayList<>();
    }

    public void newGameState(String[][][] levelData){
        powerups = new ArrayList();
        enemies = new ArrayList();

        enemyBullets = new ArrayList<>();
        playerBullets = new ArrayList<>();
        player2Bullets = new ArrayList<>();
        initLevel(levelData);
        gameOver = false;
    }

    public void initLevel(String[][][] levelData){
        this.levelData = levelData;
        levelIncrement = 0;
        LevelLoader.getInstance().setLevelData(levelData);
        bossType = null;
    }

    public void loadGameData(){
        LevelLoader.getInstance().setLevelData(levelData);
        player.newSprite(Sprite.PLAYER);
        player.imageView.relocate(player.getX(), player.getY());
        player2.newSprite(Sprite.PLAYER2);
        player2.imageView.relocate(player2.getX(), player2.getY());

        for(Enemy enemy : enemies) { enemy.newSprite(enemy.sprite); }
        for(Basic bullet : enemyBullets){ bullet.newSprite(bullet.sprite); }
        for(Basic bullet : playerBullets){ bullet.newSprite(bullet.sprite); }
        player.canShoot = true;
        player.move("STOP");
        for(PowerUp powerUp : powerups){ powerUp.newSprite(powerUp.sprite); }
    }

}
