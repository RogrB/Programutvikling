package model;

import model.player.Player;
import view.GameView;

public class GameModel {

    // Singleton
    private static GameModel inst = new GameModel();
    private GameModel(){}
    public static GameModel getInstance(){ return inst; }

    // MVC-access
    GameView gv;


    public static final double SPEED_MODIFIER = 0.17;

    public Player player = Player.getInst();

    public void setup(){
        gv = GameView.getInstance();
    }
}
