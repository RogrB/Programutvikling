package model.player;

import model.weapons.*;

import static controller.GameController.gs;

/**
 * <h1>A class for handling Player behaviour</h1>
 * This class handles movements, shooting and weapon powerups
 */
public class PlayerBehaviour implements java.io.Serializable {

    private static final long serialVersionUID = 185835451720483588L;

    /**
     * Speed value
     */      
    private int speed;
    
    /**
     * The players movement direction.
     */      
    private int dir;

    /**
     * <b>Constructor: </b>initiates the speed and direction variables to 0
     */        
    public PlayerBehaviour() {
        speed = 0;
        dir = 0;
    }


    /**
     * @param direction sets the movement direction
     */      
    void move(int direction){
        if(this.dir != direction) {
            dir = direction;
        }
    }

    /**
     * Stops movement instantly. Called when the player reaches the bounds of the screen.
     */      
    void moveStop(){
        speed = 0;
    }

    /**
     * @return new value for Player Y position
     */      
    int next(){
        int MOD_SPEED = 1;
        if(!isMaxSpeed())
            speed += dir * MOD_SPEED;
        if(dir == 0){
            if(speed > 0)
                speed -= MOD_SPEED;
            if(speed < 0)
                speed += MOD_SPEED;
        }
        return speed;
    }

    /**
     * Checks if the player is moving at max speed
     * @return {@code true} or {@code false}.
     */      
    private boolean isMaxSpeed(){
        int MAX_SPEED = 20;
        return Math.abs(speed) > MAX_SPEED;
    }
    
    /**
     * Method for handling player shooting event.
     * Checks the current weapon type equipped and created bullet objects
     * accordingly. The bullets are spawned at the appropriate position based
     * on the players X and Y values.
     */      
    void shoot(String weapontype, int x, int y, int width, int height, Weapon weapon) {
            switch(weapontype) {
                case "Basic":
                    gs.playerBullets.add(new Basic(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "SpeedBullets":
                    //gs.playerBullets.add(new SpeedBullets(x + width - 10, y + (height / 2) - 8, weapon));
                    gs.playerBullets.add(new Basic(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "DamageBullets":
                    //gs.playerBullets.add(new DamageBullets(x + width - 10, y + (height / 2) - 8, weapon));
                    gs.playerBullets.add(new Basic(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "HeatSeeking":
                    gs.playerBullets.add(new HeatSeeking(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "Doubles":
                    //gs.playerBullets.add(new Doubles(x + width - 10, y + (height / 2) - 25, weapon));
                    gs.playerBullets.add(new Basic(x + width - 10, y + (height / 2) - 25, weapon));
                    //gs.playerBullets.add(new Doubles(x + width - 10, y + (height / 2) + 15, weapon));
                    gs.playerBullets.add(new Basic(x + width - 10, y + (height / 2) + 15, weapon));
                    break;
                case "DoubleSwirl":
                    gs.playerBullets.add(new DoubleSwirl(x + width, y + (height / 2) - 25, weapon, true));
                    gs.playerBullets.add(new DoubleSwirl(x + width - 10, y + (height / 2) + 15, weapon, false));
                    break;
                case "TripleBurst":
                    gs.playerBullets.add(new TripleBurst(x + width - 10, y + (height / 2) - 25, weapon, 1));
                    gs.playerBullets.add(new TripleBurst(x + width - 10, y + (height / 2), weapon, 2));
                    gs.playerBullets.add(new TripleBurst(x + width - 10, y + (height / 2) + 15, weapon, 3));
                    break;                    
            }
    }
    
    /**
     * Method for handling weapon powerups.
     * @param weaponType input the current weapon equipped.
     * @return outputs the new weapon.
     */      
    public String powerUp(String weaponType) {
        String returnString = "";
        switch(weaponType) {
            case "Reset":
                returnString = "Basic";
                break;
            case "Basic":
                returnString = "SpeedBullets";
                break;
            case "SpeedBullets":
                returnString = "DamageBullets";
                break;
            case "DamageBullets":
                returnString = "HeatSeeking";
                break;
            case "HeatSeeking":
                returnString = "Doubles";
                break;
            case "Doubles":
                returnString = "DoubleSwirl";
                break;
            case "DoubleSwirl":
                returnString = "TripleBurst";
                break;
            case "TripleBurst":
                returnString = "TripleBurst";
                break;
        }
        return returnString;
    }
    
}
