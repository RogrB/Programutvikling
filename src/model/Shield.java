package model;

import javafx.scene.image.Image;
import assets.java.Sprite;

public class Shield extends Existance {
    
    GameModel gm = GameModel.getInstance();
    
    private int charges;
    
    public Shield(int x, int y) {
        super(x, y);
        charges += 2;
        newSprite(Sprite.SHIELD1);
        setNewDimensions();
    }
    
    public void setCharges(int charges) {
        this.charges = charges;
    }
    
    public int getCharges() {
        return this.charges;
    }
    
}
