
package model;

import assets.java.Sprite;
import javafx.scene.image.Image;

public class PowerUp extends Existance {
    
    protected Sprite sprite;
    private boolean used = false;
    GameModel gm = GameModel.getInstance();
    
    public PowerUp(Sprite sprite, int x, int y) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.height = (int) sprite.getHeight();
        this.width = (int) sprite.getWidth();        
    }
    
    public void move() {
        this.x -= 2;
    }
    
    public void powerUp() {
        if (!used) {
            gm.player.powerUp();
            System.out.println("PowerUp!");
            used = true;
            this.sprite = sprite.CLEAR; // Trenger en Purge funksjon
        }
    }
    
    public Image getSpriteImage() {
        return sprite.getImg();
    }    
    
}
