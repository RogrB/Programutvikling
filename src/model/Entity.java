package model;

import assets.java.Audio;
import assets.java.Sprite;
import javafx.scene.media.AudioClip;
import model.weapons.Weapon;

public abstract class Entity extends Existance {
    protected int health;
    protected boolean alive;
    protected boolean canShoot;
    protected boolean canMove;
    protected int bulletCount;
    protected Audio shot;
    protected Weapon weapon;
    private int deathAnimCounter;

    public Entity(Sprite sprite, int x, int y, int health){
        super(x, y);
        newSprite(sprite);
        setNewDimensions();
        this.health = health;
        this.alive = true;
        this.bulletCount = 0;
    }

    public abstract void shoot();

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return alive;
    }

    public void isDead(){
        alive = false;
        setCanShoot(false);
    }

    public boolean canShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) { this.canShoot = canShoot; }

    public boolean canMove() {
        return canMove;
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public AudioClip getShot() {
        return shot.getAudio();
    }

    public void setShot(Audio audio){
        shot = audio;
    }

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

    public void animateDeath() {
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

}
