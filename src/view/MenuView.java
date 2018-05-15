package view;

import assets.java.SoundManager;
import controller.GameController;
import controller.UserInputs;
import exceptions.FileIOException;
import io.IOManager;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import view.customElements.MenuButton;

/**
 * <h1>The Main Menu</h1>
 * The class {@code MenuView} extends {@code ViewUtil}.
 *
 * @author Jonas Ege Carlsen
 */
public class MenuView extends ViewUtil{

    /**
     * The singleton object
     */
    private static MenuView inst = new MenuView();

    /**
     * Method to access the singleton object.
     * @return Returns a reference to the singleton object.
     */
    public static MenuView getInstance(){return inst; }

    /**
     * An array of Menu Buttons used to keep track of what button is currently selected.
     */
    private MenuButton[] menuElements;

    /**
     * Button used to start a new save file.
     */
    private  MenuButton newGameButton;

    /**
     * Button used to continue the previous game.
     */
    private MenuButton continueButton;

    /**
     * Button used to go to the Multiplayer.
     */
    private MenuButton multiplayerButton;

    /**
     * Button used to load a save file.
     */
    private MenuButton loadGameButton;

    /**
     * Button used to go to options.
     */
    private MenuButton optionsButton;

    /**
     * Button used to exit the game.
     */
    private MenuButton exitButton;

    /**
     * Method to resume the last played gamesave.
     * @param event The event that this function is called from.
     */
    private void continueGame(InputEvent event){
        Stage stage = (Stage) ((Node)event.getTarget()).getScene().getWindow();
        try {
            IOManager.getInstance().loadGameState();
            GameController.getInstance().loadGame();
            Scene scene = new Scene(GameView.getInstance().initScene());
            stage.setScene(scene);
            UserInputs userInputs = new UserInputs(scene);

        } catch (FileIOException e) {
            ViewUtil.setError("Can't load previous game!");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method that takes you to the {@code NewGameView}.
     * @param event The event that this function is called from.
     */
    private void createNewSave(InputEvent event){
        goToView(event, NewGameView.getInst().initScene());
    }

    /**
     * Method that takes you to the {@code OptionsView}.
     * @param event The event that this function is called from.
     */
    private void showOptions(InputEvent event){
        goToView(event, OptionsView.getInst().initScene());
    }

    /**
     * Method that exits the game.
     */
    private void exitGame(){
        System.exit(0);
    }

    /**
     * Method that takes you to the {@code LoadGameView}.
     * @param event The event that this function is called from.
     */
    private void loadGame(InputEvent event){
        goToView(event, LoadGameView.getInst().initScene());
    }

    /**
     * Method that takes you to the {@code MultiplayerView}.
     * @param event The event that this function is called from.
     */
    private void loadMultiplayer(InputEvent event){
        goToView(event, MultiplayerView.getInst().initScene());
    }

    /**
     * Checks to see if a game has been played before.
     * @return Returns a boolean.
     */
    private boolean gameFileFound(){
        return IOManager.getInstance().saveStateExists();
    }

    /**
     * Method for handling selection of menu elements
     * Overridden from {@code ViewUtil}.
     * @param buttonName The name of the button.
     * @param event The event that this function is called from.
     */
    public void select(String buttonName, KeyEvent event){
        SoundManager.getInst().navSelect();
        if(buttonName.equals("NEW GAME")){
            createNewSave(event);
        }
        if(buttonName.equals("CONTINUE")){
            continueGame(event);
        }
        if(buttonName.equals("LOAD GAME")){
            loadGame(event);
        }
        if(buttonName.equals("MULTIPLAYER")){
            loadMultiplayer(event);
        }
        if(buttonName.equals("OPTIONS")){
            showOptions(event);
        }
        if(buttonName.equals("EXIT")){
            exitGame();
        }
    }

    /**
     * Creates all the buttons in the view.
     */
    private void createButtons(){
        newGameButton = new MenuButton("NEW GAME");
        continueButton = new MenuButton("CONTINUE");
        multiplayerButton = new MenuButton("MULTIPLAYER");
        loadGameButton = new MenuButton("LOAD GAME");
        optionsButton = new MenuButton("OPTIONS");
        exitButton = new MenuButton("EXIT");
    }

    /**
     * Sets the button click events of the view.
     */
    @Override
    void setButtonClickEvents(){
        newGameButton.setOnMouseClicked(this::createNewSave);
        continueButton.setOnMouseClicked(this::continueGame);
        multiplayerButton.setOnMouseClicked(this::loadMultiplayer);
        loadGameButton.setOnMouseClicked(this::loadGame);
        optionsButton.setOnMouseClicked(this::showOptions);
        exitButton.setOnMouseClicked(event -> System.exit(1));
    }

    /**
     * Sets the button press events of the menu container.
     * @param mainMenu The menu container of the view.
     */
    @Override
    void setButtonPressEvents(Parent mainMenu){
        mainMenu.setOnKeyPressed(event -> {
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
     * Method to decide what elements should be on the main menu
     * based off of whether a game has been played before and whether
     * the last game was lost.
     * @param mainMenu The main menu container.
     */
    private void decideMenuLayout(VBox mainMenu){
        if(gameFileFound() && GameController.getInstance().getLastGameLost()){
            mainMenu.getChildren().addAll(newGameButton, loadGameButton, multiplayerButton, optionsButton, exitButton);
            menuElements = new MenuButton[]{newGameButton, loadGameButton, multiplayerButton, optionsButton, exitButton};
        }
        else if(gameFileFound()){
            mainMenu.getChildren().addAll(continueButton, loadGameButton, newGameButton, multiplayerButton, optionsButton, exitButton);
            menuElements = new MenuButton[]{continueButton, loadGameButton, newGameButton, multiplayerButton, optionsButton, exitButton};
        }
        else{
            mainMenu.getChildren().addAll(newGameButton, multiplayerButton, optionsButton, exitButton);
            menuElements = new MenuButton[]{newGameButton, multiplayerButton, optionsButton, exitButton};
        }
    }

    /**
     * The main method of the View. Calls other methods and returns
     * a finished root node.
     * @return Returns a root node / Pane.
     */
    public Parent initScene(){

        root = initBaseScene(BG_IMG);

        VBox menuContainer = createMenuContainer(450, 300, 10);
        menuContainer.setFocusTraversable(true);

        setErrorFieldPosition();
        createButtons();
        setEvents(menuContainer);

        decideMenuLayout(menuContainer);

        menuElements[0].gainedFocus();
        root.getChildren().addAll(header, errorField, menuContainer);

        compareErrorMessage();
        return root;
    }
}
