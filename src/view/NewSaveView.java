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

public class NewSaveView extends ViewUtil{

    public static NewSaveView inst = new NewSaveView();
    public static NewSaveView getInst(){return inst;}

    private static final String BG_IMG = "assets/image/background.jpg";

    private MenuButton startGameButton;
    private MenuButton backButton;

    private TextField saveNameTextField;

    private Text writeSaveNameText;

    private Label saveNameLabel;

    private void createButtons(){
        startGameButton = new MenuButton("START");
        backButton = new MenuButton("BACK");
    }


    @Override
    void setButtonClickEvents() {
        backButton.setOnMouseClicked(event -> goToView(event, NewGameView.getInst().initScene()));
        startGameButton.setOnMouseClicked(this::checkFileName);
    }

    @Override
    void setButtonPressEvents(Parent container) {
        container.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                goToView(event, NewGameView.getInst().initScene());
            }
        });
    }

    private void setEvents(Parent container){
        setButtonClickEvents();
        setButtonPressEvents(container);
    }

    private TextField createTextField(){
        TextField textfield = new TextField();
        textfield.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                checkFileName(event);
            }
        });
        return textfield;
    }

    private void createUI(){
        saveNameLabel = createBaseLabel("SAVE NAME");
        saveNameTextField = createTextField();
        writeSaveNameText = createText("NEW GAME", 460, 275, Font.font("Verdana", 50));
        createButtons();
        setErrorFieldPosition();
    }

    @Override
    public Parent initScene() {
        root = initBaseScene(BG_IMG);

        VBox menuContainer = createMenuContainer(450, 325, 10);
        createUI();
        setEvents(menuContainer);

        menuContainer.getChildren().addAll(saveNameLabel, saveNameTextField, startGameButton, backButton);
        root.getChildren().addAll(header, writeSaveNameText, errorField, menuContainer);

        compareErrorMessage();

        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {

    }

    private void startGameView(InputEvent event){
        GameModel.gameSettings.savePrevSave(NewGameView.getInst().getSaveNumber());
        GameController.getInstance().newGame(saveNameTextField.getText());
        startGameView(event, GameView.getInstance().initScene());
    }

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
