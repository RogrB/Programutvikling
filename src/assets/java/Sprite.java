package assets.java;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum Sprite {

    // Enemies
    ASTROID("assets/image/enemyBlue1.png"),
    ENEMY_SHIP("assets/image/enemyBlue1.png"),

    // Weapons
    WEAPON_PLAYER_BASIC("assets/image/laserBlue06.png"),

    WEAPON_ENEMY_BASIC("assets/image/laserRed.png"),

    // Other
    PLAYER("assets/image/playerShip2_red.png"),
    CLEAR("assets/image/damage/clear.png");
    

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

    public Image getImg() {
        return img;
    }
}
