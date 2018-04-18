package view;

import javafx.scene.image.Image;

import model.GameModel;

public class HUD {
    
    GameView gv = GameView.getInstance();
    GameModel gm = GameModel.getInstance();
    
    private Image playerIcon = new Image("assets/image/hud/playerIcon.png");
    private Image shieldIcon = new Image("assets/image/hud/shieldIcon.png");
    private Image numeralX = new Image("assets/image/hud/numeralX.png");
    private Image lifeCounter;
    
    private static HUD inst = new HUD();
    private HUD() {}
    public static HUD getInstance() { return inst; }
    
    public void renderHUD() {
        setlifeCounter(gm.player.getHealth());
        gv.renderHUD(this, gm.player.hasShield());
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
    
}
