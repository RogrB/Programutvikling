package player;

public class PlayerMovement {
    private final int MAX_SPEED = 20;
    private final int MOD_SPEED = 1;

    private int speed;
    private int dir;

    public PlayerMovement() {
        speed = 0;
        dir = 0;
    }

    public void moveUp(){
        dir = -1;
    }

    public void moveDown(){
        dir = 1;
    }

    public void moveSlow(){
        dir = 0;
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
}
