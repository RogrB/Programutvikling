package model;

import assets.java.Sprite;
import javafx.scene.image.Image;

import static view.GameView.GAME_WIDTH;

public abstract class Existance {

    private int x, y;
    private int oldX, oldY;
    protected int height, width;

    protected Image image;

    protected Sprite sprite;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        oldX = this.x;
        this.x = x;
    }

    public void setY(int y) {
        oldY = this.y;
        this.y = y;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public Image getImage(){
        return  image;
    }

    public void newSprite(Sprite sprite){
        image = new Image(sprite.imgSrc());
        setNewDimensions();
    }

    public void newSprite(String src){
        image = new Image(src);
        setNewDimensions();
    }

    private void setNewDimensions(){
        height = (int) image.getHeight();
        width = (int) image.getWidth();
    }

    public Sprite getSprite(){
        return sprite;
    }

    public boolean collidesWith(Existance target){
        if (getX() < target.getX() + target.getWidth() && getY() < target.getY() + target.getHeight()) {
            if (target.getX() < getX() + getWidth() && target.getY() < getY() + getHeight()) {
                return true;
            }
        }
        return false;
    }

    public boolean isOffScreenLeft(){
        if (x + width < 0)
            return true;
        return false;
    }

    public boolean isOffScreenRight(){
        if(x > GAME_WIDTH)
            return true;
        return false;
    }

    public boolean isOffScreen(){
        return isOffScreenLeft() || isOffScreenRight();
    }

}
