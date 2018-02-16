package main.java;

import static main.java.Main.SPEED_MODIFIER;

public class Enemy {

    private final EnemyType TYPE;

    private MovementPattern pattern;
    private boolean dead;
    private int health;

    private double posX, posY;

    public Enemy(EnemyType TYPE, MovementPattern pattern){
        this.TYPE       = TYPE;
        this.pattern    = pattern;

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
        //System.out.println(posX);
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
}
