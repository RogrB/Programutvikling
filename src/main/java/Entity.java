package main.java;

import Weapons.Bullet;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.util.ArrayList;

public abstract class Entity {
    private int x;
    private int y;
    private double height;
    private double width;
    private int health;
    private boolean alive;
    private boolean moving;
    private boolean canShoot;
    private boolean canMove;
    private int bulletCount;
    private ImageView sprite;
    private AudioClip shot;

    private ArrayList<Bullet> bullets = new ArrayList<>();


    public Entity(int x, int y, int health, boolean alive, boolean moving, boolean canShoot, boolean canMove, int bulletCount){
        this.x = x;
        this.y = y;
        this.height = sprite.getFitHeight();
        this.width = sprite.getFitWidth();
        this.health = health;
        this.alive = alive;
        this.moving = moving;
        this.canShoot = canShoot;
        this.canMove = canMove;
        this.bulletCount = bulletCount;
        this.sprite = sprite;
        this.shot = shot;
    }

    public abstract void shoot();

    public abstract void update();
    public abstract void move(String dir);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public AudioClip getShot() {
        return shot;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
