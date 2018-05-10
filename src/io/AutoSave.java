package io;

import exceptions.FileIOException;

import java.util.Timer;
import java.util.TimerTask;

public class AutoSave {

    private static AutoSave inst = new AutoSave();
    private AutoSave(){}
    public static AutoSave getInstance(){ return inst; }

    private IOManager io = IOManager.getInstance();

    private boolean running = false;
    private Timer timer;

    public void start(){
        timer = new Timer();
        running = true;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                try {
                    io.saveGameState();
                } catch (FileIOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }, 10, 700);
    }

    public void stop(){
        if(running)
            timer.cancel();
        running = false;
    }

}
