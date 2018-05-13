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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuView extends ViewUtil{

    private static MenuView inst = new MenuView();
    public static MenuView getInstance(){return inst; }

    private static final String BG_IMG = "assets/image/background.jpg";
    private MenuButton[] menuElements;

    private String lastError = "";

    private MenuView(){

    }

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

    private void showLevelSelect(InputEvent event){
        goToView(event, LevelSelectView.getInst().initScene());
        System.out.println("Totally showed u some tight levels");
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
        if(buttonName.equals("LEVEL SELECT")){
            showLevelSelect(event);
        }
        if(buttonName.equals("OPTIONS")){
            showOptions(event);
            System.out.println(SoundManager.getInst().getPlayer().getVolume());
        }
        if(buttonName.equals("EXIT")){
            exitGame();
        }
    }

    public Parent initScene(){

        root = new Pane();
        VBox mainMenu = new VBox();
        header.setX(300);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(header.getFont().font(100));
        root.setPrefSize(VIEW_WIDTH, VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(BG_IMG));

        MenuButton newGameButton = new MenuButton("NEW GAME");
        MenuButton continueButton = new MenuButton("CONTINUE");
        MenuButton multiplayerButton = new MenuButton("MULTIPLAYER");
        MenuButton loadGameButton = new MenuButton("LOAD GAME");
        MenuButton selectLevelButton = new MenuButton("LEVEL SELECT");
        MenuButton optionsButton = new MenuButton("OPTIONS");
        MenuButton exitButton = new MenuButton("EXIT");
        setErrorFieldPosition();

        newGameButton.setOnMouseClicked(this::createNewSave);
        continueButton.setOnMouseClicked(this::continueGame);
        multiplayerButton.setOnMouseClicked(this::loadMultiplayer);
        loadGameButton.setOnMouseClicked(this::loadGame);
        selectLevelButton.setOnMouseClicked(this::showLevelSelect);
        optionsButton.setOnMouseClicked(this::showOptions);
        exitButton.setOnMouseClicked(event -> System.exit(1));

        mainMenu.setFocusTraversable(true);
        mainMenu.setSpacing(10);
        mainMenu.setTranslateY(300);
        mainMenu.setTranslateX(450);

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

        // remember to add selectLevelButton
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
        menuElements[0].gainedFocus();
        root.getChildren().addAll(header, errorField, mainMenu);

        compareErrorMessage(lastError);

        return root;

    }

}
