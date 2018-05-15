package model.enemy;

import assets.java.Sprite;
import model.weapons.Weapon;

import java.util.Objects;

/**
 * <h1>EnemyType contains the details for individual enemies</h1>
 * Handles health, sprite, shooting chance and projectile sprite for
 * all the different enemies
 */
public enum EnemyType {

    //              Health      Sprite                  Shooting chance     Weapon
    ASTEROID(       1,          Sprite.ASTEROID1,       0,                  null),
    SMALL_ASTEROID( 1,          Sprite.SMALL_ASTEROID1, 0,                  null),
    BLUE1(          2,          Sprite.ENEMY_BLUE01,    .004,               Weapon.ENEMY_BASIC),
    BLUE2(          2,          Sprite.ENEMY_BLUE02,    .004,               Weapon.ENEMY_BASIC),
    GREEN1(         2,          Sprite.ENEMY_GREEN01,   .004,               Weapon.ENEMY_BASIC),
    ORANGE1(        2,          Sprite.ENEMY_ORANGE1,   .004,               Weapon.ENEMY_BASIC),
    RED1(           2,          Sprite.ENEMY_RED1,      .004,               Weapon.ENEMY_BASIC),
    RED2(           2,          Sprite.ENEMY_RED2,      .004,               Weapon.ENEMY_BASIC),
    RED3(           2,          Sprite.ENEMY_RED3,      .004,               Weapon.ENEMY_BASIC),
    REDBIG(         7,          Sprite.ENEMY_RED_BIG,   .020,               Weapon.ENEMY_BASIC),
    
    // UFO
    UFOBLUE(        3,          Sprite.UFO_BLUE,        .008,                Weapon.ENEMY_BASIC),
    UFOGREEN(       3,          Sprite.UFO_GREEN,       .008,                Weapon.ENEMY_BASIC),
    UFORED(         3,          Sprite.UFO_RED,         .008,                Weapon.ENEMY_BASIC),
    UFOYELLOW(      3,          Sprite.UFO_YELLOW,      .008,                Weapon.ENEMY_BASIC),
    
    // Bosstypes
    BOSS01(         20,         Sprite.ENEMY_BOSS01,    .050,                Weapon.ENEMY_CIRCLE),
    BOSS02(         20,         Sprite.ENEMY_BOSS02,    .050,                Weapon.ENEMY_CIRCLE);

    /**
     * The amount of hit points an enemy has upon spawn
     */      
    public final int MAX_HEALTH;
    
    /**
     * The sprite of the enemy
     * @see Sprite
     */      
    public final Sprite SPRITE;
    
    /**
     * How likely the enemy is to shoot
     */      
    public final double SHOOTING_CHANCE;
    
    /**
     * The sprite for the enemy projectile
     */      
    public final Weapon WEAPON;
    
    /**
     * If the enemy is a boss type
     */      
    public final boolean IS_BOSS;

    /**
     * <b>Constructor: </b>sets the values required to define an EnemyType
     * @param MAX_HEALTH sets the amount of hit points an enemy has upon spawn
     * @param sprite sets the sprite for the enemy
     * @param shootingChance sets the shootingChance for the enemy - how likely the enemy is to shoot
     * @param weapon sets the projectile sprite for the enemy
     */      
    EnemyType(int MAX_HEALTH, Sprite sprite, double shootingChance, Weapon weapon) {
        this.MAX_HEALTH = MAX_HEALTH;
        this.SPRITE = sprite;
        this.SHOOTING_CHANCE = shootingChance;
        this.WEAPON = weapon;
        IS_BOSS = Objects.equals(this.name(), "BOSS01") || Objects.equals(this.name(), "BOSS02");
    }

    /**
     * @return if the enemy can shoot
     */      
    public boolean canShoot(){
        return WEAPON != null;
    }

}
