package model.player;

import assets.java.Audio;
import assets.java.Sprite;
import model.Entity;
import model.weapons.Bullet;
import model.weapons.Weapon;
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
    private int immunityTime = 3000;
    private int immunityTimer;
    private int blinkCounter;

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
            bullets.add(new Bullet(x + this.width - 10, y + (this.height / 2) - 8, weapon));
            bulletCount++;
            getShot().setVolume(0.25);
            getShot().play();
        }
    }

    private void updateBullets(){
        for (Bullet bullet : bullets) {
            bullet.setX(bullet.getX() + 20);
        }
    }

    @Override
    public void takeDamage() {
        // Metode for immunityframes etter damage
        this.immunity = true;
        super.takeDamage();
        System.out.println("Immunity starts");
        immunityBlink();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                immunityTimer++;
                if (immunityTimer == 10) { // 3 sekunder med immunity før man kan ta damage igjen
                    immunityTimer = 0;
                    immunity = false;
                    System.out.println("Immunity ends");
                    this.cancel();
                }
            }
        }, 0, 200);
        System.out.println("Health is now " + this.health);
    }

    public boolean isImmune() {
        return this.immunity;
    }
    public void setImmunity(boolean immunity) { this.immunity = immunity; }
    public int getImmunityTime() {
        return immunityTime;
    }

    public void immunityBlink() {
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
                if (immunityTimer == 9) {
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
