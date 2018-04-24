package model.enemy;

import assets.java.Sprite;
import model.Entity;
import model.GameModel;
import model.weapons.Bullet;

import java.util.Iterator;
import java.util.Random;

public class Enemy extends Entity {

    // MVC-access
    GameModel gm = GameModel.getInstance();

    private final EnemyType TYPE;
    private EnemyMovementPattern pattern;

    private int chanceToShoot;
    private int timerToShoot;
    private int animationCounter;
    private boolean scoreCount = false;
    private int enemyID;

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
            addScore();
        }

        if(isOffScreen() || getReadyToPurge()){
            purge(i);
        } else
            shoot();
    }
    
    public void addScore() {
        if (!this.scoreCount) {
            gm.player.setScore(gm.player.getScore() + 100);
            this.scoreCount = true;
        }
    }
    
    public EnemyType getType() {
        return this.TYPE;
    }
    
    public void setID(int id) {
        this.enemyID = id;
    }
    
    public int getID() {
        return this.enemyID;
    }
    
}
