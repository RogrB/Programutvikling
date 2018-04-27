package io;

import model.GameState;
import model.enemy.Enemy;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static controller.GameController.gs;

public class IOGameState {

    private static IOGameState inst = new IOGameState();
    private IOGameState(){}
    public static IOGameState getInstance(){ return inst; }

    public void saveGameState(){
        try {
            OutputStream fileOut = new FileOutputStream("tmp/GameState.ser");
            ObjectOutput output = new ObjectOutputStream(fileOut);

            output.writeObject(gs);

            output.flush();
            fileOut.flush();

            output.close();
            fileOut.close();

        } catch (IOException e) {
            System.err.println("Could not save GameState");
        }

        saveArrayList(gs.enemies, "tmp/ArrayEnemies.ser");
    }

    public void loadGameState(){

        try {
            InputStream fileIn = new FileInputStream("tmp/GameState.ser");
            ObjectInput input = new ObjectInputStream(fileIn);

            //gs = (GameState) input.readObject();
            gs = (GameState) input.readObject();


            input.close();
            fileIn.close();

        } catch (IOException i) {
            System.err.println("Could not load GameState");
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.err.println("GameState class not found");
        }

        System.out.println("Enemy count: "+gs.enemies.size());
        System.out.println("Level increment: "+gs.levelIncrement);

        gs.enemies = (ArrayList) loadList("tmp/ArrayEnemies.ser");
    }

    private void saveArrayList(ArrayList list, String src){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(src);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List loadList(String src){
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        List<Enemy> enemies = null;

        try {
            fis = new FileInputStream(src);
            ois = new ObjectInputStream(fis);
            enemies = (ArrayList<Enemy>) ois.readObject();
            //gs.enemies = (ArrayList) enemies;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Enemies loaded: "+enemies.size());
        return enemies;
    }

}
