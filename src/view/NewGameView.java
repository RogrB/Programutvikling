package view;

import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.customElements.MenuButton;

/**
 * <h1>Menu for selecting a new save file</h1>
 * The class {@code NewGameView} extends {@code ViewUtil}.
 *
 * @author  Jonas Ege Carlsen
 */
public class NewGameView extends ViewUtil{

    /**
     * The singleton object.
     */
    private static NewGameView inst = new NewGameView();

    /**
     * Method to access singleton object.
     * @return Returns a reference to the singleton object.
     */
    public static NewGameView getInst(){return inst;}

    /**
     * The number of the save that is selected. Defaults to -1.
     */
    private int saveNumber = -1;

    /**
     * First save button.
     */
    private MenuButton save1;

    /**
     * Second save button.
     */
    private MenuButton save2;

    /**
     * Third save button.
     */
    private MenuButton save3;

    /**
     * Button used to go back to {@code NewSaveView}.
     */
    private MenuButton backButton;

    /**
     * Text used to display the purpose of the View.
     */
    private Text selectSaveText;

    /**
     * An array of Menu Buttons used to keep track of what button is currently selected.
     */
    private MenuButton[] menuElements;

    /**
     * Creates all the buttons in the view.
     */
    private void createButtons(){
        save1 = new MenuButton("SAVE 1");
        save2 = new MenuButton("SAVE 2");
        save3 = new MenuButton("SAVE 3");
        backButton = new MenuButton("BACK");
    }

    /**
     * Sets the button click events for the view.
     */
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

    /**
     * Sets the button press events of the menu container.
     * @param container The menu container of the view.
     */
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

    /**
     * Creates the user interface elements of the view.
     */
    private void createUI(){
        createButtons();
        selectSaveText = createText("SELECT SAVE FILE", 500, 275, Font.font("Verdana", 50));
        setErrorFieldPosition();
    }

    /**
     * Calls other methods and returns a finished root node.
     * @return Returns a root node / Pane.
     */
    @Override
    public Parent initScene() {
        root = initBaseScene(BG_IMG);

        VBox saveFiles = new VBox();
        VBox menuContainer = createMenuContainer(370, 250, 40);

        createUI();
        setEvents(menuContainer);

        menuElements = new MenuButton[]{save1, save2, save3, backButton};
        saveFiles.getChildren().addAll(save1, save2, save3);
        saveFiles.setSpacing(10);
        menuContainer.getChildren().addAll(selectSaveText, saveFiles, backButton);
        menuContainer.setFocusTraversable(true);
        menuElements[0].gainedFocus();
        root.getChildren().addAll(header, errorField, menuContainer);

        compareErrorMessage();

        return root;
    }

    /**
     * Method for handling selection of menu elements
     * Overridden from {@code ViewUtil}, although {@code buttonName}
     * is not used in this view.
     * @param buttonName The name of the button.
     * @param event The event that this function is called from.
     */
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

    /**
     * Returns the value of the save button that was clicked.
     * @return Returns the saveNumber-int.
     */
    int getSaveNumber(){return saveNumber; }
}
