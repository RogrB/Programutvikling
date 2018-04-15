package model;

import assets.java.Audio;
import assets.java.Sprite;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import model.weapons.Weapon;

import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.image.Image;

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
        this.sprite = sprite;
        newSprite(sprite);
        setNewDimensions();
        this.setX(x);
        this.setY(y);
        this.height = (int) sprite.getHeight();
        this.width = (int) sprite.getWidth();
        this.health = health;
        this.alive = true;
        this.bulletCount = 0;
    }

    public abstract void update();
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
            animateDeath();
        }
    }
    
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    
    public void animateDeath() {
        Timer deathTimer = new Timer();
        deathTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                if (deathAnimCounter < 9) {
                    deathAnimCounter++;
                    newSprite("assets/image/playerDeath/playerDeath_00" + deathAnimCounter + ".png");
                }
                else {
                    newSprite("assets/image/damage/clear.png");
                    isDead();
                    this.cancel();
                }
            }
        }, 0, 80);
    }

}
