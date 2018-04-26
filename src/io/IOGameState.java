package io;

import model.GameState;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.io.*;
import static controller.GameController.gs;

public class IOGameState {

    private static IOGameState inst = new IOGameState();
    private IOGameState(){}
    public static IOGameState getInstance(){ return inst; }

    public void saveGameState(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("tmp/GameState.ser");
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutputStream);
            objectOutput.writeObject(gs);
            objectOutput.close();
            fileOutputStream.close();
        } catch (IOException e) {
            System.err.println("Could not save GameState");
        }
    }

    public void loadGameState(){

        //GameState gs = null;

        try {
            FileInputStream fileInputStream = new FileInputStream("tmp/GameState.ser");
            ObjectInputStream objectInput = new ObjectInputStream(fileInputStream);
            gs = (GameState) objectInput.readObject();
            objectInput.close();
            fileInputStream.close();
        } catch (IOException i) {
            System.err.println("Could not load GameState");
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.err.println("GameState class not found");
        }
        System.out.println(gs.levelIncrement);

    }
}
