package model.enemy;

import assets.java.Sprite;
import java.util.Timer;
import java.util.TimerTask;

public class SmallAsteroid extends Enemy {
    
    private int animationCounter;
    
    public SmallAsteroid(EnemyMovementPattern pattern, int x, int y) {
        super(EnemyType.SMALL_ASTEROID, pattern, x, y);
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
