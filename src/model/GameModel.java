package model;

import view.GameView;
import multiplayer.MultiplayerHandler;

/**
 * A class for model fields that doesn't need to be stored in a specific game state.
 *
 * author Åsmund Røst Wien, Jonas Ege Carlsen, Roger Birkenes Solli
 */
public class GameModel {

    /**
     * The singleton object
     */
    private static GameModel inst = new GameModel();

    /**
     * Method to access the singleton object.
     * @return Returns a reference to the singleton object.
     */
    public static GameModel getInstance(){ return inst; }

    /**
     * Boolean to check whether or not multiplayer is running.
     */
    private boolean multiplayer = false;

    /**
     * The singleton object of MultiPlayerHandler.
     */
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
