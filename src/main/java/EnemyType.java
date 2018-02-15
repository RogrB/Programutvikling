package main.java;

import javafx.scene.image.Image;

public enum EnemyType {
    ASTROID(1, null),
    SHIP(2, null);

    public final int MAX_HEALTH;
    public final Image SPRITE;

    EnemyType(int MAX_HEALTH, Image SPRITE) {
        this.MAX_HEALTH = MAX_HEALTH;
        this.SPRITE = SPRITE;
    }

}
