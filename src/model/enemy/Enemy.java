package model.enemy;

import javafx.scene.image.ImageView;

public class Enemy {

    private final EnemyType TYPE;
    private int x;
    private int y;

    private EnemyMovementPatterns pattern;
    private boolean dead;
    private int health;
    private ImageView sprite;

    private double posX, posY;

    public Enemy(EnemyType TYPE, EnemyMovementPatterns pattern, int x, int y){
        this.TYPE= TYPE;
        this.pattern = pattern;
        this.x = x;
        this.y = y;
        sprite = new ImageView("assets/enemyBlue1.png");

        dead    = false;
        health  = this.TYPE.MAX_HEALTH;
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

    public void move(){
        pattern.nextFrame();
        updatePosition(pattern.x, pattern.y);
    }

    public void updatePosition(double newX, double newY){
        posX = newX;
        posY = newY;
        // System.out.println("x: " + posX + ", y: " + posY);
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

    public double x() {
        return posX;
    }

    public double y() {
        return posY;
    }

    // SET
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public ImageView getSprite(){
        return sprite;
    }

    public void update(){
        sprite.relocate(x, y);
        pattern.nextFrame();
        posX = pattern.x;
        posY = pattern.y;
    }
}
