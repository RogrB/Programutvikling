package model;

import model.player.Player;
import model.weapons.EnemyBulletBasic;
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
    private ArrayList<EnemyBulletBasic> ebullets = new ArrayList<>();

    public ArrayList<EnemyBulletBasic> getEnemyBullets() {
        return ebullets;
    }

    public void setup(){
        gv = GameView.getInstance();
    }
}
