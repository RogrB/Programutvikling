package model.weapons;

import assets.java.Sprite;
import model.Existence;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import static controller.GameController.gs;

/**
 * <h1>Base class for all Player projectiles</h1>
 * The {@code Basic} class extends the {@code Existence} class to create a
 * base for all bullets.
 */
public class Basic extends Existence {

    /**
     * {@code Weapon} object. To control sprite, damage, fire rate
     * and damage animation
     * @see Weapon
     */  
    private final Weapon WEAPON;
    
    /**
     * Boolean that determines if {@code this} 
     * has hit a target
     */      
    private boolean isHit = false;

    /**
     * Counter for bullet death animation
     */      
    private int animCounter = 0;
    
    /**
     * Index for bullet death animation
     */      
    private int animIndex = 0;
    
    /**
     * Timer for bullet death animation
     */      
    private transient Timer timer = null;

    /**
     * <b>Constructor: </b>sets the X and Y values
     * for where the bullet is fired from and the {@code Weapon}
     * then sets sprite based on weapon type selected in the {@code Weapon} parameter
     * and defines dimensions based on the sprite.
     * @param x X Value
     * @param y Y Value
     * @param weapon sets the Weapon type in the {@code Weapon} class
     * @see {@code Weapon}
     */      
    public Basic(int x, int y, Weapon weapon) {
        super(x,y);
        WEAPON = weapon;
        newSprite(weapon.SPRITE);
        setNewDimensions();
    }

    /**
     * Method that handles bullet hit event
     * for when the bullet hits a target
     */      
    public void hasHit() {
        isHit = true;
        setOldX(getX());
        setOldY(getY());
        bulletDie();
    }
    
    /**
     * @return whether the bullet has hit a target
     */      
    public boolean getHasHit() {
        return this.isHit;
    }

    /**
     * Handles update ticks triggered by the
     * animationtimer in the {@code GameController} class
     * @see {@code GameController}
     * Moves the bullet according to the input and purges the bullet if needed
     * @param x X value
     * @param y Y value
     * @param i Iterator to iterate through {@code this} objects
     */         
    public void update(int x, int y, Iterator i){
        setX(getX() + x);
        setY(getY() + y);
        if(getReadyToPurge() || isOffScreen())
            purge(i);
    }

    /**
     * @param x Sets the X value
     */         
    @Override
    public void setX(int x){
        if(!isHit) {
            super.setX(x);
        }
    }

    /**
     * @param y Sets the Y value
     */       
    @Override
    public void setY(int y){
        if(!isHit) {
            super.setY(y);
        }
    }
    
    /**
     * @return Gets the amount of damage {@code this} does upon hit
     */       
    public int getDmg(){
        if(!isHit)
            return WEAPON.DMG;
        return 0;
    }

    /**
     * Method for animating the "bullet death" effect
     * when a bullet hits a target
     */       
    private void bulletDie(){
        if(timer == null) {
            gs.player.setBulletsHit(gs.player.getBulletsHit() + 1);
            timer = new Timer();
            int ANIM_SPEED = 20;
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
