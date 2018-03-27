package model.weapons;

import assets.java.Sprite;
import javafx.scene.image.Image;
import model.Existance;

public class Bullet extends Existance {
    private final Weapon WEAPON;
    private Image img;

    public Bullet(int x, int y, Weapon weapon) {
        this.x = x;
        this.y = y;
        WEAPON = weapon;
        img = WEAPON.SPRITE.getImg();
        height = (int)weapon.SPRITE.getHeight();
        width = (int)weapon.SPRITE.getWidth();
    }

    public Image getSpriteImage() {
        return img;
    }

    public void clearImage() {
        img = Sprite.CLEAR.getImg();
    }
    
}
