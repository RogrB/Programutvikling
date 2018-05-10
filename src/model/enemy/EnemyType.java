package model.enemy;

import assets.java.Sprite;
import model.weapons.Weapon;

import java.util.Objects;

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
    REDBIG(         2,          Sprite.ENEMY_RED_BIG,   .004,               Weapon.ENEMY_BASIC),
    
    // UFO
    UFOBLUE(        2,          Sprite.UFO_BLUE,        .004,                Weapon.ENEMY_BASIC),
    UFOGREEN(       2,          Sprite.UFO_GREEN,       .004,                Weapon.ENEMY_BASIC),
    UFORED(         2,          Sprite.UFO_RED,         .004,                Weapon.ENEMY_BASIC),
    UFOYELLOW(      2,          Sprite.UFO_YELLOW,      .004,                Weapon.ENEMY_BASIC),
    
    // Bosstypes
    BOSS01(         20,         Sprite.ENEMY_BOSS01,    .050,                Weapon.ENEMY_CIRCLE),
    BOSS02(         20,         Sprite.ENEMY_BOSS02,    .050,                Weapon.ENEMY_CIRCLE);

    public final int MAX_HEALTH;
    public final Sprite SPRITE;
    public final double SHOOTING_CHANCE;
    public final Weapon WEAPON;
    public final boolean IS_BOSS;

    EnemyType(int MAX_HEALTH, Sprite sprite, double shootingChance, Weapon weapon) {
        this.MAX_HEALTH = MAX_HEALTH;
        this.SPRITE = sprite;
        this.SHOOTING_CHANCE = shootingChance;
        this.WEAPON = weapon;
        IS_BOSS = Objects.equals(this.name(), "BOSS01") || Objects.equals(this.name(), "BOSS02");
    }

    public boolean canShoot(){
        return WEAPON != null;
    }

}
