package model.enemy;

import assets.java.Sprite;
import javafx.scene.image.Image;
import model.weapons.Weapon;

public enum EnemyType {

    //          Health      Sprite                  Shooting chance     Weapon
    ASTROID(    1,          Sprite.ASTROID1,        0,                  null),
    BLUE1(      2,          Sprite.ENEMY_BLUE01,    3000,               Weapon.ENEMY_BASIC),
    BLUE2(      2,          Sprite.ENEMY_BLUE02,    3000,               Weapon.ENEMY_BASIC),
    GREEN1(     2,          Sprite.ENEMY_GREEN01,   3000,               Weapon.ENEMY_BASIC),
    ORANGE1(    2,          Sprite.ENEMY_ORANGE1,   3000,               Weapon.ENEMY_BASIC),
    RED1(       2,          Sprite.ENEMY_RED1,      3000,               Weapon.ENEMY_BASIC),
    RED2(       2,          Sprite.ENEMY_RED2,      3000,               Weapon.ENEMY_BASIC),
    RED3(       2,          Sprite.ENEMY_RED3,      3000,               Weapon.ENEMY_BASIC),
    REDBIG(     2,          Sprite.ENEMY_RED_BIG,   3000,               Weapon.ENEMY_BASIC),
    
    // UFO
    UFOBLUE(    2,          Sprite.UFO_BLUE,        3000,                Weapon.ENEMY_BASIC),
    UFOGREEN(   2,          Sprite.UFO_GREEN,       3000,                Weapon.ENEMY_BASIC),
    UFORED(     2,          Sprite.UFO_RED,         3000,                Weapon.ENEMY_BASIC),
    UFOYELLOW(  2,          Sprite.UFO_YELLOW,      3000,                Weapon.ENEMY_BASIC),
    
    // Bosstypes
    BOSS01(     20,         Sprite.ENEMY_BOSS01,    700,                Weapon.PLAYER_DOUBLESWIRL),
    BOSS02(     20,         Sprite.ENEMY_BOSS02,    700,                Weapon.PLAYER_DOUBLESWIRL);

    public final int MAX_HEALTH;
    public final Sprite SPRITE;
    public final int SHOOTING_CHANCE;
    public final Weapon WEAPON;

    EnemyType(int MAX_HEALTH, Sprite sprite, int shootingChance, Weapon weapon) {
        this.MAX_HEALTH = MAX_HEALTH;
        this.SPRITE = sprite;
        this.SHOOTING_CHANCE = shootingChance;
        this.WEAPON = weapon;
    }

    public boolean canShoot(){
        if(WEAPON != null)
            return true;
        return false;
    }

}
