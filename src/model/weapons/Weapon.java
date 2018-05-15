package model.weapons;

import assets.java.Sprite;

/**
 * <h1>Contains the Weapon Types available in the game</h1>
 * Contains the data for all the weapon types available.
 * Sets the sprite, damage, firerate and hitanimation for all weapon types.
 * Both player and enemy weapons.
 *
 * @author Roger Birkenes Solli
 * @author Åsmund Røst Wien
 * @author Jonas Ege Carlsen
 */
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

    /**
     * {@code This' Sprite}.
     * @see Sprite
     */
    public final Sprite SPRITE;
    
    /**
     * The amount of damage the projectile does upon hit
     */    
    public final int DMG;
    
    /**
     * Decides how fast a weapon can fire
     */    
    public final int FIRERATE;
    
    /**
     * Contains the {@code Sprite} for the bullet hit effect
     * @see Sprite
     */    
    public final Sprite[] BULLET_HIT;

    /**
     * <b>Constructor: </b>sets the Sprite, damage, fireRate and BulletHit sprites for {@code this}
     * @param sprite Sets the Sprite
     * @param dmg Sets the damage
     * @param fireRate Sets the fire rate
     * @param bulletHit Sets the sprite container for the BulletHit effect
     */       
    Weapon(Sprite sprite, int dmg, int fireRate, BulletHit bulletHit){
        SPRITE = sprite;
        DMG = dmg;
        FIRERATE = fireRate;
        BULLET_HIT = bulletHit.getSprites();
    }
}
