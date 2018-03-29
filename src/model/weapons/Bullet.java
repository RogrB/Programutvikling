package model.weapons;

import assets.java.Sprite;
import javafx.scene.image.Image;
import model.Existance;

import java.util.Timer;
import java.util.TimerTask;

public class Bullet extends Existance {

    private final Weapon WEAPON;
    private Sprite sprite;
    private boolean isHit = false;

    // Animation variables
    private final int ANIM_SPEED = 1000;
    private int animCounter = 0;
    int animIndex = 0;
    private boolean pleasePurge = false;

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

    public int getDmg(){
        if(isHit)
            return WEAPON.DMG;
        return 0;
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
                    animIndex = animCounter;
                    alterSprite(WEAPON.BULLET_HIT[animIndex]);
                } else if (animCounter >= (WEAPON.BULLET_HIT.length * 2)) {
                    timer.cancel();
                    timer.purge();
                    setPleasePurge(true);
                } else if (animCounter >= (WEAPON.BULLET_HIT.length * 2)-1) {
                    alterSprite(Sprite.CLEAR);
                } else {
                    animIndex = WEAPON.BULLET_HIT.length - (animCounter - WEAPON.BULLET_HIT.length + 2);
                    alterSprite(WEAPON.BULLET_HIT[animIndex]);
                }
                System.out.print(animIndex);
                animCounter++;
            }
        }, 0, ANIM_SPEED);
    }

    private void setPleasePurge(boolean purge){
        pleasePurge = purge;
    }

    public boolean pleasePurge(){
        return pleasePurge;
    }
    
}
