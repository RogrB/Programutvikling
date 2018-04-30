
package model.weapons;


import java.util.Iterator;

public class TripleBurst extends Basic {
    
    private int level;
    
    public TripleBurst(int x, int y, Weapon weapon, int level) {
        super(
        x,
        y,
        weapon.PLAYER_TRIPLEBURST);
        this.level = level;
    }     
    
    @Override
    public void update(int x, int y, Iterator i) {
        this.setX(getX() + 20);
        switch(level) {
            case 1:
                this.setY(this.getY()+5);
                break;
            case 2:
                this.setY(this.getY());
                break;
            case 3:
                this.setY(this.getY()-5);
                break;
        }
    }
    
}
