package view;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class ViewUtil {

    public static final int VIEW_WIDTH = 1200;
    public static final int VIEW_HEIGHT = 800;
    public Pane root;
    public Text header;

    public void goToView(KeyEvent event, Parent node){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Pane root = new Pane();
        root.getChildren().add(node);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        System.out.println("Returned to Main Scene");
    }

    public Background getBackGroundImage(String BG_IMG){
        BackgroundImage bg = new BackgroundImage(
                new Image(BG_IMG),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(
                        BackgroundSize.AUTO,
                        BackgroundSize.AUTO,
                        false,
                        false,
                        true,
                        false
                )
        );
        return new Background(bg);
    }

    public abstract Parent initScene();



}
