package view;

import assets.java.SoundManager;
import controller.UserInputs;
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
    Pane root;
    Text header = new Text("SPACE GAME");
    int elementCounter = 0;
    static WarningField errorField = new WarningField();
    private static String lastErrorMessage = "";

    void goToView(InputEvent event, Parent node){
        Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
        Pane root = new Pane();
        root.getChildren().add(node);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        elementCounter = 0;
    }

    void startGameView(InputEvent event, Parent node){
        Stage stage = (Stage) ((Node)event.getTarget()).getScene().getWindow();
        Scene scene = new Scene(node);
        stage.setScene(scene);
        UserInputs userInputs = new UserInputs(scene);
    }

    Background getBackGroundImage(String BG_IMG){
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
        SoundManager.getInst().nav();
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

    void setFocusButtons(int currentFocus, int length, Parent[] menuElements){
        for(int i = 0; i < length; i++){
            if(i != currentFocus){
                menuElements[i].setFocusTraversable(false);
            }
            else{
                menuElements[currentFocus].setFocusTraversable(true);
                menuElements[currentFocus].requestFocus();
            }
        }
    }

    public abstract Parent initScene();
    public abstract void select(String buttonName, KeyEvent event);
    void setErrorFieldPosition(){
        errorField.setTranslateX(200);
        errorField.setTranslateY(750);
    }

    public int getElementCounter(){
        return elementCounter;
    }

    public static void setError(String text){
        lastErrorMessage = text;
        errorField.changeText(text);
    }

    void compareErrorMessage(String msg){
        if(!lastErrorMessage.equals(msg)){
            errorField.changeText(lastErrorMessage);
        }
    }
}
