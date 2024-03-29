package assets.java;

import javafx.scene.image.Image;

/**
 * <h1>A container for sprite source files</h1>
 * An enum which contains the source files for the most used sprites,
 * and adds some specific functionality to them.
 *
 * @author Åsmund Røst Wien
 */
public enum Sprite {

    // Enemies
    ASTEROID1("assets/image/enemies/meteor/meteor001.png"),
    ASTEROID2("assets/image/enemies/meteor/meteor002.png"),
    ASTEROID3("assets/image/enemies/meteor/meteor003.png"),
    ASTEROID4("assets/image/enemies/meteor/meteor004.png"),
    ASTEROID5("assets/image/enemies/meteor/meteor005.png"),
    ASTEROID6("assets/image/enemies/meteor/meteor006.png"),
    ASTEROID7("assets/image/enemies/meteor/meteor007.png"),
    ASTEROID8("assets/image/enemies/meteor/meteor008.png"),
    
    SMALL_ASTEROID1("assets/image/enemies/meteor/small/small_meteor001.png"),
    SMALL_ASTEROID2("assets/image/enemies/meteor/small/small_meteor002.png"),
    SMALL_ASTEROID3("assets/image/enemies/meteor/small/small_meteor003.png"),
    SMALL_ASTEROID4("assets/image/enemies/meteor/small/small_meteor004.png"),
    SMALL_ASTEROID5("assets/image/enemies/meteor/small/small_meteor005.png"),
    SMALL_ASTEROID6("assets/image/enemies/meteor/small/small_meteor006.png"),
    SMALL_ASTEROID7("assets/image/enemies/meteor/small/small_meteor007.png"),
    SMALL_ASTEROID8("assets/image/enemies/meteor/small/small_meteor008.png"),
    
    ENEMY_BLUE01("assets/image/enemies/ships/enemyBlue1.png"),
    ENEMY_BLUE02("assets/image/enemies/ships/enemyBlue2.png"),
    ENEMY_GREEN01("assets/image/enemies/ships/enemyGreen1.png"),
    ENEMY_ORANGE1("assets/image/enemies/ships/enemyOrange1.png"),
    ENEMY_RED1("assets/image/enemies/ships/enemyRed1.png"),
    ENEMY_RED2("assets/image/enemies/ships/enemyRed2.png"),
    ENEMY_RED3("assets/image/enemies/ships/enemyRed3.png"),
    ENEMY_RED_BIG("assets/image/enemies/ships/enemyRedBig.png"),
    
    UFO_BLUE("assets/image/enemies/ufos/ufoBlue.png"),
    UFO_GREEN("assets/image/enemies/ufos/ufoGreen.png"),
    UFO_RED("assets/image/enemies/ufos/ufoRed.png"),
    UFO_YELLOW("assets/image/enemies/ufos/ufoYellow.png"),
    
    ENEMY_BOSS01("assets/image/enemies/boss/boss01.png"),
    ENEMY_BOSS02("assets/image/enemies/boss/boss02.png"),

    // Weapons
    WEAPON_PLAYER_BASIC("assets/image/weapons/laserBlue06.png"),
    WEAPON_PLAYER_BASIC_DMG_1("assets/image/damage/laserBlue08.png"),
    WEAPON_PLAYER_BASIC_DMG_2("assets/image/damage/laserBlue09.png"),
    WEAPON_PLAYER_BASIC_DMG_3("assets/image/damage/laserBlue10.png"),
    WEAPON_PLAYER_BASIC_DMG_4("assets/image/damage/laserBlue11.png"),
    
    WEAPON_PLAYER_UPGRADE1("assets/image/weapons/laserBlue07.png"),
    WEAPON_PLAYER_UPGRADE2("assets/image/weapons/laserYellow.png"),
    WEAPON_PLAYER_MISSILE("assets/image/weapons/missile01.png"),
    WEAPON_PLAYER_LASERCIRCLE("assets/image/weapons/laserCircle01.png"),
    WEAPON_PLAYER_LASERCIRCLE2("assets/image/weapons/laserCircle02.png"),
    WEAPON_PLAYER_LASERCIRCLE3("assets/image/weapons/laserCircle03.png"),
    WEAPON_PLAYER_LASERCIRCLE4("assets/image/weapons/laserCircle04.png"),
    WEAPON_PLAYER_LASERCIRCLE5("assets/image/weapons/laserCircle05.png"),

    WEAPON_ENEMY_BASIC("assets/image/weapons/laserRed.png"),
    WEAPON_ENEMY_BASIC_DMG_1("assets/image/damage/laserRed08.png"),
    WEAPON_ENEMY_BASIC_DMG_2("assets/image/damage/laserRed09.png"),
    WEAPON_ENEMY_BASIC_DMG_3("assets/image/damage/laserRed10.png"),
    WEAPON_ENEMY_BASIC_DMG_4("assets/image/damage/laserRed11.png"),

    // Player
    PLAYER("assets/image/player/ship/playerShip2_red.png"),
    PLAYER_BLINK1("assets/image/player/ship/playerShip3_red.png"),
    PLAYER_BLINK2("assets/image/player/ship/playerShip4_red.png"),
    PLAYER2("assets/image/player/ship/player2.png"),
    
    // Shield
    SHIELD1("assets/image/player/shield/shield1.png"),
    SHIELD2("assets/image/player/shield/shield2.png"),
    SHIELD3("assets/image/player/shield/shield3.png"),
    
    // Other
    CLEAR("assets/image/damage/clear.png"),
    WEAPON_POWERUP("assets/image/powerup/powerUp.png"),
    HEALTH_POWERUP("assets/image/powerup/healthUp.png"),
    SHIELD_POWERUP("assets/image/powerup/powerUpShield.png"),
    
    // Player Death
    PLAYER_DEATH_1("assets/image/player_death/playerDeath_001.png"),
    PLAYER_DEATH_2("assets/image/player_death/playerDeath_002.png"),
    PLAYER_DEATH_3("assets/image/player_death/playerDeath_003.png"),
    PLAYER_DEATH_4("assets/image/player_death/playerDeath_004.png"),
    PLAYER_DEATH_5("assets/image/player_death/playerDeath_005.png"),
    PLAYER_DEATH_6("assets/image/player_death/playerDeath_006.png"),
    PLAYER_DEATH_7("assets/image/player_death/playerDeath_007.png"),
    PLAYER_DEATH_8("assets/image/player_death/playerDeath_008.png"),
    PLAYER_DEATH_9("assets/image/player_death/playerDeath_009.png");

    /**
     * The source file path of the object.
     */
    public String src;

    /**
     * Defines which sprite to use.
     * @param src The source file path for this object.
     */
    Sprite(String src){
        this.src = src;
    }

    /**
     * Returns the file path of the object.
     * @return The file path of the object.
     */
    public String getSrc() {
        return this.src;
    }

    /**
     * Returns the height of the object.
     * @return The height of the object.
     */
    public int getHeight(){
        return (int) new Image(src).getHeight();
    }

    /**
     * Returns the width of the object.
     * @return The width of the object.
     */
    public int getWidth(){
        return (int) new Image(src).getWidth();
    }
}
