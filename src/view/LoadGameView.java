package view;

import controller.GameController;
import exceptions.FileIOException;
import io.IOManager;
import javafx.scene.Parent;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.GameModel;
import view.customElements.MenuButton;

import java.util.ArrayList;

/**
 * <h1>Menu for loading save files.</h1>
 * The class {@code LoadGameView} extends {@code ViewUtil}.
 *
 * @author Jonas Ege Carlsen
 */
public class LoadGameView extends ViewUtil{

    /**
     * The singleton object
     */
    private static LoadGameView inst = new LoadGameView();

    /**
     * Method to access singleton object.
     * @return Returns a reference to the singleton object.
     */
    public static LoadGameView getInst(){return inst;}

    /**
     * Private constructor
     */
    private LoadGameView(){}

    /**
     * First save button
     */
    private MenuButton save1;

    /**
     * Second save button
     */
    private MenuButton save2;

    /**
     * Third save button
     */
    private MenuButton save3;

    /**
     * A button to go back to Main Menu with
     */
    private MenuButton backButton;

    /**
     * Text that displays the purpose of the scene.
     */
    private Text loadSaveText;

    /**
     * An array of Menu Buttons used to keep track of what button is currently selected.
     */
    private MenuButton[] menuElements;

    /**
     * Creates all the buttons in the view.
     */
    private void createButtons(){
        save1 = new MenuButton("SAVE 1");
        save1.setValue(0);
        save2 = new MenuButton("SAVE 2");
        save2.setValue(1);
        save3 = new MenuButton("SAVE 3");
        save3.setValue(2);
        backButton = new MenuButton("BACK");
        backButton.setValue(3);
    }

    /**
     * @return Returns a temporary array of save buttons.
     */
    private ArrayList<MenuButton> createTempArray(){
        ArrayList<MenuButton> AL = new ArrayList<>();
        AL.add(save1);
        AL.add(save2);
        AL.add(save3);
        checkSaves(AL);
        return AL;
    }

    /**
     * Checks if the save file assosicated with a button
     * contains anything. If not, make it inaccessible
     * and remove from the temporary array.
     * @param tempElements Temporary array to use.
     */
    private void checkSaves(ArrayList<MenuButton> tempElements){
        int counter = 0;
        for(int i = 0; i < 3; i++){
            if(!IOManager.getInstance().saveStateExists(i)){
                tempElements.get(counter).setColor(Color.GREY);
                tempElements.remove(counter);
            }
            else{
                try{
                    tempElements.get(counter).setText(IOManager.getInstance().getGameState(counter).getStateName());
                }
                catch(FileIOException e){}
                counter++;
            }
        }
    }

    /**
     * Creates the MenuElements array based on the temporary array
     * that is used in {@code checkSaves} so that one can proparly
     * navigate the buttons.
     * @param tempElements The previously used temporary array.
     * @return Returns an array of Menu Buttons.
     */
    private MenuButton[] createMenuElementsArray(ArrayList<MenuButton> tempElements){
        switch(tempElements.size()){
            case 1:
                menuElements = new MenuButton[]{tempElements.get(0), backButton};
                break;
            case 2:
                menuElements = new MenuButton[]{tempElements.get(0), tempElements.get(1), backButton};
                break;
            case 3:
                menuElements = new MenuButton[]{tempElements.get(0), tempElements.get(1), tempElements.get(2), backButton};
                break;
        }
        return menuElements;
    }

    /**
     * Sets the button click events of the view.
     */
    @Override
    void setButtonClickEvents() {
        save1.setOnMouseClicked(event -> loadGame(event, 0));
        save2.setOnMouseClicked(event -> loadGame(event, 1));
        save3.setOnMouseClicked(event -> loadGame(event, 2));
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
                elementCounter = 0;
            }
        });
    }

    /**
     * Creates the user interface elements of the view.
     */
    private void createUI(){
        createButtons();
        loadSaveText = createText("LOAD SAVE FILE", 500, 275, Font.font("Verdana", 50));
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

        VBox saveFiles = new VBox();
        VBox menuContainer = createMenuContainer(395, 250, 40);
        createUI();

        setEvents(menuContainer);

        ArrayList<MenuButton> tempElements = createTempArray();
        menuElements = createMenuElementsArray(tempElements);

        saveFiles.getChildren().addAll(save1, save2, save3);
        saveFiles.setSpacing(10);
        menuContainer.getChildren().addAll(loadSaveText, saveFiles, backButton);
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
        switch (menuElements[elementCounter].getValue()) {
            case 0:
                loadGame(event,0);
                break;
            case 1:
                loadGame(event,1);
                break;
            case 2:
                loadGame(event,2);
                break;
            case 3:
                goToView(event, MenuView.getInstance().initScene());
                break;
            }
    }

    /**
     * Loads a game save and launches the game from the last save point.
     * @param event The event that this function is called from.
     * @param gameSave The number of the game save that is to be loaded.
     */
    private void loadGame(InputEvent event, int gameSave){
        int prevSave = GameModel.gameSettings.getPrevSave();
        GameModel.gameSettings.savePrevSave(gameSave);
        if (IOManager.getInstance().saveStateExists()){
            try {
                IOManager.getInstance().loadGameState();
                GameController.getInstance().loadGame();
                startGameView(event, GameView.getInstance().initScene());
            } catch (FileIOException e) {
<<<<<<< HEAD
                System.err.println(e.getMessage());
=======
>>>>>>> c057d37d34ec9c44e3fb68a49311e79c650bba86
                GameModel.gameSettings.savePrevSave(prevSave);
            }
        } else {
            GameModel.gameSettings.savePrevSave(prevSave);
        }
    }
}
