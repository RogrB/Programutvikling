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

public class MenuView extends ViewUtil{

    private static MenuView inst = new MenuView();
    public static MenuView getInstance(){return inst; }

    private static final String BG_IMG = "assets/image/background.jpg";
    private MenuButton[] menuElements;

    private  MenuButton newGameButton;
    private MenuButton continueButton;
    private MenuButton multiplayerButton;
    private MenuButton loadGameButton;
    private MenuButton optionsButton;
    private MenuButton exitButton;

    private void continueGame(InputEvent event){
        Stage stage = (Stage) ((Node)event.getTarget()).getScene().getWindow();
        try {
            IOManager.getInstance().loadGameState();
            GameController.getInstance().loadGame();
            Scene scene = new Scene(GameView.getInstance().initScene());
            stage.setScene(scene);
            UserInputs userInputs = new UserInputs(scene);

        } catch (FileIOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void createNewSave(InputEvent event){
        goToView(event, NewGameView.getInst().initScene());
    }

    private void showOptions(InputEvent event){
        goToView(event, OptionsView.getInst().initScene());
    }

    private void exitGame(){
        System.exit(0);
    }

    private void loadGame(InputEvent event){
        goToView(event, LoadGameView.getInst().initScene());
    }

    private void loadMultiplayer(InputEvent event){
        goToView(event, MultiplayerView.getInst().initScene());
    }

    private boolean gameFileFound(){
        return IOManager.getInstance().saveStateExists();
    }

    public void select(String buttonName, KeyEvent event){ //KeyEvent is only here so you can extract Stage from an event. Hacky, I know.
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
            System.out.println(SoundManager.getInst().getPlayer().getVolume());
        }
        if(buttonName.equals("EXIT")){
            exitGame();
        }
    }

    private void createButtons(){
        newGameButton = new MenuButton("NEW GAME");
        continueButton = new MenuButton("CONTINUE");
        multiplayerButton = new MenuButton("MULTIPLAYER");
        loadGameButton = new MenuButton("LOAD GAME");
        optionsButton = new MenuButton("OPTIONS");
        exitButton = new MenuButton("EXIT");
    }


    void setButtonClickEvents(){
        newGameButton.setOnMouseClicked(this::createNewSave);
        continueButton.setOnMouseClicked(this::continueGame);
        multiplayerButton.setOnMouseClicked(this::loadMultiplayer);
        loadGameButton.setOnMouseClicked(this::loadGame);
        optionsButton.setOnMouseClicked(this::showOptions);
        exitButton.setOnMouseClicked(event -> System.exit(1));
    }

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

    private VBox createMainMenuContainer(){
        VBox mainMenu = new VBox();
        mainMenu.setFocusTraversable(true);
        mainMenu.setSpacing(10);
        mainMenu.setTranslateY(300);
        mainMenu.setTranslateX(450);
        return mainMenu;
    }

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


    public Parent initScene(){

        root = initBaseScene(BG_IMG);

        VBox mainMenu = createMainMenuContainer();

        setErrorFieldPosition();

        createButtons();
        setButtonClickEvents();
        setButtonPressEvents(mainMenu);

        decideMenuLayout(mainMenu);


        menuElements[0].gainedFocus();
        root.getChildren().addAll(header, errorField, mainMenu);

        compareErrorMessage("");
        return root;

    }
}
