package view;

import javafx.scene.Parent;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import controller.GameController;
import model.GameModel;

public class GameView extends ViewUtil {

    // Singleton
    private static GameView inst = new GameView();
    private GameView(){}
    public static GameView getInst(){ return inst; }
    

    // MVC-access
    GameController gc = GameController.getInstance();
    GameModel gm = GameModel.getInstance();

    final Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
    final GraphicsContext graphics = canvas.getGraphicsContext2D();

    public Parent initGame() {
        Pane root = new Pane();
        root.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
        root.setBackground(getBackGroundImage());

        root.getChildren().addAll(gm.player.getSpriteView(), canvas);
        renderBullet(50, 50);
        renderBullet(100, 100);
        renderBullet(200, 200);
        return root;
    }

    final Image bullet = new Image("assets/laserBlue06.png");
    public void renderBullet(double x, double y) {
        //graphics.drawImage(bullet, x, y);
        System.out.println(x + " " + y);
    }
}
