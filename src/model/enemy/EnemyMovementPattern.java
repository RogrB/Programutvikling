package model.enemy;

import static model.GameModel.SPEED_MODIFIER;

public enum EnemyMovementPattern {

    LEFT,   LEFT_PULSATING,
    SIN,    SIN_REVERSED,
    COS,    COS_REVERSED,
    TRI,    TRI_REVERSED;

    private double x, y;
    private double movementSpeed;
    private int framesAlive;
    private double modDepth, modSpeed;

    private boolean triState;
    private int triCount;

    EnemyMovementPattern(){
        movementSpeed = 1;  // Hastigheten til fienden.
        modDepth = 2;       // Modulasjonsdybden til oscillatoren
        modSpeed = 2;       // Frekvensen til oscillatoren

        if(name() == "TRI")
            triState = true;
        else if (name() == "TRI_REVERSED")
            triState = false;
    }

    private void nextX(){
        switch(this.name()){
            case "LEFT":
            case "SIN":
            case "SIN_REVERSED":
            case "COS":
            case "COS_REVERSED":
            case "TRI":
            case "TRI_REVERSED":
                x -= movementSpeed * 2;
                break;
            case "LEFT_PULSATING":
                x -= Math.cos(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied() + (movementSpeed * 2);
        }
    }

    private void nextY(){
        switch(this.name()){
            case "LEFT":
            case "LEFT_PULSATING":
                break;
            case "SIN":
                y -= Math.sin(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied();
                break;
            case "SIN_REVERSED":
                y += Math.sin(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied();
                break;
            case "COS":
                y -= Math.cos(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied();
                break;
            case "COS_REVERSED":
                y += Math.cos(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied();
                break;
            case "TRI":
            case "TRI_REVERSED":

                if(triState)
                    y -= getModifiersMultiplied();
                else
                    y += getModifiersMultiplied();

                triCount++;

                if(triCount > 60 * modDepth / modSpeed / movementSpeed) {
                    triState = !triState;
                    triCount = 0;
                }
                System.out.println(triCount);
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

    public void pushX(int x){
        this.x += x;
    }

    public void pushY(int y){
        this.y += y;
    }

    private double rads(double i){
        return Math.toRadians(i);
    }

    private double getModifiersMultiplied(){
        return  modDepth * modSpeed * movementSpeed;
    }

}
