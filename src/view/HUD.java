package view;

import javafx.scene.image.Image;
import static controller.GameController.gs;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <h1>Handles the Heads Up Display on the game screen</h1>
 * This class handles the setup of the heads up display to
 * be rendered to the game screen.
 */
public class HUD {
    
    /**
     * An instance of the {@code GameView} object.
     * @see GameView
     */        
    private GameView gv = GameView.getInstance();

    /**
     * The player icon - displayed in the top left corner of the screen.
     */        
    private Image playerIcon = new Image("assets/image/hud/playerIcon.png");
    
    /**
     * Shield icons - used to display the number of charges left on the shield
     */        
    private Image shieldIcon = new Image("assets/image/hud/shieldIcon.png");
    
    /**
     * Numeral X icon - used in combination with the {@code playerIcon} and
     * {@code lifeCounter} to display the Players current hit points
     */        
    private Image numeralX = new Image("assets/image/hud/numeralX.png");
    
    /**
     * Weapon Icon - used in combination with on-screen text to display the current
     * weapon type.
     */        
    private Image weaponTypeImg = new Image("assets/image/hud/weaponType.png");
    
    /**
     * LifeCounter image to display the Players hit points.
     */        
    private Image lifeCounter;
    
    /**
     * X position of the powerUp text
     * when the player gets a PowerUp
     */        
    private int powerUpX;
    
    /**
     * Y position of the powerUp text
     * when the player gets a PowerUp
     */        
    private int powerUpY;
    
    /**
     * Counter for handling the animation of 
     * the powerUp text
     */        
    private int fadeCounter;
    
    /**
     * Opacity value for the animation of
     * the powerUp text
     */        
    private float opacity = 1;
    
    /**
     * The singleton object.
     */    
    private static HUD inst = new HUD();
    
    /**
     * Private <b>constructor</b>
     */       
    private HUD(){ }
    
    /**
     * Method to access singleton class.
     * @return Returns a reference to the singleton object.
     */       
    public static HUD getInstance() { return inst; }
    
    /**
     * Method for rendering the {@code HUD} in the {@code GameView} class
     */        
    public void renderHUD() {
        setlifeCounter(gs.player.getHealth());
        gv.renderHUD(this, gs.player.hasShield());
    }
    
    /**
     * Method for rendering a descriptive text
     * of the type of PowerUp the player picked up in the game space.
     * The text floats upwards and fades out over time.
     * @param powerUp takes in the type of powerUp the player picked up.
     */        
    public void renderPowerUpText(String powerUp) {
        powerUpX = gs.player.getX()+35;
        powerUpY = gs.player.getY();
        
        Timer textFadeTimer = new Timer();
        textFadeTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                powerUpY -= 1;
                opacity -= 0.01;
                fadeCounter++;
                gv.renderPowerUpText(powerUp, powerUpX, powerUpY, opacity);
                if (fadeCounter > 35) {
                    this.cancel();
                    fadeCounter = 0;
                    opacity = 1;
                    gv.clearPowerUpText(powerUpX, powerUpY);
                }
            }
            
        }, 0, 30);  
    }
    
    /**
     * @return gets the HUD player icon
     * which is displayed in the top left corner of the screen.
     */        
    Image getPlayerIcon() {
        return this.playerIcon;
    }
    
    /**
     * @return gets the HUD shield icon
     * which counts the number of shield charges in the top left corner of the screen.
     */        
    Image getShieldIcon() {
        return this.shieldIcon;
    }
    
    /**
     * @return gets the HUD life counter
     * which counts the Players hit points in the top left corner of the screen.
     */        
    Image getLifeCounter() {
        return this.lifeCounter;
    }
    
    /**
     * @param n sets the life counter Icon
     */        
    private void setlifeCounter(int n) {
        this.lifeCounter = new Image("assets/image/hud/numeral" + n + ".png");
    }
    
    /**
     * @return gets the numeral X icon
     * which is displayed in the top left corner of the screen.
     */        
    Image getNumeralX() {
        return this.numeralX;
    }
    
    /**
     * @return gets the weapon type from the player object
     */        
    String weaponType() {
        return gs.player.getWeaponType();
    }
    
    /**
     * @return gets the image of the weapon icon
     * which is displayed in the top left corner of the screen.
     */        
    Image getWeaponTypeImg() {
        return this.weaponTypeImg;
    }
    
}
