package model.weapons;

import assets.java.Sprite;
import javafx.scene.image.Image;
import model.Existance;
import model.GameModel;

import java.util.Timer;
import java.util.TimerTask;

public class Bullet extends Existance {

    protected final Weapon WEAPON;
    protected Sprite sprite;
    private boolean isHit = false;

    protected int oldX, oldY;

    // Animation variables
    private final int ANIM_SPEED = 20;
    private int animCounter = 0;
    private int animIndex = 0;
    private boolean pleasePurge = false;
    private Timer timer = null;

    public Bullet(int x, int y, Weapon weapon) {
        this.setX(x);
        this.setY(y);
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
        oldX = getX();
        oldY = getY();
        bulletDie();
    }

    @Override
    public void setX(int x){
        if(!isHit) {
            oldX = getX();
            super.setX(x);
        }
    }

    @Override
    public void setY(int y){
        if(!isHit) {
            oldY = getY();
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

    private void alterSprite(Sprite s){
        sprite = s;
        setNewDimensions(s);
    }

    private void setNewDimensions(Sprite s){
        width = (int)s.getWidth();
        height = (int)s.getHeight();
    }

    private void bulletDie(){
        if(timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (animCounter < WEAPON.BULLET_HIT.length) {
                        animIndex = animCounter;
                        alterSprite(WEAPON.BULLET_HIT[animIndex]);
                    } else if (animCounter >= (WEAPON.BULLET_HIT.length * 2)) {

                        timer.cancel();
                        timer.purge();
                        purgeThis();
                    } else if (animCounter >= (WEAPON.BULLET_HIT.length * 2) - 1) {
                        alterSprite(Sprite.CLEAR);
                    } else {
                        animIndex = WEAPON.BULLET_HIT.length - (animCounter - WEAPON.BULLET_HIT.length + 2);
                        alterSprite(WEAPON.BULLET_HIT[animIndex]);
                    }
                    animCounter++;
                }
            }, 0, ANIM_SPEED);
        }
    }

    public void purgeThis(){
        if(GameModel.getInstance().getEnemyBullets().contains(this))
            GameModel.getInstance().getEnemyBullets().remove(this);

        if(GameModel.getInstance().player.getBullets().contains(this))
            GameModel.getInstance().player.getBullets().remove(this);
    }
    
    public void move() {
        this.setX(getX() + 20);
        this.setY(getY());
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }
}
