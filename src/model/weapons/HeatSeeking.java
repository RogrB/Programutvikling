
package model.weapons;

import controller.GameController;

import java.util.ArrayList;
import model.enemy.Enemy;

public class HeatSeeking extends Bullet {
    
    GameController gc = GameController.getInstance();
    ArrayList<Enemy> enemies = gc.getEnemies();
    private boolean locked = false;
    Enemy target;

    public HeatSeeking(int x, int y, Weapon weapon) {
        super(
        x,
        y,
        weapon.PLAYER_HEATSEEKING);
    }  
    
    @Override
    public void move() {

        setOldX(getX());
        setOldY(getY());

        if (enemies.isEmpty()) {
            //x += 15;
            setX(getX() + 15);
        }
        else {
            if (!locked) {
                setLock();
            }
            moveMissile();
        }
    }
    
    public void setLock() {
        // Finner nærmeste enemy i X aksen og låser inn target
        int closestX = 2500;
        int targetIndex = 0;
        for (Enemy enemy : enemies) {
            if (enemy.getX() < closestX && enemy.getX() > 20) {
                closestX = enemy.getX();
                targetIndex++;
            }
        }
        target = enemies.get(targetIndex-1);
        locked = true;
    }
    
    public void moveMissile() {
        if (target != null) {
            setX((getX() < target.getX()) ? getX()+7 : getX()-7);
            if (Math.abs(target.getY() - getY()) < 7) {
                setY((getY() < target.getY()) ? getY()+1 : getY()-1);
            }
            else {
                setY((getY() < target.getY()) ? getY()+7 : getY()-7);
            }
        }
    }
}
