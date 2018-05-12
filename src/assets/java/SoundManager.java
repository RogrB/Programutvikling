package assets.java;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import view.ViewUtil;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static model.GameModel.gameSettings;

public class SoundManager {
    private static SoundManager inst = new SoundManager();
    public static SoundManager getInst(){return inst; }
    private ExecutorService musicPool = Executors.newSingleThreadExecutor();
    private ExecutorService soundPool = Executors.newFixedThreadPool(2);
    private Map<String, Media> musicMap = new HashMap<>();
    private Map<String, AudioClip> soundMap = new HashMap<>();
    private MediaPlayer player;
    private String currentMusic = "music_menu";

    private File MUSIC_WIN =      new File("src/assets/audio/music/music_retro_1.wav");


    public SoundManager(){
        try{
            String audio = "/assets/audio/";

            loadSound("shot_player", getClass().getResource(audio + "shots/player_1.wav").toURI().toURL(), 1f);

            loadSound("shot_boss", getClass().getResource(audio + "boss_sfx/boss_shot_1.wav").toURI().toURL(), .6f);

            loadSound("shot_enemy_1", getClass().getResource(audio + "shots/enemy_1.wav").toURI().toURL(), .5f);
            loadSound("shot_enemy_2", getClass().getResource(audio + "shots/enemy_2.wav").toURI().toURL(), .5f);
            loadSound("shot_enemy_3", getClass().getResource(audio + "shots/enemy_3.wav").toURI().toURL(), .5f);
            loadSound("shot_enemy_4", getClass().getResource(audio + "shots/enemy_4.wav").toURI().toURL(), .5f);
            loadSound("shot_enemy_5", getClass().getResource(audio + "shots/enemy_5.wav").toURI().toURL(), .5f);
            loadSound("shot_enemy_6", getClass().getResource(audio + "shots/enemy_6.wav").toURI().toURL(), .5f);

            loadSound("bullet_impact", getClass().getResource(audio + "impacts/bullet_impact_1.wav").toURI().toURL(), 1f);
            loadSound("impact_1", getClass().getResource(audio + "impacts/impact_1.wav").toURI().toURL(), 1f);
            loadSound("impact_2", getClass().getResource(audio + "impacts/impact_2.wav").toURI().toURL(), 1f);
            loadSound("impact_3", getClass().getResource(audio + "impacts/impact_3.wav").toURI().toURL(), 1f);
            loadSound("impact_4", getClass().getResource(audio + "impacts/impact_4.wav").toURI().toURL(), 1f);

            loadSound("up_weapon", getClass().getResource(audio + "sfx/beep_1.wav").toURI().toURL(), 1f);
            loadSound("up_health", getClass().getResource(audio + "sfx/ascending_2.wav").toURI().toURL(), 1f);
            loadSound("up_shield", getClass().getResource(audio + "sfx/ascending_1.wav").toURI().toURL(), .7f);

            loadSound("sfx_1", getClass().getResource(audio + "sfx/hit_1.wav").toURI().toURL(), 1f);
            loadSound("sfx_2", getClass().getResource(audio + "sfx/alarm_1.wav").toURI().toURL(), .6f);

            loadSound("boss_spawn_1", getClass().getResource(audio + "boss_sfx/boss_spawn_1.wav").toURI().toURL(), 1f);
            loadSound("boss_wobble", getClass().getResource(audio + "sfx/wobble_1.wav").toURI().toURL(), .7f);
            loadSound("boss_talk_1", getClass().getResource(audio + "boss_sfx/boss_talk_1.wav").toURI().toURL(), 1f);
            loadSound("boss_talk_2", getClass().getResource(audio + "boss_sfx/boss_talk_2.wav").toURI().toURL(), 1f);
            loadSound("boss_talk_3", getClass().getResource(audio + "boss_sfx/boss_talk_3.wav").toURI().toURL(), 1f);
            loadSound("boss_talk_4", getClass().getResource(audio + "boss_sfx/boss_talk_4.wav").toURI().toURL(), 1f);
            loadSound("boss_talk_5", getClass().getResource(audio + "boss_sfx/boss_talk_5.wav").toURI().toURL(), 1f);
            loadSound("boss_talk_6", getClass().getResource(audio + "boss_sfx/boss_talk_6.wav").toURI().toURL(), 1f);

            loadSound("nav", getClass().getResource(audio + "sfx/nav_1.wav").toURI().toURL(), 1f);
            loadSound("nav_select", getClass().getResource(audio + "sfx/nav_select_1.wav").toURI().toURL(), 1f);

            loadMusic("music_win", getClass().getResource(audio + "music/music_retro_1.wav").toURI().toURL());
            loadMusic("music_menu", getClass().getResource(audio + "music/music_menu_2.wav").toURI().toURL());
            loadMusic("music_battle", getClass().getResource(audio + "music/music_battle_2.wav").toURI().toURL());
        }
        catch(Exception e){
            ViewUtil.setError(e.getMessage());
            e.printStackTrace();
        }

    }

    private void loadSound(String id, URL url, float volume){
        AudioClip audioClip = new AudioClip(url.toExternalForm());
        audioClip.setVolume(volume * ((float) gameSettings.getSoundValue() / 100));
        soundMap.put(id, audioClip);
    }

    private void loadMusic(String id, URL url){
        Media media = new Media(url.toExternalForm());
        musicMap.put(id, media);
    }

    public void playMusic(String id){
        Runnable music = () -> {
            currentMusic = id;
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
                case "music_win":
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

    private void playSound(String id){
        Runnable sound = () -> {
            soundMap.get(id).setVolume((float) gameSettings.getSoundValue() / 100);
            soundMap.get(id).play();
        };
        soundPool.execute(sound);
    }

    public void shotPlayer(){
        playSound("shot_player");
    }

    public void shotBoss(){
        playSound("shot_boss");
    }

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

    public void impactBullets(){
        playSound("bullet_impact");
    }

    public void impactShield(){
        playSound("impact_4");
    }

    public void impactPlayer(){
        playSound("sfx_1");
        playSound("sfx_2");
    }

    public void entityDead(){
        playSound("impact_2");
    }

    public void bossWobble(){
        playSound("boss_wobble");
    }

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

    public void bossDies(){
        playSound("impact_1");
        playSound("impact_3");
        //play music
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void nav(){
        playSound("nav");
    }

    public void navSelect(){
        playSound("nav_select");
    }

    public void upgradeWeapon(){
        playSound("up_weapon");
    }

    public void upgradeHealth(){
        playSound("up_health");
    }

    public void upgradeShield(){
        playSound("up_shield");
    }

    public void shutdown(){
        musicPool.shutdown();
        soundPool.shutdown();
    }
}
