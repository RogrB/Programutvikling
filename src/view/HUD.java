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

public class HUD {
    
    GameView gv = GameView.getInstance();
    GameModel gm = GameModel.getInstance();

    private Image playerIcon = new Image("assets/image/hud/playerIcon.png");
    private Image shieldIcon = new Image("assets/image/hud/shieldIcon.png");
    private Image numeralX = new Image("assets/image/hud/numeralX.png");
    private Image weaponTypeImg = new Image("assets/image/hud/weaponType.png");
    private Image lifeCounter;
    
    Text weaponText;
    
    private static HUD inst = new HUD();
    private HUD(){

    }
    public static HUD getInstance() { return inst; }
    
    public void renderHUD() {
        setlifeCounter(gs.player.getHealth());
        gv.renderHUD(this, gs.player.hasShield());
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
