package model.weapons.damage;

import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.image.Image;

public class Damage {
    // Klasse for damage animasjon
    private int x;
    private int y;
    private double width;
    private double height;
    private int teller;
    boolean finished = false;
    
    private Image sprite = new Image("assets/image/damage/laserBlue08.png");
    
    public Damage(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.display();
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public Image getSprite() {
        return this.sprite;
    }
    
    public double getWidth() {
        return this.width;
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public void display() {
        // Viser damageAnimasjon på
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                teller++;
                switch(teller) {
                    case 5:
                        sprite = new Image("assets/image/damage/laserBlue09.png");
                        break;
                    case 10:
                        sprite = new Image("assets/image/damage/laserBlue10.png");
                        break;
                    case 15:
                        sprite = new Image("assets/image/damage/laserBlue11.png");
                        break;
                    case 20:
                        sprite = new Image("assets/image/damage/laserBlue10.png");
                        break;
                    case 25:
                        sprite = new Image("assets/image/damage/laserBlue09.png");
                        break;
                    case 30:
                        sprite = new Image("assets/image/damage/laserBlue08.png");
                        break;
                    case 35:
                        finished = true;
                        this.cancel();
                        break;
                }
            }
        }, 0, 35);
    }
    
    public boolean getFinished() {
        return this.finished;
    }
    
    public void clearImage() {
        this.sprite = new Image("assets/image/damage/clear.png");
    }
    
    
}
