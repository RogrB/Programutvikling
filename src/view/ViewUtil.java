package view;

import assets.java.AudioManager;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class ViewUtil {

    public static final int VIEW_WIDTH = 1200;
    public static final int VIEW_HEIGHT = 800;
    public Pane root;
    public Text header = new Text("SPACE GAME");
    public int elementCounter = 0;
    WarningField testField;

    public void goToView(InputEvent event, Parent node){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Pane root = new Pane();
        root.getChildren().add(node);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        elementCounter = 0;
    }

    public Background getBackGroundImage(String BG_IMG){
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

    public void traverseMenu(KeyCode code, Parent[] menuElements){
        AudioManager.getInstance().nav();
        if(code == KeyCode.DOWN){
            if(elementCounter == menuElements.length -1){
                elementCounter = 0;
            }
            else{
                elementCounter++;
            }
        }
        if(code == KeyCode.UP){
            if(elementCounter == 0){
                elementCounter = menuElements.length -1;
            }
            else{
                elementCounter--;
            }
        }
    }

    public abstract Parent initScene();
    public abstract void select(String buttonName, KeyEvent event);



}
