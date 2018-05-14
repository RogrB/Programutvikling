package model;

import exceptions.FileIOException;
import io.IOManager;
import view.ViewUtil;

import java.io.Serializable;

/**
 * <h1>Manages game settings</h1>
 * This class stores the custom settings which are set by
 * the player in the {@code OptionView}.
 * <p><b>Note: </b>This class also manages calls to the
 * IOManager to store the settings when updated by the
 * player, and reads old settings when required.
 *
 * @author Åsmund Røst Wien
 * @see view.OptionsView
 */
public class GameSettings implements Serializable {

    /**
     * Variables for in game access to the settings values.
     */
    private int prevSave, musicValue, soundValue, difficultyValue;

    /**
     * <b>Constructor: </b>gets previously stored settings from file,
     * and initiates a new file if it doesn't exist.
     */
    GameSettings(){
        String url = "tmp/GameSettings.ser";
        if(IOManager.getInstance().fileExists(url)){
            readFromFile();
        } else {
            initNewSettings();
        }
    }

    /**
     * Initiates the object with standard values, and stores them to a file.
     */
    private void initNewSettings(){
        prevSave = -1;
        difficultyValue = 3;
        musicValue = 50;
        soundValue = 50;
        writeToFile();
    }

    /**
     * Sets the objects customizable fields with new parameters and writes them to the file.
     * @param difficultyValue The new difficulty value.
     * @param soundValue The new preferred sound (SFX) volume.
     * @param musicValue The new preferred music volume.
     */
    public void saveSettings(int difficultyValue, int soundValue, int musicValue){
        this.difficultyValue = difficultyValue;
        this.soundValue = soundValue;
        this.musicValue = musicValue;
        writeToFile();
    }

    /**
     * Sets the index of the previously saved game state for quick access
     * and writes it to file.
     * @param prevSave The index of the previously played save state.
     */
    public void savePrevSave(int prevSave){
        this.prevSave = prevSave;
        writeToFile();
    }

    /**
     * Writes the objects current state to file.
     */
    private void writeToFile(){
        try {
            IOManager.getInstance().saveGameSettings(this);
        } catch (FileIOException e) {
            ViewUtil.setError(e.getMessage());
            System.err.println(e.getMessage());
        }
    }

    /**
     * Sets the object's fields to the value of the stored serialized object.
     * If the stored objet is corrupt or doesn't exist, the object is
     * initialized to standard values.
     */
    private void readFromFile(){
        GameSettings fromFile;

        try {
            fromFile = IOManager.getInstance().loadGameSettings();
            prevSave = fromFile.prevSave;
            musicValue = fromFile.musicValue;
            soundValue = fromFile.soundValue;
            difficultyValue = fromFile.difficultyValue;
        } catch (FileIOException e) {
            System.err.println(e.getMessage());
            ViewUtil.setError(e.getMessage());
            initNewSettings();
        }
    }

    /**
     * Returns the music volume setting.
     * @return The music volume setting.
     */
    public int getMusicValue() {
        return musicValue;
    }

    /**
     * Returns the sfx volume setting.
     * @return The sfx volume setting.
     */
    public int getSoundValue() {
        return soundValue;
    }

    /**
     * Returns the difficulty value setting.
     * @return The difficulty value setting.
     */
    public int getDifficultyValue() {
        return difficultyValue;
    }

    /**
     * Returns the previous save state accessed by the player.
     * @return The previous save state accessed by the player.
     */
    public int getPrevSave() {
        return prevSave;
    }
}
