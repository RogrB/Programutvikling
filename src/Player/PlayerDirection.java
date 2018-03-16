package Player;

public enum PlayerDirection {
    UP(-1), DOWN(1), NONE(0);

    private int dir;
    private final int PLAYER_SPEED = 10;

    PlayerDirection(int dir) {
        this.dir = dir * PLAYER_SPEED;
    }

    public int next() {
        return dir;
    }
}
