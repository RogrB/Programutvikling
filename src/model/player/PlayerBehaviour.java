package model.player;

import model.weapons.*;

import static controller.GameController.gs;

/**
 * <h1>A class for handling Player behaviour</h1>
 * This class handles movements, shooting and weapon powerups
 *
 * @author Åsmund Røst Wien
 * @author Roger Birkenes Solli
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
     * @param weapontype the descriptive weapon type String
     * @param x X value
     * @param y Y value
     * @param width Width
     * @param height Height
     * @param weapon The current weapon type object
     */      
    void shoot(String weapontype, int x, int y, int width, int height, Weapon weapon) {
            switch(weapontype) {
                case "Basic":
                    gs.playerBullets.add(new Basic(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "SpeedBullets":
                    gs.playerBullets.add(new Basic(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "DamageBullets":
                    gs.playerBullets.add(new Basic(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "HeatSeeking":
                    gs.playerBullets.add(new HeatSeeking(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "Doubles":
                    gs.playerBullets.add(new Basic(x + width - 10, y + (height / 2) - 25, weapon));
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
    public Weapon powerUp(Weapon weaponType) {
        Weapon returnWeapon;
        switch(weaponType) {
            case PLAYER_BASIC:
                returnWeapon = Weapon.PLAYER_UPGRADED;
                gs.player.setWeaponType("SpeedBullets");
                break;
            case PLAYER_UPGRADED:
                returnWeapon = Weapon.PLAYER_UPGRADED2;
                gs.player.setWeaponType("DamageBullets");
                break;
            case PLAYER_UPGRADED2:
                returnWeapon = Weapon.PLAYER_HEATSEEKING;
                gs.player.setWeaponType("HeatSeeking");
                break;
            case PLAYER_HEATSEEKING:
                returnWeapon = Weapon.PLAYER_DOUBLES;
                gs.player.setWeaponType("Doubles");
                break;
            case PLAYER_DOUBLES:
                returnWeapon = Weapon.PLAYER_DOUBLESWIRL;
                gs.player.setWeaponType("DoubleSwirl");
                break;
            case PLAYER_DOUBLESWIRL:
                returnWeapon = Weapon.PLAYER_TRIPLEBURST;
                gs.player.setWeaponType("TripleBurst");
                break;
            case PLAYER_TRIPLEBURST:
                returnWeapon = Weapon.PLAYER_TRIPLEBURST;
                gs.player.setWeaponType("TripleBurst");
                break;
            default:
                gs.player.setWeaponType("Basic");
                returnWeapon = Weapon.PLAYER_BASIC;
                break;
        }
        return returnWeapon;
    }
    
}
