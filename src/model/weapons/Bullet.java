package model.weapons;

import assets.java.Sprite;
import controller.GameController;
import javafx.scene.image.Image;
import model.Existance;
import model.GameModel;

import java.util.Timer;
import java.util.TimerTask;

public class Bullet extends Existance {

    public final Weapon WEAPON;
    private Sprite sprite;
    private boolean isHit = false;

    // Animation variables
    private final int ANIM_SPEED = 20000;
    private int animCounter = 0;

    public Bullet(int x, int y, Weapon weapon) {
        this.x = x;
        this.y = y;
        WEAPON = weapon;
        sprite = WEAPON.SPRITE;
        setNewDimensions(sprite);
    }

    public Image getSpriteImage() {
        return sprite.getImg();
    }

    public void clearImage() {
        sprite = Sprite.CLEAR;
    }

    public void hasHit() {
        isHit = true;
        bulletDie();
    }

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

    private void alterSprite(Sprite s){
        sprite = s;
        setNewDimensions(s);
    }

    private void setNewDimensions(Sprite s){
        width = (int)s.getWidth();
        height = (int)s.getHeight();
    }

    private void bulletDie(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(animCounter < WEAPON.BULLET_HIT.length) {
                    alterSprite(WEAPON.BULLET_HIT[animCounter]);
                } else if (animCounter >= (WEAPON.BULLET_HIT.length * 2)) {
                    timer.cancel();
                    timer.purge();
                    bulletPurge();
                } else if (animCounter >= (WEAPON.BULLET_HIT.length * 2)-1) {
                    alterSprite(Sprite.CLEAR);
                } else {
                    alterSprite(WEAPON.BULLET_HIT[WEAPON.BULLET_HIT.length - (animCounter - WEAPON.BULLET_HIT.length + 2)]);
                }
                System.out.print(animCounter);
                animCounter++;
            }
        }, 0, ANIM_SPEED);
    }

    private void bulletPurge() {
        GameModel.getInstance().getEnemyBullets().remove(this);
    }
    
}
