package model.weapons;

import assets.java.Sprite;
import javafx.scene.image.Image;
import model.Existance;

public class Bullet extends Existance {
    private Sprite sprite;

    public Bullet(int x, int y, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
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
