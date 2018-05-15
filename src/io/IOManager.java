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
 * This class handles the process of reading and writing to
 * and from save states, options and back end settings.
 * These are all serialized classes whom are stored in the
 * {@code tmp/} folder or its sub directories.
 *
 * @author Åsmund Røst Wien
 */
public class IOManager {

    /**
     * The singleton object.
     */
    private static IOManager inst = new IOManager();

    /**
     * Private constructor (singleton).
     */
    private IOManager(){ initDirs(); }

    /**
     * Method to access singleton class.
     * @return Returns a reference to the singleton object.
     */
    public static IOManager getInstance(){ return inst; }

    /**
     * Field for writing to an output stream.
     */
    private FileOutputStream fileOutputStream;

    /**
     * Field for serializing an object to an output stream.
     */
    private ObjectOutputStream objectOutputStream;

    /**
     * Field for reading from an input stream.
     */
    private FileInputStream fileInputStream;

    /**
     * Field for deserializing an object from an input stream.
     */
    private ObjectInputStream objectInputStream;

    /**
     * Field for access to a directory or file stream.
     */
    private File file;

    /**
     * This method is continually called on {@code AutoSave}
     * when a single player game runs. It serializes and writes the
     * {@code GameState} and all of it's arrays of content.
     * @throws FileIOException When file can't be written to.
     * @see FileIOException
     * @see AutoSave
     */
    void saveGameState() throws FileIOException {
        String src = "tmp/"+GameModel.gameSettings.getPrevSave()+"pikk/GameState.ser";

        try {
            fileOutputStream = new FileOutputStream(src);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(gs);

            objectOutputStream.flush();
            fileOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();

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
     * Reads and returns a {@code GameState}.
     * @param saveState The save (0-2) to be returned.
     * @return The {@code GameState} object which is called for.
     * @throws FileIOException When file can't be read from.
     * @see FileIOException
     * @see GameState
     */
    public GameState getGameState(int saveState) throws FileIOException {
        String src = "tmp/"+saveState+"/GameState.ser";
        GameState res;

        try {
            fileInputStream = new FileInputStream(src);
            objectInputStream = new ObjectInputStream(fileInputStream);

            res = (GameState) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

        } catch (IOException i) {
            throw new FileIOException("Load game - Can't locate file: "+src);
        } catch (ClassNotFoundException c) {
            throw new FileIOException("Load game - Corrupt class or file in: " + src);
        }

        return res;
    }

    /**
     * Overwrites the current {@code GameState} object with the loaded instance.
     * @throws FileIOException When file can't be read from.
     * @see FileIOException
     * @see GameState
     */
    public void loadGameState() throws FileIOException {
        String src = "tmp/"+GameModel.gameSettings.getPrevSave()+"/GameState.ser";

        try {
            fileInputStream = new FileInputStream(src);
            objectInputStream = new ObjectInputStream(fileInputStream);

            gs = (GameState) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

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
     * Serializes and writes an {@code ArrayList} to a directory.
     * @param list The {@code ArrayList} to be serialized and written to the {@code src} directory.
     * @param src The directory of where to store {@code ArrayList}
     * @throws FileIOException When file can't be written to.
     * @see FileIOException
     */
    private void saveArrayList(ArrayList list, String src) throws FileIOException {
        try {
            fileOutputStream = new FileOutputStream(src);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new FileIOException("Cant't locate file: "+src);
        } catch (IOException e) {
            throw new FileIOException("Error writing to file: "+src);
        }
    }

    /**
     * Reads and returns a {@code list} from the {@code src} directory.
     * @param src Directory where the file is expected to be.
     * @return The {@code list} at the {@code src} directory, if found.
     * @throws FileIOException When file can't be read from.
     * @see FileIOException
     * @see List
     */
    private List loadList(String src) throws FileIOException {
        List<Enemy> res;

        try {
            fileInputStream = new FileInputStream(src);
            objectInputStream = new ObjectInputStream(fileInputStream);

            res = (ArrayList<Enemy>) objectInputStream.readObject();

            fileInputStream.close();
            objectInputStream.close();
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
     * @return {@code true} or {@code false}.
     * @see GameSettings
     */
    public boolean saveStateExists(){
        return saveStateExists(GameModel.gameSettings.getPrevSave());
    }

    /**
     * Checks if a speciffic save state exists, with all of it's files.
     * @param saveState The save file (0-2) to look for.
     * @return {@code true} or {@code false}.
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
        file = new File(src);
        return file.exists() && !file.isDirectory();
    }

    /**
     * Loads the stored {@code GameSettings} object.
     * @return The stored {@code GameSettings} object.
     * @throws FileIOException When file can't be read from.
     * @see FileIOException
     */
    public GameSettings loadGameSettings() throws FileIOException {
        String src = "tmp/GameSettings.ser";

        GameSettings res;

        try {
            fileInputStream = new FileInputStream(src);
            objectInputStream = new ObjectInputStream(fileInputStream);

            res = (GameSettings) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

        } catch (IOException i) {
            throw new FileIOException("Load settings - Can't locate file: "+src);
        } catch (ClassNotFoundException c) {
            throw new FileIOException("Load settings - Corrupt class or file in: "+src);
        }

        return res;
    }

    /**
     * Serializes and stores a {@code GameSettings} object.
     * @param gameSettings The object to be stored.
     * @throws FileIOException When file can't be written to.
     * @see FileIOException
     */
    public void saveGameSettings(GameSettings gameSettings) throws FileIOException {
        String src = "tmp/GameSettings.ser";

        try {
            fileOutputStream = new FileOutputStream(src);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(gameSettings);

            objectOutputStream.flush();
            fileOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            throw new FileIOException("Save settings - Could not write to file "+src);
        }
    }

    /**
     * Looks for directories, and creates them if they don't exist.
     * <p>
     * <b>Note: </b>This typically only happens the first time the
     * game runs, or if the folders are manually removed.
     */
    private void initDirs(){
        file = new File("tmp");
        if (! file.exists())
            file.mkdir();
        file = new File("tmp/0");
        if (! file.exists())
            file.mkdir();
        file = new File("tmp/1");
        if (! file.exists())
            file.mkdir();
        file = new File("tmp/2");
        if (! file.exists())
            file.mkdir();
    }

}
