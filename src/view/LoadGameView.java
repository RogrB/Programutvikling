package view;

import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoadGameView extends ViewUtil{

    public static LoadGameView inst = new LoadGameView();
    public static LoadGameView getInst(){return inst;}

    private static final String BG_IMG = "assets/image/background.jpg";

    private Text loadSaveText;

    private VBox saveFiles;
    private VBox containerVBox;

    private MenuButton save1;
    private MenuButton save2;
    private MenuButton save3;
    private MenuButton backButton;


    @Override
    public Parent initScene() {
        root = new Pane();
        saveFiles = new VBox();
        containerVBox = new VBox();
        header = new Text("SPACE GAME");
        header.setX(300);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(header.getFont().font(100));

        loadSaveText = new Text("LOAD SAVE FILE");
        loadSaveText.setX(500);
        loadSaveText.setY(275);
        loadSaveText.setFill(Color.WHITE);
        loadSaveText.setFont(header.getFont().font(50));

        root.setPrefSize(ViewUtil.VIEW_WIDTH, ViewUtil.VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(BG_IMG));

        backButton = new MenuButton("BACK");
        save1 = new MenuButton("SAVE 1");
        save2 = new MenuButton("SAVE 2");
        save3 = new MenuButton("SAVE 3");

        saveFiles.getChildren().addAll(save1, save2, save3);
        saveFiles.setSpacing(10);
        containerVBox.getChildren().addAll(loadSaveText, saveFiles, backButton);
        containerVBox.setFocusTraversable(true);
        containerVBox.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                goToView(event, MenuView.getInstance().initScene());
            }
        });
        containerVBox.setSpacing(40);
        containerVBox.setTranslateX(450);
        containerVBox.setTranslateY(250);
        root.getChildren().addAll(header, containerVBox);
        return root;
    }
}
