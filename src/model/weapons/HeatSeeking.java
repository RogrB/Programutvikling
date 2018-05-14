package model.weapons;

import java.util.ArrayList;
import java.util.Iterator;

import model.GameState;
import model.enemy.Enemy;

import static controller.GameController.gs;

/**
 * <h1>A Class that handles the creation of the HeatSeeking upgrade</h1>
 * The {@code HeatSeeking} class extends the {@code Basic} class to create a
 * weapon upgrade that targets an enemy and tracks its movements.
 * 
 * @author Roger Birkenes Solli
 */
public class HeatSeeking extends Basic {
    
    /**
     * {@code Enemy} objects located in the game state.
     * @see Enemy
     */    
    private ArrayList<Enemy> enemies = GameState.enemies;
    
    /**
     * If {@code this} has locked on to an enemy
     */    
    private boolean locked = false;
    
    /**
     * {@code Enemy} object that has been locked on to as a target.
     * @see Enemy
     */        
    private Enemy target;

    /**
     * <b>Constructor: </b>sets the X and Y values
     * for where the bullet is fired from and the {@code Weapon}
     * @param x X Value
     * @param y Y Value
     * @param weapon sets the Weapon type in the {@code Weapon} class
     * @see {@code Weapon}
     */      
    public HeatSeeking(int x, int y, Weapon weapon) {
        super(
        x,
        y,
        weapon.PLAYER_HEATSEEKING);
    }  

    /**
     * Overrides the {@code update} from the {@code Basic} bullets
     * to handle the update method.
     * Attempts to set a lock on an enemy if there is one available and no
     * lock has already been set. Then calls the {@code moveMissile} method
     * @param x X position 
     * @param y Y position
     * @param i Iterator needed to override method
     */      
    @Override
    public void update(int x, int y, Iterator i) {

        setOldX(getX());
        setOldY(getY());

        if (enemies.isEmpty()) {
            setX(getX() + 15);
        }
        else {
            if (!locked) {
                setLock();
            }
            moveMissile();
        }
    }
    
    /**
     * Finds the closest enemy in the X axis and locks on the target.
     */      
    private void setLock() {
        int closestX = 2500;
        int targetIndex = 0;
        if(!enemies.isEmpty()) {
            for (Enemy enemy : enemies) {
                if (enemy.getX() < closestX && enemy.getX() > gs.player.getX()) {
                    closestX = enemy.getX();
                    targetIndex++;
                }
            }
        }
        target = enemies.get(targetIndex-1);
        locked = true;
    }
    
    /**
     * Moves the bullet towards the locket target in both X and Y axis. 
     * If there is no target (if the target has died while
     * the bullet is still in transit) the bullet moves along the
     * X axis like a normal bullet
     */      
    private void moveMissile() {
        if (target != null && target.isAlive()) {
            if (Math.abs(target.getX() - getX()) < 7) {
                setX((getX() < target.getX()) ? getX()+1 : getX()-1);
            }
            else {
                setX((getX() < target.getX()) ? getX()+7 : getX()-7);
            }
            if (Math.abs(target.getY() - getY()) < 7) {
                setY((getY() < target.getY()) ? getY()+1 : getY()-1);
            }
            else {
                setY((getY() < target.getY()) ? getY()+7 : getY()-7);
            }
        }
        else {
            setX(getX()+7);
        }
    }
}
