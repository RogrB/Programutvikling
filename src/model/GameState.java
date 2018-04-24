package model;

import model.enemy.Enemy;
import model.levels.LevelLoader;
import model.player.Player;
import model.player.Player2;
import model.weapons.Bullet;

import java.util.ArrayList;

public class GameState implements java.io.Serializable {

    private static GameState inst = new GameState();
    private GameState(){}
    public static GameState getInstance(){ return inst; }

    public ArrayList<PowerUp> powerups = new ArrayList();
    public static ArrayList<Enemy> enemies = new ArrayList();

    public ArrayList<Bullet> enemyBullets = new ArrayList<>();
    public ArrayList<Bullet> playerBullets = new ArrayList<>();
    public ArrayList<Bullet> player2Bullets = new ArrayList<>();

    public Player player = Player.getInst();
    public Player2 player2 = Player2.getInst();

    public static String bossType;
    private boolean multiplayer = false;

    public String[][][] levelData;
    public int levelIncrement;

    public void newGameState(String[][][] levelData){

        powerups = new ArrayList();
        enemies = new ArrayList();

        enemyBullets = new ArrayList<>();
        playerBullets = new ArrayList<>();
        player2Bullets = new ArrayList<>();
        initLevel(levelData);
        
    }

    protected Object readResolve() {
        return getInstance();
    }

    public void initLevel(String[][][] levelData){
        this.levelData = levelData;
        levelIncrement = 0;
        LevelLoader.getInstance().setLevelData(levelData);
        bossType = null;
    }





}
