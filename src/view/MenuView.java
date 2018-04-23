package view;

import controller.GameController;
import controller.UserInputs;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameModel;

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
    private int elementCounter = 0;
    private VBox mainMenu;
    public MenuView(){
    }

    public void createNewGame(KeyEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        GameController.getInstance().gameStart();
        Scene scene = new Scene(GameView.getInstance().initScene());
        stage.setScene(scene);
        UserInputs userInputs = new UserInputs(scene);
        System.out.println("Totally started a new game");
    }

    public void traverseMenu(KeyCode code){
        int oldElementCounter = elementCounter;
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
        menuElements[oldElementCounter].lostFocus();
        menuElements[elementCounter].gainedFocus();
    }

    public void showLevelSelect(KeyEvent event){
        goToView(event, LevelSelectView.getInst().initScene());
        System.out.println("Totally showed u some tight levels");
    }

    public void createNewSave(KeyEvent event){
        goToView(event, NewGameView.getInst().initScene());
    }

    public void showOptions(KeyEvent event){
        goToView(event, OptionsView.getInst().initScene());
    }

    public void exitGame(){
        System.exit(0);
    }

    public void continueLastGame(){
        System.out.println("Clicked continue");
    }

    public void loadGame(KeyEvent event){
        goToView(event, LoadGameView.getInst().initScene());
    }

    public void loadMultiplayer(KeyEvent event){
        goToView(event, MultiplayerView.getInst().initScene());
    }

    public boolean gameFileFound(){
        return true; //EDIT THIS TO EDIT MENU LAYOUT
    }

    public void select(String buttonName, KeyEvent event){ //KeyEvent is only here so you can extract Stage from an event. Hacky, I know.
        if(buttonName == "NEW GAME"){
            //createNewSave(event);
            createNewGame(event);
        }
        if(buttonName == "CONTINUE"){
            continueLastGame();
        }
        if(buttonName == "LOAD GAME"){
            loadGame(event);
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
        }
    }

    public Parent initScene(){
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
        mainMenu.setFocusTraversable(true);
        mainMenu.setSpacing(10);
        mainMenu.setTranslateY(300);
        mainMenu.setTranslateX(450);
        mainMenu.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN){
                traverseMenu(event.getCode());
            }
            if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE){
                select(menuElements[elementCounter].getText(), event);
                elementCounter = 0;
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
