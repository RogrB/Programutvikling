package view;

import assets.java.SoundManager;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import model.GameModel;

import static model.GameModel.gameSettings;

public class OptionsView extends ViewUtil{

    public static OptionsView inst = new OptionsView();
    public static OptionsView getInst(){
        return inst;
    }

    private int difficultyValue = GameModel.gameSettings.getDifficultyValue();
    private int soundValue = GameModel.gameSettings.getSoundValue();
    private int musicValue = GameModel.gameSettings.getMusicValue();

    private Label soundLabel;
    private Label musicLabel;
    private Label difficultyLabel;

    private Label soundTextLabel;
    private Label musicTextLabel;
    private Label difficultyTextLabel;

    private Slider soundSlider;
    private Slider musicSlider;
    private Slider difficultySlider;

    private MenuButton backButton;

    private Parent[] menuElements;

    private OptionsView(){
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

    @Override
    void setButtonClickEvents() {
        backButton.setOnMouseClicked((MouseEvent event) -> {
            gameSettings.saveSettings(difficultyValue, soundValue, musicValue);
            SoundManager.getInst().getPlayer().setVolume((float)musicValue/100);
            goToView(event, MenuView.getInstance().initScene());
        });
    }

    @Override
    void setButtonPressEvents(Parent container) {
        container.setOnKeyPressed(event -> {
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

    private Slider createSlider(int min, int max, int value, int majorTick, int blockIncrement){
        Slider slider = new Slider(min, max, value);
        slider.setMajorTickUnit(majorTick);
        slider.setShowTickMarks(true);
        slider.setBlockIncrement(blockIncrement);
        return slider;
    }

    private void createSliders(){
        soundSlider = createSlider(0, 100, soundValue, 25, 25);
        musicSlider = createSlider(0, 100, musicValue, 25, 25);
        difficultySlider = createSlider(1, 5, difficultyValue, 1, 1);

        difficultySlider.setSnapToTicks(true);
        difficultySlider.setMinorTickCount(0);

        createListeners();
    }

    private void createLabels(){
        soundLabel = createBaseLabel("VOLUME");
        musicLabel = createBaseLabel("MUSIC");
        difficultyLabel = createBaseLabel("DIFFICULTY");

        difficultyTextLabel = createBaseLabel(setDifficultyText(difficultyValue));
        soundTextLabel = createBaseLabel(String.valueOf(soundValue));
        musicTextLabel = createBaseLabel(String.valueOf(musicValue));

        difficultyTextLabel.setTranslateX(125);
        soundTextLabel.setTranslateX(145);
        musicTextLabel.setTranslateX(145);
    }

    private void createUI(){
        createLabels();
        createSliders();
        setErrorFieldPosition();
        backButton = new MenuButton("BACK");
    }

    private void createListeners(){
        soundSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            soundTextLabel.setText(String.valueOf(newValue.intValue()));
            soundValue = newValue.intValue();
        }));
        musicSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            musicTextLabel.setText(String.valueOf(newValue.intValue()));
            musicValue = newValue.intValue();
        }));
        difficultySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            difficultySlider.setValue(newValue.intValue());
            difficultyValue = newValue.intValue();
            difficultyTextLabel.setText(setDifficultyText(difficultyValue));
        });
    }

    public Parent initScene(){
        root = initBaseScene(BG_IMG);
        createUI();
        VBox menuContainer = createMenuContainer(450, 250, 10);

        menuElements = new Parent[]{soundSlider, musicSlider, difficultySlider, backButton};
        menuContainer.getChildren().addAll(soundLabel, soundSlider, soundTextLabel, musicLabel, musicSlider, musicTextLabel, difficultyLabel, difficultySlider, difficultyTextLabel, backButton);
        elementCounter = 0;
        setButtonPressEvents(menuContainer);
        setButtonClickEvents();
        root.getChildren().addAll(header, errorField, menuContainer);

        compareErrorMessage();

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