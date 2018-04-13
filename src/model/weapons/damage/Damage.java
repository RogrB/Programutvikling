package model.weapons.damage;

import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.image.Image;
import model.Existance;

public class Damage extends Existance {
    // Klasse for damage animasjon
    private int teller;
    boolean finished = false;
    
    private Image sprite = new Image("assets/image/damage/laserBlue08.png");
    
    public Damage(int x, int y) {
        setX(x);
        setY(y);
        width = (int)sprite.getWidth();
        height = (int)sprite.getHeight();
        this.display();
    }

    /*public Image getSprite() {
        return this.sprite;
    }*/

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
                        clearImage();
                        this.cancel();
                        break;
                }
            }
        }, 0, 10);
    }
    
    public boolean getFinished() {
        return this.finished;
    }
    
    public void clearImage() {
        this.sprite = new Image("assets/image/damage/clear.png");
    }
    
    
}
