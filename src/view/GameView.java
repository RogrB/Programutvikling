package view;

import assets.java.Sprite;
import javafx.scene.Parent;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import controller.GameController;
import model.Entity;
import model.Existance;
import model.GameModel;
import model.enemy.Enemy;
import model.levels.LevelData;
import model.levels.LevelLoader;
import model.weapons.*;

import java.util.ArrayList;

public class GameView {

    // Singleton
    private static GameView inst = new GameView();
    private GameView(){}
    public static GameView getInstance(){ return inst; }

    // MVC-access
    GameController gc = GameController.getInstance();
    GameModel gm = GameModel.getInstance();

    public static final int GAME_WIDTH = 1200;
    public static final int GAME_HEIGHT = 800;

    final Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
    final GraphicsContext graphics = canvas.getGraphicsContext2D();
    final LevelLoader level2 = new LevelLoader(LevelData.LEVEL2);
    ArrayList<Enemy> enemies = level2.getEnemies();

    private static final String BG_IMG = "assets/image/background.jpg";

    public void setup(){
        gm.setup();
        gc.setup();
        gc.start();
    }

    public Background getBackGroundImage(){
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

    public Parent initGame() {
        Pane root = new Pane();
        Pane enems = new Pane();
        root.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
        root.setBackground(getBackGroundImage());

        for(Enemy e : enemies){
            enems.getChildren().add(e.getSprite());
        }

        root.getChildren().addAll(gm.player.getSprite(), enems, canvas);
        return root;
    }

    
    public void renderBullet(Bullet bullet) {
        graphics.clearRect(bullet.getOldX(), bullet.getOldY(), bullet.getWidth(), bullet.getHeight());
        graphics.drawImage(bullet.getSpriteImage(), bullet.getX(), bullet.getY());
    }
    
    public void gameOver() {
        // Is ded!
        graphics.drawImage(new Image("assets/image/gameover.png"), (GAME_WIDTH/2) - 368, (GAME_HEIGHT/2) - 51);
    }
}
