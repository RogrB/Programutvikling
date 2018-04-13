package model.player;

import assets.java.Audio;
import assets.java.Sprite;
import model.Entity;
import model.weapons.*;
import view.GameView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.image.Image;

public class Player extends Entity {

    // Singleton
    private static Player inst = new Player();
    public static Player getInst(){ return inst; }
    
    GameView gv = GameView.getInstance();

    // State
    private boolean immunity = false;
    private int immunityTime = 2000;
    private int blinkCounter;
    private String weaponType = "Bullet";
    private boolean shield = false;
    private Sprite shieldSprite = Sprite.SHIELD1;
    private int shieldHealth = 2;

    private ArrayList<Bullet> bullets = new ArrayList<>();
    private PlayerMovement move = new PlayerMovement();

    private Player(){
        super(
                Sprite.PLAYER,
                40,
                GameView.GAME_HEIGHT / 2 - (int) Sprite.PLAYER.getHeight() / 2,
                5
        );

        setCanShoot(true);
        shot = Audio.PLAYER_SHOT;
        getSprite().getImageView().relocate(getX(), getY());
        weapon = Weapon.PLAYER_BASIC;
    }

    @Override
    public void update(){
        if(!playerIsOutOfBounds()){
            //this.y = this.y + move.next();
            setY(getY() + move.next());
            getSprite().getImageView().relocate(getX(), getY());
        }
        updateBullets();
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
            shotTimer();
        }
    }
    
    public void shotTimer() {
        // Metode for å begrense hvor ofte man kan skyte
        Timer shotTimer = new Timer();
        shotTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                setCanShoot(true);
                this.cancel();
                
            }
        }, bullets.get(bullets.size()-1).getFireRate()); 
    }

    private void updateBullets(){
        for (Bullet bullet : bullets) {
            bullet.move();
        }
    }

    @Override
    public void takeDamage(){
        if (!shield) {
            if(!isImmune()){
                super.takeDamage();
                if (isAlive()) {
                    initImmunity();
                }
            }
        }
        else {
            if (getShieldHealth() > 1) {
                setShieldHealth(getShieldHealth()-1);
            }
            else {
                removeShield();
            }
        }
    }

    private void initImmunity(){
        setImmunity(true);
        immunityBlink();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                setImmunity(false);
            }
        }, getImmunityTime());

    }

    public boolean isImmune() {
        return this.immunity;
    }
    public void setImmunity(boolean immunity) { this.immunity = immunity; }
    public int getImmunityTime() {
        return immunityTime;
    }

    private void immunityBlink() {
        // Blinkeanimasjon for immunityframes
        System.out.println("Immunity start");
        Timer blinkTimer = new Timer();
        blinkTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                blinkCounter++;           
                switch(blinkCounter) {
                    case 5:
                        inst.getSprite().setImage(new Image("assets/image/playerShip3_red.png"));
                        break;
                    case 10:
                        inst.getSprite().setImage(new Image("assets/image/playerShip4_red.png"));
                        break;
                    case 15:
                        inst.getSprite().setImage(new Image("assets/image/playerShip3_red.png"));
                        break;
                    case 20:
                        inst.getSprite().setImage(new Image("assets/image/playerShip2_red.png"));
                        blinkCounter = 0;
                        break;
                }
                if (!immunity) {
                    System.out.println("immunity end");
                    this.cancel();
                    inst.getSprite().setImage(new Image("assets/image/playerShip2_red.png"));
                }
            }
        }, 0, 20);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
    
    public void setShield() {
        this.width = (int) sprite.getWidth() + 10;
        this.shield = true;
        this.shieldSprite = Sprite.SHIELD1;
    }
    
    public void removeShield() {
        this.width = (int) sprite.getWidth();        
        this.shield = false;
        this.shieldSprite = Sprite.CLEAR;
    }
    
    public Sprite getShieldSprite() {
        return this.shieldSprite;
    }
    
    public void setShieldSprite(Sprite sprite) {
        this.shieldSprite = sprite;
    }
    
    public boolean hasShield() {
        return this.shield;
    }
    
    public void setShieldHealth(int shield) {
        this.shieldHealth = shield;
    }
    
    public int getShieldHealth() {
        return this.shieldHealth;
    }
}
