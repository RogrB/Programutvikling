package model.player;

import assets.java.Sprite;
import javafx.scene.image.Image;
import model.Entity;
import model.weapons.Basic;
import model.weapons.DoubleSwirl;
import model.weapons.Doubles;
import model.weapons.HeatSeeking;
import model.weapons.SpeedBullets;
import model.weapons.DamageBullets;
import model.weapons.Weapon;
import view.ViewUtil;

import static controller.GameController.gs;

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
        weapon = Weapon.PLAYER_BASIC;
    }    
    
    @Override
    public void shoot() {
        
    }
    
    public void shoot(int x, int y) {
        switch(weaponType) {
            case "Bullet":
                gs.player2Bullets.add(new Basic(x + width - 10, y + (height / 2) - 8, weapon));
                break;
            case "Upgrade1":
                gs.player2Bullets.add(new SpeedBullets(x + width - 10, y + (height / 2) - 8, weapon));
                break;
            case "Upgrade2":
                gs.player2Bullets.add(new DamageBullets(x + width - 10, y + (height / 2) - 8, weapon));
                break;        
            case "HeatSeeking":
                gs.player2Bullets.add(new HeatSeeking(x + width - 10, y + (height / 2) - 8, weapon));
                break;
            case "Doubles":
                gs.player2Bullets.add(new Doubles(x + width - 10, y + (height / 2) - 25, weapon));
                gs.player2Bullets.add(new Doubles(x + width - 10, y + (height / 2) + 15, weapon));
                break;
            case "DoubleSwirl":
                gs.player2Bullets.add(new DoubleSwirl(x + width, y + (height / 2) - 25, weapon, true));
                gs.player2Bullets.add(new DoubleSwirl(x + width - 10, y + (height / 2) + 15, weapon, false));
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
