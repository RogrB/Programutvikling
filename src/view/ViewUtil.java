package view;

import assets.java.SoundManager;
import controller.UserInputs;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.customElements.WarningField;

public abstract class ViewUtil {

    public static final int VIEW_WIDTH = 1200;
    public static final int VIEW_HEIGHT = 800;
    protected final String BG_IMG = "assets/image/background/background_menu.png";
    Pane root;
    Text header = new Text("AERO");
    int elementCounter = 0;
    static WarningField errorField = new WarningField();
    private static String lastErrorMessage = "";

    Pane initBaseScene(String bg){
        root = new Pane();
        setHeader();
        root.setPrefSize(VIEW_WIDTH, VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(bg));
        return root;
    }

    Text createText(String textInput, int x, int y, Font font){
        Text text = new Text(textInput);
        text.setX(x);
        text.setY(y);
        text.setFill(Color.WHITE);
        text.setFont(font);
        return text;
    }

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

    private Background getBackGroundImage(String BG_IMG){
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

    abstract void setButtonClickEvents();
    abstract void setButtonPressEvents(Parent container);

    Label createBaseLabel(String labelText){
        Label label = new Label(labelText);
        label.setTextFill(Color.WHITE);
        return label;
    }

    VBox createMenuContainer(int x, int y, int spacing){
        VBox menuContainer = new VBox();
        menuContainer.setTranslateX(x);
        menuContainer.setTranslateY(y);
        menuContainer.setSpacing(spacing);
        return menuContainer;
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

    void compareErrorMessage(){
        if(!lastErrorMessage.equals("")){
            errorField.changeText(lastErrorMessage);
        }
    }

    private void setHeader(){
        header.setX(485);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(Font.font("Verdana", 100));
    }
}
