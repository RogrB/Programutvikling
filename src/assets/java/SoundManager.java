package assets.java;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import view.ViewUtil;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static model.GameModel.gameSettings;

/**
 * Loading and playing sounds and music for the entire application.
 *
 * @author Jonas Ege Carlsen
 */
public class SoundManager {

    /**
     * The singleton object.
     */
    private static SoundManager inst = new SoundManager();

    /**
     * Method to access singleton class.
     * @return Returns a reference to the singleton object.
     */
    public static SoundManager getInst(){return inst; }

    /**
     * Executor that handles playing music.
     */
    private ExecutorService musicPool = Executors.newSingleThreadExecutor();

    /**
     * Executor that handles playing sounds.
     */
    private ExecutorService soundPool = Executors.newFixedThreadPool(4);

    /**
     * Map to store music files
     */
    private Map<String, Media> musicMap = new HashMap<>();

    /**
     * Map to store sound files.
     */
    private Map<String, AudioClip> soundMap = new HashMap<>();

    /**
     * Player to play music files.
     */
    private MediaPlayer player;

    /**
     * <b>Constructor</b>. Loads music and sound files.
     */
    public SoundManager(){
        try{
            String audio = "/assets/audio/";

            loadSound("shot_player", getClass().getResource(audio + "shots/player_1.mp3").toURI().toURL(), 1f);

            loadSound("shot_boss", getClass().getResource(audio + "boss_sfx/boss_shot_1.mp3").toURI().toURL(), .5f);

            loadSound("shot_enemy_1", getClass().getResource(audio + "shots/enemy_1.mp3").toURI().toURL(), .5f);
            loadSound("shot_enemy_2", getClass().getResource(audio + "shots/enemy_2.mp3").toURI().toURL(), .5f);
            loadSound("shot_enemy_3", getClass().getResource(audio + "shots/enemy_3.mp3").toURI().toURL(), .5f);
            loadSound("shot_enemy_4", getClass().getResource(audio + "shots/enemy_4.mp3").toURI().toURL(), .5f);
            loadSound("shot_enemy_5", getClass().getResource(audio + "shots/enemy_5.mp3").toURI().toURL(), .5f);
            loadSound("shot_enemy_6", getClass().getResource(audio + "shots/enemy_6.mp3").toURI().toURL(), .5f);

            loadSound("bullet_impact", getClass().getResource(audio + "impacts/bullet_impact_1.mp3").toURI().toURL(), 1f);
            loadSound("impact_1", getClass().getResource(audio + "impacts/impact_1.mp3").toURI().toURL(), 1f);
            loadSound("impact_2", getClass().getResource(audio + "impacts/impact_2.mp3").toURI().toURL(), 1f);
            loadSound("impact_3", getClass().getResource(audio + "impacts/impact_3.mp3").toURI().toURL(), 1f);
            loadSound("impact_4", getClass().getResource(audio + "impacts/impact_4.mp3").toURI().toURL(), 1f);

            loadSound("up_weapon", getClass().getResource(audio + "sfx/beep_1.mp3").toURI().toURL(), 1f);
            loadSound("up_health", getClass().getResource(audio + "sfx/ascending_2.mp3").toURI().toURL(), 1f);
            loadSound("up_shield", getClass().getResource(audio + "sfx/ascending_1.mp3").toURI().toURL(), .5f);

            loadSound("sfx_1", getClass().getResource(audio + "sfx/hit_1.mp3").toURI().toURL(), 1f);
            loadSound("sfx_2", getClass().getResource(audio + "sfx/alarm_1.mp3").toURI().toURL(), .6f);

            loadSound("boss_spawn_1", getClass().getResource(audio + "boss_sfx/boss_spawn_1.mp3").toURI().toURL(), 1f);
            loadSound("boss_wobble", getClass().getResource(audio + "sfx/wobble_1.mp3").toURI().toURL(), .6f);
            loadSound("boss_talk_1", getClass().getResource(audio + "boss_sfx/boss_talk_1.mp3").toURI().toURL(), 1f);
            loadSound("boss_talk_2", getClass().getResource(audio + "boss_sfx/boss_talk_2.mp3").toURI().toURL(), 1f);
            loadSound("boss_talk_3", getClass().getResource(audio + "boss_sfx/boss_talk_3.mp3").toURI().toURL(), 1f);
            loadSound("boss_talk_4", getClass().getResource(audio + "boss_sfx/boss_talk_4.mp3").toURI().toURL(), 1f);
            loadSound("boss_talk_5", getClass().getResource(audio + "boss_sfx/boss_talk_5.mp3").toURI().toURL(), 1f);
            loadSound("boss_talk_6", getClass().getResource(audio + "boss_sfx/boss_talk_6.mp3").toURI().toURL(), 1f);

            loadSound("nav", getClass().getResource(audio + "sfx/nav_1.mp3").toURI().toURL(), .8f);
            loadSound("nav_select", getClass().getResource(audio + "sfx/nav_select_1.mp3").toURI().toURL(), 1f);

            loadMusic("music_menu", getClass().getResource(audio + "music/music_menu_2.mp3").toURI().toURL());
            loadMusic("music_battle", getClass().getResource(audio + "music/music_battle_2.mp3").toURI().toURL());
        }
        catch(Exception e){
            ViewUtil.setError(e.getMessage());
            System.err.println(e.getMessage());
        }

    }

    /**
     * Loads sounds into the sound map.
     * @param id Name used to extract sound.
     * @param url Location of the file.
     * @param volume To adjust volume of sound.
     */
    private void loadSound(String id, URL url, float volume){
        AudioClip audioClip = new AudioClip(url.toExternalForm());
        audioClip.setVolume(volume * ((float) gameSettings.getSoundValue() / 100));
        soundMap.put(id, audioClip);
    }

    /**
     * Loads music into the music map
     * @param id Name used to extract music.
     * @param url Location of the file.
     */
    private void loadMusic(String id, URL url){
        Media media = new Media(url.toExternalForm());
        musicMap.put(id, media);
    }

    /**
     * Method for playing music
     * @param id ID of music file to play. Pass "stop" to stop music.
     */
    public void playMusic(String id){
        Runnable music = () -> {
            switch(id){
                case "music_menu":
                    player = new MediaPlayer(musicMap.get(id));
                    player.setVolume((float) gameSettings.getMusicValue() / 100);
                    player.setCycleCount(MediaPlayer.INDEFINITE);
                    player.play();
                    break;
                case "music_battle":
                    player = new MediaPlayer(musicMap.get(id));
                    player.setVolume((float) gameSettings.getMusicValue() / 100);
                    player.setCycleCount(MediaPlayer.INDEFINITE);
                    player.play();
                    break;
                case "stop":
                    player.stop();
                    player = null;
                    break;
            }
        };
        musicPool.execute(music);
    }

    /**
     * Method for playing a sound.
     * @param id ID of sound to play.
     */
    private void playSound(String id){
        Runnable sound = () -> {
            soundMap.get(id).setVolume((float) gameSettings.getSoundValue() / 100);
            soundMap.get(id).play();
        };
        soundPool.execute(sound);
    }

    /**
     * Method that plays a sound
     */
    public void shotPlayer(){
        playSound("shot_player");
    }

    /**
     * Method that plays a sound
     */
    public void shotBoss(){
        playSound("shot_boss");
    }

    /**
     * Method that plays a sound randomly selected from 6 options.
     */
    public void shotEnemy(){
        Random rand = new Random();
        switch(rand.nextInt(5)){
            case 0:
                playSound("shot_enemy_1");
                break;
            case 1:
                playSound("shot_enemy_2");
                break;
            case 2:
                playSound("shot_enemy_3");
                break;
            case 3:
                playSound("shot_enemy_4");
                break;
            case 4:
                playSound("shot_enemy_5");
                break;
            case 5:
                playSound("shot_enemy_6");
                break;
        }
    }

    /**
     * Method that plays a sound
     */
    public void impactBullets(){
        playSound("bullet_impact");
    }

    /**
     * Method that plays a sound
     */
    public void impactShield(){
        playSound("impact_4");
    }

    /**
     * Method that plays two sounds.
     */
    public void impactPlayer(){
        playSound("sfx_1");
        playSound("sfx_2");
    }

    /**
     * Method that plays a sound.
     */
    public void entityDead(){
        playSound("impact_2");
    }

    /**
     * Method that plays a sound.
     */
    public void bossWobble(){
        playSound("boss_wobble");
    }

    /**
     * Method that plays a random boss "talk" on average every third second.
     */
    public void bossTalk(){
        Random rand = new Random();
        if(rand.nextInt(180) == 0){
            switch(rand.nextInt(5)){
                case 1:
                    playSound("boss_talk_1");
                    break;
                case 2:
                    playSound("boss_talk_2");
                    break;
                case 3:
                    playSound("boss_talk_3");
                    break;
                case 4:
                    playSound("boss_talk_4");
                    break;
                case 5:
                    playSound("boss_talk_5");
                    break;
            }
        }
    }

    /**
     * Method that plays two sounds.
     */
    public void bossDies(){
        playSound("impact_1");
        playSound("impact_3");
    }

    /**
     * Method that plays a sound.
     */
    public void nav(){
        playSound("nav");
    }

    /**
     * Method that plays a sound.
     */
    public void navSelect(){
        playSound("nav_select");
    }

    /**
     * Method that plays a sound.
     */
    public void upgradeWeapon(){
        playSound("up_weapon");
    }

    /**
     * Method that plays a sound.
     */
    public void upgradeHealth(){
        playSound("up_health");
    }

    /**
     * Method that plays a sound.
     */
    public void upgradeShield(){
        playSound("up_shield");
    }

    /**
     * Method to shut down the thread pools.
     */
    public void shutdown(){
        musicPool.shutdown();
        soundPool.shutdown();
    }

    /**
     * @return Returns the music player
     */
    public MediaPlayer getPlayer() {
        return player;
    }
}
