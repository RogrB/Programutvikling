package model;

import assets.java.Audio;
import assets.java.Sprite;
import model.weapons.Bullet;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.util.ArrayList;

public abstract class Entity extends Existance {
    protected int health;
    protected boolean alive;
    protected boolean canShoot;
    protected boolean canMove;
    protected int bulletCount;
    protected Sprite sprite;
    protected Audio shot;
    protected Sprite weapon;

    protected ArrayList<Bullet> bullets = new ArrayList<>();

    public Entity(Sprite sprite, int x, int y, int health){
        this.sprite = sprite;
        this.x = x;
        this.y = y;
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

    public ImageView getSprite() {
        return sprite.getView();
    }

    public AudioClip getShot() {
        return shot.getAudio();
    }

    public void setShot(Audio audio){
        shot = audio;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
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
    }
    
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

}
