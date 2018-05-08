package io;

import exceptions.FileIOException;
import model.GameSettings;
import model.GameState;
import model.enemy.Enemy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static controller.GameController.gs;

public class IOManager {

    private static IOManager inst = new IOManager();
    private IOManager(){}
    public static IOManager getInstance(){ return inst; }

    public void saveGameState() throws FileIOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        String src = "tmp/GameState.ser";

        try {
            fos = new FileOutputStream(src);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(gs);

            oos.flush();
            fos.flush();
            oos.close();
            fos.close();

            saveArrayList(gs.enemies, "tmp/ArrayEnemies.ser");
            saveArrayList(gs.enemyBullets, "tmp/ArrayEnemyBullets.ser");
            saveArrayList(gs.playerBullets, "tmp/ArrayPlayerBullets.ser");
            saveArrayList(gs.powerups, "tmp/ArrayPowerups.ser");

        } catch (IOException e) {
            throw new FileIOException("Save game - Could not save to file: "+src);
        } catch (FileIOException f) {
            throw new FileIOException("Save game - "+f.getMessage());
        }
    }

    public void loadGameState() throws FileIOException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        String src = "tmp/GameState.ser";

        try {
            fis = new FileInputStream(src);
            ois = new ObjectInputStream(fis);

            gs = (GameState) ois.readObject();

            ois.close();
            fis.close();

            gs.enemies = (ArrayList) loadList("tmp/ArrayEnemies.ser");
            gs.enemyBullets = (ArrayList) loadList("tmp/ArrayEnemyBullets.ser");
            gs.playerBullets = (ArrayList) loadList("tmp/ArrayPlayerBullets.ser");
            gs.powerups = (ArrayList) loadList("tmp/ArrayPowerups.ser");

        } catch (IOException i) {
            throw new FileIOException("Load game - Can't locate file: "+src);
        } catch (ClassNotFoundException c) {
            throw new FileIOException("Load game - Corrupt class or file in: "+src);
        } catch (FileIOException f) {
            throw new FileIOException("Load game - "+f.getMessage());
        }
    }

    private void saveArrayList(ArrayList list, String src) throws FileIOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(src);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            throw new FileIOException("Cant't locate file: "+src);
        } catch (IOException e) {
            throw new FileIOException("Error writing to file: "+src);
        }
    }

    private List loadList(String src) throws FileIOException {
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
            throw new FileIOException("Can't locate file: "+src);
        } catch (IOException e) {
            throw new FileIOException("Corrupt save file: "+src);
        } catch (ClassNotFoundException e) {
            throw new FileIOException("Corrupt class state in: "+src);
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

    public boolean fileExists(String src){
        File file = new File(src);
        if(file.exists() && !file.isDirectory())
            return true;
        return false;
    }

    public GameSettings loadGameSettings() throws FileIOException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        String src = "tmp/GameSettings.ser";

        GameSettings res = null;

        try {
            fis = new FileInputStream(src);
            ois = new ObjectInputStream(fis);

            res = (GameSettings) ois.readObject();

            ois.close();
            fis.close();

            System.out.println("Loaded game settings");

        } catch (IOException i) {
            throw new FileIOException("Load settings - Can't locate file: "+src);
        } catch (ClassNotFoundException c) {
            throw new FileIOException("Load settings - Corrupt class or file in: "+src);
        }

        return res;
    }

    public void saveGameSettings(GameSettings gameSettings) throws FileIOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        String src = "tmp/GameSettings.ser";

        try {
            fos = new FileOutputStream(src);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(gameSettings);

            oos.flush();
            fos.flush();
            oos.close();
            fos.close();

            System.out.println("Saved game settings");

        } catch (IOException e) {
            throw new FileIOException("Save settings - Could not write to file "+src);
        }
    }

}
