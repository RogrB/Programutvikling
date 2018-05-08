package io;

import exceptions.FileIOException;
import view.GameView;

import java.util.Timer;
import java.util.TimerTask;

public class AutoSave {

    private static AutoSave inst = new AutoSave();
    private AutoSave(){}
    public static AutoSave getInstance(){ return inst; }

    IOManager io = IOManager.getInstance();

    private Timer timer;

    public void start(){
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                try {
                    io.saveGameState();
                } catch (FileIOException e) {
                    System.err.println(e.getMessage());
                    GameView.getInstance().getField().changeText("ERROR");
                }
            }
        }, 700, 700);
    }

    public void stop(){
        timer.cancel();
    }

}
