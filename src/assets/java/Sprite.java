package assets.java;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum Sprite {

    // Enemies
    ASTROID("assets/image/enemyBlue1.png"),
    ENEMY_SHIP("assets/image/enemyBlue1.png"),

    // Weapons
    WEAPON_PLAYER_BASIC("assets/image/laserBlue06.png"),
    WEAPON_PLAYER_BASIC_DMG_1("assets/image/damage/laserBlue08.png"),
    WEAPON_PLAYER_BASIC_DMG_2("assets/image/damage/laserBlue09.png"),
    WEAPON_PLAYER_BASIC_DMG_3("assets/image/damage/laserBlue10.png"),
    WEAPON_PLAYER_BASIC_DMG_4("assets/image/damage/laserBlue11.png"),
    
    WEAPON_PLAYER_UPGRADE1("assets/image/laserBlue07.png"),
    WEAPON_PLAYER_UPGRADE2("assets/image/laserYellow.png"),

    WEAPON_ENEMY_BASIC("assets/image/laserRed.png"),
    WEAPON_ENEMY_BASIC_DMG_1("assets/image/damage/laserRed08.png"),
    WEAPON_ENEMY_BASIC_DMG_2("assets/image/damage/laserRed09.png"),
    WEAPON_ENEMY_BASIC_DMG_3("assets/image/damage/laserRed10.png"),
    WEAPON_ENEMY_BASIC_DMG_4("assets/image/damage/laserRed11.png"),

    // Other
    PLAYER("assets/image/playerShip2_red.png"),
    CLEAR("assets/image/damage/clear.png"),
    
    // Player Death
    PLAYER_DEATH_1("assets/image/playerDeath/playerDeath_001.png"),
    PLAYER_DEATH_2("assets/image/playerDeath/playerDeath_002.png"),
    PLAYER_DEATH_3("assets/image/playerDeath/playerDeath_003.png"),
    PLAYER_DEATH_4("assets/image/playerDeath/playerDeath_004.png"),
    PLAYER_DEATH_5("assets/image/playerDeath/playerDeath_005.png"),
    PLAYER_DEATH_6("assets/image/playerDeath/playerDeath_006.png"),
    PLAYER_DEATH_7("assets/image/playerDeath/playerDeath_007.png"),
    PLAYER_DEATH_8("assets/image/playerDeath/playerDeath_008.png"),
    PLAYER_DEATH_9("assets/image/playerDeath/playerDeath_009.png");
    

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

    public void setImg(Image img){
        this.img = img;
    }

}
