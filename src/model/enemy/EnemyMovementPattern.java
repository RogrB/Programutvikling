package model.enemy;

import static model.GameModel.SPEED_MODIFIER;

public enum EnemyMovementPattern {

    LEFT,
    SIN,    SIN_REVERSED,
    COS,    COS_REVERSED;

    private double x, y;
    private double movementSpeed;
    private int framesAlive;
    private double modDepth, modSpeed;

    EnemyMovementPattern(){
        movementSpeed = 2;  // Hastigheten til fienden.
        modDepth = 2;       // Modulasjonsdybden til oscillatoren
        modSpeed = 2;       // Frekvensen til oscillatoren
    }

    private void nextX(){
        switch(this.name()){
            case "LEFT":
            case "SIN":
            case "SIN_REVERSED":
            case "COS":
            case "COS_REVERSED":
                x -= movementSpeed;
                break;
        }
    }

    private void nextY(){
        switch(this.name()){
            case "LEFT":
                break;
            case "SIN":
                y -= Math.sin(rads(framesAlive * modSpeed * movementSpeed)) * modDepth * modSpeed * movementSpeed;
                break;
            case "SIN_REVERSED":
                y += Math.sin(rads(framesAlive * modSpeed * movementSpeed)) * modDepth * modSpeed * movementSpeed;
                break;
            case "COS":
                y -= Math.cos(rads(framesAlive * modSpeed * movementSpeed)) * modDepth * modSpeed * movementSpeed;
                break;
            case "COS_REVERSED":
                y += Math.cos(rads(framesAlive * modSpeed * movementSpeed)) * modDepth * modSpeed * movementSpeed;
                break;
        }
    }

    public void updatePosition(){
        framesAlive++;
        nextX();
        nextY();
    }

    public void setStartPosition(int x, int y){
        if(this.x == 0 && this.y == 0){
            this.x = x;
            this.y = y;
        }
    }

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    private double rads(double i){
        return Math.toRadians(i);
    }

}


/*public class EnemyMovementPattern {

    private double modDepth = 3;
    private double modLength = 1;
    private double modSpeed = 20;

    public double framesAlive = 0;

    public double x, y;
    public double patX, patY;
    public double origX, origY;

    public String name;

    private boolean triState;

    public EnemyMovementPattern(String name, double x, double y){
         this.name = name;
         this.x = x;
         this.y = y;
         this.patX = 0;
         this.patY = 0;
         this.origX = x;
         this.origY = y;

         if(name == "TRI"){
             triState = true;
         }
    }

    private double nextY(){
         switch(this.name){
             case "LEFT":
             case "LEFT_PULL":
                 break;

             case "SIN":
                 return Math.sin(rads(framesAlive/modLength)) * modDepth;
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
        switch(this.name){
             case "LEFT":
             case "SIN":
             case "SIN_REV":
             case "COS":
             case "COS_REV":
             case "TRI":
             case "TRI_REV":
                 return framesAlive;
             case "CLOCK":
             case "CLOCK_REV":
                 return Math.sin(rads(framesAlive/modLength)) * modDepth;
             case "LEFT_PULL":
                 return ((Math.sin(rads(framesAlive/modLength)) * modDepth) + 1) / 2;
         }
         return 0;
    }

    public void nextFrame(){
        framesAlive = framesAlive - (1 * SPEED_MODIFIER);
        patX = nextX();
        patY = nextY();

        x += patX;
        y += patY;
    }

    public void resetCoords(){
        x = origX;
        y = origY;
    }

    public void setStartPosision(double x, double y){
        this.x = x;
        this.y = y;
    }

    private double rads(double i){
        return Math.toRadians(i);
    }
}*/
