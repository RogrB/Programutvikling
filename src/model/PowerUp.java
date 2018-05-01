
package model;

import assets.java.Sprite;

import java.util.Iterator;

public class PowerUp extends Existance {
    
    private boolean pickedUp = false;
    private String name;
    
    public PowerUp(Sprite sprite, int x, int y) {
        super(x, y);
        newSprite(sprite);
        setNewDimensions();
        name = sprite.name();
    }

    public void update(int x, int y, Iterator i){
        setX(getX() + x);
        setY(getY() + y);
        if(isOffScreen() || getReadyToPurge())
            purge(i);
    }

    public void setPickedUp(){
        pickedUp = true;
        newSprite(Sprite.CLEAR);
        isReadyToPurge();
    }

    public boolean isPickedUp(){
        return pickedUp;
    }

    public String getName(){
        return name;
    }
    
}