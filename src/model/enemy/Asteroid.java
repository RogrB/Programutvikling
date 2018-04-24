package model.enemy;

import controller.GameController;
import assets.java.Sprite;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Asteroid extends Enemy {
    
    private int animationCounter;
    private boolean spawned = false;

     public Asteroid(EnemyMovementPattern pattern, int x, int y){
        super(EnemyType.ASTEROID, pattern, x, y);
        animate();
        }
     
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
    
    @Override
    public void update(Iterator i){
        if(isAlive()) {
            getPattern().updatePosition();
            setX(getPattern().getX());
            setY(getPattern().getY());
        } else {
            setOldX(getX());
            setOldY(getY());
            animateDeath();
        }

        if(isOffScreen() || getReadyToPurge()){
            purge(i);
        }
    }    
    
    public boolean getSpawned() {
        return this.spawned;
    }
    
    public void setSpawned(boolean spawn) {
        this.spawned = spawn;
    }
    
}
