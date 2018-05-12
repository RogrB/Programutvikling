
package model.weapons;


import java.util.Iterator;

/**
 * <h1>A Class that handles the creation of the TripleBurst upgrade</h1>
 * The {@code TripleBurst} class extends the {@code Basic} class to create a
 * weapon upgrade that fires thee bullets that spread out as they move across the field.
 * @author Roger Birkenes Solli
 */
public class TripleBurst extends Basic {
    
    /**
     * Determines which position the bullet
     * was fired from. 1 out of 3.
     * Decides which direction it travels
     */        
    private int level;
    
    /**
     * <b>Constructor: </b>sets the X and Y values
     * for where the bullet is fired from and the {@code Weapon}
     * @param x X Value
     * @param y Y Value
     * @param weapon sets the Weapon type in the {@code Weapon} class @see {@code Weapon}
     * @param level sets the position the bullet was fired from
     */        
    public TripleBurst(int x, int y, Weapon weapon, int level) {
        super(
        x,
        y,
        weapon.PLAYER_TRIPLEBURST);
        this.level = level;
    }     
    
    /**
     * Overrides the {@code update} from the {@code Basic} bullets
     * to handle the update method.
     * Moves the bullet along the X axis and changes its Y value
     * based on where it was fired from to spread the bullets out
     * as they travel.
     * @param x X position 
     * @param y Y position
     * @param i Iterator needed to override method
     */     
    @Override
    public void update(int x, int y, Iterator i) {
        this.setX(getX() + 20);
        switch(level) {
            case 1:
                this.setY(this.getY()+5);
                break;
            case 2:
                this.setY(this.getY());
                break;
            case 3:
                this.setY(this.getY()-5);
                break;
        }
    }
    
}
