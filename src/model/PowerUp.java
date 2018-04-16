
package model;

import assets.java.Sprite;

public class PowerUp extends Existance {
    
    private boolean used = false;
    GameModel gm = GameModel.getInstance();

    private String name;
    
    public PowerUp(Sprite sprite, int x, int y) {
        super(x, y);
        newSprite(sprite);
        setNewDimensions();
        name = sprite.name();
    }
    
    public void move() {
        setX(getX() - 2);
        setY(getY());
    }
    
    public void powerUp() {
        if (!used) {
            switch(this.name) {
                case "WEAPON_POWERUP":
                    gm.player.powerUp(); 
                    System.out.println("Weapon upgraded");
                    break;
                case "HEALTH_POWERUP":
                    gm.player.setHealth(gm.player.getHealth() + 1);
                    System.out.println("Health up");
                    break;
                case "SHIELD_POWERUP":
                    gm.player.setShield();
                    System.out.println("Shield!");
                    break;
            }
            used = true;
            newSprite(Sprite.CLEAR); // Trenger en Purge funksjon
        }
    }
    
}