package model.weapons;

import assets.java.Sprite;

public enum BulletHit {
    PLAYER_BASIC( new Sprite[] {
            Sprite.WEAPON_PLAYER_BASIC_DMG_1,
            Sprite.WEAPON_PLAYER_BASIC_DMG_2,
            Sprite.WEAPON_PLAYER_BASIC_DMG_3,
            Sprite.WEAPON_PLAYER_BASIC_DMG_4
    }),
    ENEMY_BASIC( new Sprite[] {
            Sprite.WEAPON_ENEMY_BASIC_DMG_1,
            Sprite.WEAPON_ENEMY_BASIC_DMG_2,
            Sprite.WEAPON_ENEMY_BASIC_DMG_3,
            Sprite.WEAPON_ENEMY_BASIC_DMG_4
    });

    public final Sprite[] HIT_SPRITES;

    BulletHit(Sprite[] list){
        HIT_SPRITES = list;
    }

    public Sprite[] getSprites(){
        return HIT_SPRITES;
    }
}
