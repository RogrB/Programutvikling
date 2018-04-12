package model;

public abstract class Existance {

    protected int x, y;
    protected int height, width;

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
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean collidesWith(Existance y){
        if(getX() + getWidth() > y.getX() &&
                y.getX() + getWidth() > getX()){

            if(getY() + getHeight() > y.getY() &&
                    y.getY() + getHeight() > getY()){
                return true;
            }
        }
        return false;
    }

}
