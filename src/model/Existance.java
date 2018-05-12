package model;

import assets.java.Sprite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.ViewUtil;

import java.util.Iterator;

/**
 * <h1>Class to define core properties of visual objects.</h1>
 * This class is extended by any object that can be viewed visually, as
 * all existing things have these properties.
 * <p>
 * <b>Note: </b>This class also stores the objects dimensions and
 * position at the previous frame, for rendering purposes.
 *
 * @author Åsmund Røst Wien
 */
public abstract class Existance implements java.io.Serializable {

    /**
     * The X and Y position of {@code this}.
     */
    private int x, y;

    /**
     * The X and Y position of {@code this} at the previous frame.
     */
    private int oldX, oldY;

    /**
     * The dimensions of {@code this}.
     */
    protected int height, width;

    /**
     * The dimensions of {@code this} at the previous frame.
     */
    private int oldHeight, oldWidth;

    /**
     * Boolean to check if {@code this} is ready to be purged from all game logic.
     */
    private boolean readyToPurge;

    /**
     * {@code This' Image}.
     * @see Image
     */
    protected transient Image image;

    /**
     * {@code This' ImageView}.
     * @see ImageView
     */
    public transient ImageView imageView;

    /**
     * {@code This' Sprite}.
     * @see Sprite
     */
    public Sprite sprite;

    /**
     * Sets the X- and Y- coordinates of a new object on screen.
     * @param x Left side position of object.
     * @param y Top side position of object.
     */
    public Existance(int x, int y){
        this.x = x;
        oldX = x;
        this.y = y;
        oldY = y;
        readyToPurge = false;
    }

    /**
     * Get the X position of the object.
     * @return Position at the far left of the object.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the Y position of the object
     * @return Position at the top of the object.
     */
    public int getY() {
        return y;
    }

    /**
     * Get the width of the object.
     * @return The width of the object.
     */
    protected int getWidth() {
        return width;
    }

    /**
     * Get the height of the object.
     * @return The height of the object.
     */
    private int getHeight() {
        return height;
    }

    /**
     * Set a new X position for the object.
     * <p>
     * <b>Note: </b> Also updates the oldX.
     * @param x The new positon at the far left.
     */
    public void setX(int x) {
        oldX = this.x;
        this.x = x;
    }

    /**
     * Set a new Y position for the object.
     * <p>
     * <b>Note: </b> Also updates the oldY.
     * @param y The new positon at the top.
     */
    public void setY(int y) {
        oldY = this.y;
        this.y = y;
    }

    /**
     * Method for manual control of the oldX variable.
     * <p>
     * <b>Note: </b>Only for speciffic rendering purposes.
     * @param oldX The old X position.
     */
    protected void setOldX(int oldX) {
        this.oldX = oldX;
    }

    /**
     * Method for manual control of the oldY variable.
     * <p>
     * <b>Note: </b>Only for speciffic rendering purposes.
     * @param oldY The old Y position.
     */
    protected void setOldY(int oldY) {
        this.oldY = oldY;
    }

    /**
     * Get the oldX value.
     * <p>
     * <b>Note: </b>the x position at the previous game frame.
     * @return oldX
     */
    public int getOldX() {
        return oldX;
    }

    /**
     * Get the oldY value.
     * <p>
     * <b>Note: </b>the y position at the previous game frame.
     * @return oldY
     */
    public int getOldY() {
        return oldY;
    }

    /**
     * Get the oldHeight value.
     * <p>
     * <b>Note: </b>the object height at the previous game frame.
     * @return oldHeight
     */
    public int getOldHeight(){
        return oldHeight;
    }

    /**
     * Get the oldWidth value.
     * <p>
     * <b>Note: </b>the object width at the previous game frame.
     * @return oldWidth
     */
    public int getOldWidth() {
        return oldWidth;
    }

    /**
     * Get the object's {@code Image}.
     * @return The object's {@code Image}.
     */
    public Image getImage(){
        return  image;
    }

    /**
     * Get the object's {@code ImageView}.
     * <p>
     * <b>Note: </b>Most objects simply use the {@code Image}, though a few speciffic use the ImageView for simplicity.
     * @return The object's {@code ImageView}.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Redefines an objects {@code Sprite}, and redefines it's dimensions.
     * @param sprite The new {@code Sprite} for the object to have.
     * @see Sprite
     */
    public void newSprite(Sprite sprite){
        newSprite(sprite.src);
        this.sprite = sprite;
    }

    /**
     * Redefines an objects {@code Sprite}, and redefines it's dimensions.
     * @param src The src of the new image-file for which the object's {@code Sprite} is to be set as.
     */
    public void newSprite(String src){
        image = new Image(src);
        imageView = new ImageView(image);
        setNewDimensions();
    }

    /**
     * Re-defines the object's new dimensions, and updates the old ones.
     */
    protected void setNewDimensions(){
        oldHeight = height;
        oldWidth = width;
        height = (int) image.getHeight();
        width = (int) image.getWidth();
    }

    /**
     * Test to see if an {@code Existance} collides with another {@code Existance}.
     * @param target The target {@code Existance} for {@code this} to be measured against.
     * @return {@code true} or {@code false}.
     */
    public boolean collidesWith(Existance target){
        if (getX() < target.getX() + target.getWidth() && getY() < target.getY() + target.getHeight()) {
            if (target.getX() < getX() + getWidth() && target.getY() < getY() + getHeight()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Test to see if {@code this} has moved out of the left side of the screen.
     * @return {@code true} or {@code false}.
     */
    private boolean isOffScreenLeft(){
        return x + width < 0;
    }

    /**
     * Test to see if {@code this} has moved out of the right side of the screen.
     * @return {@code true} or {@code false}.
     */
    private boolean isOffScreenRight(){
        return x > ViewUtil.VIEW_WIDTH + ViewUtil.VIEW_WIDTH / 10;
    }

    /**
     * Test to see if {@code this} has moved out of the screen.
     * @return {@code true} or {@code false}.
     */
    protected boolean isOffScreen(){
        return isOffScreenLeft() || isOffScreenRight();
    }

    /**
     * Test to see if {@code this} is ready to be purged from all game logic.
     * <p>
     * <b>Note: </b>This is usually set when an object is dead or off the board, and therefore ready to be removed.
     * @return {@code true} or {@code false}.
     */
    protected boolean getReadyToPurge(){
        return readyToPurge;
    }

    /**
     * Set {@code this} to be purged.
     * <p>
     * <b>Note: </b>This is usually set when an object is dead or off the board, and therefore ready to be removed.
     */
    public void isReadyToPurge() {
        this.readyToPurge = true;
    }

    /**
     * Remove {@code this} from an iterator which handles the game logic.
     * @param iterator The iterator from where to remove {@code this} object.
     */
    protected void purge(Iterator iterator){
        iterator.remove();
    }

}
