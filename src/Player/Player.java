package Player;

import Weapons.Bullet;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import java.util.ArrayList;

public class Player {

    private int y = 370; // Startpunkt fra top
    private int oldY = 370; // Old Y posisjon
    private int x = 40; // Startpunkt fra venstre
    private int width = 75; // Bredde
    private int height = 112; // Høyde
    private int health = 3; // Helse
    private boolean alive = true; // Spiller død/levende
    private boolean moving; // Om spiller er i bevegelse (brukes ikke ATM)
    private boolean canShoot = false;
    private boolean canMove = true;
    private int score; // Poengsum
    private PlayerDirection dir = PlayerDirection.NONE;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int bulletCount = 0; // antall kuler som har blitt skutt
    ImageView sprite = new ImageView("assets/playerShip2_red.png");




    // Getters og setters
    public int getY() {
        return this.y;
    }

    public void shoot() {
        if(canShoot) {
            bullets.add(new Bullet(x + width - 10, y + (height / 2) + 3));
            bulletCount++;
            AudioClip laser = new AudioClip("file:src/assets/newLaser.mp3");
            laser.setVolume(0.25);
            laser.play();
        }
    }

    public void update(){
        sprite.relocate(x, y);
        this.y = this.y + dir.next();
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

    public int getOldY() {
        return this.oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    // Metoder for bevegelse - Flytter 5 pixler av gangen
    public void moveUp() {
            //this.y-=5;
            dir = PlayerDirection.UP;
    }

    public void moveDown() {
        //this.y+=5;
        dir = PlayerDirection.DOWN;
    }

    public void moveStop() {
        dir = PlayerDirection.NONE;
    }

    public void move() {
        this.y = this.y + dir.next();
    }


    public boolean isAlive() {
        return alive;
    }

    public boolean isMoving() {
        return moving;
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
}
