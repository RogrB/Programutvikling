package view;

import assets.java.SoundManager;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.GameModel;
import model.GameSettings;

import static model.GameModel.gameSettings;

public class OptionsView extends ViewUtil{

    public static OptionsView inst = new OptionsView();
    public static OptionsView getInst(){
        return inst;
    }

    private String lastError = "";

    private static final String BG_IMG = "assets/image/background.jpg";

    private int difficultyValue = GameModel.gameSettings.getDifficultyValue();
    private int soundValue = GameModel.gameSettings.getSoundValue();
    private int musicValue = GameModel.gameSettings.getMusicValue();

    private Label soundTextLabel;
    private Label musicTextLabel;
    private Label difficultyTextLabel;

    private Slider difficultySlider;

    private MenuButton backButton;

    private Parent[] menuElements;

    public OptionsView(){
    }

    private String setDifficultyText(int difficultyValue){
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
        root = initBaseScene(BG_IMG);
        VBox optionsMenu = new VBox();
        Label soundLabel = new Label("VOLUME");
        soundLabel.setTextFill(Color.WHITE);
        Label musicLabel = new Label("MUSIC");
        musicLabel.setTextFill(Color.WHITE);
        Label difficultyLabel = new Label("DIFFICULTY");
        difficultyLabel.setTextFill(Color.WHITE);
        difficultyTextLabel = new Label(setDifficultyText(difficultyValue));
        difficultyTextLabel.setTranslateX(125);
        difficultyTextLabel.setTextFill(Color.WHITE);
        Slider soundSlider = new Slider(0, 100, soundValue);
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
        Slider musicSlider = new Slider(0, 100, musicValue);
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
        setErrorFieldPosition();
        backButton = new MenuButton("BACK");

        menuElements = new Parent[]{soundSlider, musicSlider, difficultySlider, backButton};
        optionsMenu.getChildren().addAll(soundLabel, soundSlider, soundTextLabel, musicLabel, musicSlider, musicTextLabel, difficultyLabel, difficultySlider, difficultyTextLabel, backButton);
        optionsMenu.setSpacing(10);
        optionsMenu.setTranslateX(450);
        optionsMenu.setTranslateY(250);
        elementCounter = 0;
        optionsMenu.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN){
                backButton.lostFocus();
                traverseMenu(event.getCode(), menuElements);
                setFocusButtons(elementCounter, 3, menuElements);
                if(elementCounter == 3) backButton.gainedFocus();
            }
            if(event.getCode() == KeyCode.ENTER && elementCounter == 3 || event.getCode() == KeyCode.SPACE && elementCounter == 3){
                select("BACK", event);
            }
            if(event.getCode() == KeyCode.ENTER && elementCounter < 3 || event.getCode() == KeyCode.SPACE && elementCounter == 3){
                traverseMenu(KeyCode.DOWN, menuElements);
                setFocusButtons(elementCounter, 3, menuElements);
                if(elementCounter == 3){
                    backButton.gainedFocus();
                }
            }
            if(event.getCode() == KeyCode.ESCAPE){
                gameSettings.saveSettings(difficultyValue, soundValue, musicValue);
                SoundManager.getInst().getPlayer().setVolume((float)musicValue / 100);
                goToView(event, MenuView.getInstance().initScene());
            }
        });
        backButton.setOnMouseClicked((MouseEvent event) -> {
            gameSettings.saveSettings(difficultyValue, soundValue, musicValue);
            SoundManager.getInst().getPlayer().setVolume((float)musicValue/100);
            goToView(event, MenuView.getInstance().initScene());
        });
        root.getChildren().addAll(header, errorField, optionsMenu);

        compareErrorMessage(lastError);

        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {
        if(buttonName.equals("BACK")){
            SoundManager.getInst().getPlayer().setVolume((float)musicValue/100);
            gameSettings.saveSettings(difficultyValue, soundValue, musicValue);
            goToView(event, MenuView.getInstance().initScene());
        }
    }
}
