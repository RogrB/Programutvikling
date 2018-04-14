
package model.weapons;


public class DoubleSwirl extends Bullet {
    
    private boolean top;
    private boolean goingUp;
    private int swirlCounter = 0;
    
    public DoubleSwirl(int x, int y, Weapon weapon, boolean top) {
        super(
        x,
        y,
        weapon.PLAYER_DOUBLESWIRL);
        this.top = top;
        if (top) {
            this.goingUp = false;
        }
    }     
    
    @Override
    public void move() {
        this.setX(getX() + 20);
        if (top) {
            if (goingUp) {
                this.setY(this.getY()-5);
            }
            else {
                this.setY(this.getY()+5);
            }
        }
        else {
            if (goingUp) {
                this.setY(this.getY()+5);
            }
            else {
                this.setY(this.getY()-5);
            }
        }
        swirlCounter++;
        if (swirlCounter == 6) {
            swirlCounter = 0;
            goingUp = !(goingUp);
        }
    }
    
}
