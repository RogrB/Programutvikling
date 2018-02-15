package main.java;

public enum MovementPattern {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    public final double x, y;

    MovementPattern(double x, double y){
        this.x = x;
        this.y = y;
    }


}
