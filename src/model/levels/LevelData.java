package model.levels;

/**
 * <h1>Class to store level definitions.</h1>
 * This class stores and allows access to multiple three dimensional
 * string objects which stores the level definitions over time. These
 * definitions include which enemies to spawn at what time where on
 * the board, how they act and what they look like. This applies for
 * the level specific boss as well.
 *
 * @author Jonas Ege Carlsen
 */
public class LevelData {

    /**
     * Definitions for {@code LEVEL1}.
     */
    private static final String[][][] LEVEL1 = new String[][][]{
            {{"0"}, {"0"}, {"0"},                   {"0"}, {"0"}, {"0"}, {"0"}, {"1", "RED1", "SIN_REVERSED"},  {"1", "RED1", "SIN_REVERSED"},  {"1", "RED1", "SIN_REVERSED"},    {"0"}, {"0"}, {"0"},                  {"0"}, {"1", "GREEN1", "LEFT_PULSATING"},   {"0"}, {"0"}, {"0"}},
            {{"0"}, {"0"}, {"1", "BLUE2", "COS"},   {"0"}, {"0"}, {"0"}, {"0"}, {"0"},                          {"0"},                          {"0"},                            {"0"}, {"0"}, {"0"},                  {"0"}, {"1", "GREEN1", "LEFT_PULSATING"},   {"0"}, {"0"}, {"0"}},
            {{"0"}, {"1", "BLUE2", "COS"}, {"0"},   {"0"}, {"0"}, {"0"}, {"0"}, {"0"},                          {"0"},                          {"0"},                            {"0"}, {"0"}, {"0"},                  {"0"}, {"0"},                               {"0"}, {"0"}, {"0"}},
            {{"1", "BLUE2", "COS"}, {"0"}, {"0"},   {"0"}, {"0"}, {"0"}, {"0"}, {"0"},                          {"0"},                          {"0"},                            {"0"}, {"0"}, {"0"},                  {"0"}, {"1", "ORANGE1", "LEFT_PULSATING"},  {"0"}, {"0"}, {"3", "BOSS01", "BOSS_EIGHT"}},
            {{"0"}, {"1", "BLUE2", "COS"}, {"0"},   {"0"}, {"0"}, {"0"}, {"0"}, {"0"},                          {"0"},                          {"0"},                            {"0"}, {"0"}, {"2", "MADNESS_01"},    {"0"}, {"0"},                               {"0"}, {"0"}, {"0"}},
            {{"0"}, {"0"}, {"1", "BLUE2", "COS"},   {"0"}, {"0"}, {"0"}, {"0"}, {"0"},                          {"0"},                          {"0"},                            {"0"}, {"0"}, {"0"},                  {"0"}, {"1", "RED1", "LEFT_PULSATING"},     {"0"}, {"0"}, {"0"}},
            {{"0"}, {"0"}, {"0"},                   {"0"}, {"0"}, {"0"}, {"0"}, {"1", "RED1", "SIN"},           {"1", "RED1", "SIN"},           {"1", "RED1", "SIN"},             {"0"}, {"0"}, {"0"},                  {"0"}, {"1", "RED1", "LEFT_PULSATING"},     {"0"}, {"0"}, {"0"}}
    };

    /**
     * Definitions for {@code LEVEL2}.
     */
    private static final String[][][] LEVEL2 = new String[][][] {
        {{"0"}, {"0"}, {"0"}, {"1", "UFOBLUE", "TRI"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"2", "MADNESS_03"}, {"0"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"1", "REDBIG", "MADNESS_02"}, {"0"}, {"0"}},
        {{"1", "GREEN1", "COS"}, {"0"}, {"0"}, {"1", "UFOBLUE", "TRI"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"0"}, {"3", "BOSS02", "BOSS_OVAL"}},
        {{"1", "GREEN1", "COS_REVERSED"}, {"0"}, {"0"}, {"1", "UFOBLUE", "TRI"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"2", "MADNESS_03"}, {"0"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"1", "REDBIG", "MADNESS_02"}, {"0"}, {"0"}},
        {{"0"}, {"0"}, {"0"}, {"1", "UFOBLUE", "TRI"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"0"}, {"0"}}
    };

    /**
     * Definitions for {@code LEVEL3}.
     */
    private static final String[][][] LEVEL3 = new String[][][] {
        {{"0"}, {"1", "BLUE2", "SIN_REVERSED"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "UFORED", "LEFT"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS"}, {"0"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"0"}, {"0"}, {"2", "MADNESS_01"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"1", "REDBIG", "COS_REVERSED"}, {"0"}, {"0"}, {"1", "UFORED", "LEFT"}, {"0"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS_REVERSED"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS_REVERSED"}, {"0"}, {"0"}, {"0"}},
        {{"1", "RED3", "LEFT"}, {"1", "BLUE1", "MADNESS_01"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "UFOBLUE", "LEFT"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS"}, {"0"}, {"0"}, {"0"}, {"3", "BOSS01", "BOSS_EIGHT"}},
        {{"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS_REVERSED"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"0"}, {"0"}, {"2", "MADNESS_01"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"1", "REDBIG", "COS"}, {"0"}, {"0"}, {"1", "UFORED", "LEFT"}, {"0"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS_REVERSED"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"1", "BLUE2", "SIN"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "UFORED", "LEFT"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS"}, {"0"}, {"0"}, {"0"}, {"0"}}
    };

    /**
     * Definitions for {@code LEVEL4}.
     */
    private static final String[][][] LEVEL4 = new String[][][]{
            {{"0"}, {"0"}, {"0"}},
            {{"2", "MADNESS_01"}, {"0"}},
            {{"0"}, {"0"}, {"0"}},
            {{"2", "MADNESS_01"}, {"0"}, {"0"}},
            {{"0"}, {"0"}, {"0"}},
            {{"0"}, {"0"}, {"0"}},
            {{"0"}, {"0"}, {"0"}}
    };

    /**
     * Method to access the level data.
     * @param levelName The name of the level to access.
     * @return The three dimensional String array which contains the level data over time.
     */
    public static String[][][] getLevel(String levelName){
        switch (levelName) {
            case "LEVEL1":
                return LEVEL1;
            case "LEVEL2":
                return LEVEL2;
            case "LEVEL3":
                return LEVEL3;
            case "LEVEL4":
                return LEVEL4;
            default:
                return null;
        }
    }
}