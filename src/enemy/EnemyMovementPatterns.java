package enemy;

import static main.java.GameLogic.SPEED_MODIFIER;

public enum EnemyMovementPatterns {
    LEFT,   LEFT_PULL,
    SIN,    SIN_REV,
    COS,    COS_REV,
    TRI,    TRI_REV,
    CLOCK,  CLOCK_REV;

    private double modDepth = 50;
    private double modLength = 1;
    private double modSpeed = 20;

    public int framesAlive = 0;

    public double x, y;

    private boolean triState;

    EnemyMovementPatterns() {

        x = 0;
        y = 0;

        if(this.name() == "TRI"){
            triState = true;
        }
    }

    private double nextY(){

        switch(this.name()){
            case "LEFT":
            case "LEFT_PULL":
                break;

            case "SIN":
                return
                        Math.sin(rads(framesAlive/modLength)) * modDepth;
            case "SIN_REV":
                return Math.sin(rads(framesAlive/modLength)) * modDepth * -1;

            case "COS":
                return Math.cos(rads(framesAlive/modLength)) * modDepth;
            case "COS_REV":
                return Math.cos(rads(framesAlive/modLength)) * modDepth * -1;

            case "TRI_REV":
                triState = false;
            case "TRI":
                if(y >= modDepth)
                    triState = false;

                else if(y <= modDepth * -1)
                    triState = true;

                if(triState)
                    return y+1;
                return y-1;

            case "CLOCK":
                return Math.cos(rads(framesAlive/modLength)) * modDepth * -1;
            case "CLOCK_REV":
                return Math.cos(rads(framesAlive/modLength)) * modDepth;
        }

        return 0;
    }

    private double nextX(){

        switch(this.name()){
            case "LEFT":
            case "SIN":
            case "SIN_REV":
            case "COS":
            case "COS_REV":
            case "TRI":
            case "TRI_REV":
                return framesAlive * SPEED_MODIFIER;
            case "CLOCK":
            case "CLOCK_REV":
                return Math.sin(rads(framesAlive/modLength)) * modDepth;
            case "LEFT_PULL":
                return ((Math.sin(rads(framesAlive/modLength)) * modDepth) + 1) / 2;

        }

        return 0;
    }

    public void nextFrame(){
        framesAlive--;
        x = nextX();
        y = nextY();

    }

    private double rads(double i){
        return Math.toRadians(i);
    }

}
