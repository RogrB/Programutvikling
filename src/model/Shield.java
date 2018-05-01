package model;

import assets.java.Sprite;
import java.util.Timer;
import java.util.TimerTask;

public class Shield extends Existance {
    
    private int charges;
    private boolean immunity = false;
    private int immunitytime = 250;
    private boolean broken = false;
    private int animCounter;
    
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
    
    public void setCharges(int charges) {
        this.charges = charges;
    }
    
    public int getCharges() {
        return this.charges;
    }
    
    public boolean isImmune() {
        return this.immunity;
    }
    
    public void setImmunity(boolean immunity) {
        this.immunity = immunity;
    }
    
    public void immunityTimer() {
        Timer shotTimer = new Timer();
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
    
    public void takeDamage() {
        setImmunity(true);
        immunityTimer();    
        animate();
        setCharges(getCharges()-1);
    }

    public boolean isBroken() {
        return this.broken;
    }
    
    public void animate() {
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
