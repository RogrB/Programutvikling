package view;

import controller.GameController;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.GameModel;
import view.customElements.MenuButton;

import java.util.Objects;

/**
 * Menu for choosing a name for a new save file.
 * The class {@code NewSaveView} extends {@code ViewUtil}.
 *
 * @author Jonas Ege Carlsen
 */
public class NewSaveView extends ViewUtil{

    /**
     * The singleton object
     */
    public static NewSaveView inst = new NewSaveView();

    /**
     * Method to access the singleton object.
     * @return Returns a reference to the singleton object.
     */
    public static NewSaveView getInst(){return inst;}

    /**
     * Button used to start the game.
     */
    private MenuButton startGameButton;

    /**
     * Button used to go back to {@code NewGameView}.
     */
    private MenuButton backButton;

    /**
     * Textfield used to type the name of the new save file.
     */
    private TextField saveNameTextField;

    /**
     * Text that displays the purpose of the scene.
     */
    private Text titleText;

    /**
     * Label that describes what the textfield is for.
     */
    private Label saveNameLabel;

    /**
     * Method that creates all the buttons in a view.
     */
    private void createButtons(){
        startGameButton = new MenuButton("START");
        backButton = new MenuButton("BACK");
    }

    /**
     * Sets the button click events for the view.
     */
    @Override
    void setButtonClickEvents() {
        backButton.setOnMouseClicked(event -> goToView(event, NewGameView.getInst().initScene()));
        startGameButton.setOnMouseClicked(this::checkFileName);
    }

    /**
     * Sets the button press events for the menu container.
     * @param container The menu container of the view.
     */
    @Override
    void setButtonPressEvents(Parent container) {
        container.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                goToView(event, NewGameView.getInst().initScene());
            }
        });
    }

    /**
     * Sets the view events.
     * @param container The menu container of the view.
     */
    private void setEvents(Parent container){
        setButtonClickEvents();
        setButtonPressEvents(container);
    }

    /**
     * Creates a TextField and attaches an
     * event handler to it.
     * @return Returns a TextField.
     */
    private TextField createTextField(){
        TextField textfield = new TextField();
        textfield.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                checkFileName(event);
            }
        });
        return textfield;
    }

    /**
     * Creates all the user interface elements of the view.
     */
    private void createUI(){
        saveNameLabel = createBaseLabel("SAVE NAME");
        saveNameTextField = createTextField();
        titleText = createText("NEW GAME", 460, 275, Font.font("Verdana", 50));
        createButtons();
        setErrorFieldPosition();
    }

    /**
     * The main method of the View. Calls other methods and returns
     * a finished root node.
     * @return Returns a root node / Pane.
     */
    @Override
    public Parent initScene() {
        root = initBaseScene(BG_IMG);

        VBox menuContainer = createMenuContainer(450, 325, 10);
        createUI();
        setEvents(menuContainer);

        menuContainer.getChildren().addAll(saveNameLabel, saveNameTextField, startGameButton, backButton);
        root.getChildren().addAll(header, titleText, errorField, menuContainer);

        compareErrorMessage();

        return root;
    }
    /**
     * Method to call different functions based off of a value.
     * Overridden from {@code ViewUtil}, although it is not used
     * in this view.
     * @param buttonName The name of the button.
     * @param event The event that this function is called from.
     */
    @Override
    public void select(String buttonName, KeyEvent event) {

    }

    /**
     * Method that initiates a new game.
     * @param event The event that this function is called from.
     */
    private void startGameView(InputEvent event){
        GameModel.gameSettings.savePrevSave(NewGameView.getInst().getSaveNumber());
        GameController.getInstance().newGame(saveNameTextField.getText());
        startGameView(event, GameView.getInstance().initScene());
    }

    /**
     * Method that checks if the file name is valid.
     * @param event The event that this function is called from.
     */
    private void checkFileName(InputEvent event){
        if(!Objects.equals(saveNameTextField.getText(), "") && saveNameTextField.getText().length() < 15){
            startGameView(event);
        }
        else if(Objects.equals(saveNameTextField.getText(), "")){
            errorField.changeText("Save name can't be blank!");
        }
        else if(saveNameTextField.getText().length() > 15){
            errorField.changeText("Save name is too long!");
        }
    }
}
