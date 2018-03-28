package model.weapons;

import assets.java.Sprite;

public enum Weapon {

    //              Sprite                          Damage  HitAnimation
    PLAYER_BASIC(   Sprite.WEAPON_PLAYER_BASIC,     1,      BulletHit.PLAYER_BASIC),
    ENEMY_BASIC(    Sprite.WEAPON_ENEMY_BASIC,      1,      BulletHit.ENEMY_BASIC);


    public final Sprite SPRITE;
    public final int DMG;
    public final Sprite[] BULLET_HIT;

    Weapon(Sprite sprite, int dmg, BulletHit bulletHit){
        SPRITE = sprite;
        DMG = dmg;
        BULLET_HIT = bulletHit.getSprites();
    }
}
