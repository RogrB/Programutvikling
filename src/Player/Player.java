package Player;

public class Player {
    
    private int y = 325; // Startpunkt fra top
    private int oldY = 30; // Old Y posisjon
    private int x = 40; // Startpunkt fra venstre
    private int width = 30; // Bredde
    private int height = 30; // Høyde
    private int health = 3; // Helse
    private boolean alive = true; // Spiller død/levende
    private boolean moving; // Om spiller er i bevegelse (brukes ikke ATM)
    private int score; // Poengsum
    
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
        this.y-=5;
    }
    
    public void moveDown() {
        this.y+=5;
    }
    

}