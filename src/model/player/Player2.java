package model.player;

import assets.java.Sprite;
import static controller.GameController.gs;
import javafx.scene.image.Image;
import model.Entity;
import model.weapons.Basic;
import model.weapons.DoubleSwirl;
import model.weapons.HeatSeeking;
import model.weapons.TripleBurst;
import model.weapons.Weapon;
import view.GameView;
import view.ViewUtil;

/**
 * <h1>The Player2 class - For multiplayer events</h1>
 * The {@code Enemy} class extends the {@code Entity} class and handles
 * all the events for the Player2 object
 */
public class Player2 extends Entity {

    /**
     * The singleton object.
     */
    private static Player2 inst = new Player2();
    
    /**
     * Method to access singleton class.
     * @return Returns a reference to the singleton object.
     */        
    public static Player2 getInst(){ return inst; }

    /**
     * The weapon type currently equipped - can be upgraded several times
     */           
    private String weaponType = "Basic";

    /**
     * <b>Constructor: </b>sets the player sprite, X and Y positions as well as health.
     */     
    public Player2(){
        super(
                Sprite.PLAYER2,
                40,
                ViewUtil.VIEW_HEIGHT / 2 - (int) new Image(Sprite.PLAYER2.src).getHeight() / 2,
                5
        );

        setCanShoot(true);
        weapon = Weapon.PLAYER_BASIC;
    }    
    
    /**
     * Overrides the {@code shoot} method in the superclass
     * Unused, as the method needs X and Y coordinates
     */        
    @Override
    public void shoot() {
        
    }
    
    /**
     * Shoot method that takes X and Y values as input.
     * Creates new bullet objects based on the current weaponType at correct position
     * @param x X position
     * @param y Y position
     */        
    public void shoot(int x, int y) {
        switch(weaponType) {
            case "Basic":
                gs.player2Bullets.add(new Basic(x + width - 10, y + (height / 2) - 8, weapon));
                break;
            case "SpeedBullets":
                gs.player2Bullets.add(new Basic(x + width - 10, y + (height / 2) - 8, weapon));
                break;
            case "DamageBullets":
                gs.player2Bullets.add(new Basic(x + width - 10, y + (height / 2) - 8, weapon));
                break;
            case "HeatSeeking":
                gs.player2Bullets.add(new HeatSeeking(x + width - 10, y + (height / 2) - 8, weapon));
                break;
            case "Doubles":
                gs.player2Bullets.add(new Basic(x + width - 10, y + (height / 2) - 25, weapon));
                gs.player2Bullets.add(new Basic(x + width - 10, y + (height / 2) + 15, weapon));
                break;
            case "DoubleSwirl":
                gs.player2Bullets.add(new DoubleSwirl(x + width, y + (height / 2) - 25, weapon, true));
                gs.player2Bullets.add(new DoubleSwirl(x + width - 10, y + (height / 2) + 15, weapon, false));
                break;
            case "TripleBurst":
                gs.playerBullets.add(new TripleBurst(x + width - 10, y + (height / 2) - 25, weapon, 1));
                gs.playerBullets.add(new TripleBurst(x + width - 10, y + (height / 2), weapon, 2));
                gs.playerBullets.add(new TripleBurst(x + width - 10, y + (height / 2) + 15, weapon, 3));
                break;  
            }        
    }
    
    /**
     * Method that handles updating of the player2 object 
     * on each tick of the animationtimer in the {@code GameController} class.
     * Calls the render method in the {@code GameView} class.
     */        
    public void update(){
        GameView.getInstance().renderPlayer2();
    }
    
    /**
     * Clears the player2 sprite
     */        
    public void unsetSprite() {
        newSprite(Sprite.CLEAR);
    }
    
    /**
     * @param weaponType sets the current weaponType
     */        
    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    } 
    
    /**
     * Method that handles weapon upgrades for player2 
     */        
    public void powerUp() {
        switch(weapon) {
            case PLAYER_BASIC:
                weapon = Weapon.PLAYER_UPGRADED;
                weaponType = "SpeedBullets";
                break;
            case PLAYER_UPGRADED:
                weapon = Weapon.PLAYER_UPGRADED2;
                weaponType = "DamageBullets";
                break;
            case PLAYER_UPGRADED2:
                weapon = Weapon.PLAYER_HEATSEEKING;
                weaponType = "HeatSeeking";
                break;
            case PLAYER_HEATSEEKING:
                weapon = Weapon.PLAYER_DOUBLES;
                weaponType = "Doubles";
                break;
            case PLAYER_DOUBLES:
                weapon = Weapon.PLAYER_DOUBLESWIRL;
                weaponType = "DoubleSwirl";
                break;
            case PLAYER_DOUBLESWIRL:
                weapon = Weapon.PLAYER_TRIPLEBURST;
                weaponType = "TripleBurst";
                break;
            case PLAYER_TRIPLEBURST:
                weapon = Weapon.PLAYER_TRIPLEBURST;
                weaponType = "TripleBurst";
                break;
            default:
                weaponType = "Basic";
                weapon = Weapon.PLAYER_BASIC;
                break;
        }
    }    
    
}
