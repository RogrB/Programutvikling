package assets.java;

import javafx.scene.media.AudioClip;

public enum Audio {
    PLAYER_SHOT("file:src/assets/audio/newLaser.mp3");

    AudioClip audio;

    Audio (String url){
        audio = new AudioClip(url);
    }

    public AudioClip getAudio() {
        return audio;
    }
}
