package main.java;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application{

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final double SPEED_MODIFIER = 0.17;

    private ArrayList<Enemy> enemies = new ArrayList<>();

    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        generateEnemies();
        moveEnemies();

        return root;
    }

    public void generateEnemies() {
        Enemy test = new Enemy(EnemyType.ASTROID, MovementPattern.LEFT);
        enemies.add(test);
    }

    public void moveEnemies(){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Enemy enemy : enemies){
                    enemy.move();
                }
            }
        };

        timer.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Working Title: Pippi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
