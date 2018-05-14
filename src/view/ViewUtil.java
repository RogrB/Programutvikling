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

/**
 * <h1>Abstract parent class for all Views</h1>
 * Every View in this application inherits from this class.
 *
 * @author Jonas Ege Carlsen
 */
public abstract class ViewUtil {

    /**
     * Width of the application.
     */
    public static final int VIEW_WIDTH = 1200;

    /**
     * Height of the application.
     */
    public static final int VIEW_HEIGHT = 800;

    /**
     * Default background image for every view.
     */
    final String BG_IMG = "assets/image/background/background_menu.png";

    /**
     * The base for all content in every view.
     */
    Pane root;

    /**
     * Header Text for the title of the game.
     */
    Text header = new Text("AERO");

    /**
     * Keeps track of what element is focused in an array full of elements.
     */
    int elementCounter = 0;

    /**
     * Warningfield used for every view. Displays error messages and warnings.
     */
    static WarningField errorField = new WarningField();

    /**
     * The last error message the application threw.
     */
    private static String lastErrorMessage = "";

    /**
     * Provides a basic root-pane template.
     * @param bg Background image of the view.
     * @return Returns a Pane.
     */
    Pane initBaseScene(String bg){
        root = new Pane();
        setHeader();
        root.setPrefSize(VIEW_WIDTH, VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(bg));
        return root;
    }

    /**
     * Method for creating a Text object.
     * @param textInput Desired text
     * @param x X coordinate for Text object.
     * @param y Y coordinate for Text object.
     * @param font Font for Text object.
     * @return Returns a Text object.
     */
    Text createText(String textInput, int x, int y, Font font){
        Text text = new Text(textInput);
        text.setX(x);
        text.setY(y);
        text.setFill(Color.WHITE);
        text.setFont(font);
        return text;
    }

    /**
     * Method used for switching between scenes. Requires
     * an event to be able to find the Stage.
     * @param event The event that this function is called with.
     * @param node A root Pane from a View.
     */
    void goToView(InputEvent event, Parent node){
        Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
        Scene scene = new Scene(node);
        stage.setScene(scene);
        elementCounter = 0;
    }

    /**
     * Method used for starting a new game. Requires
     * and event to be able to find the Stage.
     * @param event The event that this function is called with.
     * @param node A root Pane from a View.
     */
    void startGameView(InputEvent event, Parent node){
        Stage stage = (Stage) ((Node)event.getTarget()).getScene().getWindow();
        Scene scene = new Scene(node);
        stage.setScene(scene);
        UserInputs userInputs = new UserInputs(scene);
    }

    /**
     * Creates a background image to be used for a View.
     * @param BG_IMG The location of the image file.
     * @return Returns a Background
     */
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

    /**
     * Method for moving through an array of menu elements.
     * This function controls the value of elementCounter,
     * which in turn keeps track of the selected element.
     * @param code The KeyCode pressed in menu container.
     * @param menuElements An array of elements from a View.
     */
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

    /**
     * Disables and enables menu elements based on
     * the currently focused element.
     * @param currentFocus The currently focused menu element.
     * @param length Length of menu elements to iterate through.
     * @param menuElements An array of elements from a View.
     */
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

    /**
     * Sets the button click events for the view.
     */
    abstract void setButtonClickEvents();

    /**
     * Sets the button press events for menu container.
     * @param container The menu container of the view.
     */
    abstract void setButtonPressEvents(Parent container);

    /**
     * Sets the view events.
     * @param container The menu container of the view.
     */
    void setEvents(Parent container){
        setButtonClickEvents();
        setButtonPressEvents(container);

    }

    /**
     * Creates a basic Label.
     * @param labelText The Label text
     * @return Returns a Label
     */
    Label createBaseLabel(String labelText){
        Label label = new Label(labelText);
        label.setTextFill(Color.WHITE);
        return label;
    }

    /**
     * Creates a basic menu container.
     * @param x x coordinates for container.
     * @param y y coordinates for container.
     * @param spacing spacing for container.
     * @return Returns a VBox.
     */
    VBox createMenuContainer(int x, int y, int spacing){
        VBox menuContainer = new VBox();
        menuContainer.setTranslateX(x);
        menuContainer.setTranslateY(y);
        menuContainer.setSpacing(spacing);
        return menuContainer;
    }

    /**
     * Calls other methods and returns a finished root node.
     * @return Returns a root node / Pane.
     */
    public abstract Parent initScene();

     /**
     * Method for handling selection of menu elements
     * @param buttonName inputs the name of the button that was pressed
     * @param event inputs the event
     */
    public abstract void select(String buttonName, KeyEvent event);

    /**
     * Method for placing the errorField on a view.
     */
    void setErrorFieldPosition(){
        errorField.setTranslateX(200);
        errorField.setTranslateY(750);
    }

    /**
     * Returns the {@code elementCounter} variable.
     * @return Returns the elementCounter.
     */
    public int getElementCounter(){
        return elementCounter;
    }

    /**
     * Method to set error messages to display in the WarningField.
     * @param text The error text.
     */
    public static void setError(String text){
        lastErrorMessage = text;
        errorField.changeText(text);
    }

    /**
     * Method to check if an error occured
     * while switching views.
     */
    void compareErrorMessage(){
        if(!lastErrorMessage.equals("")){
            errorField.changeText(lastErrorMessage);
            lastErrorMessage = "";
        }
    }

    /**
     * Method used to set the Game Title properties.
     */
    private void setHeader(){
        header.setX(485);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(Font.font("Verdana", 100));
    }
}
