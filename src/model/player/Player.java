package model.player;

import assets.java.Audio;
import assets.java.Sprite;
import model.Entity;
import model.weapons.Bullet;
import javafx.scene.media.AudioClip;
import view.GameView;
import java.util.Timer;
import java.util.TimerTask;

import java.util.ArrayList;

public class Player extends Entity {

    // Singleton
    private static Player inst = new Player();
    public static Player getInst(){ return inst; }

    private int score;
    private boolean immunity = false;

    private PlayerMovement move = new PlayerMovement();
    private int teller;
    

    private Player(){
        super(
                Sprite.PLAYER,
                40,
                GameView.GAME_HEIGHT / 2 - (int) Sprite.PLAYER.getHeight() / 2,
                3
        );

        setCanShoot(true);
        shot = Audio.PLAYER_SHOT;
        getSprite().relocate(x, y);
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

    @Override
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
            bullets.add(new Bullet(x + this.width - 10, y + (this.height / 2) - 8));
            bulletCount++;
            getShot().setVolume(0.25);
            getShot().play();
        }
    }

    private void updateBullets(){
        for (Bullet bullet : bullets) {
            bullet.setX(bullet.getX() + 12);
        }
    }

    @Override
    public void takeDamage() {
        // Metode for immunityframes etter damage
        this.immunity = true;
        this.health--;
        System.out.println("Immunity starts");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                teller++;
                if (teller == 2) { // 3 sekunder med immunity før man kan ta damage igjen
                    teller = 0;
                    immunity = false;                    
                    System.out.println("Immunity ends");
                    this.cancel();
                }
            }
        }, 0, 1000);
        System.out.println("Health is now " + this.health);
    }

    public boolean getImmunity() {
        return this.immunity;
    }

    public void setImmunity(boolean immunity) {
        this.immunity = immunity;
    }
    
}
