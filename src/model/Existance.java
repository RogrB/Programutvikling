package model;

import assets.java.Sprite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.ViewUtil;

import java.util.Iterator;

public abstract class Existance implements java.io.Serializable {

    private int x, y;
    private int oldX, oldY;
    protected int height, width;
    private int oldHeight, oldWidth;
    private boolean readyToPurge;

    protected transient Image image;
    transient ImageView imageView;
    public Sprite sprite;

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

    protected int getWidth() {
        return width;
    }

    private int getHeight() {
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

    protected void setOldX(int oldX) {
        this.oldX = oldX;
    }

    protected void setOldY(int oldY) {
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
        this.sprite = sprite;
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

    private boolean isOffScreenLeft(){
        return x + width < 0;
    }

    private boolean isOffScreenRight(){
        return x > ViewUtil.VIEW_WIDTH + ViewUtil.VIEW_WIDTH / 10;
    }

    protected boolean isOffScreen(){
        return isOffScreenLeft() || isOffScreenRight();
    }

    protected boolean getReadyToPurge(){
        return readyToPurge;
    }

    public void isReadyToPurge() {
        this.readyToPurge = true;
    }

    protected void purge(Iterator iterator){
        iterator.remove();
    }

}
