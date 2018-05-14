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
import view.customElements.MenuButton;

import static model.GameModel.gameSettings;

/**
 * Menu for showing and changing options.
 * The class {@code OptionsView} extends {@code ViewUtil}.
 *
 * @author Jonas Ege Carlsen
 */
public class OptionsView extends ViewUtil{

    /**
     * The singleton object.
     */
    public static OptionsView inst = new OptionsView();

    /**
     * Method to access the singleton object.
     * @return Returns a reference to the singleton object.
     */
    public static OptionsView getInst(){
        return inst;
    }

    /**
     *  A value used to set the default difficulty slider value
     *  as well as for saving eventual changes.
     */
    private int difficultyValue = GameModel.gameSettings.getDifficultyValue();

    /**
     * A value used to set the default sound slider value
     * as well as for saving eventual changes.
     */
    private int soundValue = GameModel.gameSettings.getSoundValue();

    /**
     * A value used to set the default music slider value
     * as well as for saving eventual changes.
     */
    private int musicValue = GameModel.gameSettings.getMusicValue();

    /**
     * Label used for displaying the purpose of the sound slider.
     */
    private Label soundLabel;

    /**
     * Label used for displaying the purpose of the music slider.
     */
    private Label musicLabel;

    /**
     * Label used for displaying the purpose of the difficulty slider.
     */
    private Label difficultyLabel;

    /**
     * Label used for showing the sound slider value.
     */
    private Label soundTextLabel;

    /**
     * Label used for showing the music slider value.
     */
    private Label musicTextLabel;

    /**
     * Label used for showing the difficulty slider value.
     */
    private Label difficultyTextLabel;

    /**
     * Slider used to adjust the sound volume.
     */
    private Slider soundSlider;

    /**
     * Slider used to adjust the music volume.
     */
    private Slider musicSlider;

    /**
     * Slider used to adjust the difficulty.
     */
    private Slider difficultySlider;

    /**
     * Button used to go back to {@code MenuView}.
     */
    private MenuButton backButton;

    /**
     * An array of menu elements used to keep track of what button is currently selected.
     */
    private Parent[] menuElements;

    /**
     * Private constructor
     */
    private OptionsView(){
    }

    /**
     * Method used for getting the difficulty text
     * based off of an Integer value.
     * @param difficultyValue The value associate with a name.
     * @return Returns a String.
     */
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

    /**
     * Sets the button click events of the view.
     */
    @Override
    void setButtonClickEvents() {
        backButton.setOnMouseClicked((MouseEvent event) -> {
            gameSettings.saveSettings(difficultyValue, soundValue, musicValue);
            SoundManager.getInst().getPlayer().setVolume((float)musicValue/100);
            goToView(event, MenuView.getInstance().initScene());
        });
    }

    /**
     * Sets the button press events of the menu container.
     * @param container The menu container of the view.
     */
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

    /**
     * Creates a Slider element to be used in the view.
     * @param min The minimum value.
     * @param max The maximum value.
     * @param value The default value.
     * @param majorTick The length of a major tick unit.
     * @param blockIncrement The length of a block increment.
     * @return Returns a Slider object.
     */
    private Slider createSlider(int min, int max, int value, int majorTick, int blockIncrement){
        Slider slider = new Slider(min, max, value);
        slider.setMajorTickUnit(majorTick);
        slider.setShowTickMarks(true);
        slider.setBlockIncrement(blockIncrement);
        return slider;
    }

    /**
     * Creates all the sliders in the view.
     */
    private void createSliders(){
        soundSlider = createSlider(0, 100, soundValue, 25, 25);
        musicSlider = createSlider(0, 100, musicValue, 25, 25);
        difficultySlider = createSlider(1, 5, difficultyValue, 1, 1);

        difficultySlider.setSnapToTicks(true);
        difficultySlider.setMinorTickCount(0);

        createListeners();
    }

    /**
     * Creates all the Labels in the view.
     */
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

    /**
     * Creates all the user interface elements of the view.
     */
    private void createUI(){
        createLabels();
        createSliders();
        setErrorFieldPosition();
        backButton = new MenuButton("BACK");
    }

    /**
     * Creates a listener for every slider in the view
     * so that changes can be saved.
     */
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

    /**
     * The main method of the View. Calls other methods and returns
     * a finished root node.
     * @return Returns a root node / Pane.
     */
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
    /**
     * Method to call different functions based off of a value.
     * Overridden from {@code ViewUtil}.
     * @param buttonName The name of the button.
     * @param event The event that this function is called from.
     */
    @Override
    public void select(String buttonName, KeyEvent event) {
        if(buttonName.equals("BACK")){
            SoundManager.getInst().getPlayer().setVolume((float)musicValue/100);
            gameSettings.saveSettings(difficultyValue, soundValue, musicValue);
            goToView(event, MenuView.getInstance().initScene());
        }
    }
}