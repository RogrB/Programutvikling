package model.player;

import java.util.ArrayList;
import model.GameModel;
import model.weapons.*;

public class PlayerBehaviour {
    private final int MAX_SPEED = 20;
    private final int MOD_SPEED = 1;
    
    //GameModel gm = GameModel.getInstance();
    
    private String weaponType = "Bullet";   
    private ArrayList<Bullet> bullets;

    private int speed;
    private int dir;

    public PlayerBehaviour() {
        speed = 0;
        dir = 0;
    }

    public void move(int direction){
        if(this.dir != direction) {
            dir = direction;
        }
    }

    public void moveStop(){
        speed = 0;
    }

    public int next(){
        if(!isMaxSpeed())
            speed += dir * MOD_SPEED;
        if(dir == 0){
            if(speed > 0)
                speed -= MOD_SPEED;
            if(speed < 0)
                speed += MOD_SPEED;
        }
        return speed;
    }

    private boolean isMaxSpeed(){
        if(Math.abs(speed) > MAX_SPEED)
            return true;
        return false;
    }
    
    public void shoot(String weapontype, int x, int y, int width, int height, Weapon weapon) {
            switch(weapontype) {
                case "Bullet":
                    bullets.add(new Bullet(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "Upgrade1":
                    bullets.add(new Upgrade1(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "Upgrade2":
                    bullets.add(new Upgrade2(x + width - 10, y + (height / 2) - 8, weapon));
                    break;        
                case "HeatSeeking":
                    bullets.add(new HeatSeeking(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "Doubles":
                    bullets.add(new Doubles(x + width - 10, y + (height / 2) - 25, weapon));
                    bullets.add(new Doubles(x + width - 10, y + (height / 2) + 15, weapon));
                    break;
                case "DoubleSwirl":
                    bullets.add(new DoubleSwirl(x + width, y + (height / 2) - 25, weapon, true));
                    bullets.add(new DoubleSwirl(x + width - 10, y + (height / 2) + 15, weapon, false));
                    break;
            }       
    }
    
    public String powerUp(String weaponType) {
        String returnString = "";
        switch(weaponType) {
            case "Bullet":
                returnString = "Upgrade1";
                break;
            case "Upgrade1":
                returnString = "Upgrade2";
                break;
            case "Upgrade2":
                returnString = "HeatSeeking";
                break;
            case "HeatSeeking":
                returnString = "Doubles";
                break;
            case "Doubles":
                returnString = "DoubleSwirl";
                break;
            case "DoubleSwirl":
                returnString = "DoubleSwirl";
                break;
        }
        return returnString;
    }
    
}
