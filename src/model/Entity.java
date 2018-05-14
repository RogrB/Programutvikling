package model;

import assets.java.SoundManager;
import assets.java.Sprite;
import model.enemy.Enemy;
import model.weapons.Weapon;

/**
 * <h1>Base class for all living things</h1>
 * The {@code Entity} class extends the {@code Existence} class to create
 * a base class for all living entities. All enemies and both players
 * extends this.
 *
 * @author Åsmund Røst Wien
 */
public abstract class Entity extends Existence {

    /**
     * The objects health points.
     */
    protected int health;

    /**
     * If the object is alive.
     */
    private boolean alive;

    /**
     * If the object can shoot. Can be both generally and temporarily.
     */
    protected boolean canShoot;

    /**
     * The {@code Weapon} type of the object. Can be null.
     * @see Weapon
     */
    protected Weapon weapon;

    /**
     * Special functionality to animate the objects sprites on death.
     */
    private int deathAnimCounter;

    /**
     * <b>Constructor: </b>Requires the entities {@code Sprite}, XY
     * position and health points to initiate an object.
     * @param sprite The {@code Sprite} of the object. Required to define dimensions.
     * @param x The X position of the object on spawn.
     * @param y The Y position of the object on spawn.
     * @param health The health points for the object to initiate with.
     */
    public Entity(Sprite sprite, int x, int y, int health){
        super(x, y);
        newSprite(sprite);
        setNewDimensions();
        this.health = health;
        this.alive = true;
    }

    /**
     * An abstract function for the subclass to implement it's own shooting functionality.
     */
    public abstract void shoot();

    /**
     * Returns the health points of the object.
     * @return The health points of the object.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Checks if the object is alive.
     * @return {@code true} or {@code false}.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Defines the object as dead, and triggers it's death animation.
     */
    public void isDead(){
        if(alive)
            SoundManager.getInst().entityDead();

        if(this.getClass() == Enemy.class && alive)
            if(((Enemy)this).getType().IS_BOSS)
                SoundManager.getInst().bossDies();

        alive = false;
        setCanShoot(false);
    }

    /**
     * Checks if the object can shoot.
     * @return {@code true} or {@code false}.
     */
    protected boolean canShoot() {
        return canShoot;
    }

    /**
     * Defines if the object can shoot.
     * @param canShoot {@code true} or {@code false}.
     */
    public void setCanShoot(boolean canShoot) { this.canShoot = canShoot; }

    /**
     * Updates the objects health points.
     * @param health New health points value.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * The object takes 1 point of damage.
     * * <p><b>Note: </b>If {@code health} < 1, {@code isDead()} is called.
     */
    public void takeDamage(){
        takeDamage(-1);
    }

    /**
     * The object takes damage according to the {@code dmg} parameter.
     * * <p><b>Note: </b>If {@code health} < 1, {@code isDead()} is called.
     * @param dmg How much damage for the object to take.
     */
    public void takeDamage(int dmg){
        dmg = Math.abs(dmg);
        health -= dmg;
        if(health <= 0) {
            isDead();
        }
    }

    /**
     * Iterate through the death animation of the object.
     */
    protected void animateDeath() {
        deathAnimCounter++;
        if (deathAnimCounter < 9) {
            newSprite("assets/image/player_death/playerDeath_00" + deathAnimCounter + ".png");
        } else {
            newSprite(Sprite.CLEAR);
        }
        if (deathAnimCounter > 9 ){
            isReadyToPurge();
        }
    }

    /**
     * Defines the object as alive.
     */
    protected void setAlive(){
        this.alive = true;
    }

}
