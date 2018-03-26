package model.weapons;


import javafx.scene.image.Image;

public class EnemyBulletBasic extends Bullet {
    private int width;
    private int height;
    
    private Image spriteImg = new Image("assets/image/laserRed.png");
    

    public EnemyBulletBasic(double x, double y) {
        super(x, y);
        height = (int)spriteImg.getHeight();
        width = (int)spriteImg.getWidth();                
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
