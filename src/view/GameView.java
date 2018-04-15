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
import model.PowerUp;

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
    final Canvas hudCanvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
    final Canvas bulletLayerCanvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
    final Canvas enemyLayerCanvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
    
    final GraphicsContext graphics = canvas.getGraphicsContext2D();
    final GraphicsContext hud = hudCanvas.getGraphicsContext2D();
    final GraphicsContext bulletLayer = bulletLayerCanvas.getGraphicsContext2D();
    final GraphicsContext enemyLayer = enemyLayerCanvas.getGraphicsContext2D();
    
    LevelLoader level2 = new LevelLoader(LevelData.LEVEL2);
    ArrayList<Enemy> enemies = level2.getEnemies(); // trengs den her lenger?

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
        root.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
        root.setBackground(getBackGroundImage());

        root.getChildren().addAll(gm.player.getImageView(), canvas, hudCanvas, enemyLayerCanvas, bulletLayerCanvas);
        return root;
    }

    public void render(Existance object) {
        GraphicsContext gc;
        if(object instanceof Bullet)
            gc = bulletLayer;
        else if(object instanceof Enemy)
            gc = enemyLayer;
        else
            gc = graphics;

        gc.clearRect(object.getOldX(), object.getOldY(), object.getOldWidth(), object.getOldHeight());
        gc.drawImage(object.getImage(), object.getX(), object.getY());
    }

    
    public void clearLast(Existance object) {
        graphics.clearRect(object.getOldX()-10, object.getOldY()-10, object.getWidth()+30, object.getHeight()+30);
    }

    public ArrayList<Enemy> getEnemies(){ // trengs denne her?
        return enemies;
    }

    public void gameOver() {
        // Is ded!
        graphics.drawImage(new Image("assets/image/gameover.png"), (GAME_WIDTH/2) - 368, (GAME_HEIGHT/2) - 51);
    }
    
    public void renderShield() {
        graphics.clearRect(gm.player.getX(), gm.player.getY()-30, gm.player.getWidth()+5, gm.player.getHeight()+70);
        graphics.drawImage(gm.player.getImage(), gm.player.getX(), gm.player.getY()-1);
    }
    
    public void renderHUD(HUD h, boolean shield) {
        hud.clearRect(15, 15, 120, 50);
        hud.drawImage(h.getPlayerIcon(), 20, 20);
        hud.drawImage(h.getNumeralX(), 50, 20);
        hud.drawImage(h.getLifeCounter(), 70, 20);
        if(shield) {
            hud.drawImage(h.getShieldIcon(), 20, 50);
        }
    }
}
