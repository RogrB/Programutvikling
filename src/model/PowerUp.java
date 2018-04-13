
package model;

import assets.java.Sprite;

public class PowerUp extends Existance {
    
    private boolean used = false;
    GameModel gm = GameModel.getInstance();
    
    public PowerUp(Sprite sprite, int x, int y) {
        this.sprite = sprite;
        setX(x);
        setY(y);
        this.height = (int) sprite.getHeight();
        this.width = (int) sprite.getWidth();        
    }
    
    public void move() {
        setX(getX() - 2);
        setY(getY());
    }
    
    public void powerUp() {
        if (!used) {
            switch(this.sprite) {
                case WEAPON_POWERUP:
                    gm.player.powerUp(); 
                    System.out.println("Weapon upgraded");
                    break;
                case HEALTH_POWERUP:
                    gm.player.setHealth(gm.player.getHealth() + 1);
                    System.out.println("Health up");
                    break;
                case SHIELD_POWERUP:
                    gm.player.setShield();
                    System.out.println("Shield!");
                    break;
            }
            used = true;
            this.sprite = sprite.CLEAR; // Trenger en Purge funksjon
        }
    }
    
}