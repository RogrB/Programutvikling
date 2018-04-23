package view;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LevelSelectView extends ViewUtil{

    private static LevelSelectView inst = new LevelSelectView();
    public static LevelSelectView getInst(){return inst;}

    public LevelSelectView(){}

    private static final String BG_IMG = "assets/image/background.jpg";

    private Text levelName;

    public HBox row1;
    public HBox row2;
    public VBox levelsVBox;
    public VBox containerVBox;

    public ImageView level1;
    public ImageView level2;
    public ImageView level3;
    public ImageView level4;
    public ImageView level5;
    public ImageView level6;
    public ImageView level7;
    public ImageView level8;

    private MenuButton backButton;

    public Parent initScene(){
        root = new Pane();
        header = new Text("SPACE GAME");

        header.setX(300);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(header.getFont().font(100));

        root.setPrefSize(VIEW_WIDTH, VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(BG_IMG));

        levelName = new Text("Level 1");
        levelName.setX(525);
        levelName.setY(275);
        levelName.setFill(Color.WHITE);
        levelName.setFont(header.getFont().font(50));

        level1 = new ImageView("assets/image/placeholderBorder.png");
        level1.setFitHeight(160);
        level1.setFitWidth(200);
        level2 = new ImageView("assets/image/placeholderBorder.png");
        level2.setFitHeight(160);
        level2.setFitWidth(200);
        level3 = new ImageView("assets/image/placeholderBorder.png");
        level3.setFitHeight(160);
        level3.setFitWidth(200);
        level4 = new ImageView("assets/image/placeholderBorder.png");
        level4.setFitHeight(160);
        level4.setFitWidth(200);
        level5 = new ImageView("assets/image/placeholderBorder.png");
        level5.setFitHeight(160);
        level5.setFitWidth(200);
        level6 = new ImageView("assets/image/placeholderBorder.png");
        level6.setFitHeight(160);
        level6.setFitWidth(200);
        level7 = new ImageView("assets/image/placeholderBorder.png");
        level7.setFitHeight(160);
        level7.setFitWidth(200);
        level8 = new ImageView("assets/image/placeholderBorder.png");
        level8.setFitHeight(160);
        level8.setFitWidth(200);

        backButton = new MenuButton("BACK");

        row1 = new HBox();
        row1.getChildren().addAll(level1, level2, level3, level4);
        row1.setSpacing(10);
        row2 = new HBox();
        row2.getChildren().addAll(level5, level6, level7, level8);
        row2.setSpacing(10);
        levelsVBox = new VBox();
        levelsVBox.getChildren().addAll(row1, row2);
        containerVBox = new VBox();
        containerVBox.setFocusTraversable(true);
        containerVBox.getChildren().addAll(levelsVBox, backButton);
        containerVBox.setSpacing(40);
        containerVBox.setTranslateX(185);
        containerVBox.setTranslateY(325);
        containerVBox.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                goToView(event, MenuView.getInstance().initScene());
            }
        });
        backButton.setOnMouseClicked(event -> goToView(event, MenuView.getInstance().initScene()));
        root.getChildren().addAll(header, levelName, containerVBox);

        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {

    }
}
