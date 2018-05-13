package model.enemy;

import assets.java.Sprite;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <h1>The SmallAsteroid class</h1>
 * The {@code SmallAsteroid} class extends the {@code Enemy} class to create a
 * type of enemy that behaves differently than regular enemies.
 * This enemy is created when a regular Asteroid is shot and breaks off into
 * two smaller asteroids.
 */
public class SmallAsteroid extends Enemy {
    
    /**
     * Counter for the animation method
     * that creates a spin effect
     */       
    private int animationCounter;
    
    /**
     * <b>Constructor: </b>sets values required by the superclass {@code Enemy}
     * which extends {@code Entity}
     * @param pattern sets the movement pattern of {@code this} based on patterns defined in the {@code EnemyMovementPattern} class
     * @param x sets the X value
     * @param y sets the Y value
     */       
    public SmallAsteroid(EnemyMovementPattern pattern, int x, int y) {
        super(EnemyType.SMALL_ASTEROID, pattern, x, y);
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
                            newSprite(Sprite.SMALL_ASTEROID2);
                            break;
                        case 10:
                            newSprite(Sprite.SMALL_ASTEROID3);
                            break;
                        case 15:
                            newSprite(Sprite.SMALL_ASTEROID4);
                            break;
                        case 20:
                            newSprite(Sprite.SMALL_ASTEROID5);
                            break;
                        case 25:
                            newSprite(Sprite.SMALL_ASTEROID6);
                            break;
                        case 30:
                            newSprite(Sprite.SMALL_ASTEROID7);
                            break;
                        case 35:
                            newSprite(Sprite.SMALL_ASTEROID8);
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
}
