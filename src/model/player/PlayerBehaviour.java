package model.player;

import model.weapons.*;

import static controller.GameController.gs;

public class PlayerBehaviour implements java.io.Serializable {

    private static final long serialVersionUID = 185835451720483588L;

    private int speed;
    private int dir;

    public PlayerBehaviour() {
        speed = 0;
        dir = 0;
    }


    void move(int direction){
        if(this.dir != direction) {
            dir = direction;
        }
    }

    void moveStop(){
        speed = 0;
    }

    int next(){
        int MOD_SPEED = 1;
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
        int MAX_SPEED = 20;
        return Math.abs(speed) > MAX_SPEED;
    }
    
    void shoot(String weapontype, int x, int y, int width, int height, Weapon weapon) {
            switch(weapontype) {
                case "Basic":
                    gs.playerBullets.add(new Basic(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "SpeedBullets":
                    gs.playerBullets.add(new SpeedBullets(x + width - 10, y + (height / 2) - 8, weapon));
                    break;
                case "DamageBullets":
                    gs.playerBullets.add(new DamageBullets(x + width - 10, y + (height / 2) - 8, weapon));
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
                case "TripleBurst":
                    gs.playerBullets.add(new TripleBurst(x + width - 10, y + (height / 2) - 25, weapon, 1));
                    gs.playerBullets.add(new TripleBurst(x + width - 10, y + (height / 2), weapon, 2));
                    gs.playerBullets.add(new TripleBurst(x + width - 10, y + (height / 2) + 15, weapon, 3));
                    break;                    
            }
    }
    
    public String powerUp(String weaponType) {
        String returnString = "";
        switch(weaponType) {
            case "Reset":
                returnString = "Basic";
                break;
            case "Basic":
                returnString = "SpeedBullets";
                break;
            case "SpeedBullets":
                returnString = "DamageBullets";
                break;
            case "DamageBullets":
                returnString = "HeatSeeking";
                break;
            case "HeatSeeking":
                returnString = "Doubles";
                break;
            case "Doubles":
                returnString = "DoubleSwirl";
                break;
            case "DoubleSwirl":
                returnString = "TripleBurst";
                break;
            case "TripleBurst":
                returnString = "TripleBurst";
                break;                
        }
        return returnString;
    }
    
}
