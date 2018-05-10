package io;

import exceptions.FileIOException;
import model.GameModel;
import model.GameSettings;
import model.GameState;
import model.enemy.Enemy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static controller.GameController.gs;

/**
 * <h1>Reads and writes save states</h1>
 * Class to handle reading and writing to and from save states, options
 * and back end settings. These are all serialized classes whom are saved
 * in the <i>tmp/</i> folder.
 * <p>
 * <b>Note:</b> The IOManager is a singleton.
 * 
 * @author  Åsmund Røst Wien
 */
public class IOManager {

    private static IOManager inst = new IOManager();
    private IOManager(){ initDirs(); }
    public static IOManager getInstance(){ return inst; }

    /**
     * This method is constantly called from <i>io.AutoSave.java</i>
     * when a single player game runs. It serializes and writes the
     * GameState and all of it's arrays of content.
     * @throws FileIOException
     */
    public void saveGameState() throws FileIOException {
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

    /**
     * This method is constantly called from <i>io.AutoSave.java</i>
     * when a single player game runs. It serializes and writes the
     * GameState and all of it's arrays of content.
     * @param saveState The save state (0-2) to be returned.
     * @return The GameState object which is called for.
     * @throws FileIOException
     */
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

    /**
     * Overwrites the current GameState object with the loaded instance.
     * @throws FileIOException
     */
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

    /**
     * Serializes and writes an <i>ArrayList</i> to a directory.
     * @param list The ArrayList to be serialized and written to the <i>src</i> directory.
     * @param src The directory of where to store <i>list</i>.
     * @throws FileIOException
     */
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

    /**
     * Reads and returns a <i>list</i> from the <i>src</i> directory.
     * @param src Directory where the file is expected to be.
     * @return The <i>list</i> at the <i>src</i> directory, if found.
     * @throws FileIOException
     */
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

    /**
     * Checks if the last played save state exists, with all of it's files.
     * @return <i>true</i> or <i>false</i>.
     */
    public boolean saveStateExists(){
        return saveStateExists(GameModel.gameSettings.getPrevSave());
    }

    /**
     * Checks if a speciffic save state exists, with all of it's files.
     * @param saveState The save file (0-2) to look for.
     * @return <i>True</i> or <i>false</i>.
     */
    public boolean saveStateExists(int saveState){
        return fileExists("tmp/" + saveState + "/GameState.ser") &&
                fileExists("tmp/" + saveState + "/ArrayEnemies.ser") &&
                fileExists("tmp/" + saveState + "/ArrayEnemyBullets.ser") &&
                fileExists("tmp/" + saveState + "/ArrayPlayerBullets.ser") &&
                fileExists("tmp/" + saveState + "/ArrayPowerups.ser");
    }

    /**
     * Checks if a speciffic file exists.
     * @param src The file destination.
     * @return <i>true</i> or <i>false</i>.
     */
    public boolean fileExists(String src){
        File file = new File(src);
        return file.exists() && !file.isDirectory();
    }

    /**
     * Loads the stored GameSettings object.
     * @return The stored GameSettings object.
     * @throws FileIOException
     */
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

    /**
     * Serializes and stores a GameSettings object.
     * @param gameSettings The object to be stored.
     * @throws FileIOException
     */
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

    /**
     * Looks for directories, and creates them if they don't exist.
     * <b>Note: </b>This typically only happens the first time the
     * game runs, or if the folders are manually removed.
     */
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
