package model.enemy;

import assets.java.SoundManager;

import java.util.Objects;

import static java.lang.Math.*;

/**
 * <h1>Implementation of an enemies movement</h1>
 * This class calculates an enemies movement across the screen by
 * implementing a simple LFO (low frequency oscillator)
 * functionality. The different types of LFOs are defined by their
 * names (strings) whom are passed through the constructor. The
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
    private double modDepth, modWavelength;

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
        modWavelength = 2.5;// Frekvensen til oscillatoren

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
                x -= cos(toRadians(framesAlive * modWavelength * movementSpeed)) * getModifiersMultiplied() + (movementSpeed * 2);
                break;

            case "BOSS_LINE":
                bossInit();
                break;

            case "BOSS_EIGHT":
                bossInit();
                if(!bossInitializing)
                    x -= cos(toRadians(framesAlive * modWavelength * movementSpeed)) * getModifiersMultiplied();
                break;

            case "BOSS_OVAL":
                bossInit();
                if(!bossInitializing)
                    x -= sin(toRadians(framesAlive * modWavelength * movementSpeed)) * getModifiersMultiplied();
                break;

            case "MADNESS_01":
            case "MADNESS_03":
                x -= cos(toRadians(framesAlive * modWavelength * movementSpeed)) * getModifiersMultiplied() + (movementSpeed * 2);
                break;

            case "MADNESS_02":
                x -= cos(toRadians(framesAlive * modWavelength * movementSpeed * 2)) * getModifiersMultiplied() + (movementSpeed * 2);
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
                y -= sin(toRadians(framesAlive * modWavelength * movementSpeed)) * getModifiersMultiplied();
                break;

            case "SIN_REVERSED":
                y += sin(toRadians(framesAlive * modWavelength * movementSpeed)) * getModifiersMultiplied();
                break;

            case "COS":
                y -= cos(toRadians(framesAlive * modWavelength * movementSpeed)) * getModifiersMultiplied();
                break;

            case "COS_REVERSED":
                y += cos(toRadians(framesAlive * modWavelength * movementSpeed)) * getModifiersMultiplied();
                break;

            case "BOSS_EIGHT":
                if (!bossInitializing)
                    y -= cos(toRadians(framesAlive * modWavelength * movementSpeed / 2)) * getModifiersMultiplied();
                break;

            case "BOSS_OVAL":
                if (!bossInitializing)
                    y -= cos(toRadians(framesAlive * modWavelength * movementSpeed)) * getModifiersMultiplied() * 2;
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

                if (triCount > 60 * modDepth / modWavelength / movementSpeed) {
                    triState = !triState;
                    triCount = 0;
                }
                break;

            case "MADNESS_01":
            case "MADNESS_02":
                y -= cos(toRadians(framesAlive * modWavelength * movementSpeed / 2)) * getModifiersMultiplied();
                break;

            case "MADNESS_03":
                y -= cos(toRadians(framesAlive * modWavelength * movementSpeed / 2)) * getModifiersMultiplied();
                break;
            default:
                break;

        }
    }

    /**
     * Updates the object's frame counter and initiates
     * calculations for this frames X and Y position.
     */
    public void updatePosition(){
        framesAlive++;
        nextX();
        nextY();
    }

    /**
     * Defines the start position for this object.
     * <b>Note: </b>can only be defined if X and Y is not already set.
     * @param x X start position of the object.
     * @param y Y start position of the object.
     */
    public void setStartPosition(int x, int y){
        if(this.x == 0 && this.y == 0){
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Returns the object's position on the X axis.
     * @return The object's position on the X axis.
     */
    public int getX() {
        return (int)x;
    }

    /**
     * Returns the object's position on the Y axis.
     * @return The object's position on the Y axis.
     */
    public int getY() {
        return (int)y;
    }

    /**
     * Sets the LFO modulation depth. Allows for customization of movement patterns.
     * <p>{@code std value = 1.0}
     * @param modDepth The new modulation depth value.
     */
    public void setModDepth(double modDepth) {
        this.modDepth = modDepth;
    }

    /**
     * Sets the LFO modulation wavelength. Allows for customization of movement patterns.
     * * <p>{@code std value = 2.5}
     * @param modWavelength The new modulation wavelength value.
     */
    public void setModWavelength(double modWavelength) {
        this.modWavelength = modWavelength;
    }

    /**
     * Defines the speed of which the object traverses through the movement pattern.
     * * <p>{@code std value = 1.0}
     * @param movementSpeed The new movement speed value.
     */
    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    /**
     * Custom functionality for bosses. Allows them to move forwards
     * onto the game board before they start looping.
     */
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

    /**
     * Returns the multiplication of all modifiers for simple access to these.
     * <p><b>Note: </b>the modifiers are: {@code modDepth, modWavelength, movementSpeed}.
     * @return The multiplication of all modifiers for simple access to these.
     */
    private double getModifiersMultiplied(){
        return  modDepth * modWavelength * movementSpeed;
    }

}
