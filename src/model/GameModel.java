package model;

import view.GameView;
import multiplayer.MultiplayerHandler;

public class GameModel {

    // Singleton
    private static GameModel inst = new GameModel();
    public static GameModel getInstance(){ return inst; }

    private boolean multiplayer = false;
    private MultiplayerHandler mp = MultiplayerHandler.getInstance();
    public static GameSettings gameSettings;

    private GameModel(){
        gameSettings = new GameSettings();
    }

    public void mvcSetup(){
        GameView gv = GameView.getInstance();
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
