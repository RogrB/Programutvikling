package view;

import javafx.scene.Parent;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import controller.GameController;
import model.GameModel;
import model.enemy.Enemy;
import model.enemy.EnemyMovementPatterns;
import model.enemy.EnemyType;
import model.levels.LevelData;
import model.levels.LevelLoader;

import java.util.ArrayList;

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
    final LevelLoader level2 = new LevelLoader(LevelData.LEVEL2);
    ArrayList<Enemy> enemies = level2.getEnemies();

    public Parent initGame() {
        Pane root = new Pane();
        Pane enems = new Pane();
        root.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
        root.setBackground(getBackGroundImage());
//        Enemy eee = new Enemy(EnemyType.SHIP, EnemyMovementPatterns.CLOCK, 800, 40);
//        eee.update();
//        enemies.add(eee);

        for(Enemy e : enemies){
            enems.getChildren().add(e.getSprite());
        }

        root.getChildren().addAll(gm.player.getSpriteView(), enems, canvas);
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
