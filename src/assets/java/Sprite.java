package assets.java;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum Sprite {
    PLAYER("assets/image/playerShip2_red.png"),
    ASTROID("assets/image/enemyBlue1.png"),
    ENEMY_SHIP("assets/image/enemyBlue1.png"),
    PLAYERBLINK("assets/image/playerShip3_red.png"),
    PLAYERBLINK2("assets/image/playerShip4_red.png");
    

    Image img;
    ImageView view;

    Sprite(String src){
        this.img =  new Image(src) ;
        this.view = new ImageView(this.img);
    }

    public double getHeight(){
        return img.getHeight();
    }

    public double getWidth(){
        return img.getWidth();
    }

    public ImageView getView(){
        return view;
    }
}
