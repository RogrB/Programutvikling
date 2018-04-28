package model.weapons;

import java.util.ArrayList;
import java.util.Iterator;

import model.enemy.Enemy;

import static controller.GameController.gs;

public class HeatSeeking extends Basic {
    
    ArrayList<Enemy> enemies = gs.enemies;
    private boolean locked = false;
    Enemy target;

    public HeatSeeking(int x, int y, Weapon weapon) {
        super(
        x,
        y,
        weapon.PLAYER_HEATSEEKING);
    }  

    @Override
    public void update(int x, int y, Iterator i) {

        setOldX(getX());
        setOldY(getY());

        if (enemies.isEmpty()) {
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
        if(!enemies.isEmpty()) {
            for (Enemy enemy : enemies) {
                if (enemy.getX() < closestX && enemy.getX() > 20) {
                    closestX = enemy.getX();
                    targetIndex++;
                }
            }
        }
        target = enemies.get(targetIndex-1);
        locked = true;
    }
    
    public void moveMissile() {
        if (target != null && target.isAlive()) {
            if (Math.abs(target.getX() - getX()) < 7) {
                setX((getX() < target.getX()) ? getX()+1 : getX()-1);
            }
            else {
                setX((getX() < target.getX()) ? getX()+7 : getX()-7);
            }
            if (Math.abs(target.getY() - getY()) < 7) {
                setY((getY() < target.getY()) ? getY()+1 : getY()-1);
            }
            else {
                setY((getY() < target.getY()) ? getY()+7 : getY()-7);
            }
        }
        else {
            setX(getX()+7);
        }
    }
}
