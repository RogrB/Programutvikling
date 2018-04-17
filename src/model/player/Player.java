package model.player;

import assets.java.Audio;
import assets.java.Sprite;
import model.Entity;
import model.weapons.*;
import view.GameView;
import model.Shield;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.image.Image;

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

    private ArrayList<Bullet> bullets = new ArrayList<>();
    private PlayerMovement move = new PlayerMovement();

    private Player(){
        super(
                Sprite.PLAYER,
                40,
                GameView.GAME_HEIGHT / 2 - (int) new Image(Sprite.PLAYER.src).getHeight() / 2,
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
        if(!playerIsOutOfBounds()){
            setY(getY() + move.next());
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
        if(getY() + move.next() < 0) {
            move.moveStop();
            return true;
        }
        if(getY() + this.height + move.next() >= GameView.GAME_HEIGHT) {
            move.moveStop();
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
        switch(getWeaponType()) {
            case "Bullet":
                setWeaponType("Upgrade1");
                break;
            case "Upgrade1":
                setWeaponType("Upgrade2");
                break;
            case "Upgrade2":
                setWeaponType("HeatSeeking");
                break;
            case "HeatSeeking":
                setWeaponType("Doubles");
                break;
            case "Doubles":
                setWeaponType("DoubleSwirl");
                break;
        }
    }
    
    public void move(String dir){
        switch(dir){
            case "UP":
                move.move(-1);
                break;
            case "DOWN":
                move.move(1);
                break;
            case "STOP":
                move.move(0);
        }
    }

    @Override
    public void shoot() {
        if(canShoot()) {
            switch(getWeaponType()) {
                case "Bullet":
                    bullets.add(new Bullet(getX() + this.width - 10, getY() + (this.height / 2) - 8, weapon));
                    break;
                case "Upgrade1":
                    bullets.add(new Upgrade1(getX() + this.width - 10, getY() + (this.height / 2) - 8, weapon));
                    break;
                case "Upgrade2":
                    bullets.add(new Upgrade2(getX() + this.width - 10, getY() + (this.height / 2) - 8, weapon));
                    break;        
                case "HeatSeeking":
                    bullets.add(new HeatSeeking(getX() + this.width - 10, getY() + (this.height / 2) - 8, weapon));
                    break;
                case "Doubles":
                    bullets.add(new Doubles(getX() + this.width - 10, getY() + (this.height / 2) - 25, weapon));
                    bullets.add(new Doubles(getX() + this.width - 10, getY() + (this.height / 2) + 15, weapon));
                    break;
                case "DoubleSwirl":
                    bullets.add(new DoubleSwirl(getX() + this.width, getY() + (this.height / 2) - 25, weapon, true));
                    bullets.add(new DoubleSwirl(getX() + this.width - 10, getY() + (this.height / 2) + 15, weapon, false));
                    break;
            }
            bulletCount++;
            getShot().setVolume(0.25);
            getShot().play();
            setCanShoot(false);
            shotDelayTimer();
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
        }, bullets.get(bullets.size()-1).getFireRate()); 
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

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
    
    public void setShield() {
        if (!hasShield()) {
            this.width = (int) getWidth() + 10;
        }
        this.hasShield = true;
        shield = new Shield(this.getX(), this.getY(), true);
    }
    
    public void removeShield() {
        this.width = (int) getWidth() - 10;
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
    
}
