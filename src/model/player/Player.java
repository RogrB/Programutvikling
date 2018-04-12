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

    // State
    private boolean immunity = false;
    private int immunityTime = 2000;
    private int blinkCounter;
    private String weaponType = "Bullet";

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
        getSprite().relocate(x, y);
        weapon = Weapon.PLAYER_BASIC;
    }

    @Override
    public void update(){
        if(!playerIsOutOfBounds()){
            this.y = this.y + move.next();
            getSprite().relocate(x, y);
        }
        updateBullets();
    }

    private boolean playerIsOutOfBounds(){
        if(y + move.next() < 0) {
            move.moveStop();
            return true;
        }
        if(y + this.height + move.next() >= GameView.GAME_HEIGHT) {
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
                    bullets.add(new Bullet(x + this.width - 10, y + (this.height / 2) - 8, weapon));
                    break;
                case "Upgrade1":
                    bullets.add(new Upgrade1(x + this.width - 10, y + (this.height / 2) - 8, weapon));
                    break;
                case "Upgrade2":
                    bullets.add(new Upgrade2(x + this.width - 10, y + (this.height / 2) - 8, weapon));
                    break;        
                case "HeatSeeking":
                    bullets.add(new HeatSeeking(x + this.width - 10, y + (this.height / 2) - 8, weapon));
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
        if(!isImmune()){
            super.takeDamage();
            if (isAlive()) {
                initImmunity();
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
                    this.cancel();
                    inst.getSprite().setImage(new Image("assets/image/playerShip2_red.png"));
                }
            }
        }, 0, 20);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
