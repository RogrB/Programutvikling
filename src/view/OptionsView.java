package view;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OptionsView extends ViewUtil{

    public static OptionsView inst = new OptionsView();
    public static OptionsView getInst(){
        return inst;
    }

    private static final String BG_IMG = "assets/image/background.jpg";
    public VBox optionsMenu;

    public Label volumeLabel;
    public Label difficultyLabel;

    public Slider volumeSlider;
    public Slider difficultySlider;

    public MenuButton backButton;

    public OptionsView(){
    }

    public Parent initScene(){
        root = new Pane();
        optionsMenu = new VBox();
        header = new Text("SPACE GAME");
        header.setX(300);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(header.getFont().font(100));
        root.setPrefSize(VIEW_WIDTH, VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(BG_IMG));
        volumeLabel = new Label("VOLUME");
        difficultyLabel = new Label("DIFFICULTY");
        volumeSlider = new Slider();
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeSlider.setValue(40);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setBlockIncrement(10);
        volumeSlider.setMinorTickCount(10);
        difficultySlider = new Slider();
        difficultySlider.setMin(0);
        difficultySlider.setMax(100);
        difficultySlider.setValue(40);
        difficultySlider.setShowTickMarks(true);
        difficultySlider.setBlockIncrement(10);
        difficultySlider.setMinorTickCount(10);
        backButton = new MenuButton("BACK");
        optionsMenu.getChildren().addAll(volumeLabel, volumeSlider, difficultyLabel, difficultySlider, backButton);
        optionsMenu.setSpacing(10);
        optionsMenu.setTranslateX(450);
        optionsMenu.setTranslateY(250);
        optionsMenu.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                goToView(event, MenuView.getInstance().initScene());
            }
        });
        backButton.setOnMouseClicked(event -> goToView(event, MenuView.getInstance().initScene()));
        root.getChildren().addAll(header, optionsMenu);
        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {

    }
}
