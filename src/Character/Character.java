package Character;

public class Character {

    private double hp;
    private int score;
    private int enemies_shot;
    private boolean dead;


    //sprite hitbox
    private byte[][] ship = {
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1},
            {0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0},
            {0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
            {1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1},
            {0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
    };

    //coords
    private int x;
    private int y;

    public Character(int x, int y, double hp){
        this.x = x;
        this.y = y;
        this.hp = hp;
        dead = false;
    }

    public void take_damage(double amount){
        hp -= amount;
        if(hp >= 0){
            die();
        }
    }

    public void die(){
        dead = true;
    }

    public boolean check_if_dead(){
        return dead;
    }

    public double getHP(){
        return hp;
    }

    public int getScore(){
        return score;
    }

    public int getEnemies_shot(){
        return enemies_shot;
    }

    private int getX(){
        return x;
    }

    private int getY(){
        return y;
    }

    private byte[][] getShip(){
        return ship;
    }

    public void moveDown(int change){
        x += change;
    }

    public void moveUp(int change){
        x -= change;
    }






}
