package model;

import model.player.Player;
import model.weapons.Basic;
import view.GameView;
import multiplayer.MultiplayerHandler;

import java.util.ArrayList;
import model.player.Player2;

public class GameModel {

    // Singleton
    private static GameModel inst = new GameModel();
    private GameModel(){}
    public static GameModel getInstance(){ return inst; }

    // MVC-access
    GameView gv;

    private boolean multiplayer = false;

    MultiplayerHandler mp = MultiplayerHandler.getInstance();

    public void mvcSetup(){
        gv = GameView.getInstance();
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
