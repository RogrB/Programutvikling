package view;

import controller.GameController;
import exceptions.FileIOException;
import io.IOManager;
import javafx.scene.Parent;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.GameModel;

import static controller.GameController.gs;
import java.util.ArrayList;

public class LoadGameView extends ViewUtil{

    private static LoadGameView inst = new LoadGameView();
    private LoadGameView(){}
    public static LoadGameView getInst(){return inst;}

    private MenuButton save1;
    private MenuButton save2;
    private MenuButton save3;
    private MenuButton backButton;

    private Text loadSaveText;

    private MenuButton[] menuElements;

    private void createButtons(){
        save1 = new MenuButton("SAVE 1");
        save1.setValue(0);
        save2 = new MenuButton("SAVE 2");
        save2.setValue(1);
        save3 = new MenuButton("SAVE 3");
        save3.setValue(2);
        backButton = new MenuButton("BACK");
        backButton.setValue(3);
    }

    private ArrayList<MenuButton> createTempArray(){
        ArrayList<MenuButton> AL = new ArrayList<>();
        AL.add(save1);
        AL.add(save2);
        AL.add(save3);
        checkSaves(AL);
        return AL;
    }

    private void checkSaves(ArrayList<MenuButton> tempElements){
        int counter = 0;
        for(int i = 0; i < 3; i++){
            if(!IOManager.getInstance().saveStateExists(i)){
                tempElements.get(counter).setColor(Color.GREY);
                tempElements.remove(counter);
            }
            else{
                try{
                    tempElements.get(counter).setText(IOManager.getInstance().getGameState(counter).getStateName());
                }
                catch(Exception e){
                    System.err.println(e.getMessage());
                    ViewUtil.setError(e.getMessage());
                }
                counter++;
            }
        }
    }

    private MenuButton[] createMenuElementsArray(ArrayList<MenuButton> tempElements){
        switch(tempElements.size()){
            case 1:
                menuElements = new MenuButton[]{tempElements.get(0), backButton};
                break;
            case 2:
                menuElements = new MenuButton[]{tempElements.get(0), tempElements.get(1), backButton};
                break;
            case 3:
                menuElements = new MenuButton[]{tempElements.get(0), tempElements.get(1), tempElements.get(2), backButton};
                break;
        }
        return menuElements;
    }

    @Override
    void setButtonClickEvents() {
        backButton.setOnMouseClicked(event -> goToView(event, MenuView.getInstance().initScene()));
    }

    @Override
    void setButtonPressEvents(Parent container) {
        container.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                goToView(event, MenuView.getInstance().initScene());
            }
            if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN){
                menuElements[elementCounter].lostFocus();
                traverseMenu(event.getCode(), menuElements);
                menuElements[elementCounter].gainedFocus();
            }
            if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE){
                select(menuElements[elementCounter].getText(), event);
                elementCounter = 0;
            }
        });
    }

    private void setEvents(Parent container){
        setButtonClickEvents();
        setButtonPressEvents(container);
    }

    private void createUI(){
        createButtons();
        loadSaveText = createText("LOAD SAVE FILE", 500, 275, Font.font("Verdana", 50));
        setErrorFieldPosition();
    }

    @Override
    public Parent initScene() {
        root = initBaseScene(BG_IMG);

        VBox saveFiles = new VBox();
        VBox menuContainer = createMenuContainer(395, 250, 40);
        createUI();

        setEvents(menuContainer);

        ArrayList<MenuButton> tempElements = createTempArray();
        menuElements = createMenuElementsArray(tempElements);

        saveFiles.getChildren().addAll(save1, save2, save3);
        saveFiles.setSpacing(10);
        menuContainer.getChildren().addAll(loadSaveText, saveFiles, backButton);
        menuContainer.setFocusTraversable(true);
        menuElements[0].gainedFocus();
        root.getChildren().addAll(header, errorField, menuContainer);

        compareErrorMessage();

        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {
        switch (menuElements[elementCounter].getValue()) {
            case 0:
                loadGame(event,0);
                break;
            case 1:
                loadGame(event,1);
                break;
            case 2:
                loadGame(event,2);
                break;
            case 3:
                goToView(event, MenuView.getInstance().initScene());
                break;
            }
    }

    private void loadGame(InputEvent event, int gameSave){
        int prevSave = GameModel.gameSettings.getPrevSave();
        GameModel.gameSettings.savePrevSave(gameSave);
        if (IOManager.getInstance().saveStateExists()){
            try {
                IOManager.getInstance().loadGameState();
                GameController.getInstance().loadGame();
                startGameView(event, GameView.getInstance().initScene());
            } catch (FileIOException e) {
                System.err.println(e.getMessage());
                ViewUtil.setError(e.getMessage());
                GameModel.gameSettings.savePrevSave(prevSave);
            }
        } else {
            GameModel.gameSettings.savePrevSave(prevSave);
        }
    }
}
