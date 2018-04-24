package model.player;

import assets.java.Audio;
import assets.java.Sprite;
import java.util.ArrayList;
import javafx.scene.image.Image;
import model.Entity;
import model.GameModel;
import model.Shield;
import model.weapons.Bullet;
import model.weapons.DoubleSwirl;
import model.weapons.Doubles;
import model.weapons.HeatSeeking;
import model.weapons.Upgrade1;
import model.weapons.Upgrade2;
import model.weapons.Weapon;
import view.GameView;
import view.ViewUtil;

public class Player2 extends Entity {

    // Singleton
    private static Player2 inst = new Player2();
    public static Player2 getInst(){ return inst; }

    private String weaponType = "Bullet";
    private PlayerBehaviour behave = new PlayerBehaviour();    

    public Player2(){
        super(
                Sprite.PLAYER2,
                40,
                ViewUtil.VIEW_HEIGHT / 2 - (int) new Image(Sprite.PLAYER2.src).getHeight() / 2,
                5
        );

        setCanShoot(true);
        getImageView().relocate(getX(), getY());        
        shot = Audio.PLAYER_SHOT;
        weapon = Weapon.PLAYER_BASIC;
    }    
    
    @Override
    public void shoot() {
        
    }
    
    public void shoot(int x, int y) {
        switch(weaponType) {
            case "Bullet":
                GameModel.getInstance().getPlayer2Bullets().add(new Bullet(x + width - 10, y + (height / 2) - 8, weapon));
                break;
            case "Upgrade1":
                GameModel.getInstance().getPlayer2Bullets().add(new Upgrade1(x + width - 10, y + (height / 2) - 8, weapon));
                break;
            case "Upgrade2":
                GameModel.getInstance().getPlayer2Bullets().add(new Upgrade2(x + width - 10, y + (height / 2) - 8, weapon));
                break;        
            case "HeatSeeking":
                GameModel.getInstance().getPlayer2Bullets().add(new HeatSeeking(x + width - 10, y + (height / 2) - 8, weapon));
                break;
            case "Doubles":
                GameModel.getInstance().getPlayer2Bullets().add(new Doubles(x + width - 10, y + (height / 2) - 25, weapon));
                GameModel.getInstance().getPlayer2Bullets().add(new Doubles(x + width - 10, y + (height / 2) + 15, weapon));
                break;
            case "DoubleSwirl":
                GameModel.getInstance().getPlayer2Bullets().add(new DoubleSwirl(x + width, y + (height / 2) - 25, weapon, true));
                GameModel.getInstance().getPlayer2Bullets().add(new DoubleSwirl(x + width - 10, y + (height / 2) + 15, weapon, false));
                break;
            }        
    }
    
    public void update(){
        getImageView().relocate(getX(), getY());
    }
    
    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    } 
    
    public void powerUp() {
        this.weaponType = behave.powerUp(weaponType);
    }    
    
}
