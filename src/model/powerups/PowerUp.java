
package model.powerups;

import assets.java.Sprite;
import model.Existence;

import java.util.Iterator;

/**
 * <h1>PowerUp Class</h1>
 * This class handles the behaviour of PowerUps in the game space.
 * The {@code PowerUp} class extends the {@code Existence} class
 * to receive values needed to exist in the game space. 
 */
public class PowerUp extends Existence {
    
    /**
     * Boolean that decides if {@code this} has been picked up
     * to prevent the PowerUp from triggering multiple times
     */    
    private boolean pickedUp = false;
    
    /**
     * Name of the PowerUp - decides which action 
     * is to be taken when the player picks it up.
     */      
    private String name;
    
    /**
     * <b>Constructor</b> Sets X and Y positions, sprite and dimensions.
     * Name is derived from the type of sprite selected.
     * @param sprite sets the sprite of the PowerUp
     * @param x sets the X position
     * @param y sets the Y position
     */    
    public PowerUp(Sprite sprite, int x, int y) {
        super(x, y);
        newSprite(sprite);
        setNewDimensions();
        name = sprite.name();
    }

  /**
     * Method that updates the object every tick
     * from the animation timer in the {@code GameController} class
     * @param x sets the X position
     * @param y sets the Y position
     * @param i iterator
     */      
    public void update(int x, int y, Iterator i){
        setX(getX() + x);
        setY(getY() + y);
        if(isOffScreen() || getReadyToPurge())
            purge(i);
    }

  /**
     * Method to set {@code this} as activated by the player.
     * To prevent PowerUps from triggering multiple times
     */      
    public void setPickedUp(){
        pickedUp = true;
        newSprite(Sprite.CLEAR);
        isReadyToPurge();
    }

  /**
     * @return if the PowerUp has been activated by the player.
     */      
    public boolean isPickedUp(){
        return pickedUp;
    }

  /**
     * @return gets the name of the PowerUp
     */      
    public String getName(){
        return name;
    }
    
}