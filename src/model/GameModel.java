package model;

import model.player.Player;
import model.weapons.Bullet;
import view.GameView;
import multiplayer.MultiplayerHandler;

import java.util.ArrayList;

public class GameModel {

    // Singleton
    private static GameModel inst = new GameModel();
    private GameModel(){}
    public static GameModel getInstance(){ return inst; }

    // MVC-access
    GameView gv;

    MultiplayerHandler mp;
    private int playerNumber;
    private boolean multiplayer = false;

    public static final double SPEED_MODIFIER = 1;

    private ArrayList<Bullet> enemyBullets = new ArrayList<>();
    private ArrayList<Bullet> playerBullets = new ArrayList<>();
    public Player player = Player.getInst();

    public ArrayList<Bullet> getEnemyBullets() {
        return enemyBullets;
    }
    
    public ArrayList<Bullet> getPlayerBullets() {
        return playerBullets;
    }    

    public void mvcSetup(){
        gv = GameView.getInstance();
    }
    
    public void setP1() {
        mp = new MultiplayerHandler("localhost", 7, 8);
        playerNumber = 1;
        multiplayer = true;
    }
    
    public void setP2() {
        mp = new MultiplayerHandler("localhost", 8, 7);
        playerNumber = 2;
        multiplayer = true;
    }
    
    public void testSend() {
        mp.send("Player " + playerNumber + " sends a packet");
    }
    
    public boolean getMultiplayerStatus() {
        return this.multiplayer;
    }
    
    public void setMultiplayerStatus(boolean status) {
        this.multiplayer = status;
    }
    
    public MultiplayerHandler getMP() {
        return this.mp;
    }
}
