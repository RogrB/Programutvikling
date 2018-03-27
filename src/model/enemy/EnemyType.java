package model.enemy;

import assets.java.Sprite;
import javafx.scene.image.Image;
import model.weapons.Weapon;

public enum EnemyType {

    //          Health      Sprite                  Weapon
    ASTROID(    1,          Sprite.ASTROID,         null),
    SHIP(       2,          Sprite.ENEMY_SHIP,      Weapon.ENEMY_BASIC);

    public final int MAX_HEALTH;
    public final Sprite SPRITE;
    public final Weapon WEAPON;

    EnemyType(int MAX_HEALTH, Sprite sprite, Weapon weapon) {
        this.MAX_HEALTH = MAX_HEALTH;
        this.SPRITE = sprite;
        this.WEAPON = weapon;
    }

    public boolean canShoot(){
        if(WEAPON != null)
            return true;
        return false;
    }

}
