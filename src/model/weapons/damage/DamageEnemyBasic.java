package model.weapons.damage;

import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.image.Image;

public class DamageEnemyBasic extends Damage {
    // Klasse for damage animasjon - EnemyBasic
    private double width;
    private double height;
    private int teller;
    
    private Image sprite = new Image("assets/image/damage/laserRed08.png");
    
    public DamageEnemyBasic(int x, int y) {
        super(x, y);
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.display();
    }
    
    @Override
    public Image getSprite() {
        return this.sprite;
    }
    
    @Override
    public double getWidth() {
        return this.width;
    }
    
    @Override
    public double getHeight() {
        return this.height;
    }
    
    @Override
    public void display() {
        // Viser damageAnimasjon på
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                teller++;
                switch(teller) {
                    case 5:
                        sprite = new Image("assets/image/damage/laserRed09.png");
                        break;
                    case 10:
                        sprite = new Image("assets/image/damage/laserRed10.png");
                        break;
                    case 15:
                        sprite = new Image("assets/image/damage/laserRed11.png");
                        break;
                    case 20:
                        sprite = new Image("assets/image/damage/laserRed10.png");
                        break;
                    case 25:
                        sprite = new Image("assets/image/damage/laserRed09.png");
                        break;
                    case 30:
                        sprite = new Image("assets/image/damage/laserRed08.png");
                        break;
                    case 35:
                        finished = true;
                        clearImage();
                        this.cancel();
                        break;
                }
            }
        }, 0, 10);
    }
    
    @Override
    public void clearImage() {
        this.sprite = new Image("assets/image/damage/clear.png");
    }
    
}
