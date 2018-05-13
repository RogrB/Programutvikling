package model.enemy;

import assets.java.SoundManager;
import model.Entity;
import model.IdGen;
import model.weapons.Basic;

import java.util.Iterator;
import java.util.Random;

import static controller.GameController.gs;
import static model.GameModel.gameSettings;

/**
 * <h1>Base class for enemy object</h1>
 * The {@code Enemy} class extends the {@code Entity} class to create a
 * base for all enemies. Further details are handled in the {@code EnemyType} class
 */
public class Enemy extends Entity {

    /**
     * The type of enemy as defined in the {@code EnemyType} class
     * @see EnemyType
     */    
    private final EnemyType TYPE;
    
    /**
     * The movement pattern for {@code this}
     * as defined in the {@code EnemyMovementPattern} class
     * @see EnemyMovementPattern
     */    
    private EnemyMovementPattern pattern;

    /**
     * How likely {@code this} is to shoot
     */    
    private double chanceToShoot;
    
    /**
     * If {@code this} has awarded a score
     * upon death
     */    
    private boolean scoreCount = false;
    
    /**
     * The ID for {@code this}
     * Generated by the {@code IdGen} class
     * @see IdGen
     */    
    private final int enemyID;

    /**
     * <b>Constructor: </b>sets values required by the superclass {@code Entity}
     * and initializes the object. Also pulls an ID for the enemy from the {@code IdGen} class.
     * @param enemyType sets enemy details for the {@code EnemyType} class
     * @param pattern sets the movement pattern of {@code this} based on patterns defined in the {@code EnemyMovementPattern} class
     * @param x sets the X value
     * @param y sets the Y value
     */      
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

        if(TYPE.SHOOTING_CHANCE == 0)
            canShoot = false;

        enemyID = IdGen.getInstance().newId();

    }

    /**
     * @return health of {@code this}
     */  
    public int getHealth() {
        return health;
    }
    
    /**
     * @return {@code this} MovementPattern
     */      
    EnemyMovementPattern getPattern(){
        return pattern;
    }

    /**
     * @param chanceToShoot sets how likely {@code this} is to shoot
     */      
    public void setChanceToShoot(int chanceToShoot) {
        this.chanceToShoot = chanceToShoot;
    }

    /**
     * Method for handling shooting mechanism for enemies.
     * Gives enemies a random chance to shoot based on
     * {@code chanceToShoot}
     */      
    @Override
    public void shoot() {
        Random random = new Random();
        if(canShoot()) {
            double shotMod = (double) gameSettings.getDifficultyValue()/3;
            if(random.nextDouble() > 1 - (chanceToShoot * shotMod)) {
                playShotAudio();
                gs.enemyBullets.add(new Basic(getX() + 10, getY() + (this.height / 2) - 8, weapon));
            }
        }
    }

    /**
     * Method for playing audio when enemy shoots
     */      
    private void playShotAudio(){
        if(!TYPE.IS_BOSS){
            SoundManager.getInst().shotEnemy();
        } else {
            SoundManager.getInst().shotBoss();
        }
    }

    /**
     * Method for updating {@code this} for each tick
     * of the animation timer in the {@code GameController} class.
     * Handles movement updates, and checks if the enemy is still alive
     * or need to be purged. 
     * @param i takes in Iterator
     */      
    public void update(Iterator i){
        if(isAlive()) {
            pattern.updatePosition();
            setX(pattern.getX());
            setY(pattern.getY());

            if(TYPE.IS_BOSS){
                SoundManager.getInst().bossTalk();
            }

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
    
    /**
     * Adds score count when {@code this} is killed by player
     */      
    private void addScore() {
        if (!this.scoreCount) {
            gs.player.setScore(gs.player.getScore() + 100);
            gs.player.setEnemiesKilled(gs.player.getEnemiesKilled() + 1);
            this.scoreCount = true;
        }
    }
    
    /**
     * @return {@code this} type of enemy
     */      
    public EnemyType getType() {
        return this.TYPE;
    }

    /**
     * @return {@code this} ID - required for updating
     * enemies between multiplayer clients
     */      
    public int getID() {
        return this.enemyID;
    }
    
}
