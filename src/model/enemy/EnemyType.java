package model.enemy;

import assets.java.Sprite;
import javafx.scene.image.Image;

public enum EnemyType {
    ASTROID(1, Sprite.ASTROID, 1),
    SHIP(2, Sprite.ENEMY_SHIP, 1);

    public final int MAX_HEALTH;
    public final Sprite SPRITE;

    public int speed;

    EnemyType(int MAX_HEALTH, Sprite sprite, int speed) {
        this.MAX_HEALTH = MAX_HEALTH;
        this.SPRITE = sprite;
        this.speed = speed;
    }

}
