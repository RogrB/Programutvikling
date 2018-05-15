package model.enemy;

import assets.java.Sprite;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <h1>The Asteroid class</h1>
 * The {@code Asteroid} class extends the {@code Enemy} class to create a
 * type of enemy that behaves differently than regular enemies.
 * 
 * @author Roger Birkenes Solli
 */
public class Asteroid extends Enemy {
    
    /**
     * Counter for the animation method
     * that creates a spin effect
     */      
    private int animationCounter;
    
    /**
     * When an Asteroid object is shot it breaks off into
     * to smaller asteroids. This boolean checks if that has
     * happened.
     */      
    private boolean spawned = false;

    /**
     * <b>Constructor: </b>sets values required by the superclass {@code Enemy}
     * which extends {@code Entity}
     * @param pattern sets the movement pattern of {@code this} based on patterns defined in the {@code EnemyMovementPattern} class
     * @param x sets the X value
     * @param y sets the Y value
     */     
     public Asteroid(EnemyMovementPattern pattern, int x, int y){
        super(EnemyType.ASTEROID, pattern, x, y);
        animate();
        }
     
    /**
     * Method for animating the Asteroid.
     * Updates the asteroids sprites to
     * create a spinning effect
     */      
    private void animate() {
        Timer spinTimer = new Timer();
        spinTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                if(isAlive()) {
                    switch (animationCounter) {
                        case 5:
                            newSprite(Sprite.ASTEROID2);
                            break;
                        case 10:
                            newSprite(Sprite.ASTEROID3);
                            break;
                        case 15:
                            newSprite(Sprite.ASTEROID4);
                            break;
                        case 20:
                            newSprite(Sprite.ASTEROID5);
                            break;
                        case 25:
                            newSprite(Sprite.ASTEROID6);
                            break;
                        case 30:
                            newSprite(Sprite.ASTEROID7);
                            break;
                        case 35:
                            newSprite(Sprite.ASTEROID8);
                            animationCounter = 0;
                            break;
                    }
                    animationCounter++;
                    if(!isAlive()) {
                        this.cancel();
                    }
                }
            }
        }, 0, 20);
    }     
    
    /**
     * Overrides the {@code update} method of the {@code Enemy} superclass
     * Since asteroids cannot shoot or be a Boss type they behave differently
     * than regular enemies.
     */     
    @Override
    public void update(Iterator i){
        if(isAlive()) {
            getPattern().updatePosition();
            setX(getPattern().getX());
            setY(getPattern().getY());
        } else {
            setOldX(getX());
            setOldY(getY());
            //animateDeath();
        }

        if(isOffScreen() || getReadyToPurge()){
            purge(i);
        }
    }    
    
    /**
     * @return Gets the spawned boolean
     * which decides if {@code this} has spawned 
     * two small asteroids upon death
     */     
    public boolean getSpawned() {
        return this.spawned;
    }
    
    /**
     * @param spawn sets the spawned boolean
     */     
    public void setSpawned(boolean spawn) {
        this.spawned = spawn;
    }
    
}
