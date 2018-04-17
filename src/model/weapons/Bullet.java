package model.weapons;

import assets.java.Sprite;
import model.Existance;
import model.GameModel;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Bullet extends Existance {

    protected final Weapon WEAPON;
    private boolean isHit = false;

    // Animation variables
    private final int ANIM_SPEED = 20;
    private int animCounter = 0;
    private int animIndex = 0;
    private Timer timer = null;

    public Bullet(int x, int y, Weapon weapon) {
        super(x,y);
        WEAPON = weapon;
        newSprite(weapon.SPRITE);
        setNewDimensions();
    }

    public void hasHit() {
        isHit = true;
        setOldX(getX());
        setOldY(getY());
        bulletDie();
    }

    public void update(int x, int y, Iterator i){
        setX(getX() + x);
        setY(getY() + y);
        if(getReadyToPurge() || isOffScreen())
            purge(i);
    }

    @Override
    public void setX(int x){
        if(!isHit) {
            super.setX(x);
        }
    }

    @Override
    public void setY(int y){
        if(!isHit) {
            super.setY(y);
        }
    }
    
    public int getDmg(){
        if(!isHit)
            return WEAPON.DMG;
        return 0;
    }
    
    public int getFireRate() {
        return WEAPON.FIRERATE;
    }

    private void bulletDie(){
        if(timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (animCounter < WEAPON.BULLET_HIT.length) {
                        animIndex = animCounter;
                        newSprite(WEAPON.BULLET_HIT[animIndex]);
                    } else if (animCounter >= (WEAPON.BULLET_HIT.length * 2)) {

                        timer.cancel();
                        timer.purge();
                        isReadyToPurge();
                    } else if (animCounter >= (WEAPON.BULLET_HIT.length * 2) - 1) {
                        newSprite(Sprite.CLEAR);
                    } else {
                        animIndex = WEAPON.BULLET_HIT.length - (animCounter - WEAPON.BULLET_HIT.length + 2);
                        newSprite(WEAPON.BULLET_HIT[animIndex]);
                    }
                    animCounter++;
                }
            }, 0, ANIM_SPEED);
        }
    }
}
