package assets.java;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    
    ENEMY_BLUE01("assets/image/enemies/enemyBlue1.png"),
    ENEMY_BLUE02("assets/image/enemies/enemyBlue2.png"),
    ENEMY_GREEN01("assets/image/enemies/enemyGreen1.png"),
    ENEMY_ORANGE1("assets/image/enemies/enemyOrange1.png"),
    ENEMY_RED1("assets/image/enemies/enemyRed1.png"),
    ENEMY_RED2("assets/image/enemies/enemyRed2.png"),
    ENEMY_RED3("assets/image/enemies/enemyRed3.png"),
    ENEMY_RED_BIG("assets/image/enemies/enemyRedBig.png"),
    
    UFO_BLUE("assets/image/enemies/ufoBlue.png"),
    UFO_GREEN("assets/image/enemies/ufoGreen.png"),
    UFO_RED("assets/image/enemies/ufoRed.png"),
    UFO_YELLOW("assets/image/enemies/ufoYellow.png"),
    
    ENEMY_BOSS01("assets/image/enemies/boss01.png"),
    ENEMY_BOSS02("assets/image/enemies/boss02.png"),

    // Weapons
    WEAPON_PLAYER_BASIC("assets/image/laserBlue06.png"),
    WEAPON_PLAYER_BASIC_DMG_1("assets/image/damage/laserBlue08.png"),
    WEAPON_PLAYER_BASIC_DMG_2("assets/image/damage/laserBlue09.png"),
    WEAPON_PLAYER_BASIC_DMG_3("assets/image/damage/laserBlue10.png"),
    WEAPON_PLAYER_BASIC_DMG_4("assets/image/damage/laserBlue11.png"),
    
    WEAPON_PLAYER_UPGRADE1("assets/image/laserBlue07.png"),
    WEAPON_PLAYER_UPGRADE2("assets/image/laserYellow.png"),
    WEAPON_PLAYER_MISSILE("assets/image/missile01.png"),
    WEAPON_PLAYER_LASERCIRCLE("assets/image/laserCircle01.png"),
    WEAPON_PLAYER_LASERCIRCLE2("assets/image/laserCircle02.png"),
    WEAPON_PLAYER_LASERCIRCLE3("assets/image/laserCircle03.png"),
    WEAPON_PLAYER_LASERCIRCLE4("assets/image/laserCircle04.png"),

    WEAPON_ENEMY_BASIC("assets/image/laserRed.png"),
    WEAPON_ENEMY_BASIC_DMG_1("assets/image/damage/laserRed08.png"),
    WEAPON_ENEMY_BASIC_DMG_2("assets/image/damage/laserRed09.png"),
    WEAPON_ENEMY_BASIC_DMG_3("assets/image/damage/laserRed10.png"),
    WEAPON_ENEMY_BASIC_DMG_4("assets/image/damage/laserRed11.png"),

    // Player
    PLAYER("assets/image/player/playerShip2_red.png"),
    PLAYER_BLINK1("assets/image/player/playerShip3_red.png"),
    PLAYER_BLINK2("assets/image/player/playerShip4_red.png"),
    
    // Shield
    SHIELD1("assets/image/player/shield1.png"),
    SHIELD2("assets/image/player/shield2.png"),
    SHIELD3("assets/image/player/shield3.png"),
    
    // Other
    CLEAR("assets/image/damage/clear.png"),
    WEAPON_POWERUP("assets/image/powerUp.png"),
    HEALTH_POWERUP("assets/image/healthUp.png"),
    SHIELD_POWERUP("assets/image/powerUpShield.png"),    
    
    // Player Death
    PLAYER_DEATH_1("assets/image/playerDeath/playerDeath_001.png"),
    PLAYER_DEATH_2("assets/image/playerDeath/playerDeath_002.png"),
    PLAYER_DEATH_3("assets/image/playerDeath/playerDeath_003.png"),
    PLAYER_DEATH_4("assets/image/playerDeath/playerDeath_004.png"),
    PLAYER_DEATH_5("assets/image/playerDeath/playerDeath_005.png"),
    PLAYER_DEATH_6("assets/image/playerDeath/playerDeath_006.png"),
    PLAYER_DEATH_7("assets/image/playerDeath/playerDeath_007.png"),
    PLAYER_DEATH_8("assets/image/playerDeath/playerDeath_008.png"),
    PLAYER_DEATH_9("assets/image/playerDeath/playerDeath_009.png");

    public String src;

    Sprite(String src){
        this.src = src;
    }
    
    public String getSrc() {
        return this.src;
    }

    public int getHeight(){
        return (int) new Image(src).getHeight();
    }

    public int getWidth(){
        return (int) new Image(src).getWidth();
    }
}
