package model.enemy;

import assets.java.Sprite;
import model.Entity;
import model.GameModel;
import model.weapons.Bullet;

import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Enemy extends Entity {

    // MVC-access
    GameModel gm = GameModel.getInstance();

    private final EnemyType TYPE;
    private EnemyMovementPattern pattern;

    private int chanceToShoot;
    private int timerToShoot;
    private int animationCounter;

    public Enemy(EnemyType enemyType, EnemyMovementPattern pattern, int x, int y){
        super(
                enemyType.SPRITE,
                x,
                y,
                enemyType.MAX_HEALTH
        );

        this.TYPE = enemyType;
        this.pattern = pattern;
        pattern.setStartPosition(x, y);

        canShoot = TYPE.canShoot();
        weapon = TYPE.WEAPON;
        health = TYPE.MAX_HEALTH;
        chanceToShoot = TYPE.SHOOTING_CHANCE;
        timerToShoot = chanceToShoot;

        if(TYPE.SHOOTING_CHANCE == 0)
            canShoot = false;

        if (this.TYPE == TYPE.ASTEROID) {
            animateAsteroid();
        }
    }

    // GET-SET
    public int getHealth() {
        return health;
    }
    public EnemyMovementPattern getPattern(){
        return pattern;
    }

    public void setChanceToShoot(int chanceToShoot) {
        this.chanceToShoot = chanceToShoot;
    }

    @Override
    public void shoot() {
        Random random = new Random();
        if(canShoot()) {
            if(random.nextInt(chanceToShoot) < timerToShoot) {
                timerToShoot--;
            }
            else {
                timerToShoot = chanceToShoot;
                gm.getEnemyBullets().add(new Bullet(getX() + 10, getY() + (this.height / 2) - 8, weapon));
                bulletCount++;
            }
        }
    }

    public void update(Iterator i){
        if(isAlive()) {
            pattern.updatePosition();
            setX(pattern.getX());
            setY(pattern.getY());
        } else {
            setOldX(getX());
            setOldY(getY());
            animateDeath();
        }

        if(isOffScreen() || getReadyToPurge()){
            purge(i);
        } else
            shoot();
    }
    
    private void animateAsteroid() {
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
    
}
