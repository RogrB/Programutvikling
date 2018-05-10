package model;

import assets.java.SoundManager;
import assets.java.Sprite;
import model.enemy.Enemy;
import model.weapons.Weapon;

public abstract class Entity extends Existance {
    protected int health;
    private boolean alive;
    protected boolean canShoot;
    protected Weapon weapon;
    private int deathAnimCounter;

    public Entity(Sprite sprite, int x, int y, int health){
        super(x, y);
        newSprite(sprite);
        setNewDimensions();
        this.health = health;
        this.alive = true;
    }

    public abstract void shoot();

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return alive;
    }

    public void isDead(){
        if(alive)
            SoundManager.getInst().entityDead();

        if(this.getClass() == Enemy.class && alive)
            if(((Enemy)this).getType().IS_BOSS)
                SoundManager.getInst().bossDies();

        alive = false;
        setCanShoot(false);
    }

    protected boolean canShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) { this.canShoot = canShoot; }

    public void setHealth(int health) {
        this.health = health;
    }

    public void takeDamage(){
        takeDamage(-1);
    }

    public void takeDamage(int dmg){
        dmg = Math.abs(dmg);
        health -= dmg;
        if(health <= 0) {
            isDead();
        }
    }

    protected void animateDeath() {
        deathAnimCounter++;
        if (deathAnimCounter < 9) {
            newSprite("assets/image/playerDeath/playerDeath_00" + deathAnimCounter + ".png");
        } else {
            newSprite(Sprite.CLEAR);
        }
        if (deathAnimCounter > 9 ){
            isReadyToPurge();
        }
    }

    protected void setAlive(){
        this.alive = true;
    }

}
