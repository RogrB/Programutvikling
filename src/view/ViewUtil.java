package view;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

public abstract class ViewUtil {

    public static final int GAME_WIDTH = 1200;
    public static final int GAME_HEIGHT = 800;

    public Background getBackGroundImage(){
        Image img = new Image("assets/image/background.jpg");
        BackgroundImage bg = new BackgroundImage(
                img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(
                        BackgroundSize.AUTO,
                        BackgroundSize.AUTO,
                        false,
                        false,
                        true,
                        false
                )
        );
        return new Background(bg);
    }

}
