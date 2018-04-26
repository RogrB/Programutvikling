package model.player;

import model.GameModel;
import model.GameState;
import model.weapons.*;

import static controller.GameController.gs;

public class PlayerBehaviour implements java.io.Serializable {

    private static final long serialVersionUID = 185835451720483588L;

    private final int MAX_SPEED = 20;
    private final int MOD_SPEED = 1;
    // MVC-access

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
                    gs.playerBullets.add(new Bullet(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "Upgrade1":
                    gs.playerBullets.add(new Upgrade1(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "Upgrade2":
                    gs.playerBullets.add(new Upgrade2(x + width - 10, y + (height / 2) - 8, weapon));
                    break;        
                case "HeatSeeking":
                    gs.playerBullets.add(new HeatSeeking(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "Doubles":
                    gs.playerBullets.add(new Doubles(x + width - 10, y + (height / 2) - 25, weapon));
                    gs.playerBullets.add(new Doubles(x + width - 10, y + (height / 2) + 15, weapon));
                    break;
                case "DoubleSwirl":
                    gs.playerBullets.add(new DoubleSwirl(x + width, y + (height / 2) - 25, weapon, true));
                    gs.playerBullets.add(new DoubleSwirl(x + width - 10, y + (height / 2) + 15, weapon, false));
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
