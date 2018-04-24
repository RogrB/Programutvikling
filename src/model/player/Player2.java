package model.player;

import assets.java.Audio;
import assets.java.Sprite;
import java.util.ArrayList;
import javafx.scene.image.Image;
import model.Entity;
import model.GameModel;
import model.Shield;
import model.weapons.Bullet;
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
        //behave.shoot(weaponType, x, y, width, height, weapon);
        GameModel.getInstance().getPlayer2Bullets().add(new Bullet(x + width - 10, y + (height / 2) - 8, weapon));
    }
    
    public void update(){
        getImageView().relocate(getX(), getY());
    }
    
    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }    
    
}
