package player;

import weapons.Bullet;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import main.java.GameView;

import java.util.ArrayList;

public class Player{

    private int x;
    private int y;
    ImageView sprite = new ImageView("assets/playerShip2_red.png");
    private double height = sprite.getFitHeight();
    private double width = sprite.getFitWidth();
    private int health = 3;
    private boolean alive = true;
    private boolean moving;
    private boolean canShoot = true;
    private int bulletCount = 0;
    private int score;
    private AudioClip laser = new AudioClip("file:src/assets/newLaser.mp3");

    private ArrayList<Bullet> bullets = new ArrayList<>();
    private PlayerMovement      move = new PlayerMovement();



    public Player(){
        this.y = GameView.HEIGHT / 2 - (int)this.height/ 2;
        this.x = 40;
        sprite.relocate(x, y);
    }

    public void shoot() {
        if(canShoot) {
            bullets.add(new Bullet(x + (int)this.width - 10, y + ((int) this.height / 2) + 3));
            bulletCount++;
            laser.setVolume(0.25);
            laser.play();
        }
    }

    public void update(){
        if(!playerIsOutOfBounds()){
            this.y = this.y + move.next();
            sprite.relocate(x, y);
        }
    }

    private boolean playerIsOutOfBounds(){
        if(y + move.next() < 0) {
            move.moveStop();
            return true;
        }
        if(y + (int)this.height + move.next() >= GameView.HEIGHT) {
            move.moveStop();
            return true;
        }
        return false;
    }

    public void move(String dir){
        switch(dir){
            case "UP":
                move.move(-1);
                break;
            case "DOWN":
                move.move(1);
                break;
            case "STOP":
                move.move(0);
        }
    }

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


    public int getBulletCount() {
        return bulletCount;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public AudioClip getLaser() {
        return laser;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
