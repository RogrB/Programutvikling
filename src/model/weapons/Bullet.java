package model.weapons;

import assets.java.Sprite;
import javafx.scene.image.Image;
import model.Existance;

public class Bullet extends Existance {
    private Sprite sprite;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        sprite = Sprite.WEAPON_PLAYER_BASIC;
        height = (int)sprite.getHeight();
        width = (int)sprite.getWidth();
    }

    public Image getSpriteImage() {
        return sprite.getImg();
    }

    public void clearImage() {
        this.sprite = Sprite.CLEAR;
    }
    
}
