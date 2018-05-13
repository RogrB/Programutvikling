
package model.weapons;

/**
 * <h1>A Class that handles the creation of the DamageBullets upgrade</h1>
 * The {@code DamageBullets} class extends the {@code Basic} class to create a
 * weapon upgrade that does more damage than regular bullets. 
 * @author Roger Birkenes Solli
 */
public class DamageBullets extends Basic {
    
    /**
     * <b>Constructor: </b>sets the X and Y values
     * for where the bullet is fired from and the {@code Weapon}
     * @param x X Value
     * @param y Y Value
     * @param weapon sets the Weapon type in the {@code Weapon} class @see {@code Weapon}
     */      
    public DamageBullets(int x, int y, Weapon weapon) {
        super(
        x,
        y,
        weapon.PLAYER_UPGRADED2);
    }    
        
    
}
