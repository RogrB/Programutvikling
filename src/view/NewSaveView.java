package view;

import controller.GameController;
import controller.UserInputs;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameModel;

import java.util.Objects;

public class NewSaveView extends ViewUtil{

    public static NewSaveView inst = new NewSaveView();
    public static NewSaveView getInst(){return inst;}

    private static final String BG_IMG = "assets/image/background.jpg";


    private Parent menuElements[];

    private TextField saveNameTextField;

    @Override
    public Parent initScene() {
        root = new Pane();
        VBox containerVBox = new VBox();
        header.setX(300);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(header.getFont().font(100));
        System.out.println("NewSaveView::saveNumber == "+NewGameView.getInst().getSaveNumber());

        root.setPrefSize(ViewUtil.VIEW_WIDTH, ViewUtil.VIEW_HEIGHT);

        root.setBackground(getBackGroundImage(BG_IMG));
        Text writeSaveNameText = new Text("NEW GAME");
        writeSaveNameText.setX(475);
        writeSaveNameText.setY(275);
        writeSaveNameText.setFill(Color.WHITE);
        writeSaveNameText.setFont(header.getFont().font(50));
        errorField = new WarningField();
        errorField.setTranslateX(475);
        errorField.setTranslateY(250);

        Label saveNameLabel = new Label("SAVE NAME");
        saveNameLabel.setTextFill(Color.WHITE);

        saveNameTextField = new TextField();

        saveNameTextField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE && !Objects.equals(saveNameTextField.getText(), "")){
                startGameView(event);
            }
        });

        MenuButton startGameButton = new MenuButton("START");
        startGameButton.setOnMouseClicked(event -> {
            if(!Objects.equals(saveNameTextField.getText(), "")){
                startGameView(event);
            }
        });
        MenuButton backButton = new MenuButton("BACK");
        backButton.setOnMouseClicked(event -> goToView(event, NewGameView.getInst().initScene()));

        menuElements = new Parent[]{saveNameTextField, startGameButton, backButton};
        containerVBox.getChildren().addAll(saveNameLabel, saveNameTextField, startGameButton, backButton);
        containerVBox.setSpacing(10);
        containerVBox.setTranslateX(450);
        containerVBox.setTranslateY(325);
        containerVBox.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                goToView(event, NewGameView.getInst().initScene());
            }
        });
        root.getChildren().addAll(header, writeSaveNameText, errorField, containerVBox);

        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {

    }

    private void startGameView(InputEvent event){
        GameController.getInstance().newGame();
        startGameView(event, GameView.getInstance().initScene());
        GameModel.gameSettings.savePrevSave(NewGameView.getInst().getSaveNumber());
        System.out.println("Totally started a new game");
    }
}
