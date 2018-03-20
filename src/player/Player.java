package player;

import weapons.Bullet;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import main.java.GameView;

import java.util.ArrayList;

public class Player {

    public final int PLAYERWIDTH = 75;  // Bredde
    public final int PLAYERHEIGHT = 112;// Høyde

    private int     y;                  // Startpunkt fra top
    private int     x;                  // Startpunkt fra venstre
    private int     health = 3;         // Helse
    private boolean alive = true;       // Spiller død/levende
    private boolean moving;             // Om spiller er i bevegelse (brukes ikke ATM)
    private boolean canShoot = false;
    private int     score;              // Poengsum
    private int     bulletCount = 0;    // antall kuler som har blitt skutt

    private PlayerMovement      move = new PlayerMovement();
    private ArrayList<Bullet>   bullets = new ArrayList<>();

    ImageView sprite = new ImageView("assets/playerShip2_red.png");

    public Player(){
        this.y = GameView.HEIGHT / 2 - this.PLAYERHEIGHT/ 2;
        this.x = 40;
        sprite.relocate(x, y);
    }

    public void shoot() {
        if(canShoot) {
            bullets.add(new Bullet(x + PLAYERWIDTH - 10, y + (PLAYERHEIGHT / 2) + 3));
            bulletCount++;
            AudioClip laser = new AudioClip("file:src/assets/newLaser.mp3");
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
        if(y + PLAYERHEIGHT + move.next() >= GameView.HEIGHT) {
            move.moveStop();
            return true;
        }
        return false;
    }

    public void moveUp() {
        move.moveUp();
    }

    public void moveDown() {
        move.moveDown();
    }

    public void moveStop() {
        move.moveSlow();
    }

    // Getters og setters
    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean getAlive() {
        return this.alive;
    }

    public boolean getMoving() {
        return this.moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public ImageView getSprite(){
        return sprite;
    }

    public void setCanShoot(boolean bool){
        canShoot = bool;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isMoving() {
        return moving;
    }
}
