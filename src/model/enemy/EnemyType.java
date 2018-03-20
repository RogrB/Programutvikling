package model.enemy;

import javafx.scene.image.Image;

public enum EnemyType {
    ASTROID(1, null, 1),
    SHIP(2, null, 1);

    public final int MAX_HEALTH;
    public final Image SPRITE;

    public int speed;

    EnemyType(int MAX_HEALTH, Image SPRITE, int speed) {
        this.MAX_HEALTH = MAX_HEALTH;
        this.SPRITE = SPRITE;
        this.speed = speed;
    }

}
