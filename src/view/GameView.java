package view;

import assets.java.AudioManager;
import assets.java.SoundManager;
import controller.UserInputs;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import controller.GameController;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Existance;
import model.GameModel;
import model.GameState;
import model.enemy.Enemy;
import model.enemy.EnemyType;
import model.levels.LevelData;
import model.weapons.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import java.text.DecimalFormat;
import javafx.application.Platform;
import multiplayer.MultiplayerHandler;
import model.player.Player;
import model.player.Player2;

import static model.GameState.bossType;
import static controller.GameController.gs;
import static view.ViewUtil.VIEW_HEIGHT;
import static view.ViewUtil.VIEW_WIDTH;

public class GameView extends ViewUtil{

    // Singleton
    private static GameView inst = new GameView();
    private GameView(){}
    public static GameView getInstance(){ return inst; }

    // MVC-access
    private GameController gc = GameController.getInstance();
    private GameModel gm = GameModel.getInstance();

    private final Canvas canvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
    private final Canvas hudCanvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
    private final Canvas bulletLayerCanvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
    private final Canvas enemyLayerCanvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
    private final Canvas playerLayerCanvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
    private final Canvas player2LayerCanvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);

    private Text scoreText;
    private Text levelText;
    private Text weaponType;
    
    private final static Font powerUpFont = new Font("SansSerif", 12);
    
    private final GraphicsContext graphics = canvas.getGraphicsContext2D();
    private final GraphicsContext hud = hudCanvas.getGraphicsContext2D();
    private final GraphicsContext bulletLayer = bulletLayerCanvas.getGraphicsContext2D();
    private final GraphicsContext enemyLayer = enemyLayerCanvas.getGraphicsContext2D();
    private final GraphicsContext playerLayer = playerLayerCanvas.getGraphicsContext2D();
    private final GraphicsContext player2Layer = player2LayerCanvas.getGraphicsContext2D();

    private Rectangle dialogBackground;
    private Text dialogText;
    public StackPane dialogBox;

    private MenuButton retryButton;
    private MenuButton exitToMenuButton;
    private VBox lostButtonContainer;
    private VBox wonButtonContainer;
    private MenuButton continueButton;
    private MenuButton exitToMenuButton2;

    private MenuButton[] menuElementsLost;
    private MenuButton[] menuElementsWon;
    private AudioManager am;
    private SoundManager sm;
    
    Pane root;

    private static final String BG_IMG = "assets/image/background.jpg";

    public void mvcSetup(){
        gm.mvcSetup();
        gc.mvcSetup();
        am = AudioManager.getInstance();
        sm = SoundManager.getInst();
    }

    public Parent initScene() {

        //AudioManager.getInstance().setMusic("BATTLE");
        SoundManager.getInst().playMusic("music_battle");

        scoreText = new Text(VIEW_WIDTH - 150, 60, "Score: " + Integer.toString(gs.player.getScore()));
        levelText = new Text(VIEW_WIDTH - 150, 30, "Level 1"); // Må hente riktig level fra leveldata

        scoreText.setFill(Color.WHITE);
        scoreText.setFont(Font.font("Verdana", 20));  
        levelText.setFill(Color.WHITE);
        scoreText.setFont(Font.font("Verdana", 20));
        
        weaponType = new Text(110, 30, "WeaponType: ");
        weaponType.setFill(Color.WHITE);
        weaponType.setFont(Font.font("Verdana", 14));

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
                if(gm.getMultiplayerStatus()) {
                    MultiplayerHandler.getInstance().send("Disconnect", 0, 0);
                    MultiplayerHandler.getInstance().disconnect();
                    System.out.println("Game paused, disconnected from multiplayer");
                }                
                gc.gamePause();
            }
        });
        dialogBox.setOpacity(0);
        for(int i = 0; i < EnemyType.BOSS01.MAX_HEALTH; i++){
            Rectangle rect = new Rectangle();
            rect.setFill(Color.GREEN);
            rect.setHeight(30);
            rect.setWidth(30);
        }

        continueButton = new MenuButton("CONTINUE");
        exitToMenuButton2 = new MenuButton("MAIN MENU");
        wonButtonContainer = new VBox();
        wonButtonContainer.setOpacity(1);
        wonButtonContainer.setTranslateX(450);
        wonButtonContainer.setTranslateY(550);

        errorField = new WarningField();
        errorField.setTranslateX(475);
        errorField.setTranslateY(250);

        retryButton = new MenuButton("RETRY");
        exitToMenuButton = new MenuButton("MAIN MENU");
        menuElementsLost = new MenuButton[]{retryButton, exitToMenuButton};
        menuElementsWon = new MenuButton[]{continueButton, exitToMenuButton2};
        lostButtonContainer = new VBox();
        lostButtonContainer.getChildren().addAll(retryButton, exitToMenuButton);
        lostButtonContainer.setTranslateX(450);
        lostButtonContainer.setTranslateY(550);
        lostButtonContainer.setOpacity(0);
        
        root = new Pane();

        root.setPrefSize(VIEW_WIDTH, VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(BG_IMG));
        if(gm.getMultiplayerStatus()) {
            root.getChildren().addAll(errorField, canvas, hudCanvas, enemyLayerCanvas, bulletLayerCanvas, playerLayerCanvas, scoreText, levelText, weaponType, dialogBox, player2LayerCanvas);
        }
        else {
            root.getChildren().addAll(errorField, wonButtonContainer, lostButtonContainer, canvas, hudCanvas, enemyLayerCanvas, bulletLayerCanvas, playerLayerCanvas, scoreText, levelText, weaponType, dialogBox);
        }
        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {
        if(buttonName.equals("RETRY")){
            System.out.println("Totally started a new game");
            gc.newGame();
            gs.player.init();
            lostButtonContainer.setOpacity(0);
            System.out.println(gs.player.isAlive());
        }
        else if(buttonName.equals("MAIN MENU")){
            goToView(event, MenuView.getInstance().initScene());
        }
        elementCounter = 0;
    }

    public void render(Existance object) {
        GraphicsContext gc;
        if(object instanceof Basic)
            gc = bulletLayer;
        else if(object instanceof Enemy)
            gc = enemyLayer;
        else if (object instanceof Player)
            gc = playerLayer;
        else if (object instanceof Player2)
            gc = player2Layer;
        else
            gc = graphics;

        gc.clearRect(object.getOldX(), object.getOldY(), object.getOldWidth(), object.getOldHeight());
        gc.drawImage(object.getImage(), object.getX(), object.getY());
    }

    public void gameOver() {
        // Is ded!
        menuElementsLost[0].gainedFocus();
        lostButtonContainer.setOpacity(1);
        graphics.drawImage(new Image("assets/image/gameover.png"), (VIEW_WIDTH/2) - 368, (VIEW_HEIGHT/2) - 51);
    }

    public void gameWon(){
        renderScoreScreen();
        getMenuElementsWon()[0].gainedFocus();
        wonButtonContainer.setOpacity(1);
    }
    
    public void renderShield() {
        graphics.clearRect(gs.player.getX()-10, gs.player.getY()-30, gs.player.getOldWidth()+35, gs.player.getOldHeight()+70);
        graphics.drawImage(gs.player.getShieldSprite(), gs.player.getX(), gs.player.getY()-1);
    }

    public void clearAllGraphics(){
        graphics.clearRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        hud.clearRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        bulletLayer.clearRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        enemyLayer.clearRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        playerLayer.clearRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        player2Layer.clearRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
    }
    
    public void renderScoreScreen() {
        
	Platform.runLater(new Runnable() {
            @Override
            public void run() {
                float bullets = gs.player.getBulletCount();
                float hits = gs.player.getBulletsHit();
                float accuracy = (hits / bullets) * 100;
                DecimalFormat accuracyFormat = new DecimalFormat("#.00");
                
                Text levelComplete = new Text((VIEW_WIDTH/2) - 140, (VIEW_HEIGHT/2) - 180, "Level 1 Cleared!"); // Trenger å hente levelnr fra leveldata
                Text scoreT = new Text((VIEW_WIDTH/2) - 300, (VIEW_HEIGHT/2) - 90, "Score:   " + Integer.toString(gs.player.getScore()));
                Text shotsFired = new Text((VIEW_WIDTH/2) - 300, (VIEW_HEIGHT/2) - 40, "Shots fired:   " + Float.toString(bullets));
                Text enemiesHit = new Text((VIEW_WIDTH/2) - 300, (VIEW_HEIGHT/2) + 10, "Enemies hit:   " + Float.toString(hits));
                Text enemiesKilled = new Text((VIEW_WIDTH/2) - 300, (VIEW_HEIGHT/2) + 60, "Enemies killed:   " + Integer.toString(gs.player.getEnemiesKilled()));

                Text hitPercent = new Text((VIEW_WIDTH/2) - 300, (VIEW_HEIGHT/2) + 110, "Accuracy:   " + accuracyFormat.format(accuracy) + "%");

                levelComplete.setFill(Color.WHITE);
                levelComplete.setFont(Font.font("Verdana", 32));  
                scoreT.setFill(Color.WHITE);
                scoreT.setFont(Font.font("Verdana", 30));        
                shotsFired.setFill(Color.WHITE);
                shotsFired.setFont(Font.font("Verdana", 30));
                enemiesHit.setFill(Color.WHITE);
                enemiesHit.setFont(Font.font("Verdana", 30));
                enemiesKilled.setFill(Color.WHITE);
                enemiesKilled.setFont(Font.font("Verdana", 30));
                hitPercent.setFill(Color.WHITE);
                hitPercent.setFont(Font.font("Verdana", 30));

                root.getChildren().addAll(levelComplete, scoreT, shotsFired, enemiesHit, enemiesKilled, hitPercent);

                hud.drawImage(new Image("assets/image/scorescreen.png"), (VIEW_WIDTH/2) - 360, (VIEW_HEIGHT/2) - 225);                
            }
	});              
    }
    
    void renderHUD(HUD h, boolean shield) {
        hud.clearRect(15, 15, 120, 50);
        hud.drawImage(h.getPlayerIcon(), 20, 20);
        hud.drawImage(h.getNumeralX(), 50, 20);
        hud.drawImage(h.getLifeCounter(), 70, 20);
        hud.drawImage(h.getWeaponTypeImg(), 100, 15);
        if(shield) {
            hud.drawImage(h.getShieldIcon(), 20, 50);
            if (gs.player.shield().getCharges() == 2) {
                hud.drawImage(h.getShieldIcon(), 45, 50);
            }
        }
        if(bossType != null){
            EnemyType boss = EnemyType.valueOf(bossType);
            for(Enemy enemy : GameState.enemies){
                if(enemy.getType() == boss){
                    hud.clearRect(VIEW_WIDTH/3, 40, 328, 105);
                    if (enemy.getHealth() > 19) {
                        hud.drawImage(new Image("assets/image/hud/bossHealth_Bar_Full.png"), VIEW_WIDTH/3, 40);
                        hud.drawImage(new Image("assets/image/hud/bossHealth_Border.png"), VIEW_WIDTH/3, 40);
                    }
                    else if (enemy.getHealth() == 19) {
                        hud.drawImage(new Image("assets/image/hud/bossHealth_Bar.png"), (VIEW_WIDTH/3) + 70, 73);
                        hud.drawImage(new Image("assets/image/hud/bossHealth_Border.png"), VIEW_WIDTH/3, 40);
                    }
                    else if (enemy.getHealth() <= 0) {
                        hud.drawImage(new Image("assets/image/hud/bossHealth_Border.png"), VIEW_WIDTH/3, 40);
                    }
                    else {
                        hud.drawImage(new Image("assets/image/hud/bossHealth_Bar.png"), (VIEW_WIDTH/3) + 70, 73, 230 - ((boss.MAX_HEALTH - enemy.getHealth()) * 11.7), 30);
                        hud.drawImage(new Image("assets/image/hud/bossHealth_Border.png"), VIEW_WIDTH/3, 40);
                    }

                }
            }
        }

        scoreText.setText("Score: " + Integer.toString(gs.player.getScore()));
        levelText.setText(getLevelName()); // må hente riktig level fra leveldata
        weaponType.setText(h.weaponType());
    }
    
    void renderPowerUpText(String powerUp, int x, int y, float opacity) {
        hud.clearRect(x-10, y-10, 300, 100);
        hud.setFill(new Color(1, 1, 1, opacity));
        hud.setFont(powerUpFont);
        hud.fillText(powerUp, x, y);
    }
    
    void clearPowerUpText(int x, int y) {
        hud.clearRect(x-10, y-50, 300, 300);
    }

    public WarningField getField(){
        return errorField;
    }

    public MenuButton[] getMenuElementsLost(){return menuElementsLost;}

    public MenuButton[] getMenuElementsWon(){return menuElementsWon;}

    private String getLevelName(){
        if(gs.levelData.equals(LevelData.LEVEL1)){
            return "Level 1";
        } else if (gs.levelData.equals(LevelData.LEVEL2)){
            return "Level 2";
        } else {
            return "Custom Level";
        }
    }
}
