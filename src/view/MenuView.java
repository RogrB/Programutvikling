package view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MenuView{

    private static MenuView inst = new MenuView();
    private MenuView(){}
    public static MenuView getInstance(){return inst; }

    public static final int MENU_WIDTH = 1200;
    public static final int MENU_HEIGHT = 800;
    private static final String BG_IMG = "assets/image/background.jpg";

    public Background getBackGroundImage(){
        BackgroundImage bg = new BackgroundImage(
                new Image(BG_IMG),
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

    public Parent initScene(){
        Pane root = new Pane();
        VBox menuContent = new VBox();
        root.setPrefSize(MENU_WIDTH, MENU_HEIGHT);
        root.setBackground(getBackGroundImage());
        Button startButton = new Button("Start");
        Button optionsButton = new Button("Options");
        Button exitButton = new Button("Exit");
        menuContent.getChildren().addAll(startButton, optionsButton, exitButton);
        root.getChildren().addAll(menuContent);
        return root;
    }
}
