package model;

import multiplayer.MultiplayerHandler;

/**
 * A class for model specific fields that doesn't need to be stored in a specific game state.
 *
 * @author Åsmund Røst Wien
 * @author Jonas Ege Carlsen
 * @author Roger Birkenes Solli
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
     * Local access to the instance of the MultiplayerHandler object.
     * @see MultiplayerHandler
     */
    private MultiplayerHandler mp = MultiplayerHandler.getInstance();

    /**
     * The future reference to the GameSettings object.
     */
    public static GameSettings gameSettings;

    /**
     * Private constructor.
     */
    private GameModel(){
        gameSettings = new GameSettings();
    }

    /**
     * Checks if the current game is a multiplayer game or not.
     * @return {@code true} or {@code false}.
     */
    public boolean getMultiplayerStatus() {
        return this.multiplayer;
    }

    /**
     * Defines if the game currently is a multiplayer game or not.
     * @param status {@code true} or {@code false}.
     */
    public void setMultiplayerStatus(boolean status) {
        this.multiplayer = status;
    }

    /**
     * Returns access to the MultiplayerHandler object.
     * @return Access to the MultiplayerHandler object.
     * @see MultiplayerHandler
     */
    public MultiplayerHandler getMP() {
        return this.mp;
    }
}
