package Player;

import Weapons.Bullet;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import main.java.Main;

import java.util.ArrayList;

public class Player {

    private int playerwidth = 75; // Bredde
    private int playerhight = 112;// Høyde

    private int     y;                  // Startpunkt fra top
    private int     x;                  // Startpunkt fra venstre
    private int     health = 3;         // Helse
    private boolean alive = true;       // Spiller død/levende
    private boolean moving;             // Om spiller er i bevegelse (brukes ikke ATM)
    private boolean canShoot = false;
    private boolean canMove = true;
    private int     score;              // Poengsum
    private int     bulletCount = 0;    // antall kuler som har blitt skutt

    private PlayerDirection     dir = PlayerDirection.NONE;
    private ArrayList<Bullet>   bullets = new ArrayList<>();

    ImageView sprite = new ImageView("assets/playerShip2_red.png");

    public Player(){
        this.y = (Main.HEIGHT + this.playerhight) / 2;
        this.x = 40;
        sprite.relocate(x, y);
    }

    public void shoot() {
        if(canShoot) {
            bullets.add(new Bullet(x + playerwidth - 10, y + (playerhight / 2) + 3));
            bulletCount++;
            AudioClip laser = new AudioClip("file:src/assets/newLaser.mp3");
            laser.setVolume(0.25);
            laser.play();
        }
    }

    public void update(){
        /*if(y - (playerhight / 2) > 0 && y + (playerhight / 2) < Main.HEIGHT){
            sprite.relocate(x, y);
            this.y = this.y + dir.next();
            System.out.println("y: " + y + "x: " + x);
        }*/
        if(!playerIsOutOfBounds()){
            this.y = this.y + dir.next();
            sprite.relocate(x, y);
        }
    }

    private boolean playerIsOutOfBounds(){
        if(y - dir.next() <= 0)
            return true;
        else if(y + playerhight + dir.next() >= Main.HEIGHT)
            return true;
        return false;
    }

    public void moveUp() {
        dir = PlayerDirection.UP;
    }

    public void moveDown() {
        dir = PlayerDirection.DOWN;
    }

    public void moveStop() {
        dir = PlayerDirection.NONE;
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

    public int getPlayerwidth() {
        return this.playerwidth;
    }

    public void setPlayerwidth(int playerwidth) {
        this.playerwidth = playerwidth;
    }

    public int getPlayerhight() {
        return this.playerhight;
    }

    public void setPlayerhight(int playerhight) {
        this.playerhight = playerhight;
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

    public void setCanMove(boolean bool){
        canMove = bool;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isMoving() {
        return moving;
    }
}
