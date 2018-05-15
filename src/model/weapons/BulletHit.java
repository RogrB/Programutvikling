package model.weapons;

import assets.java.Sprite;

/**
 * <h1>Handles the sprites for the "bullet death" animation</h1>
 * Contains the sprites needed to animate the bullet hit effect
 * for both player and enemy bullets
 *
 * @author Åsmund Røst Wien
 * @author Roger Birkenes Solli
 */
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

    /**
     * {@code Sprite} objects
     * @see Sprite
     */  
    public final Sprite[] HIT_SPRITES;

    /**
     * @param list sets sprites list
     */      
    BulletHit(Sprite[] list){
        HIT_SPRITES = list;
    }

    /**
     * @return gets sprites list
     */      
    public Sprite[] getSprites(){
        return HIT_SPRITES;
    }
}
