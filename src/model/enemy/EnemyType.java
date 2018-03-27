package model.enemy;

import assets.java.Sprite;
import javafx.scene.image.Image;

public enum EnemyType {
    ASTROID(1, Sprite.ASTROID, 0, null),
    SHIP(2, Sprite.ENEMY_SHIP, 1, Sprite.WEAPON_ENEMY_BASIC);

    public final int MAX_HEALTH;
    public final Sprite SPRITE;
    public final int DMG;
    public final Sprite WEAPON;

    EnemyType(int MAX_HEALTH, Sprite sprite, int dmg, Sprite weapon) {
        this.MAX_HEALTH = MAX_HEALTH;
        this.SPRITE = sprite;
        this.DMG = dmg;
        this.WEAPON = weapon;
    }

    public boolean canShoot(){
        if(WEAPON != null)
            return true;
        return false;
    }

}
