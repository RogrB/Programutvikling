package model.weapons;

import assets.java.Sprite;
import javafx.scene.image.Image;
import model.Existance;

public class Bullet extends Existance {
    public final Weapon WEAPON;
    private Image img;
    private boolean isHit = false;

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

    public void hasHit(Boolean hit) { this.isHit = hit; }

    @Override
    public void setX(int x){
        if(!isHit)
            super.setX(x);
    }

    @Override
    public void setY(int y){
        if(!isHit)
            super.setY(y);
    }
    
}
