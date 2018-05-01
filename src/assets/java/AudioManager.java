package assets.java;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import java.util.Random;

import static view.OptionsView.musicValue;
import static view.OptionsView.soundValue;

public class AudioManager {

    private static AudioManager inst = new AudioManager();
    public static AudioManager getInstance() { return inst; }

    private final String AUDIO_ASSETS = "src/assets/audio/";

    private final File MUSIC_MENU =     new File(AUDIO_ASSETS + "music/music_menu_2.wav");
    private final File MUSIC_BATTLE =   new File(AUDIO_ASSETS + "music/music_battle_2.wav");
    private final File MUSIC_WIN =      new File(AUDIO_ASSETS + "music/music_retro_1.wav");

    private final File SHOT_PLAYER_01 = new File(AUDIO_ASSETS + "shots/player_1.wav");

    private final File SHOT_BOSS =      new File(AUDIO_ASSETS + "boss_sfx/boss_shot_1.wav");
    private final File SHOT_ENEMY[] = {
                                        new File(AUDIO_ASSETS + "shots/enemy_1.wav"),
                                        new File(AUDIO_ASSETS + "shots/enemy_2.wav"),
                                        new File(AUDIO_ASSETS + "shots/enemy_3.wav"),
                                        new File(AUDIO_ASSETS + "shots/enemy_4.wav"),
                                        new File(AUDIO_ASSETS + "shots/enemy_5.wav"),
                                        new File(AUDIO_ASSETS + "shots/enemy_6.wav")
    };

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
    private final File BOSS_TALK[] = {
                                        new File(AUDIO_ASSETS + "boss_sfx/boss_talk_1.wav"),
                                        new File(AUDIO_ASSETS + "boss_sfx/boss_talk_2.wav"),
                                        new File(AUDIO_ASSETS + "boss_sfx/boss_talk_3.wav"),
                                        new File(AUDIO_ASSETS + "boss_sfx/boss_talk_4.wav"),
                                        new File(AUDIO_ASSETS + "boss_sfx/boss_talk_5.wav"),
                                        new File(AUDIO_ASSETS + "boss_sfx/boss_talk_6.wav")
    };

    private final File NAV =            new File(AUDIO_ASSETS + "sfx/nav_1.wav");
    private final File NAV_SELECT =     new File(AUDIO_ASSETS + "sfx/nav_select_1.wav");

    MediaPlayer musicPlayer;
    Random rand;

    private AudioManager(){
        rand = new Random();
    }

    public void setMusic(String setting){
        Media track;

        switch (setting){
            case "MENU":
                try {
                    if (musicPlayer.getMedia().getSource().equals(MUSIC_MENU.toURI().toString())) {
                        break;
                    } else if (musicPlayer.getMedia().getSource().equals(MUSIC_BATTLE.toURI().toString())) {
                        musicPlayer.stop();
                    }
                } catch (Exception e) {}

                track = new Media(MUSIC_MENU.toURI().toString());
                musicPlayer = new MediaPlayer(track);
                musicPlayer.setVolume((float) musicValue / 100 * .3);
                musicPlayer.play();
                break;

            case "BATTLE":
                if(musicPlayer.getMedia().getSource().equals(MUSIC_BATTLE.toURI().toString())) {
                    break;
                } else if (musicPlayer.getMedia().getSource().equals(MUSIC_MENU.toURI().toString())) {
                    musicPlayer.stop();
                }

                track = new Media(MUSIC_BATTLE.toURI().toString());
                musicPlayer = new MediaPlayer(track);
                musicPlayer.setVolume((float) musicValue / 100);
                musicPlayer.play();
                musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                break;
        }
    }


    public void shotPlayer(){
        play(SHOT_PLAYER_01, 1f);
    }
    public void shotBoss(){
        play(SHOT_BOSS, .6f);
    }
    public void shotEnemy(){
        play(SHOT_ENEMY[rand.nextInt(SHOT_ENEMY.length)], .5f);
    }

    public void impactBullets(){
        play(BULLET_IMPACT, 1f);
    }
    public void impactShield(){
        play(IMPACT_04, 1f);
    }
    public void impactPlayer() {
        play(SFX_1, 1f);
        play(SFX_2, .6f);
    }

    public void entityDead(){
        play(IMPACT_02, 1f);
    }
    public void bossWobble(){
        play(BOSS_WOBBLE_1, .7f);
    }
    public void bossTalk(){
        if(rand.nextInt(180) == 0)
            play(BOSS_TALK[rand.nextInt(BOSS_TALK.length)], 1f);
    }

    public void bossDies(){
        play(IMPACT_01, 1f);
        play(IMPACT_03, 1f);
        play(MUSIC_WIN, 1f);
        musicPlayer.stop();
    }

    public void nav(){
        play(NAV, 1f);
    }
    public void navSelect(){
        play(NAV_SELECT, 1f);
    }

    public void upgradeWeapon() {
        play(UP_WEAPON, 1f);
    }
    public void upgradeHealth() {
        play(UP_HEALTH, 1f);
    }
    public void upgradeShield() {
        play(UP_SHIELD, .7f);
    }

    private void play(File file, Float volume){
        Media sound = new Media(file.toURI().toString());
        MediaPlayer sfxPlayer = new MediaPlayer(sound);
        sfxPlayer.setVolume(volume * ((float) soundValue / 100));
        sfxPlayer.play();
    }
}
