package model.enemy;

import static java.lang.Math.*;

public class EnemyMovementPattern {

    /*
    * LEFT,       LEFT_PULSATING,
    * SIN,        SIN_REVERSED,
    * COS,        COS_REVERSED,
    * TRI,        TRI_REVERSED,
    *
    * MADNESS_01, MADNESS_02, MADNESS_03,
    * BOSS_LINE,  BOSS_EIGHT, BOSS_OVAL;
    * */

    private String name;

    private double x, y;
    private double movementSpeed;
    private int framesAlive;
    private double modDepth, modSpeed;

    // Triangle functionality
    private boolean triState = false;
    private int triCount;

    // Boss functionality
    private boolean bossInitializing = true;
    private int bossCounter;
    private final int BOSS_INIT_TIME = 250;

    public EnemyMovementPattern(String name){

        this.name = name;

        movementSpeed = 1;  // Hastigheten til fienden.
        modDepth = 1;       // Modulasjonsdybden til oscillatoren
        modSpeed = 2.5;     // Frekvensen til oscillatoren

        if(name == "TRI")
            triState = true;
    }

    private void nextX(){
        switch(name){
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
                x -= cos(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied() + (movementSpeed * 2);
                break;

            case "BOSS_LINE":
                bossInit();
                break;

            case "BOSS_EIGHT":
                bossInit();
                if(!bossInitializing)
                    x -= cos(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied();
                break;

            case "BOSS_OVAL":
                bossInit();
                if(!bossInitializing)
                    x -= sin(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied();
                break;

            case "MADNESS_01":
            case "MADNESS_03":
                x -= cos(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied() + (movementSpeed * 2);
                break;

            case "MADNESS_02":
                x -= cos(rads(framesAlive * modSpeed * movementSpeed * 2)) * getModifiersMultiplied() + (movementSpeed * 2);
                break;

        }
    }

    private void nextY(){
        switch(name) {
            case "LEFT":
            case "LEFT_PULSATING":
                break;

            case "SIN":
                y -= sin(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied();
                break;

            case "SIN_REVERSED":
                y += sin(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied();
                break;

            case "COS":
                y -= cos(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied();
                break;

            case "COS_REVERSED":
                y += cos(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied();
                break;

            case "BOSS_EIGHT":
                if (!bossInitializing)
                    y -= cos(rads(framesAlive * modSpeed * movementSpeed / 2)) * getModifiersMultiplied();
                break;

            case "BOSS_OVAL":
                if (!bossInitializing)
                    y -= cos(rads(framesAlive * modSpeed * movementSpeed)) * getModifiersMultiplied() * 2;
                break;

            case "BOSS_LINE":
                if (bossInitializing)
                    break;

            case "TRI":
            case "TRI_REVERSED":
                if (triState)
                    y -= getModifiersMultiplied();
                else
                    y += getModifiersMultiplied();

                triCount++;

                if (triCount > 60 * modDepth / modSpeed / movementSpeed) {
                    triState = !triState;
                    triCount = 0;
                }
                break;

            case "MADNESS_01":
            case "MADNESS_02":
                y -= cos(rads(framesAlive * modSpeed * movementSpeed / 2)) * getModifiersMultiplied();
                break;

            case "MADNESS_03":
                y -= cos(rads(framesAlive * modSpeed * movementSpeed / 2)) * getModifiersMultiplied();
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

    public void setModDepth(double modDepth) {
        this.modDepth = modDepth;
    }

    public void setModSpeed(double modSpeed) {
        this.modSpeed = modSpeed;
    }

    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    private void bossInit(){
        if(bossCounter < BOSS_INIT_TIME) {
            framesAlive = 0;
            x--;
            bossCounter++;
            modDepth = 2;
        } else
            bossInitializing = false;
    }

    private double rads(double i){
        return toRadians(i);
    }

    private double getModifiersMultiplied(){
        return  modDepth * modSpeed * movementSpeed;
    }

}
