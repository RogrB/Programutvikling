package main.java;

public enum MovementPattern {
    LEFT(0, 1);

    public final int x, y;

    MovementPattern(int x, int y){
        this.x = x;
        this.y = y;
    }
}
