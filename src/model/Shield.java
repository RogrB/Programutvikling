package model;

import assets.java.Sprite;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <h1>Shield Class</h1>
 * This class handles the behaviour of Shields in the game.
 * The {@code Shield} class extends the {@code Existence} class
 * to receive values needed to exist in the game space. 
 * 
 * @author Roger Birkenes Solli
 */
public class Shield extends Existence {
    
    /**
     * The number of charges - The amount of times a shield can be hit before it breaks.
     * A shield has two charges when activated.
     */     
    private int charges;
    
    /**
     * Boolean that decides if the shield is immune to damage.
     */     
    private boolean immunity = false;
    
    /**
     * Boolean that decides if the shield is broken
     */     
    private boolean broken = false;
    
    /**
     * Counter for handling the damage animation
     */     
    private int animCounter;
    
    /**
     * <b>Constructor</b>
     * If the shield is active it sets X and Y positions, sprite, dimensions and number of charges.
     * If the shield is not active it sets the charges to 0 and clears the sprite.
     * @param active decides if the shield is active or not.
     * @param x sets the X position
     * @param y sets the Y position
     */       
    public Shield(int x, int y, boolean active) {
        super(x, y);
        if (active) {
            charges = 2;
            newSprite(Sprite.SHIELD1);
            setNewDimensions();
        }
        else {
            charges = 0;
            newSprite(Sprite.CLEAR);
            setNewDimensions();
        }
    }
    
    /**
     * @param charges sets the number of charges the shield has
     */     
    private void setCharges(int charges) {
        this.charges = charges;
    }
    
    /**
     * @return gets the amount of charges the shield has
     */     
    public int getCharges() {
        return this.charges;
    }
    
    /**
     * @return if the shield is immune to damage
     */     
    public boolean isImmune() {
        return this.immunity;
    }
    
    /**
     * @param immunity sets the immunity state of the shield
     */     
    private void setImmunity(boolean immunity) {
        this.immunity = immunity;
    }
    
    /**
     * Method that starts a short timer during which the shield
     * is immune to damage. This is to make sure the shield only gets
     * damaged by an enemy or projectile once and to prevent from burning through
     * all the charges instantly.
     */     
    private void immunityTimer() {
        Timer shotTimer = new Timer();
        int immunitytime = 250;
        shotTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                setImmunity(false);
                if (getCharges() == 0) {
                    broken = true;
                }
                this.cancel();
                
            }
        }, immunitytime);
    }    
    
    /**
     * Method that handles damage done 
     * to the shield.
     * Triggers the {@code immunityTimer()} method
     * as well as the damage animation.
     */     
    public void takeDamage() {
        setImmunity(true);
        immunityTimer();    
        animate();
        setCharges(getCharges()-1);
    }

    /**
     * @return returns whether the shield is broken or not
     */     
    public boolean isBroken() {
        return this.broken;
    }
    
    /**
     * Method that animates an effect that occurs
     * when the shield takes damage. To give visual
     * feedback to the player that damage has been registered.
     * The method changes the sprite of the shield based on the 
     * {@code animCounter}
     */     
    private void animate() {
        Timer shieldBlinkTimer = new Timer();
        shieldBlinkTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                    switch (animCounter) {
                        case 2:
                            newSprite(Sprite.SHIELD2);
                            break;
                        case 4:
                            newSprite(Sprite.SHIELD3);
                            break;
                        case 6:
                            newSprite(Sprite.SHIELD2);
                            break;
                        case 8:
                            newSprite(Sprite.SHIELD1);
                            animCounter = 0;
                            this.cancel();
                            break;
                    }
                    animCounter++;
                }
            
        }, 0, 20);           
    }
    
}
