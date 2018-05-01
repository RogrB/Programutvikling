package view;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class OptionsView extends ViewUtil{

    public static OptionsView inst = new OptionsView();
    public static OptionsView getInst(){
        return inst;
    }

    private static final String BG_IMG = "assets/image/background.jpg";
    public VBox optionsMenu;

    public static int difficultyValue = 3;
    public static int soundValue = 50;
    public static int musicValue = 50;

    public Label soundLabel;
    private Label soundTextLabel;
    private Label musicTextLabel;
    public Label musicLabel;
    public Label difficultyLabel;
    public Label difficultyTextLabel;

    public Slider soundSlider;
    public Slider musicSlider;
    public Slider difficultySlider;

    public MenuButton backButton;

    private Parent[] menuElements;

    public OptionsView(){
    }

    public String setDifficultyText(int difficultyValue){
        String text = "";
        switch(difficultyValue){
            case 1:
                text = "EASY";
                break;
            case 2:
                text = "CASUAL";
                break;
            case 3:
                text = "NORMAL";
                break;
            case 4:
                text = "HARD";
                break;
            case 5:
                text = "INSANE";
                break;
        }
        return text;
    }

    public Parent initScene(){
        root = new Pane();
        optionsMenu = new VBox();
        header.setX(300);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(header.getFont().font(100));
        root.setPrefSize(VIEW_WIDTH, VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(BG_IMG));
        soundLabel = new Label("VOLUME");
        soundLabel.setTextFill(Color.WHITE);
        musicLabel = new Label("MUSIC");
        musicLabel.setTextFill(Color.WHITE);
        difficultyLabel = new Label("DIFFICULTY");
        difficultyLabel.setTextFill(Color.WHITE);
        difficultyTextLabel = new Label(setDifficultyText(difficultyValue));
        difficultyTextLabel.setTranslateX(125);
        difficultyTextLabel.setTextFill(Color.WHITE);
        soundSlider = new Slider(0, 100, soundValue);
        soundSlider.setShowTickMarks(true);
        soundSlider.setMajorTickUnit(25);
        soundSlider.setBlockIncrement(25);
        soundTextLabel = new Label(String.valueOf(soundValue));
        soundSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            soundTextLabel.setText(String.valueOf(newValue.intValue()));
            soundValue = newValue.intValue();
        }));
        soundTextLabel.setTranslateX(145);
        soundTextLabel.setTextFill(Color.WHITE);
        musicSlider = new Slider(0, 100, musicValue);
        musicSlider.setShowTickMarks(true);
        musicSlider.setMajorTickUnit(25);
        musicSlider.setBlockIncrement(25);
        musicTextLabel = new Label(String.valueOf(musicValue));
        musicSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            musicTextLabel.setText(String.valueOf(newValue.intValue()));
            musicValue = newValue.intValue();
        }));
        musicTextLabel.setTranslateX(145);
        musicTextLabel.setTextFill(Color.WHITE);
        difficultySlider = new Slider(1, 5, difficultyValue);
        difficultySlider.setSnapToTicks(true);
        difficultySlider.setShowTickMarks(true);
        difficultySlider.setMajorTickUnit(1);
        difficultySlider.setMinorTickCount(0);
        difficultySlider.setBlockIncrement(1);
        difficultySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            difficultySlider.setValue(newValue.intValue());
            difficultyValue = newValue.intValue();
            difficultyTextLabel.setText(setDifficultyText(difficultyValue));
        });
        backButton = new MenuButton("BACK");
        optionsMenu.getChildren().addAll(soundLabel, soundSlider, soundTextLabel, musicLabel, musicSlider, musicTextLabel, difficultyLabel, difficultySlider, difficultyTextLabel, backButton);
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
