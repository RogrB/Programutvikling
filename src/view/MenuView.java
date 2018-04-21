package view;

import controller.UserInputs;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.Main;

import java.util.ArrayList;

public class MenuView{

    public static MenuView inst = new MenuView();
    public static MenuView getInstance(){return inst; }

    public static final int MENU_WIDTH = 1200;
    public static final int MENU_HEIGHT = 800;
    private static final String BG_IMG = "assets/image/background.jpg";
    public Pane root;
    public MenuButton newGameButton;
    public MenuButton loadGameButton;
    public MenuButton continueButton;
    public MenuButton multiplayerButton;
    public MenuButton selectLevelButton;
    public MenuButton optionsButton;
    public MenuButton exitButton;
    public MenuButton[] menuElements;
    public int elementCounter = 0;
    public Scene scene;
    public MenuView(){
    }

    public Background getBackGroundImage(){
        BackgroundImage bg = new BackgroundImage(
                new Image(BG_IMG),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(
                        BackgroundSize.AUTO,
                        BackgroundSize.AUTO,
                        false,
                        false,
                        true,
                        false
                )
        );
        return new Background(bg);
    }

    public void createNewGame(KeyEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        GameView.getInstance().setup();
        scene = new Scene(GameView.getInstance().initGame());
        stage.setScene(scene);
        UserInputs userInputs = new UserInputs(scene);
        System.out.println("Totally started a new game");
    }

    public void traverseMenu(KeyCode code){
        //menuElements[elementCounter].focused();
        int oldElementCounter = elementCounter;
        if(code == KeyCode.DOWN){
            if(elementCounter == menuElements.length -1){
                elementCounter = 0;
            }
            else{
                elementCounter++;
            }
            //menuElements[elementCounter].setFocusTraversable(true);
        }
        if(code == KeyCode.UP){
            if(elementCounter == menuElements.length - menuElements.length){
                elementCounter = menuElements.length -1;
            }
            else{
                elementCounter--;
            }
            //menuElements[elementCounter].setFocusTraversable(true);
        }
        menuElements[oldElementCounter].lostFocus();
        menuElements[elementCounter].gainedFocus();
    }

    public void showLevelSelect(){
        System.out.println("Totally showed u some tight levels");
    }

    public void showOptions(){
        System.out.println("Totally showed u sum looney options");
    }

    public void exitGame(){
        System.exit(0);
    }

    public void continueLastGame(){
        System.out.println("Clicked continue");
    }

    public void loadGame(){
        System.out.println("Clicked load");
    }

    public void loadMultiplayer(){
        System.out.println("clicked multiplayer");
    }

    public boolean gameFileFound(){
        return true; //EDIT THIS TO EDIT MENU LAYOUT
    }

    public void select(String buttonName, KeyEvent event){ //KeyEvent is only here so you can extract Stage from an event. Hacky, I know.
        if(buttonName == "NEW GAME"){
            createNewGame(event);
        }
        if(buttonName == "CONTINUE"){
            continueLastGame();
        }
        if(buttonName == "LOAD GAME"){
            loadGame();
        }
        if(buttonName == "MULTIPLAYER"){
            loadMultiplayer();
        }
        if(buttonName == "LEVEL SELECT"){
            showLevelSelect();
        }
        if(buttonName == "OPTIONS"){
            showOptions();
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
        Pane root = new Pane();
        VBox mainMenu = new VBox();
        Text header = new Text("SPACE GAME");
        header.setX(300);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(header.getFont().font(100));
        root.setPrefSize(MENU_WIDTH, MENU_HEIGHT);
        root.setBackground(getBackGroundImage());
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
