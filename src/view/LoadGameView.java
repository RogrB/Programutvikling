package view;

import controller.GameController;
import exceptions.FileIOException;
import io.IOManager;
import javafx.scene.Parent;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.GameModel;

import static controller.GameController.gs;
import java.util.ArrayList;

public class LoadGameView extends ViewUtil{

    public static LoadGameView inst = new LoadGameView();
    public static LoadGameView getInst(){return inst;}

    private static final String BG_IMG = "assets/image/background.jpg";

    private ArrayList<MenuButton> tempElements;
    private String lastError = "";

    private MenuButton[] menuElements;
    
    @Override
    public Parent initScene() {
        root = new Pane();
        VBox saveFiles = new VBox();
        VBox containerVBox = new VBox();
        header.setX(300);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(header.getFont().font(100));

        Text loadSaveText = new Text("LOAD SAVE FILE");
        loadSaveText.setX(500);
        loadSaveText.setY(275);
        loadSaveText.setFill(Color.WHITE);
        loadSaveText.setFont(header.getFont().font(50));

        root.setPrefSize(ViewUtil.VIEW_WIDTH, ViewUtil.VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(BG_IMG));

        MenuButton backButton = new MenuButton("BACK");
        MenuButton save1 = new MenuButton("SAVE 1");
        MenuButton save2 = new MenuButton("SAVE 2");
        MenuButton save3 = new MenuButton("SAVE 3");
        tempElements = new ArrayList<>();
        tempElements.add(save1);
        tempElements.add(save2);
        tempElements.add(save3);

        errorField.setTranslateX(200);
        errorField.setTranslateY(750);

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
                    System.out.println(e);
                }
                counter++;
            }
        }

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

        saveFiles.getChildren().addAll(save1, save2, save3);
        saveFiles.setSpacing(10);
        containerVBox.getChildren().addAll(loadSaveText, saveFiles, backButton);
        containerVBox.setFocusTraversable(true);
        containerVBox.setOnKeyPressed(event -> {
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
        backButton.setOnMouseClicked(event -> goToView(event, MenuView.getInstance().initScene()));
        menuElements[0].gainedFocus();
        containerVBox.setSpacing(40);
        containerVBox.setTranslateX(450);
        containerVBox.setTranslateY(250);
        root.getChildren().addAll(header, errorField, containerVBox);

        compareErrorMessage(lastError);

        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {
        switch (elementCounter) {
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
        System.out.println(gs.getStateName());
        System.out.println("Totally loaded a rad gamesave");
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
                GameModel.gameSettings.savePrevSave(prevSave);
            }
        } else {
            GameModel.gameSettings.savePrevSave(prevSave);
        }
    }
}
