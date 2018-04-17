package view;

import controller.UserInputs;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.Main;

public class MenuView{

    public static MenuView inst = new MenuView();
    public MenuView(){}
    public static MenuView getInstance(){return inst; }

    public static final int MENU_WIDTH = 1200;
    public static final int MENU_HEIGHT = 800;
    private static final String BG_IMG = "assets/image/background.jpg";
    public Pane root;
    public Stage stage;

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

    public void createNewGame(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE){
            GameView.getInstance().setup();
            Scene scene = new Scene(GameView.getInstance().initGame());
            stage.setScene(scene);
            UserInputs userInputs = new UserInputs(scene);
            System.out.println("Totally started a new game");
        }
    }

    public void showLevelSelect(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE){
            System.out.println("Totally showed u some tight levels");
        }
    }

    public void showOptions(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE){
            System.out.println("Totally showed u sum looney options");
        }
    }

    public void exitGame(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE){
            System.exit(0);
        }
    }

    public Parent initScene(Stage stage) throws Exception{
        root = FXMLLoader.load(getClass().getClassLoader().getResource("assets/fxml/MenuView.fxml"));
        return root;
    }
}
