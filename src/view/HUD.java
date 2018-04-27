package view;

import javafx.scene.image.Image;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.GameModel;
import model.GameState;
import model.enemy.EnemyType;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import java.util.ArrayList;
import static controller.GameController.gs;
import java.util.Timer;
import java.util.TimerTask;

public class HUD {
    
    GameView gv = GameView.getInstance();
    GameModel gm = GameModel.getInstance();

    private Image playerIcon = new Image("assets/image/hud/playerIcon.png");
    private Image shieldIcon = new Image("assets/image/hud/shieldIcon.png");
    private Image numeralX = new Image("assets/image/hud/numeralX.png");
    private Image weaponTypeImg = new Image("assets/image/hud/weaponType.png");
    private Image lifeCounter;
    
    private int powerUpX;
    private int powerUpY;
    private int fadeCounter;
    private float opacity = 1;
    
    Text weaponText;
    
    private static HUD inst = new HUD();
    private HUD(){

    }
    public static HUD getInstance() { return inst; }
    
    public void renderHUD() {
        setlifeCounter(gs.player.getHealth());
        gv.renderHUD(this, gs.player.hasShield());
    }
    
    public void initRenderPowerUpText(String powerUp) {
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
    
    public Image getPlayerIcon() {
        return this.playerIcon;
    }
    
    public Image getShieldIcon() {
        return this.shieldIcon;
    }
    
    public Image getLifeCounter() {
        return this.lifeCounter;
    }
    
    public void setlifeCounter(int n) {
        this.lifeCounter = new Image("assets/image/hud/numeral" + n + ".png");
    }
    
    public Image getNumeralX() {
        return this.numeralX;
    }
    
    public String weaponType() {
        String weapon = gs.player.getWeaponType();
        return weapon;
    }
    
    public Image getWeaponTypeImg() {
        return this.weaponTypeImg;
    }
    
}
