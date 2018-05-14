
package model.weapons;


import java.util.Iterator;

/**
 * <h1>A Class that handles the creation of the DoubleSwirl upgrade</h1>
 * The {@code DoubleSwirl} class extends the {@code Basic} class to create a
 * weapon upgrade. 
 * This weapon upgrade fires two bullets that spins around each other
 * in the Y axis
 * 
 * @author Roger Birkenes Solli
 */
public class DoubleSwirl extends Basic {

    /**
     * If the {@code this} is fired at the top or bottom. 
     * Decides the direction of the spin
     */    
    private boolean top;
    
    /**
     * Current travel direction - up or down
     */     
    private boolean goingUp;
    
    /**
     * Counter for the amount of times {@code this} travels in the
     * given direction. Based on ticks from the animationtimer in
     * {@code GameController}
     * After 6 ticks it changes direction, thus creating the "swirl" movement
     */       
    private int swirlCounter = 0;
    
    /**
     * <b>Constructor: </b>sets the X and Y values
     * for where the bullet is fired from and the {@code Weapon}
     * @param x X Value
     * @param y Y Value
     * @param weapon sets the Weapon type in the {@code Weapon} class
     * @param top sets if the bullet is fired at the top or bottom
     * @see Weapon
     */       
    public DoubleSwirl(int x, int y, Weapon weapon, boolean top) {
        super(
        x,
        y,
        weapon.PLAYER_DOUBLESWIRL);
        this.top = top;
        if (top) {
            this.goingUp = false;
        }
    }     
    
    /**
     * Overrides the {@code update} from the {@code Basic} bullets.
     * This method moves the bullets along the X axis, as well as handles
     * the Y movements to create the "swirl" effect.
     * After 6 ticks from the anmationtimer it changes direction
     */       
    @Override
    public void update(int x, int y, Iterator i) {
        this.setX(getX() + 20);
        if (top) {
            if (goingUp) {
                this.setY(this.getY()-5);
            }
            else {
                this.setY(this.getY()+5);
            }
        }
        else {
            if (goingUp) {
                this.setY(this.getY()+5);
            }
            else {
                this.setY(this.getY()-5);
            }
        }
        swirlCounter++;
        if (swirlCounter == 6) {
            swirlCounter = 0;
            goingUp = !(goingUp);
        }
    }
    
}
