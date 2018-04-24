package model.player;

import assets.java.Audio;
import assets.java.Sprite;
import model.Entity;
import model.weapons.*;
import model.Shield;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.image.Image;
import model.GameModel;
import multiplayer.MultiplayerHandler;
import view.ViewUtil;

public class Player extends Entity {

    // Singleton
    private static Player inst = new Player();
    public static Player getInst(){ return inst; }

    // State
    private boolean immunity = false;
    private int immunityTime = 2000;
    private int blinkCounter;
    private String weaponType = "Bullet";
    private boolean hasShield = false;
    private boolean shooting= false;
    Shield shield = new Shield(getX(), getY(), hasShield());
    private int score;

    private PlayerBehaviour playerBehaviour = new PlayerBehaviour();

    private Player(){
        super(
                Sprite.PLAYER,
                40,
                ViewUtil.VIEW_HEIGHT / 2 - (int) new Image(Sprite.PLAYER.src).getHeight() / 2,
                5
        );

        setCanShoot(true);
        shot = Audio.PLAYER_SHOT;
        getImageView().relocate(getX(), getY());
        weapon = Weapon.PLAYER_BASIC;
        if (hasShield) {
            setShield();
        }
    }

    public void update(){
        playerBehaviour.mvcSetup();
        if(!playerIsOutOfBounds()){
            setY(getY() + playerBehaviour.next());
            getImageView().relocate(getX(), getY());
        }
        if(shield.isBroken() && hasShield()) {
            removeShield();
        }
        if(shooting){
            shoot();
        }
    }

    private boolean playerIsOutOfBounds(){
        if(getY() + playerBehaviour.next() < 0) {
            playerBehaviour.moveStop();
            return true;
        }

        if(getY() + this.height + playerBehaviour.next() >= ViewUtil.VIEW_HEIGHT) {
            playerBehaviour.moveStop();
            return true;
        }
        return false;
    }
    
    public String getWeaponType() {
        return this.weaponType;
    }
    
    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }
    
    public void powerUp() {
        this.weaponType = playerBehaviour.powerUp(weaponType);
        if(GameModel.getInstance().getMultiplayerStatus()) {
            MultiplayerHandler.getInstance().send("PowerUp", 0, 0);
        }
    }
    
    public void move(String dir){
        switch(dir){
            case "UP":
                playerBehaviour.move(-1);
                break;
            case "DOWN":
                playerBehaviour.move(1);
                break;
            case "STOP":
                playerBehaviour.move(0);
        }
    }

    @Override
    public void shoot() {
        if(canShoot()) {
            playerBehaviour.shoot(this.weaponType, getX(), getY(), this.width, this.height, weapon);
            setCanShoot(false);
            shotDelayTimer();
            if (GameModel.getInstance().getMultiplayerStatus()) {
                GameModel.getInstance().getMP().send("Shoot", getX(), getY());
            }
        }
    }
    
    public void shotDelayTimer() {
        Timer shotTimer = new Timer();
        shotTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                setCanShoot(true);
                this.cancel();
            }
        }, 300);
    }


    @Override
    public void takeDamage(){
        if (!hasShield()) {
            if(!isImmune()){
                super.takeDamage();
                if (isAlive()) {
                    initImmunity();
                }
            }
        }
        else {
            if (!shield.isImmune()) {
                shield.takeDamage();
            }
        }
    }

    private void initImmunity(){
        setImmunity(true);
        immunityBlinkAnimation();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                setImmunity(false);
            }
        }, getImmunityTime());

    }

    public void isShooting(){
        shooting = true;
    }

    public void isNotShooting(){
        shooting = false;
    }

    public boolean isImmune() {
        return this.immunity;
    }
    public void setImmunity(boolean immunity) { this.immunity = immunity; }
    public int getImmunityTime() {
        return immunityTime;
    }

    private void immunityBlinkAnimation() {
        Timer blinkTimer = new Timer();
        blinkTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                blinkCounter++;           
                switch(blinkCounter) {
                    case 5:
                        getImageView().setImage(new Image("assets/image/player/playerShip3_red.png"));
                        break;
                    case 10:
                        getImageView().setImage(new Image("assets/image/player/playerShip4_red.png"));
                        break;
                    case 15:
                        getImageView().setImage(new Image("assets/image/player/playerShip3_red.png"));
                        break;
                    case 20:
                        getImageView().setImage(new Image("assets/image/player/playerShip2_red.png"));
                        blinkCounter = 0;
                        break;
                }
                if (!immunity) {
                    this.cancel();
                    getImageView().setImage(new Image("assets/image/player/playerShip2_red.png"));
                }
            }
        }, 0, 20);
    }

    public void setShield() {
        if (!hasShield()) {
            this.width = getWidth() + 10;
        }
        this.hasShield = true;
        shield = new Shield(this.getX(), this.getY(), true);
    }
    
    public void removeShield() {
        this.width = getWidth() - 10;
        shield.newSprite(Sprite.CLEAR);
        this.hasShield = false;
    }
    
    public boolean hasShield() {
        return this.hasShield;
    }
    
    public Image getShieldSprite() {
        return shield.getImage();
    }
    
    public int getShieldCharges() {
        return shield.getCharges();
    }
    
    public Shield shield() {
        return this.shield;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
}
