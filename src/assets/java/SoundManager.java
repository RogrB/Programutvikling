package assets.java;

import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static model.GameModel.gameSettings;

public class SoundManager {
    private static SoundManager inst = new SoundManager();
    public static SoundManager getInst(){return inst; }
    ExecutorService soundPool = Executors.newFixedThreadPool(2);
    Map<String, AudioClip> soundMap = new HashMap<>();

    private final String AUDIO_ASSETS = "src/assets/audio/";

    private final File SHOT_PLAYER_01 = new File(AUDIO_ASSETS + "shots/player_1.wav");

    private final File SHOT_BOSS =      new File(AUDIO_ASSETS + "boss_sfx/boss_shot_1.wav");
    private final File SHOT_ENEMY_1 = new File(AUDIO_ASSETS + "shots/enemy_1.wav");
    private final File SHOT_ENEMY_2 = new File(AUDIO_ASSETS + "shots/enemy_2.wav");
    private final File SHOT_ENEMY_3 = new File(AUDIO_ASSETS + "shots/enemy_3.wav");
    private final File SHOT_ENEMY_4 = new File(AUDIO_ASSETS + "shots/enemy_4.wav");
    private final File SHOT_ENEMY_5 = new File(AUDIO_ASSETS + "shots/enemy_5.wav");
    private final File SHOT_ENEMY_6 = new File(AUDIO_ASSETS + "shots/enemy_6.wav");

    private final File BULLET_IMPACT =  new File(AUDIO_ASSETS + "impacts/bullet_impact_1.wav");
    private final File IMPACT_01 =      new File(AUDIO_ASSETS + "impacts/impact_1.wav");
    private final File IMPACT_02 =      new File(AUDIO_ASSETS + "impacts/impact_2.wav");
    private final File IMPACT_03 =      new File(AUDIO_ASSETS + "impacts/impact_3.wav");
    private final File IMPACT_04 =      new File(AUDIO_ASSETS + "impacts/impact_4.wav");

    private final File UP_WEAPON =      new File(AUDIO_ASSETS + "sfx/beep_1.wav");
    private final File UP_HEALTH =      new File(AUDIO_ASSETS + "sfx/ascending_2.wav");
    private final File UP_SHIELD =      new File(AUDIO_ASSETS + "sfx/ascending_1.wav");

    private final File SFX_1 =          new File(AUDIO_ASSETS + "sfx/hit_1.wav");
    private final File SFX_2 =          new File(AUDIO_ASSETS + "sfx/alarm_1.wav");

    private final File BOSS_SPAWN_1 =   new File(AUDIO_ASSETS + "boss_sfx/boss_spawn_1.wav");
    private final File BOSS_WOBBLE_1 =  new File(AUDIO_ASSETS + "sfx/wobble_1.wav");
    private final File BOSS_TALK_1 = new File(AUDIO_ASSETS + "boss_sfx/boss_talk_1.wav");
    private final File BOSS_TALK_2 = new File(AUDIO_ASSETS + "boss_sfx/boss_talk_2.wav");
    private final File BOSS_TALK_3 = new File(AUDIO_ASSETS + "boss_sfx/boss_talk_3.wav");
    private final File BOSS_TALK_4 = new File(AUDIO_ASSETS + "boss_sfx/boss_talk_4.wav");
    private final File BOSS_TALK_5 = new File(AUDIO_ASSETS + "boss_sfx/boss_talk_5.wav");
    private final File BOSS_TALK_6 = new File(AUDIO_ASSETS + "boss_sfx/boss_talk_6.wav");

    private final File NAV =            new File(AUDIO_ASSETS + "sfx/nav_1.wav");
    private final File NAV_SELECT =     new File(AUDIO_ASSETS + "sfx/nav_select_1.wav");


    public SoundManager(){
        try{
            loadSound("shot_player_01", (SHOT_PLAYER_01).toURI().toURL(), 1f);
            loadSound("shot_boss", (SHOT_BOSS).toURI().toURL(), .6f);
            loadSound("shot_enemy_1", (SHOT_ENEMY_1).toURI().toURL(), .5f);
            loadSound("shot_enemy_2", (SHOT_ENEMY_2).toURI().toURL(), .5f);
            loadSound("shot_enemy_3", (SHOT_ENEMY_3).toURI().toURL(), .5f);
            loadSound("shot_enemy_4", (SHOT_ENEMY_4).toURI().toURL(), .5f);
            loadSound("shot_enemy_5", (SHOT_ENEMY_5).toURI().toURL(), .5f);
            loadSound("shot_enemy_6", (SHOT_ENEMY_6).toURI().toURL(), .5f);
            loadSound("bullet_impact", (BULLET_IMPACT).toURI().toURL(), 1f);
            loadSound("impact_1", (IMPACT_01).toURI().toURL(), 1f);
            loadSound("impact_2", (IMPACT_02).toURI().toURL(), 1f);
            loadSound("impact_3", (IMPACT_03).toURI().toURL(), 1f);
            loadSound("impact_4", (IMPACT_04).toURI().toURL(), 1f);
            loadSound("up_weapon", (UP_WEAPON).toURI().toURL(), 1f);
            loadSound("up_health", (UP_HEALTH).toURI().toURL(), 1f);
            loadSound("up_shield", (UP_SHIELD).toURI().toURL(), .7f);
            loadSound("sfx_1", (SFX_1).toURI().toURL(), 1f);
            loadSound("sfx_2", (SFX_2).toURI().toURL(), .6f);
            loadSound("boss_spawn_1", (BOSS_SPAWN_1).toURI().toURL(), 1f);
            loadSound("boss_wobble", (BOSS_WOBBLE_1).toURI().toURL(), .7f);
            loadSound("boss_talk_1", (BOSS_TALK_1).toURI().toURL(), 1f);
            loadSound("boss_talk_2", (BOSS_TALK_2).toURI().toURL(), 1f);
            loadSound("boss_talk_3", (BOSS_TALK_3).toURI().toURL(), 1f);
            loadSound("boss_talk_4", (BOSS_TALK_4).toURI().toURL(), 1f);
            loadSound("boss_talk_5", (BOSS_TALK_5).toURI().toURL(), 1f);
            loadSound("boss_talk_6", (BOSS_TALK_6).toURI().toURL(), 1f);
            loadSound("nav", (NAV).toURI().toURL(), 1f);
            loadSound("nav_select", (NAV_SELECT).toURI().toURL(), 1f);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void loadSound(String id, URL url, float volume){
        AudioClip audioClip = new AudioClip(url.toExternalForm());
        audioClip.setVolume(volume * ((float) gameSettings.getSoundValue() / 100));
        soundMap.put(id, audioClip);
    }

    public void playSound(String id){
        Runnable sound = new Runnable(){
            @Override
            public void run(){
                soundMap.get(id).play();
            }
        };
        soundPool.execute(sound);
    }

    public void shutdown(){
        soundPool.shutdown();
    }
}
