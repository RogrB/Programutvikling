package model.weapons;

import assets.java.Sprite;
import javafx.scene.image.Image;
import model.Existance;
import model.GameModel;
import view.GameView;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Bullet extends Existance {

    protected final Weapon WEAPON;
    private boolean isHit = false;
    private boolean readyToPurge;
    
    GameModel gm = GameModel.getInstance();

    // Animation variables
    private final int ANIM_SPEED = 20;
    private int animCounter = 0;
    private int animIndex = 0;
    private Timer timer = null;
    private final int range;
    private int travelled;

    public Bullet(int x, int y, Weapon weapon) {
        this.setX(x);
        this.setY(y);
        range = 1100;
        WEAPON = weapon;
        newSprite(weapon.SPRITE);
        setNewDimensions();
        readyToPurge = false;
    }

    public void clearImage() {
        newSprite(Sprite.CLEAR);
    }

    public void hasHit() {
        isHit = true;
        setOldX(getX());
        setOldY(getY());
        bulletDie();
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
                        //purgeThis();
                        setReadyToPurge();
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

    public void purgeThis(Iterator iterator){
        if (!gm.getEnemyBullets().isEmpty()) {
            /*if(gm.getEnemyBullets().contains(this))
                gm.getEnemyBullets().remove(this);*/
            iterator.remove();
        }
        if (!gm.player.getBullets().isEmpty()) {
            if(gm.player.getBullets().contains(this))
                gm.player.getBullets().remove(this);
        }
    }
    
    public void move() {
        this.setX(getX() + 20);
        this.setY(getY());
        travelled += 20;
    }

    public boolean outOfRange(){
        System.out.println(travelled);
        return travelled > range;
    }

    public boolean isReadyToPurge(){
        return readyToPurge;
    }

    public void setReadyToPurge() {
        this.readyToPurge = true;
    }
}
