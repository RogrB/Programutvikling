package io;

import exceptions.LoadFileException;
import model.GameState;
import model.enemy.Enemy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static controller.GameController.gs;

public class IOGameState {

    private static IOGameState inst = new IOGameState();
    private IOGameState(){}
    public static IOGameState getInstance(){ return inst; }

    public void saveGameState(){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream("tmp/GameState.ser");
            oos = new ObjectOutputStream(fos);

            oos.writeObject(gs);

            oos.flush();
            fos.flush();
            oos.close();
            fos.close();

        } catch (IOException e) {
            System.err.println("Could not save GameState");
        }

        saveArrayList(gs.enemies, "tmp/ArrayEnemies.ser");
        saveArrayList(gs.enemyBullets, "tmp/ArrayEnemyBullets.ser");
        saveArrayList(gs.playerBullets, "tmp/ArrayPlayerBullets.ser");
        saveArrayList(gs.powerups, "tmp/ArrayPowerups.ser");
    }

    public void loadGameState() throws LoadFileException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream("tmp/GameState.ser");
            ois = new ObjectInputStream(fis);

            gs = (GameState) ois.readObject();

            ois.close();
            fis.close();

            gs.enemies = (ArrayList) loadList("tmp/ArrayEnemies.ser");
            gs.enemyBullets = (ArrayList) loadList("tmp/ArrayEnemyBullets.ser");
            gs.playerBullets = (ArrayList) loadList("tmp/ArrayPlayerBullets.ser");
            gs.powerups = (ArrayList) loadList("tmp/ArrayPowerups.ser");

        } catch (IOException i) {
            throw new LoadFileException("Can't locate file: tmp/GameState.ser");
        } catch (ClassNotFoundException c) {
            throw new LoadFileException("Corrupt class or file in: tmp/GameState.ser");
        } catch (LoadFileException l) {
            throw new LoadFileException(l.getMessage());
        }
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

    private List loadList(String src) throws LoadFileException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        List<Enemy> res = null;

        try {
            fis = new FileInputStream(src);
            ois = new ObjectInputStream(fis);

            res = (ArrayList<Enemy>) ois.readObject();

            fis.close();
            ois.close();
        } catch (FileNotFoundException e) {
            throw new LoadFileException("Can't locate file: "+src);
        } catch (IOException e) {
            throw new LoadFileException("Error loading file: "+src);
        } catch (ClassNotFoundException e) {
            throw new LoadFileException("Corrupt class or file in: "+src);
        }
        return res;
    }

    public boolean saveStateExists(){
        if(!fileExists("tmp/GameState.ser") ||
                !fileExists("tmp/ArrayEnemies.ser") ||
                !fileExists("tmp/ArrayEnemyBullets.ser") ||
                !fileExists("tmp/ArrayPlayerBullets.ser") ||
                !fileExists("tmp/ArrayPowerups.ser"))
            return false;
        return true;
    }

    private boolean fileExists(String src){
        File file = new File(src);
        if(file.exists() && !file.isDirectory())
            return true;
        return false;
    }

}
