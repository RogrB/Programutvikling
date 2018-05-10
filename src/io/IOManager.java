package io;

import exceptions.FileIOException;
import model.GameModel;
import model.GameSettings;
import model.GameState;
import model.enemy.Enemy;
import view.ViewUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static controller.GameController.gs;

public class IOManager {

    private static IOManager inst = new IOManager();
    private IOManager(){ initDirs(); }
    public static IOManager getInstance(){ return inst; }

    void saveGameState() throws FileIOException {
        FileOutputStream fos;
        ObjectOutputStream oos;

        String src = "tmp/"+GameModel.gameSettings.getPrevSave()+"/GameState.ser";

        try {
            fos = new FileOutputStream(src);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(gs);

            oos.flush();
            fos.flush();
            oos.close();
            fos.close();

            saveArrayList(GameState.enemies, "tmp/"+GameModel.gameSettings.getPrevSave()+"/ArrayEnemies.ser");
            saveArrayList(gs.enemyBullets, "tmp/"+GameModel.gameSettings.getPrevSave()+"/ArrayEnemyBullets.ser");
            saveArrayList(gs.playerBullets, "tmp/"+GameModel.gameSettings.getPrevSave()+"/ArrayPlayerBullets.ser");
            saveArrayList(gs.powerups, "tmp/"+GameModel.gameSettings.getPrevSave()+"/ArrayPowerups.ser");

        } catch (IOException e) {
            throw new FileIOException("Save game - Could not save to file: "+src);
        } catch (FileIOException f) {
            throw new FileIOException("Save game - "+f.getMessage());
        }
    }

    public GameState getGameState(int saveState) throws FileIOException {
        FileInputStream fis;
        ObjectInputStream ois;

        String src = "tmp/"+saveState+"/GameState.ser";
        GameState res = null;

        try {
            fis = new FileInputStream(src);
            ois = new ObjectInputStream(fis);

            res = (GameState) ois.readObject();

            ois.close();
            fis.close();

        } catch (IOException i) {
            throw new FileIOException("Load game - Can't locate file: "+src);
        } catch (ClassNotFoundException c) {
            throw new FileIOException("Load game - Corrupt class or file in: " + src);
        }

        return res;
    }

    public void loadGameState() throws FileIOException {
        FileInputStream fis;
        ObjectInputStream ois;

        String src = "tmp/"+GameModel.gameSettings.getPrevSave()+"/GameState.ser";

        try {
            fis = new FileInputStream(src);
            ois = new ObjectInputStream(fis);

            gs = (GameState) ois.readObject();

            ois.close();
            fis.close();

            gs.enemies = (ArrayList) loadList("tmp/"+GameModel.gameSettings.getPrevSave()+"/ArrayEnemies.ser");
            gs.enemyBullets = (ArrayList) loadList("tmp/"+GameModel.gameSettings.getPrevSave()+"/ArrayEnemyBullets.ser");
            gs.playerBullets = (ArrayList) loadList("tmp/"+GameModel.gameSettings.getPrevSave()+"/ArrayPlayerBullets.ser");
            gs.powerups = (ArrayList) loadList("tmp/"+GameModel.gameSettings.getPrevSave()+"/ArrayPowerups.ser");

        } catch (IOException i) {
            throw new FileIOException("Load game - Can't locate file: "+src);
        } catch (ClassNotFoundException c) {
            throw new FileIOException("Load game - Corrupt class or file in: "+src);
        } catch (FileIOException f) {
            throw new FileIOException("Load game - "+f.getMessage());
        }
    }

    private void saveArrayList(ArrayList list, String src) throws FileIOException {
        FileOutputStream fos;
        ObjectOutputStream oos;

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
        FileInputStream fis;
        ObjectInputStream ois;

        List<Enemy> res;

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
        return saveStateExists(GameModel.gameSettings.getPrevSave());
    }

    public boolean saveStateExists(int saveState){
        return fileExists("tmp/" + saveState + "/GameState.ser") &&
                fileExists("tmp/" + saveState + "/ArrayEnemies.ser") &&
                fileExists("tmp/" + saveState + "/ArrayEnemyBullets.ser") &&
                fileExists("tmp/" + saveState + "/ArrayPlayerBullets.ser") &&
                fileExists("tmp/" + saveState + "/ArrayPowerups.ser");
    }

    public boolean fileExists(String src){
        File file = new File(src);
        return file.exists() && !file.isDirectory();
    }

    public GameSettings loadGameSettings() throws FileIOException {
        FileInputStream fis;
        ObjectInputStream ois;

        String src = "tmp/GameSettings.ser";

        GameSettings res;

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
        FileOutputStream fos;
        ObjectOutputStream oos;

        String src = "tmp/GameSettings.ser";

        try {
            fos = new FileOutputStream(src);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(gameSettings);

            oos.flush();
            fos.flush();
            oos.close();
            fos.close();

        } catch (IOException e) {
            throw new FileIOException("Save settings - Could not write to file "+src);
        }
    }

    private void initDirs(){
        File directory;

        directory = new File("tmp");
        if (! directory.exists())
            directory.mkdir();
        directory = new File("tmp/0");
        if (! directory.exists())
            directory.mkdir();
        directory = new File("tmp/1");
        if (! directory.exists())
            directory.mkdir();
        directory = new File("tmp/2");
        if (! directory.exists())
            directory.mkdir();
    }

}
