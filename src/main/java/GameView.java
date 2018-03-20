package main.java;

import controller.GameController;
import model.enemy.Enemy;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.enemy.EnemyMovementPatterns;
import model.enemy.EnemyType;
import model.levels.LevelData;

import java.util.ArrayList;

public class GameView extends Application {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    public ArrayList<Enemy> enemies = new ArrayList<>();

    GameController gc = GameController.getInstance();

    // Background Image
    String imgpath = "image/background.jpg";
    Image img = new Image(imgpath);
    BackgroundImage bg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Working Title: Pippi");
        Scene scene = new Scene(initGame());

        gc.setKeyListeners(scene);

        primaryStage.setScene(scene);
        primaryStage.show();


        // ANIMATION TIMER, UPDATES VIEW
        /*AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.player.update();

            }
        }; timer.start();*/
    }

    public ArrayList getEnemies(){
        return enemies;
    }

    private Parent initGame() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);
        root.setBackground(new Background(bg));
        Enemy eee = new Enemy(EnemyType.SHIP, EnemyMovementPatterns.CLOCK, 400, 400);
        eee.update();

        int levelWidth = LevelData.LEVEL2.length * 60;
        for(int i = 0; i < LevelData.LEVEL2.length; i++){
            String line = LevelData.LEVEL2[i];
            for(int j = 0; j < line.length(); j++){
                switch(line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        enemies.add(new Enemy(EnemyType.SHIP, EnemyMovementPatterns.CLOCK, 1 + (j * 100),1 + (i * 120)));
                        System.out.format("Enemy added at x: %d, y: %d \n", i*60, j*60);
                        break;
                    case '2':
                        enemies.add(new Enemy(EnemyType.ASTROID, EnemyMovementPatterns.CLOCK, 1 + (j * 100), 1 + (i * 120)));
                        break;
                }
            }
        }

        for(Enemy e: enemies){
            root.getChildren().add(e.getSprite());
            e.update();
        }

        root.getChildren().addAll(gc.gl.player.getSprite(), eee.getSprite());

        return root;
    }

}
