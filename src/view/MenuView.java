package view;

import assets.java.AudioManager;
import controller.GameController;
import controller.UserInputs;
import exceptions.FileIOException;
import io.IOGameState;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuView extends ViewUtil{

    public static MenuView inst = new MenuView();
    public static MenuView getInstance(){return inst; }

    private static final String BG_IMG = "assets/image/background.jpg";
    private MenuButton newGameButton;
    private MenuButton loadGameButton;
    private MenuButton continueButton;
    private MenuButton multiplayerButton;
    private MenuButton selectLevelButton;
    private MenuButton optionsButton;
    private MenuButton exitButton;
    private MenuButton[] menuElements;
    private VBox mainMenu;

    private MenuView(){

    }

    public void createNewGame(InputEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        GameController.getInstance().newGame();
        Scene scene = new Scene(GameView.getInstance().initScene());
        stage.setScene(scene);
        UserInputs userInputs = new UserInputs(scene);
        System.out.println("Totally started a new game");
    }

    public void continueGame(InputEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        try {
            IOGameState.getInstance().loadGameState();
            GameController.getInstance().gameStart();
            Scene scene = new Scene(GameView.getInstance().initScene());
            stage.setScene(scene);
            UserInputs userInputs = new UserInputs(scene);

        } catch (FileIOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void showLevelSelect(InputEvent event){
        goToView(event, LevelSelectView.getInst().initScene());
        System.out.println("Totally showed u some tight levels");
    }

    public void createNewSave(InputEvent event){
        goToView(event, NewGameView.getInst().initScene());
    }

    public void showOptions(InputEvent event){
        goToView(event, OptionsView.getInst().initScene());
    }

    public void exitGame(){
        System.exit(0);
    }

    public void continueLastGame(InputEvent event){
        goToView(event, NewGameView.getInst().initScene());
    }

    public void loadGame(InputEvent event){
        goToView(event, LoadGameView.getInst().initScene());
    }

    public void loadMultiplayer(InputEvent event){
        goToView(event, MultiplayerView.getInst().initScene());
    }

    public boolean gameFileFound(){
        return IOGameState.getInstance().saveStateExists();
    }

    public void select(String buttonName, KeyEvent event){ //KeyEvent is only here so you can extract Stage from an event. Hacky, I know.
        /*if(buttonName == "NEW GAME"){
            //createNewSave(event);
            createNewGame(event);
        }
        if(buttonName == "CONTINUE"){
            continueGame(event);
        }
        if(buttonName == "LOAD GAME"){
            gameStart(event);
        }
        if(buttonName == "MULTIPLAYER"){
            loadMultiplayer(event);
        }
        if(buttonName == "LEVEL SELECT"){
            showLevelSelect(event);
        }
        if(buttonName == "OPTIONS"){
            showOptions(event);
        }
        if(buttonName == "EXIT"){
            exitGame();
        }*/
    }

    public Parent initScene(){

        AudioManager.getInstance().setMusic("MENU");

        /*
        På et eller annet tidspunkt må vi legge inn en greie som sjekker om spilleren har spilt før, slik at man første gang
        kun vil ha "NEW GAME". Etter at spilleren har spilt en gang bør dette bli til "LOAD GAME" og "NEW GAME". Tenker vi kan
        ha tre forskjellige save-slots.
         */
        root = new Pane();
        mainMenu = new VBox();
        Text header = new Text("SPACE GAME");
        header.setX(300);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(header.getFont().font(100));
        root.setPrefSize(VIEW_WIDTH, VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(BG_IMG));

        newGameButton = new MenuButton("NEW GAME");
        continueButton = new MenuButton("CONTINUE");
        multiplayerButton = new MenuButton("MULTIPLAYER");
        loadGameButton = new MenuButton("LOAD GAME");
        selectLevelButton = new MenuButton("LEVEL SELECT");
        optionsButton = new MenuButton("OPTIONS");
        exitButton = new MenuButton("EXIT");

        newGameButton.setOnMouseClicked(event -> createNewGame(event));
        continueButton.setOnMouseClicked(event -> continueGame(event));
        multiplayerButton.setOnMouseClicked(event -> loadMultiplayer(event));
        loadGameButton.setOnMouseClicked(event -> loadGame(event));
        selectLevelButton.setOnMouseClicked(event -> showLevelSelect(event));
        optionsButton.setOnMouseClicked(event -> showOptions(event));
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
                AudioManager.getInstance().nav();
            }
            if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE){
                select(menuElements[elementCounter].getText(), event);
                elementCounter = 0;
                AudioManager.getInstance().navSelect();
            }
        });

        if(gameFileFound()){
            mainMenu.getChildren().addAll(continueButton, loadGameButton, newGameButton, multiplayerButton, selectLevelButton, optionsButton, exitButton);
            menuElements = new MenuButton[]{continueButton, loadGameButton, newGameButton, multiplayerButton, selectLevelButton, optionsButton, exitButton};
        }
        else{
            mainMenu.getChildren().addAll(newGameButton, multiplayerButton, selectLevelButton, optionsButton, exitButton);
            menuElements = new MenuButton[]{newGameButton, multiplayerButton, selectLevelButton, optionsButton, exitButton};
        }
        menuElements[0].gainedFocus();
        root.getChildren().addAll(header, mainMenu);
        return root;

    }

}
