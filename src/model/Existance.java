package model;

import assets.java.Sprite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Iterator;

import static view.GameView.GAME_WIDTH;

public abstract class Existance {

    private int x, y;
    private int oldX, oldY;
    protected int height, width;
    protected int oldHeight, oldWidth;
    private boolean readyToPurge;

    protected Image image;
    protected ImageView imageView;

    public Existance(int x, int y){
        this.x = x;
        oldX = x;
        this.y = y;
        oldY = y;
        readyToPurge = false;
    }

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

    public int getOldHeight(){
        return oldHeight;
    }

    public int getOldWidth() {
        return oldWidth;
    }

    public Image getImage(){
        return  image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void newSprite(Sprite sprite){
        newSprite(sprite.src);
    }

    public void newSprite(String src){
        image = new Image(src);
        imageView = new ImageView(image);
        setNewDimensions();
    }

    protected void setNewDimensions(){
        oldHeight = height;
        oldWidth = width;
        height = (int) image.getHeight();
        width = (int) image.getWidth();
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

    public boolean getReadyToPurge(){
        return readyToPurge;
    }

    public void isReadyToPurge() {
        this.readyToPurge = true;
    }

    public void purge(Iterator iterator){
        iterator.remove();
    }

}
