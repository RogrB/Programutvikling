package view;

import javafx.scene.Parent;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import controller.GameController;
import javafx.scene.paint.Color;
import model.Existance;
import model.GameModel;
import model.enemy.Enemy;
import model.weapons.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

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
    
    Text scoreText = new Text(GAME_WIDTH - 150, 30, "Score: " + Integer.toString(gm.player.getScore()));    
    
    final GraphicsContext graphics = canvas.getGraphicsContext2D();
    final GraphicsContext hud = hudCanvas.getGraphicsContext2D();
    final GraphicsContext bulletLayer = bulletLayerCanvas.getGraphicsContext2D();
    final GraphicsContext enemyLayer = enemyLayerCanvas.getGraphicsContext2D();

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
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(Font.font ("Verdana", 20));          
        
        Pane root = new Pane();
        root.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
        root.setBackground(getBackGroundImage());

        root.getChildren().addAll(gm.player.getImageView(), canvas, hudCanvas, enemyLayerCanvas, bulletLayerCanvas, scoreText);
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

    public void gameOver() {
        // Is ded!
        graphics.drawImage(new Image("assets/image/gameover.png"), (GAME_WIDTH/2) - 368, (GAME_HEIGHT/2) - 51);
    }
    
    public void renderShield() {
        graphics.clearRect(gm.player.getX()-10, gm.player.getY()-30, gm.player.getOldWidth()+35, gm.player.getOldHeight()+70);
        graphics.drawImage(gm.player.getShieldSprite(), gm.player.getX(), gm.player.getY()-1);
    }
    
    public void renderHUD(HUD h, boolean shield) {
        hud.clearRect(15, 15, 120, 50);
        hud.drawImage(h.getPlayerIcon(), 20, 20);
        hud.drawImage(h.getNumeralX(), 50, 20);
        hud.drawImage(h.getLifeCounter(), 70, 20);
        if(shield) {
            hud.drawImage(h.getShieldIcon(), 20, 50);
            if (gm.player.shield().getCharges() == 2) {
                hud.drawImage(h.getShieldIcon(), 45, 50);
            }
        }
        scoreText.setText("Score: " + Integer.toString(gm.player.getScore()));
    }
}
