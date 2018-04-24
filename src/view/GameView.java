package view;

import javafx.scene.Parent;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import controller.GameController;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Existance;
import model.GameModel;
import model.enemy.Enemy;
import model.enemy.EnemyType;
import model.weapons.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class GameView extends ViewUtil{

    // Singleton
    private static GameView inst = new GameView();
    private GameView(){}
    public static GameView getInstance(){ return inst; }

    // MVC-access
    GameController gc = GameController.getInstance();
    GameModel gm = GameModel.getInstance();

    final Canvas canvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
    final Canvas hudCanvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
    final Canvas bulletLayerCanvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
    final Canvas enemyLayerCanvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
    
    Text scoreText = new Text(VIEW_WIDTH - 150, 60, "Score: " + Integer.toString(gm.player.getScore()));
    Text levelText = new Text(VIEW_WIDTH - 150, 30, "Level 1"); // Må hente riktig level fra leveldata
    
    final GraphicsContext graphics = canvas.getGraphicsContext2D();
    final GraphicsContext hud = hudCanvas.getGraphicsContext2D();
    final GraphicsContext bulletLayer = bulletLayerCanvas.getGraphicsContext2D();
    final GraphicsContext enemyLayer = enemyLayerCanvas.getGraphicsContext2D();

    private ArrayList<Rectangle> bossHealthBarList = new ArrayList<>();
    public HBox bossHealthBar = new HBox();
    private int listCount;
    private Rectangle dialogBackground;
    private Text dialogText;
    public StackPane dialogBox;

    private static final String BG_IMG = "assets/image/background.jpg";

    public void mvcSetup(){
        gm.mvcSetup();
        gc.mvcSetup();
    }

    public Parent initScene() {
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(Font.font("Verdana", 20));  
        levelText.setFill(Color.WHITE);
        scoreText.setFont(Font.font("Verdana", 20));

        dialogBackground = new Rectangle(600, 200);
        dialogBackground.setFill(Color.BLACK);
        dialogBackground.setStroke(Color.WHITE);
        dialogBackground.setArcHeight(15);
        dialogBackground.setArcWidth(15);

        dialogText = new Text("Sup G. U totally nailed this level.\nTap enter or space to proceed to\nthe next level.\nTap Escape to go to main menu.");
        dialogText.setFill(Color.WHITE);
        dialogText.setFont(dialogText.getFont().font(30));
        dialogText.setTranslateX(-60);
        dialogText.setTranslateY(-10);

        dialogBox = new StackPane();
        dialogBox.setTranslateY(575);
        dialogBox.setTranslateX(300);

        dialogBox.getChildren().addAll(dialogBackground, dialogText);
        dialogBox.setFocusTraversable(true);
        dialogBox.requestFocus();
        dialogBox.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                goToView(event, MenuView.getInstance().initScene());
            }
        });
        dialogBox.setOpacity(0);
        for(int i = 0; i < EnemyType.BOSS01.MAX_HEALTH; i++){
            Rectangle rect = new Rectangle();
            rect.setFill(Color.GREEN);
            rect.setHeight(30);
            rect.setWidth(30);
            bossHealthBarList.add(rect);
        }
        for(Rectangle rectangle : bossHealthBarList){
            bossHealthBar.getChildren().add(rectangle);
        }
        listCount = bossHealthBarList.size() -1;
        bossHealthBar.setTranslateX(300);
        bossHealthBar.setTranslateY(750);
        
        Pane root = new Pane();
        root.setPrefSize(VIEW_WIDTH, VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(BG_IMG));
        if(gm.getMultiplayerStatus()) {
            root.getChildren().addAll(gm.player.getImageView(), canvas, hudCanvas, enemyLayerCanvas, bulletLayerCanvas, scoreText, levelText, bossHealthBar, dialogBox, gm.player2.getImageView());
        }
        else {
            root.getChildren().addAll(gm.player.getImageView(), canvas, hudCanvas, enemyLayerCanvas, bulletLayerCanvas, scoreText, levelText, bossHealthBar, dialogBox);            
        }
        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {

    }

    public void render(Existance object) {
        GraphicsContext gc;
        if(object instanceof Bullet)
            gc = bulletLayer;
        else if(object instanceof Enemy)
            gc = enemyLayer;
        else
            gc = graphics;
        //System.out.println("Rendering object at: " + object.getX() + " , " + object.getY() + " using " + object.getImage());

        gc.clearRect(object.getOldX(), object.getOldY(), object.getOldWidth(), object.getOldHeight());
        gc.drawImage(object.getImage(), object.getX(), object.getY());
    }

    public void gameOver() {
        // Is ded!
        graphics.drawImage(new Image("assets/image/gameover.png"), (VIEW_WIDTH/2) - 368, (VIEW_HEIGHT/2) - 51);
    }
    
    public void renderShield() {
        graphics.clearRect(gm.player.getX()-10, gm.player.getY()-30, gm.player.getOldWidth()+35, gm.player.getOldHeight()+70);
        graphics.drawImage(gm.player.getShieldSprite(), gm.player.getX(), gm.player.getY()-1);
    }

    public void renderHealthBar(){
        bossHealthBarList.get(listCount).setFill(Color.RED);
        listCount--;
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
        levelText.setText("Level 1"); // må hente riktig level fra leveldata
    }
}
