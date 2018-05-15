package model.enemy;

/**
 * <h1>Identification generator</h1>
 * This is a class to generate unique identification
 * numbers, specifically used for multiplayer purposes.
 *
 * @author Åsmund Røst Wien
 */
public class IdGen {

    /**
     * The singleton object.
     */
    private static IdGen inst = new IdGen();

    /**
     * Private constructor (singleton).
     */
    private IdGen(){}

    /**
     * Method to access singleton class.
     * @return Returns a reference to the singleton object.
     */
    public static IdGen getInstance(){ return inst; }

    /**
     * The field for counting through iterations of generated ID numbers.
     */
    private int id = 0;

    /**
     * Generate a new unique ID number.
     * <p><b>Note: </b>Iterates by 1 every time.
     * @return A new unique ID.
     */
    public int newId(){
        return ++id;
    }

    /**
     * Resets the IDs for a new multiplayer game.
     */
    public void resetIDs(){
        id = 0;
    }
}
