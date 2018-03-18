package Weapons;

import javafx.scene.image.ImageView;

public class Bullet {
    private double x;
    private double y;
    private double oldX;
    private double oldY;
    private ImageView sprite;

    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        sprite = new ImageView("assets/laserBlue06.png");
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

    // Metode for å flytte kulene mot høyre - 12 pixler av gangen
    public void update() {
        this.x+= 12;
    }

    public ImageView getSprite(){
        return sprite;
    }
}
