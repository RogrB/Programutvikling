package model;

import exceptions.FileIOException;
import io.IOManager;

import java.io.Serializable;
import java.util.ArrayList;

public class GameSettings implements Serializable {
    private int prevSave;
    private int musicvalue;
    private int soundValue;
    private int difficultyValue;

    public GameSettings(){
        String url = "tmp/GameSettings.ser";
        if(IOManager.getInstance().fileExists(url)){
            readFromFile();
        } else {
            initNewSettings();
        }
    }

    private void initNewSettings(){
        prevSave = -1;
        difficultyValue = 3;
        musicvalue = 50;
        soundValue = 50;
        writeToFile();
    }

    public void saveSettings(int difficultyValue, int soundValue, int musicvalue){
        this.difficultyValue = difficultyValue;
        this.soundValue = soundValue;
        this.musicvalue = musicvalue;
        writeToFile();
    }

    public void savePrevSave(int prevSave){
        this.prevSave = prevSave;
        System.out.println("GameSettings::prevSave == "+this.prevSave);
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
        GameSettings fromFile;

        try {
            fromFile = IOManager.getInstance().loadGameSettings();
            prevSave = fromFile.prevSave;
            musicvalue = fromFile.musicvalue;
            soundValue = fromFile.soundValue;
            difficultyValue = fromFile.difficultyValue;
        } catch (FileIOException e) {
            System.err.println(e.getMessage());
            initNewSettings();
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
