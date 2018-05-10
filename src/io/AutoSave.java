package io;

import exceptions.FileIOException;
import view.ViewUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <h1>Automatically saves the game</h1>
 * This thread is run in paralell with the game's progression,
 * and automatically saves the GameState every 0,7 seconds.
 * <p>
 * <b>Note: </b>This is only a functionality in single player.
 */
public class AutoSave {

    /**
     * The singleton object.
     */
    private static AutoSave inst = new AutoSave();

    /**
     * Private constructor (singleton).
     */
    private AutoSave(){}

    /**
     * Method to access singleton class.
     * @return Returns a reference to the singleton object.
     */
    public static AutoSave getInstance(){ return inst; }

    /**
     * Functionality for managing the {@code Timer}.
     */
    private boolean running = false;

    /**
     * {@code Timer} object to run.
     */
    private Timer timer;

    /**
     * Method to start (and initiate) the {@code AutoSave}.
     */
    public void start(){
        timer = new Timer();
        running = true;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                try {
                    IOManager.getInstance().saveGameState();
                } catch (FileIOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }, 10, 700);
    }

    /**
     * Method to stop the {@code AutoSave}.
     */
    public void stop(){
        if(running)
            timer.cancel();
        running = false;
    }

}
