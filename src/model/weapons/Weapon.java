package model.weapons;

import assets.java.Sprite;

public enum Weapon {

    //              Sprite                          Damage
    PLAYER_BASIC(   Sprite.WEAPON_PLAYER_BASIC,     1),
    ENEMY_BASIC(    Sprite.WEAPON_ENEMY_BASIC,      1);


    public final Sprite SPRITE;
    public final int DMG;

    Weapon(Sprite sprite, int dmg){
        SPRITE = sprite;
        DMG = dmg;
    }
}
