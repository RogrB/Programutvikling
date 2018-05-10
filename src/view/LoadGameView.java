package view;

import controller.GameController;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.GameModel;

public class LoadGameView extends ViewUtil{

    public static LoadGameView inst = new LoadGameView();
    public static LoadGameView getInst(){return inst;}

    private static final String BG_IMG = "assets/image/background.jpg";

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

        errorField = new WarningField();
        errorField.setTranslateX(475);
        errorField.setTranslateY(250);

        menuElements = new MenuButton[]{save1, save2, save3, backButton};

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
            }
        });
        backButton.setOnMouseClicked(event -> goToView(event, MenuView.getInstance().initScene()));
        menuElements[0].gainedFocus();
        containerVBox.setSpacing(40);
        containerVBox.setTranslateX(450);
        containerVBox.setTranslateY(250);
        root.getChildren().addAll(header, errorField, containerVBox);
        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {
        switch (buttonName) {
            case "BACK":
                goToView(event, MenuView.getInstance().initScene());
            case "SAVE 1":
                GameModel.gameSettings.savePrevSave(0);
                System.out.println(0);
                break;
            case "SAVE 2":
                GameModel.gameSettings.savePrevSave(1);
                break;
            case "SAVE 3":
                GameModel.gameSettings.savePrevSave(2);
                break;
        }
        System.out.println("Totally loaded a rad gamesave");
    }
}
