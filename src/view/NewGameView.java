package view;

import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.awt.*;

public class NewGameView extends ViewUtil{

    public static NewGameView inst = new NewGameView();
    public static NewGameView getInst(){return inst;}

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

        Text selectSaveText = new Text("SELECT SAVE FILE");
        selectSaveText.setX(500);
        selectSaveText.setY(275);
        selectSaveText.setFill(Color.WHITE);
        selectSaveText.setFont(header.getFont().font(50));

        root.setPrefSize(ViewUtil.VIEW_WIDTH, ViewUtil.VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(BG_IMG));

        MenuButton backButton = new MenuButton("BACK");
        MenuButton save1 = new MenuButton("SAVE 1");
        save1.setOnMouseClicked(event -> goToView(event, NewSaveView.getInst().initScene()));
        MenuButton save2 = new MenuButton("SAVE 2");
        save2.setOnMouseClicked(event -> goToView(event, NewSaveView.getInst().initScene()));
        MenuButton save3 = new MenuButton("SAVE 3");
        save3.setOnMouseClicked(event -> goToView(event, NewSaveView.getInst().initScene()));

        menuElements = new MenuButton[]{save1, save2, save3, backButton};

        saveFiles.getChildren().addAll(save1, save2, save3);
        saveFiles.setSpacing(10);
        containerVBox.getChildren().addAll(selectSaveText, saveFiles, backButton);
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
        menuElements[0].gainedFocus();
        containerVBox.setSpacing(40);
        containerVBox.setTranslateX(450);
        containerVBox.setTranslateY(250);
        backButton.setOnMouseClicked(event -> goToView(event, MenuView.getInstance().initScene()));
        root.getChildren().addAll(header, containerVBox);
        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {
        switch(elementCounter){
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:
                goToView(event, MenuView.getInstance().initScene());
        }

    }
}
