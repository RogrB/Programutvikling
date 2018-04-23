package model.player;

import assets.java.Audio;
import assets.java.Sprite;
import java.util.ArrayList;
import javafx.scene.image.Image;
import model.Entity;
import model.Shield;
import model.weapons.Bullet;
import model.weapons.Weapon;
import view.GameView;
import view.ViewUtil;

public class Player2 extends Entity {

    /*
    // Singleton
    private static Player2 inst = new Player2();
    public static Player2 getInst(){ return inst; }
    */
    private String weaponType = "Bullet";
    private PlayerBehaviour behave = new PlayerBehaviour();    

    private ArrayList<Bullet> bullets;

    public Player2(){
        super(
                Sprite.PLAYER2,
                40,
                ViewUtil.VIEW_HEIGHT / 2 - (int) new Image(Sprite.PLAYER2.src).getHeight() / 2,
                5
        );

        setCanShoot(true);
        shot = Audio.PLAYER_SHOT;
        weapon = Weapon.PLAYER_BASIC;
    }    
    
    @Override
    public void shoot() {
        behave.shoot(weaponType, getX(), getY(), width, height, weapon);
    }
    
    public void update(){
        // input from multiplayer
    }    
    
    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }    
    
}
