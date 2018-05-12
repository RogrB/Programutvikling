package model.enemy;

import assets.java.SoundManager;

import java.util.Objects;

import static java.lang.Math.*;

/**
 * <h1>Implementation of an enemies movement</h1>
 * This class calculates and implements an enemies movement across
 * the screen by implementing it's own simple LFO (low frequency
 * oscillator) functionality. The different LFOs are defined by
 * names (strings) which are passed through the constructor. The
 * available options are:
 *
 * {@code
 * LEFT,       LEFT_PULSATING
 * SIN,        SIN_REVERSED
 * COS,        COS_REVERSED
 * TRI,        TRI_REVERSED
 *
 * MADNESS_01, MADNESS_02, MADNESS_03
 * BOSS_LINE,  BOSS_EIGHT, BOSS_OVAL
 * }.
 *
 * All enemies has an {@code EnemyMovementPattern}.
 *
 * @author Åsmund Røst Wien
 * @see Enemy
 */
public class EnemyMovementPattern implements java.io.Serializable {

    /**
     * The name which defines this objects pattern behaviour.
     */
    private String name;

    /**
     * The current X and Y position of the {@code Enemy} who has this pattern.
     * Used to calculate the next X and Y position by the LFO.
     */
    private double x, y;

    /**
     * The movement speed for this {@code Enemy}.
     */
    private double movementSpeed;

    /**
     * How many frames this {@code Enemy} has existed.
     * Used to calculate the forward angle of the LFO.
     */
    private int framesAlive;

    /**
     * Attributes to manipulate the LFOs wavelength and modulation depth.
     */
    private double modDepth, modSpeed;

    /**
     * <b>Triangle functionality: </b>Custom attribute used specifically
     * for implementation of the triangle movement patterns.
     * <p>
     * {@code triState} defines the current direction of the triangle.
     */
    private boolean triState = false;

    /**
     * <b>Triangle functionality: </b>Custom attribute used specifically
     * for implementation of the triangle movement patterns.
     * <p>
     * {@code triCount} defines the modulation depth of the triangle pattern.
     */
    private int triCount;

    /**
     * <b>Boss functionality: </b>Custom attribute for calculating the
     * boss position as it spawns.
     * <p>
     * {@code bossInitializing} is set for the duration of the spawn movement.
     */
    private boolean bossInitializing = true;

    /**
     * <b>Boss functionality: </b>Custom attribute for calculating the
     * boss position as it spawns.
     * <p>
     * {@code bossInitializing} timer for definition of how long the
     * boss should spawn before the regular pattern kicks in.
     */
    private int bossCounter;

    /**
     * <b>Constructor: </b> used to define attributes for the LFO to be set.
     * @param name Takes a name {@code String} as an input parameter.
     */
    public EnemyMovementPattern(String name){

        this.name = name;

        movementSpeed = 1;  // Hastigheten til fienden.
        modDepth = 1;       // Modulasjonsdybden til oscillatoren
        modSpeed = 2.5;     // Frekvensen til oscillatoren

        if(Objects.equals(name, "TRI"))
            triState = true;
    }

    /**
     * Calculates the objects next position across the LFOs X axis.
     */
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
            default:
                x -= movementSpeed * 2;
                break;

        }
    }

    /**
     * Calculates the objects next position across the LFOs Y axis.
     */
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
            default:
                break;

        }
    }

    /**
     * 
     */
    void updatePosition(){
        framesAlive++;
        nextX();
        nextY();
    }

    void setStartPosition(int x, int y){
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
        int BOSS_INIT_TIME = 250;
        if(bossCounter < BOSS_INIT_TIME) {

            if(bossCounter == 0)
                SoundManager.getInst().bossWobble();
            framesAlive = 0;
            x--;
            bossCounter++;
            modDepth = 2;
        } else {
            bossInitializing = false;
        }
    }

    private double rads(double i){
        return toRadians(i);
    }

    private double getModifiersMultiplied(){
        return  modDepth * modSpeed * movementSpeed;
    }

}
