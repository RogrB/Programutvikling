
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
        if (enemies.isEmpty()) {
            x += 10;
        }
        else {
            if (!locked) {
                setLock();
            }
            moveMissile();
        }
    }
    
    public void setLock() {
        int closestX = 2500;
        int closestY = 0;
        int targetIndex = 0;
        for (Enemy enemy : enemies) {
            if (enemy.getX() < closestX && enemy.getX() > 20) {
                closestX = enemy.getX();
                closestY = enemy.getY();
                targetIndex++;
            }
        }
        System.out.println("Locked on " + closestX + " , " + closestY);  // Printout
        target = enemies.get(targetIndex-1);
        locked = true;
        System.out.println(target);  // Printout
    }
    
    public void moveMissile() {
        if (target != null) {
            x = (x < target.getX()) ? x+7 : y-7;
            y = (y < target.getY()) ? y+7 : y-7;     
        }
    }
    
}
