
package model.weapons;

/**
 * <h1>A Class that handles the creation of the Doubles upgrade</h1>
 * The {@code Doubles} class extends the {@code Basic} class to create a
 * weapon upgrade. 
 * @author Roger Birkenes Solli
 */
public class Doubles extends Basic {
    
    /**
     * <b>Constructor: </b>sets the X and Y values
     * for where the bullet is fired from and the {@code Weapon}
     * @param x X Value
     * @param y Y Value
     * @param weapon sets the Weapon type in the {@code Weapon} class @see {@code Weapon}
     */      
    public Doubles(int x, int y, Weapon weapon) {
        super(
        x,
        y,
        weapon.PLAYER_DOUBLES);
    }        
    
}
