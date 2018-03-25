package model.weapons;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class EnemyBulletBasic extends Bullet {
    private double x;
    private double y;
    private int width;
    private int height;
    
    private Image spriteImg = new Image("assets/image/laserRed.png");
    private ImageView sprite = new ImageView(spriteImg);

    public EnemyBulletBasic(double x, double y) {
        super(x, y);
        this.x = x;
        this.y = y;
        height = (int)spriteImg.getHeight();
        width = (int)spriteImg.getWidth();                
    }
    
    @Override
    public double getX() {
        return this.x;
    }
    
    @Override
    public double getY() {
        return this.y;
    }
    
    @Override
    public void setX(double x) {
        this.x = x;
    }
    
    @Override
    public void setY(double y) {
        this.y = y;
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public Image getSpriteImage() {
        return spriteImg;
    }
    
}
