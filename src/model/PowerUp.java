
package model;

import assets.java.Sprite;
import view.ViewUtil;

import java.util.Iterator;
import java.util.Random;

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

    public void update(int x, int y, Iterator i){
        setX(getX() + x);
        setY(getY() + y);
        if(isOffScreen() || getReadyToPurge())
            purge(i);
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
            newSprite(Sprite.CLEAR);
            isReadyToPurge();
        }
    }

    public PowerUp newPowerUp(){
        Random random = new Random();
        int ySpawn = random.nextInt(ViewUtil.VIEW_HEIGHT - getHeight()) + getHeight();
        return new PowerUp(Sprite.WEAPON_POWERUP, ViewUtil.VIEW_WIDTH - 1, ySpawn);
    }
    
}