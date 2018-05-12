package model.levels;

public class LevelData {
    /*
    INFO ABOUT SPAWNING STUFF IN
    NUMBER TYPE_SPRITE_MOVEMENT PATTERN
    MOVEMENT PATTERN ABBREVIATIONS
    * LEFT = L   LEFT_PULSATING = L_P
    * SIN        SIN_REVERSED = SIN_R
    * COS        COS_REVERSED = COS_R
    * TRI        TRI_REVERSED = TRI_R
    *
    * MADNESS_01, MADNESS_02, MADNESS_03 = M_01, M_02, M_03
    * BOSS_LINE,  BOSS_EIGHT, BOSS_OVAL;
    *
    ENEMY ABBREVIATIONS
    * enemyBlue1    = EB_1      ufoBlue = UB
    * enemyBlue2    = EB_2      ufoGreen = UG
    * enemyGreen1   = EG_1      ufoRed = UR
    * enemyOrange1  = EO_1      ufoYellow = UY
    * enemyRed1     = ER_1      Asteroid = AS
    * enemyRed2     = ER_2
    * enemyRed3     = ER_3
    * enemyRedBig   = ERB
    POWER-UP ABBREVIATIONS
    * something
    * something else
    NUMBERED TYPES
    * 0 = Nothing
    * 1 = Enemy
    * 2 = Asteroid
    * 3 = Boss
    * 4 = Power-up
    EXAMPLE SPAWNS
    1_EB1_COS
    3-BOSS_EIGHT
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

    private static final String[][][] LEVEL2 = new String[][][] {
        {{"0"}, {"0"}, {"0"}, {"1", "UFOBLUE", "TRI"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"2", "MADNESS_03"}, {"0"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"1", "REDBIG", "MADNESS_02"}, {"0"}, {"0"}},
        {{"1", "GREEN1", "COS"}, {"0"}, {"0"}, {"1", "UFOBLUE", "TRI"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"0"}, {"3", "BOSS02", "BOSS_OVAL"}},
        {{"1", "GREEN1", "COS_REVERSED"}, {"0"}, {"0"}, {"1", "UFOBLUE", "TRI"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"2", "MADNESS_03"}, {"0"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"1", "REDBIG", "MADNESS_02"}, {"0"}, {"0"}},
        {{"0"}, {"0"}, {"0"}, {"1", "UFOBLUE", "TRI"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"0"}, {"0"}}
    };
    
    private static final String[][][] LEVEL3 = new String[][][] {
        {{"0"}, {"1", "BLUE2", "SIN_REVERSED"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "UFORED", "LEFT"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS"}, {"0"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"0"}, {"0"}, {"2", "MADNESS_01"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"1", "REDBIG", "COS_REVERSED"}, {"0"}, {"0"}, {"1", "UFORED", "LEFT"}, {"0"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS_REVERSED"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS_REVERSED"}, {"0"}, {"0"}, {"0"}},
        {{"1", "RED3", "LEFT"}, {"1", "BLUE1", "MADNESS_01"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "UFOBLUE", "LEFT"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS"}, {"0"}, {"0"}, {"0"}, {"3", "BOSS01", "BOSS_EIGHT"}},
        {{"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS_REVERSED"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"0"}, {"0"}, {"2", "MADNESS_01"}, {"0"}, {"0"}, {"1", "RED3", "LEFT_PULSATING"}, {"0"}, {"0"}, {"1", "REDBIG", "COS"}, {"0"}, {"0"}, {"1", "UFORED", "LEFT"}, {"0"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS_REVERSED"}, {"0"}, {"0"}, {"0"}},
        {{"0"}, {"1", "BLUE2", "SIN"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"0"}, {"1", "UFORED", "LEFT"}, {"0"}, {"0"}, {"1", "ORANGE1", "COS"}, {"0"}, {"0"}, {"0"}, {"0"}}
    };    

    private static final String[][][] LEVEL4 = new String[][][]{
            {{"0"}, {"0"}, {"0"}},
            {{"2", "MADNESS_01"}, {"0"}},
            {{"0"}, {"0"}, {"0"}},
            {{"2", "MADNESS_01"}, {"0"}, {"0"}},
            {{"0"}, {"0"}, {"0"}},
            {{"0"}, {"0"}, {"0"}},
            {{"0"}, {"0"}, {"0"}}
    };    

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