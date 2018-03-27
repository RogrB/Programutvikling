package model;

import model.player.Player;
import model.weapons.Bullet;
import view.GameView;

import java.util.ArrayList;

public class GameModel {

    // Singleton
    private static GameModel inst = new GameModel();
    private GameModel(){}
    public static GameModel getInstance(){ return inst; }

    // MVC-access
    GameView gv;


    public static final double SPEED_MODIFIER = 1;

    public Player player = Player.getInst();
    private ArrayList<Bullet> enemyBullets = new ArrayList<>();

    public ArrayList<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public void setup(){
        gv = GameView.getInstance();
    }
}
