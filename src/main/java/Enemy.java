package main.java;

public class Enemy {

    private final EnemyType TYPE;

    private boolean dead;
    private int health;

    private int posX, posY;

    public Enemy(EnemyType TYPE){
        this.TYPE = TYPE;
        health = this.TYPE.MAX_HEALTH;
        dead = false;
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

    public void move(MovementPattern pattern){
        
    }

    public void updatePosition(int newX, int newY){
        posX = newX;
        posY = newY;
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

    public int x() {
        return posX;
    }

    public int y() {
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
