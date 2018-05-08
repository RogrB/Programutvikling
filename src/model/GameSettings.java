package model;

import exceptions.FileIOException;
import io.IOManager;

import java.io.Serializable;

public class GameSettings implements Serializable {
    private int prevSave;
    private int musicvalue;
    private int soundValue;
    private int difficultyValue;

    private final String url = "tmp/GameSettings.ser";

    public GameSettings(){
        if(IOManager.getInstance().fileExists(url)){
            readFromFile();
        } else {
            prevSave = -1;
            difficultyValue = 3;
            musicvalue = 50;
            soundValue = 50;
            writeToFile();
        }
    }

    public void saveSettings(int difficultyValue, int soundValue, int musicvalue){
        this.difficultyValue = difficultyValue;
        this.soundValue = soundValue;
        this.musicvalue = musicvalue;
        writeToFile();
    }

    public void savePrevSave(int prevSave){
        this.prevSave = prevSave;
        writeToFile();
    }

    private void writeToFile(){
        try {
            IOManager.getInstance().saveGameSettings(this);
        } catch (FileIOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void readFromFile(){
        GameSettings fromFile = null;

        try {
            fromFile = IOManager.getInstance().loadGameSettings();
            prevSave = fromFile.prevSave;
            musicvalue = fromFile.musicvalue;
            soundValue = fromFile.soundValue;
            difficultyValue = fromFile.difficultyValue;
        } catch (FileIOException e) {
            System.err.println(e.getMessage());
        }
    }

    public int getMusicValue() {
        return musicvalue;
    }

    public int getSoundValue() {
        return soundValue;
    }

    public int getDifficultyValue() {
        return difficultyValue;
    }

    public int getPrevSave() {
        return prevSave;
    }
}
