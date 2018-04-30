package model.weapons;

import assets.java.Sprite;

public enum Weapon {

    //                  Sprite                              Damage  FireRate    HitAnimation            
    PLAYER_BASIC(       Sprite.WEAPON_PLAYER_BASIC,         1,      300,        BulletHit.PLAYER_BASIC),
    PLAYER_UPGRADED(    Sprite.WEAPON_PLAYER_UPGRADE2,      1,      150,        BulletHit.PLAYER_BASIC),
    PLAYER_UPGRADED2(   Sprite.WEAPON_PLAYER_MISSILE,       2,      300,        BulletHit.PLAYER_BASIC),
    PLAYER_HEATSEEKING( Sprite.WEAPON_PLAYER_LASERCIRCLE3,  1,      300,        BulletHit.PLAYER_BASIC),
    PLAYER_DOUBLES(     Sprite.WEAPON_PLAYER_LASERCIRCLE,   1,      300,        BulletHit.PLAYER_BASIC),
    PLAYER_DOUBLESWIRL( Sprite.WEAPON_PLAYER_LASERCIRCLE2,  1,      300,        BulletHit.PLAYER_BASIC),
    PLAYER_TRIPLEBURST( Sprite.WEAPON_PLAYER_LASERCIRCLE5,  1,      300,        BulletHit.PLAYER_BASIC),
    ENEMY_BASIC(        Sprite.WEAPON_ENEMY_BASIC,          1,      300,        BulletHit.ENEMY_BASIC),
    ENEMY_CIRCLE(       Sprite.WEAPON_PLAYER_LASERCIRCLE4,  1,      300,        BulletHit.ENEMY_BASIC);


    public final Sprite SPRITE;
    public final int DMG;
    public final int FIRERATE;
    public final Sprite[] BULLET_HIT;

    Weapon(Sprite sprite, int dmg, int fireRate, BulletHit bulletHit){
        SPRITE = sprite;
        DMG = dmg;
        FIRERATE = fireRate;
        BULLET_HIT = bulletHit.getSprites();
    }
}
