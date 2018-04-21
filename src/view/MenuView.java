package view;

import controller.UserInputs;
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

public class MenuView{

    public static MenuView inst = new MenuView();
    public MenuView(){}
    public static MenuView getInstance(){return inst; }

    public static final int MENU_WIDTH = 1200;
    public static final int MENU_HEIGHT = 800;
    private static final String BG_IMG = "assets/image/background.jpg";
    public Pane root;

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
        if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE){
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            GameView.getInstance().setup();
            Scene scene = new Scene(GameView.getInstance().initGame());
            stage.setScene(scene);
            UserInputs userInputs = new UserInputs(scene);
            System.out.println("Totally started a new game");
        }
    }

    public void showLevelSelect(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE){
            System.out.println("Totally showed u some tight levels");
        }
    }

    public void showOptions(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE){
            System.out.println("Totally showed u sum looney options");
        }
    }

    public void exitGame(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE){
            System.exit(0);
        }
    }

    public Parent initScene(){
        /*
        På et eller annet tidspunkt må vi legge inn en greie som sjekker om spilleren har spilt før, slik at man første gang
        kun vil ha "NEW GAME". Etter at spilleren har spilt en gang bør dette bli til "LOAD GAME" og "NEW GAME". Tenker vi kan
        ha tre forskjellige save-slots.
         */
        Pane root = new Pane();
        VBox vbox = new VBox();
        root.setPrefSize(MENU_WIDTH, MENU_HEIGHT);
        root.setBackground(getBackGroundImage());
        Button startButton = new Button("START");
        startButton.setOnKeyPressed(event -> createNewGame(event));
        Button selectLevelButton = new Button("LEVEL SELECT");
        selectLevelButton.setOnKeyPressed(event -> showLevelSelect(event));
        Button optionsButton = new Button("OPTIONS");
        optionsButton.setOnKeyPressed(event -> showOptions(event));
        Button exitButton = new Button("EXIT");
        exitButton.setOnKeyPressed(event -> exitGame(event));
        vbox.getChildren().addAll(startButton, selectLevelButton, optionsButton, exitButton);
        root.getChildren().add(vbox);
        return root;

    }
}
