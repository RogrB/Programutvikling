package model;

import javafx.animation.AnimationTimer;
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
    GameView gv = GameView.getInst();
    
    public static final double SPEED_MODIFIER = 0.17;

    public Player player = Player.getInst();
}
