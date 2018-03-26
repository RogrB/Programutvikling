package model.weapons;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Bullet {
    private double x;
    private double y;
    private double oldX;
    private double oldY;
    private int width;
    private int height;

    private Image sprite;

    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        sprite = new Image("assets/image/laserBlue06.png");
        height = (int)sprite.getHeight();
        width = (int)sprite.getWidth();
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getOldX() {
        return this.oldX;
    }

    public void setOldX(double x) {
        this.oldX = x;
    }

    public double getOldY() {
        return this.oldY;
    }

    public void setOldY(double y) {
        this.oldY = y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }

    // Metode for å flytte kulene mot høyre - 12 pixler av gangen
    public void update() {
        this.x+= 12;
    }

    
    public Image getSpriteImage() {
        return sprite;
    }

    public void clearImage() {
        this.sprite = new Image("assets/image/damage/clear.png");
    }
    
}
