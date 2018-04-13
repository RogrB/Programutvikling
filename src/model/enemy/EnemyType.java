package model.enemy;

import assets.java.Sprite;
import javafx.scene.image.Image;
import model.weapons.Weapon;

public enum EnemyType {

    //          Health      Sprite                  Shooting chance     Weapon
    ASTROID(    1,          Sprite.ASTROID,         0,                  null),
    SHIP(       2,          Sprite.ENEMY_SHIP,      3000,               Weapon.ENEMY_BASIC),
    BOSS01(     20,         Sprite.ENEMY_BOSS01,    700,                Weapon.PLAYER_DOUBLESWIRL);

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
