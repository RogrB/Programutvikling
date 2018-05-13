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

    private static final String BG_IMG = "assets/image/background.jpg";

    private int difficultyValue = GameModel.gameSettings.getDifficultyValue();
    private int soundValue = GameModel.gameSettings.getSoundValue();
    private int musicValue = GameModel.gameSettings.getMusicValue();

    private Label soundLabel;
    private Label musicLabel;
    private Label difficultyLabel;



    private Label soundValueLabel;
    private Label musicValueLabel;
    private Label difficultyValueLabel;

    private Slider difficultySlider;
    private Slider soundSlider;
    private Slider musicSlider;

    private MenuButton backButton;

    private Parent[] menuElements;

    private OptionsView(){}

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

    private void createDescriptiveLabels(){
        soundLabel = createBaseLabel("VOLUME");
        musicLabel = createBaseLabel("MUSIC");
        difficultyLabel = createBaseLabel("DIFFICULTY");
    }

    private void createValueLabels(){
        musicValueLabel = createBaseLabel(String.valueOf(musicValue));
        difficultyValueLabel = createBaseLabel(setDifficultyText(difficultyValue));
        soundValueLabel = createBaseLabel(String.valueOf(soundValue));

        musicValueLabel.setTranslateX(145);
        difficultyValueLabel.setTranslateX(125);
        soundValueLabel.setTranslateX(145);
    }

    private void createSliders(){
        soundSlider = createSlider(0, 100, soundValue, 25, 25);
        musicSlider = createSlider(0, 100, musicValue, 25, 25);
        difficultySlider = createSlider(1, 5, difficultyValue, 1, 1);

        difficultySlider.setSnapToTicks(true);
        difficultySlider.setMinorTickCount(0);
    }

    private Slider createSlider(int min, int max, int val, int majorTick, int blockIncrement){
        Slider slider = new Slider(min, max, val);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(majorTick);
        slider.setBlockIncrement(blockIncrement);
        return slider;
    }

    private void createSliderListener(Slider slider, Label label, int setValue){
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            label.setText(String.valueOf(newValue.intValue()));
        });
    }

    @Override
    void setButtonClickEvents(){
        backButton.setOnMouseClicked((MouseEvent event) -> {
            gameSettings.saveSettings(difficultyValue, soundValue, musicValue);
            SoundManager.getInst().getPlayer().setVolume((float)musicValue/100);
            goToView(event, MenuView.getInstance().initScene());
        });
    }
    @Override
    void setButtonPressEvents(Parent optionsMenu){
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
    }

    public Parent initScene(){
        root = initBaseScene(BG_IMG);
        VBox optionsMenu = createMenuContainer(450, 250, 10);
        createDescriptiveLabels();
        //createValueLabels();
        difficultyValueLabel = new Label(setDifficultyText(difficultyValue));
        difficultyValueLabel.setTranslateX(125);
        difficultyValueLabel.setTextFill(Color.WHITE);

        soundValueLabel = new Label(String.valueOf(soundValue));
        soundValueLabel.setTranslateX(145);
        soundValueLabel.setTextFill(Color.WHITE);

        musicValueLabel = new Label(String.valueOf(musicValue));
        musicValueLabel.setTranslateX(145);
        musicValueLabel.setTextFill(Color.WHITE);
        createSliders();

        backButton = new MenuButton("BACK");

        soundSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            soundValueLabel.setText(String.valueOf(newValue.intValue()));
            soundValue = newValue.intValue();
        }));
        musicValueLabel = new Label(String.valueOf(musicValue));
        musicSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            musicValueLabel.setText(String.valueOf(newValue.intValue()));
            musicValue = newValue.intValue();
        }));
        difficultySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            difficultySlider.setValue(newValue.intValue());
            difficultyValue = newValue.intValue();
            difficultyValueLabel.setText(setDifficultyText(difficultyValue));
        });
        setErrorFieldPosition();
        setButtonClickEvents();
        setButtonPressEvents(optionsMenu);

        menuElements = new Parent[]{soundSlider, musicSlider, difficultySlider, backButton};
        optionsMenu.getChildren().addAll(soundLabel, soundSlider, soundValueLabel, musicLabel, musicSlider, musicValueLabel, difficultyLabel, difficultySlider, difficultyValueLabel, backButton);
        elementCounter = 0;

        root.getChildren().addAll(header, errorField, optionsMenu);

        compareErrorMessage("");

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
