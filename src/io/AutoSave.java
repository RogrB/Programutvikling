package io;

import java.util.Timer;
import java.util.TimerTask;

public class AutoSave {

    private static AutoSave inst = new AutoSave();
    private AutoSave(){}
    public static AutoSave getInstance(){ return inst; }

    IOGameState io = IOGameState.getInstance();

    private Timer timer;

    public void start(){
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                io.saveGameState();
            }
        }, 700, 700);
    }

    public void stop(){
        timer.cancel();
    }

}
