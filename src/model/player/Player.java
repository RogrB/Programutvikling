package model.player;

import assets.java.SoundManager;
import assets.java.Sprite;
import model.Entity;
import model.PowerUp;
import model.weapons.*;
import model.Shield;

import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.image.Image;
import model.GameModel;
import multiplayer.MultiplayerHandler;
import view.GameView;
import view.ViewUtil;
import view.HUD;

public class Player extends Entity {

    // Singleton
    private static Player inst = new Player();
    public static Player getInst(){ return inst; }

    // State
    private boolean immunity = false;
    private boolean playing = false;
    private int blinkCounter;
    private String weaponType = "Bullet";
    private boolean hasShield = false;
    private boolean shooting= false;
    private Shield shield = new Shield(getX(), getY(), hasShield());
    private int score;
    private int enemiesKilled;
    private int bulletsHit;
    private int bulletCount;

    private PlayerBehaviour playerBehaviour = new PlayerBehaviour();

    private Player(){
        super(
                Sprite.PLAYER,
                40,
                ViewUtil.VIEW_HEIGHT / 2 - (int) new Image(Sprite.PLAYER.src).getHeight() / 2,
                5
        );

        setCanShoot(true);
        //getImageView().relocate(getX(), getY());
        weapon = Weapon.PLAYER_BASIC;
    }
    
    public void init() {
        inst = new Player();
        if (hasShield) {
            removeShield();
        }
        setHealth(5);
        setY(ViewUtil.VIEW_HEIGHT / 2 - (int) new Image(Sprite.PLAYER.src).getHeight() / 2);
        setAlive();
        immunity = false;
        shooting = false;
        score = 0;
        this.weaponType = playerBehaviour.powerUp("Reset");
        enemiesKilled = 0;
        bulletsHit = 0;
        bulletCount = 0;
        canShoot = true;
    }

    public void update(){
        if(!playerIsOutOfBounds()){
            setY(getY() + playerBehaviour.next());
            GameView.getInstance().render(this);
        }
        if(shield.isBroken() && hasShield()) {
            removeShield();
        }
        if(shooting){
            shoot();
        }
    }

    private boolean playerIsOutOfBounds(){
        if(getY() + playerBehaviour.next() < 10) {
            playerBehaviour.moveStop();
            return true;
        }

        if(getY() + this.height + playerBehaviour.next() >= ViewUtil.VIEW_HEIGHT) {
            playerBehaviour.moveStop();
            return true;
        }
        return false;
    }

    public void powerUp(PowerUp powerUp) {
        switch(powerUp.getName()) {
            case "WEAPON_POWERUP":
                powerUp();
                HUD.getInstance().renderPowerUpText("Weapon Upgraded!");
                SoundManager.getInst().upgradeWeapon();
                break;
            case "HEALTH_POWERUP":
                setHealth(getHealth() + 1);
                HUD.getInstance().renderPowerUpText("Health up!");
                SoundManager.getInst().upgradeHealth();
                break;
            case "SHIELD_POWERUP":
                setShield();
                HUD.getInstance().renderPowerUpText("Shield!");
                SoundManager.getInst().upgradeShield();
                break;
        }
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
            SoundManager.getInst().shotPlayer();
            playerBehaviour.shoot(this.weaponType, getX(), getY(), this.width, this.height, weapon);
            setCanShoot(false);
            shotDelayTimer();
            bulletCount++;
            if (GameModel.getInstance().getMultiplayerStatus()) {
                GameModel.getInstance().getMP().send("Shoot", getX(), getY());
            }
        }
    }
    
    private void shotDelayTimer() {
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
                SoundManager.getInst().impactPlayer();
                super.takeDamage();
                if (isAlive()) {
                    initImmunity();
                }
            }
        }
        else {
            if (!shield.isImmune()) {
                shield.takeDamage();

                SoundManager.getInst().impactShield();
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

    private boolean isImmune() {
        return this.immunity;
    }
    private void setImmunity(boolean immunity) { this.immunity = immunity; }
    private int getImmunityTime() {
        return 2000;
    }

    private void immunityBlinkAnimation() {
        Timer blinkTimer = new Timer();
        blinkTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                blinkCounter++;           
                switch(blinkCounter) {
                    case 5:
                        newSprite(Sprite.PLAYER_BLINK1);
                        break;
                    case 10:
                        newSprite(Sprite.PLAYER_BLINK2);
                        break;
                    case 15:
                        newSprite(Sprite.PLAYER_BLINK1);
                        break;
                    case 20:
                        newSprite(Sprite.PLAYER);
                        blinkCounter = 0;
                        break;
                }
                if (!immunity) {
                    this.cancel();
                    newSprite(Sprite.PLAYER);
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
    
    private void removeShield() {
        this.width = getWidth() - 10;
        shield.newSprite(Sprite.CLEAR);
        this.hasShield = false;
    }

    public void isPlaying(){playing = true; }
    public void isNotPlaying() {playing = false; }
    public boolean getPlaying(){return playing;}
    
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
    
    public String getWeaponType() {
        return this.weaponType;
    }
    
    public int getEnemiesKilled() {
        return this.enemiesKilled;
    }
    
    public void setEnemiesKilled(int e) {
        this.enemiesKilled = e;
    }
    
    public int getBulletsHit() {
        return this.bulletsHit;
    }
    
    public void setBulletsHit(int hit) {
        this.bulletsHit = hit;
    }
    
    public int getBulletCount() {
        return this.bulletCount;
    }
    
}
