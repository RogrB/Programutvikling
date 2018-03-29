package model.weapons;

import assets.java.Sprite;
import controller.GameController;
import javafx.scene.image.Image;
import view.GameView;

import java.util.Timer;
import java.util.TimerTask;

/*public class BulletHitAnimation {

    GameController gc = GameController.getInstance();
    GameView gv = GameView.getInstance();

    private final int ANIM_SPEED = 100;
    private final Sprite[] SPRITES;
    private final int TIME_DONE;

    public final int X, Y;
    public final double WIDTH, HEIGHT;

    private Image image;
    private boolean countingDown = false;
    private int counter = 0;

    public BulletHitAnimation(Bullet b){
        SPRITES = b.WEAPON.BULLET_HIT;
        TIME_DONE = ANIM_SPEED * (SPRITES.length + 1);
        X = b.getX();
        Y = b.getY();
        WIDTH = b.getWidth();
        HEIGHT = b.getHeight();

        start();
    }

    private void start(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(counter < SPRITES.length || countingDown) {
                    image = SPRITES[counter].getImg();
                    counter++;
                } else {
                    countingDown = true;
                    counter--;
                    image = SPRITES[counter].getImg();
                }
                if(counter <= 0 && countingDown){
                    timer.cancel();
                    timer.purge();
                    selfdistruct();
                }
                render();
            }
        }, 0, ANIM_SPEED);
    }

    private void render() {
        gv.renderBulletHits(this);
    }
    private void selfdistruct(){
        //gc.bulletHitAnimations.remove(this);
    }

    public Image getImage() {
        return image;
    }
}*/
