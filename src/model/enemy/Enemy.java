package model.enemy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy {

    private final EnemyType TYPE;
    private int x;
    private int y;
    private int height;
    private int width;

    private EnemyMovementPattern pattern;
    private boolean dead;
    private int health;
    private Image spriteImg;
    private ImageView sprite;
    int i = 100;

    private double posX, posY;

    public Enemy(EnemyType TYPE, EnemyMovementPattern pattern, int x, int y){
        this.TYPE= TYPE;
        this.pattern = pattern;
        this.x = x;
        this.y = y;
        spriteImg = new Image("assets/enemyBlue1.png");
        sprite = new ImageView(spriteImg);
        height = (int)spriteImg.getHeight();
        width = (int)spriteImg.getWidth();

        dead    = false;
        health  = this.TYPE.MAX_HEALTH;

        sprite.relocate(x, y);
    }

    // FUNCTIONS
    public boolean takeDamage(){
        health--;
        if(health > 0)
            return true;

        die();
        return false;
    }

    public void die() {
        dead = true;
    }

    public void updatePosition(double newX, double newY){
        x = (int)newX;
        y = (int)newY;
        // System.out.println("x: " + posX + ", y: " + posY);
    }

    public void updatePatternStartingPoint(double x, double y){
        pattern.x = x;
        pattern.y = y;
    }

    // GET
    public boolean isDead() {
        return dead;
    }

    public EnemyType getTYPE() {
        return TYPE;
    }

    public int getHealth() {
        return health;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public ImageView getSprite(){
        return sprite;
    }

    public EnemyMovementPattern getPattern(){
        return pattern;
    }

    public void move(){

        sprite.relocate(i, i);
        i += 10;
        System.out.println(i);
    }

    public void update(){
        sprite.relocate(x, y);
        pattern.nextFrame();
        x = (int)pattern.x;
        y = (int)pattern.y;
        // System.out.format("X: %d, Y: %d \n", x, y);
        pattern.resetCoords();
    }
}
