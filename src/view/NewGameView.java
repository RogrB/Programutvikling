package view;

import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.GameModel;

import java.awt.*;

public class NewGameView extends ViewUtil{

    public static NewGameView inst = new NewGameView();
    public static NewGameView getInst(){return inst;}


    private static final String BG_IMG = "assets/image/background.jpg";

    private int saveNumber = -1;

    private MenuButton save1;
    private MenuButton save2;
    private MenuButton save3;
    private MenuButton backButton;

    private MenuButton[] menuElements;

    private void createButtons(){
        save1 = new MenuButton("SAVE 1");
        save2 = new MenuButton("SAVE 2");
        save3 = new MenuButton("SAVE 3");
        backButton = new MenuButton("BACK");
    }


    @Override
    void setButtonClickEvents() {
        save1.setOnMouseClicked(event -> {
            saveNumber = 0;
            goToView(event, NewSaveView.getInst().initScene());
        });

        save2.setOnMouseClicked(event -> {
            saveNumber = 1;
            goToView(event, NewSaveView.getInst().initScene());
        });
        save3.setOnMouseClicked(event -> {
            saveNumber = 2;
            goToView(event, NewSaveView.getInst().initScene());
        });
        backButton.setOnMouseClicked(event -> goToView(event, MenuView.getInstance().initScene()));
    }

    @Override
    void setButtonPressEvents(Parent container) {
        container.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                goToView(event, MenuView.getInstance().initScene());
            }
            if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN){
                menuElements[elementCounter].lostFocus();
                traverseMenu(event.getCode(), menuElements);
                menuElements[elementCounter].gainedFocus();
            }
            if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE){
                select(menuElements[elementCounter].getText(), event);
            }
        });
    }

    @Override
    public Parent initScene() {
        root = initBaseScene(BG_IMG);

        VBox saveFiles = new VBox();
        VBox menuContainer = createMenuContainer(410, 250, 40);

        Text selectSaveText = createText("SELECT SAVE FILE", 500, 275, Font.font("Verdana", 50));
        createButtons();

        setErrorFieldPosition();

        setButtonClickEvents();
        setButtonPressEvents(menuContainer);

        menuElements = new MenuButton[]{save1, save2, save3, backButton};
        saveFiles.getChildren().addAll(save1, save2, save3);
        saveFiles.setSpacing(10);
        menuContainer.getChildren().addAll(selectSaveText, saveFiles, backButton);
        menuContainer.setFocusTraversable(true);
        menuElements[0].gainedFocus();
        root.getChildren().addAll(header, errorField, menuContainer);

        compareErrorMessage("");

        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {
        if(elementCounter >= 0 && elementCounter < 3){
            saveNumber = elementCounter;
            goToView(event, NewSaveView.getInst().initScene());
        }
        else if(elementCounter == 3){
            goToView(event, MenuView.getInstance().initScene());
        }
    }

    int getSaveNumber(){return saveNumber; }
}
