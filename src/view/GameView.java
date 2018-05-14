package view;

import assets.java.SoundManager;
import javafx.scene.Parent;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import controller.GameController;
import javafx.scene.paint.Color;
import model.Existence;
import model.GameModel;
import model.GameState;
import model.enemy.Enemy;
import model.enemy.EnemyType;
import model.weapons.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import java.text.DecimalFormat;

import javafx.application.Platform;
import model.player.Player;

import static model.GameState.bossType;
import static controller.GameController.gs;

/**
 * <h1>The main class for rendering all in-game objects</h1>
 * Everything that is displayed to the player
 * in-game is handled through this class, including the Heads Up Display.
 */
public class GameView extends ViewUtil{

    /**
     * The singleton object.
     */
    private static GameView inst = new GameView();

    /**
     * Private <b>constructor</b>
     */
    private GameView(){}
    
    /**
     * Method to access singleton class.
     * @return Returns a reference to the singleton object.
     */
    public static GameView getInstance(){ return inst; }

    /**
     * Accesses an instance of the {@code GameController} object
     */
    private GameController gc = GameController.getInstance();
    
    /**
     * Accesses an instance of the {@code GameModel} object
     */
    private GameModel gm = GameModel.getInstance();
    /**
     * A default canvas, for rendering everything
     * that does not need its own layer
     */
    private final Canvas canvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);

    /**
     * A canvas for rendering the Heads Up Display
     */
    private final Canvas hudCanvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);

    /**
     * A canvas for rendering the bullets
     */
    private final Canvas bulletLayerCanvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);

    /**
     * A canvas for rendering enemies
     */
    private final Canvas enemyLayerCanvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);

    /**
     * A canvas for rendering the player
     */
    private final Canvas playerLayerCanvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);

    /**
     * A canvas for rendering player2 - needs its own canvas to avoid
     * clearing issues between the players
     */
    private final Canvas player2LayerCanvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);

    /**
     * Text for displaying the score on the HUD
     */
    private Text scoreText;

    /**
     * Text for displaying the current level on the HUD
     */
    private Text levelText;

    /**
     * Text for displaying the current weapon type on the HUD
     */
    private Text weaponType;

    /**
     * Sets font for the powerUp text
     */
    private final static Font powerUpFont = new Font("SansSerif", 12);

    /**
     * GraphicsContext for the default canvas layer
     */
    private final GraphicsContext graphics = canvas.getGraphicsContext2D();

    /**
     * GraphicsContext for the HUD canvas layer
     */
    private final GraphicsContext hud = hudCanvas.getGraphicsContext2D();

    /**
     * GraphicsContext for the bullet canvas layer
     */
    private final GraphicsContext bulletLayer = bulletLayerCanvas.getGraphicsContext2D();

    /**
     * GraphicsContext for the enemy canvas layer
     */
    private final GraphicsContext enemyLayer = enemyLayerCanvas.getGraphicsContext2D();
    
    /**
     * GraphicsContext for the player canvas layer
     */         
    private final GraphicsContext playerLayer = playerLayerCanvas.getGraphicsContext2D();
    
    /**
     * GraphicsContext for the player2 canvas layer
     */
    private final GraphicsContext player2Layer = player2LayerCanvas.getGraphicsContext2D();

    /**
     * Container for the Game-Over menu buttons
     */
    private VBox lostButtonContainer;

    /**
     * Container for the Level-complete menu buttons
     */
    private VBox wonButtonContainer;

    /**
     * Array of MenuButtons for the Game-Over menu
     */
    private MenuButton[] menuElementsLost;

    /**
     * Array of MenuButtons for the Level-Complete menu
     */
    private MenuButton[] menuElementsWon;

    /**
     * Text for the Scorescreen - Level complete
     */
    private Text levelComplete = new Text();

    /**
     * Text for the Scorescreen - Score count
     */
    private Text scoreT = new Text();

    /**
     * Text for the Scorescreen - Amounts of shots fired
     */
    private Text shotsFired = new Text();

    /**
     * Text for the Scorescreen - Amount of enemies hit
     */
    private Text enemiesHit = new Text();

    /**
     * Text for the Scorescreen - Amount of enemies killed
     */
    private Text enemiesKilled = new Text();

    /**
     * Text for the Scorescreen - Accuracy
     */
    private Text hitPercent = new Text();

    private MenuButton continueButton;
    private MenuButton retryButton;
    private MenuButton exitToMenuButton;
    private MenuButton exitToMenuButton2;

    private Pane requestPane;

    /**
     * Background source
     */             
    private static final String BG_IMG = "assets/image/background.jpg";

    /**
     * sets up the model-view-controller
     */
    public void mvcSetup(){
        gm.mvcSetup();
        gc.mvcSetup();
    }

    @Override
    void setButtonClickEvents() {

    }

    @Override
    void setButtonPressEvents(Parent container) {

    }

    private void createTexts(){
        scoreText = createText("Score: " + Integer.toString(gs.player.getScore()), VIEW_WIDTH - 150, 60, Font.font("Verdana", 20));
        levelText = createText("Level: " + Integer.toString(gs.getLevelIterator()), VIEW_WIDTH - 150, 30, Font.font("Verdana", 20));
        weaponType = createText("WeaponType: ", 110, 30, Font.font("Verdana", 14));
    }

    private void createButtons(){
        continueButton = new MenuButton("CONTINUE");
        retryButton = new MenuButton("RETRY");
        exitToMenuButton = new MenuButton("MAIN MENU");
        exitToMenuButton2 = new MenuButton("MAIN MENU");
    }

    private void createUI(){
        createTexts();
        createButtons();
        setErrorFieldPosition();
    }

    private void createContainers(){
        wonButtonContainer = createMenuContainer(450, 675, 0);
        wonButtonContainer.getChildren().addAll(continueButton, exitToMenuButton2);
        wonButtonContainer.setOpacity(0);
        menuElementsWon = new MenuButton[]{continueButton, exitToMenuButton2};

        lostButtonContainer = createMenuContainer(450, 550, 0);
        lostButtonContainer.getChildren().addAll(retryButton, exitToMenuButton);
        lostButtonContainer.setOpacity(0);
        menuElementsLost = new MenuButton[]{retryButton, exitToMenuButton};
    }

    private void decideLayout(){
        if(gm.getMultiplayerStatus()) {
            root.getChildren().addAll(errorField, wonButtonContainer, canvas, hudCanvas, enemyLayerCanvas, bulletLayerCanvas, playerLayerCanvas, scoreText, levelText, weaponType, requestPane, player2LayerCanvas);
        }
        else {
            root.getChildren().addAll(errorField, wonButtonContainer, lostButtonContainer, playerLayerCanvas, canvas, hudCanvas, enemyLayerCanvas, bulletLayerCanvas, scoreText, levelText, weaponType, requestPane);
        }
    }

    private Pane createRequestPane(){
        Pane pane = new Pane();
        pane.setFocusTraversable(true);
        pane.requestFocus();
        pane.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                if(SoundManager.getInst().getPlayer() != null){
                    SoundManager.getInst().playMusic("stop");
                }
                SoundManager.getInst().playMusic("music_menu");
                goToView(event, MenuView.getInstance().initScene());
                gc.gamePause();
            }
        });
        return pane;
    }

    /**
     * Method for initiating the object
     * @return gets the root pane
     */
    public Parent initScene() {
        if(SoundManager.getInst().getPlayer() != null){
            SoundManager.getInst().playMusic("stop");
        }
        SoundManager.getInst().playMusic("music_battle");

        createUI();
        createContainers();
        requestPane = createRequestPane();

        root = initBaseScene(BG_IMG);
        decideLayout();

        compareErrorMessage();

        return root;
    }

    /**
     * Method for handling selection of menu elements
     * @param buttonName inputs the name of the button that was pressed
     * @param event inputs the event
     */
    @Override
    public void select(String buttonName, KeyEvent event) {
        if(buttonName.equals("RETRY")){
            System.out.println("Totally started a new game");
            gc.newGame();
            gs.player.init();
            lostButtonContainer.setOpacity(0);
            System.out.println(gs.player.isAlive());
        }
        if(buttonName.equals("MAIN MENU")){
            if(SoundManager.getInst().getPlayer() != null){
                SoundManager.getInst().playMusic("stop");
            }
            SoundManager.getInst().playMusic("music_menu");
            goToView(event, MenuView.getInstance().initScene());
        }
        if(buttonName.equals("CONTINUE")){
            System.out.println("Next level!");
            gc.nextGame();
            wonButtonContainer.setOpacity(0);
            clearScoreScreen();
        }
        elementCounter = 0;
    }

    /**
     * Method that handles the rendering of most objects
     * to the game screen.
     * The method clears the previous frames image based on the old dimensions and draws a
     * new one at the updated position.
     * @param object takes in an {@code Existance} object to be rendered
     */
    public void render(Existence object) {
        GraphicsContext gc;
        if(object instanceof Basic)
            gc = bulletLayer;
        else if(object instanceof Enemy)
            gc = enemyLayer;
        else if (object instanceof Player)
            gc = playerLayer;
        else
            gc = graphics;

        if(object instanceof Basic) {
            gc.clearRect(object.getOldX()-10, object.getOldY(), object.getOldWidth()+50, object.getOldHeight()+1);
        }
        else {
            gc.clearRect(object.getOldX(), object.getOldY(), object.getOldWidth(), object.getOldHeight());
        }
        gc.drawImage(object.getImage(), object.getX(), object.getY());
    }

    /**
     * Method that handles rendering player2 to the game screen
     */
    public void renderPlayer2() {
        player2Layer.clearRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        if(GameModel.getInstance().getMultiplayerStatus()) {
            player2Layer.drawImage(gs.player2.getImage(), gs.player2.getX(), gs.player2.getY());
        }
    }

    /**
     * Method that handles the visual feedback of the
     * game over event.
     */
    public void gameOver() {
        // Is ded!
        menuElementsLost[0].gainedFocus();
        lostButtonContainer.setOpacity(1);
        graphics.drawImage(new Image("assets/image/gameover.png"), (VIEW_WIDTH/2) - 368, (VIEW_HEIGHT/2) - 51);
    }

    /**
     * Method that handles the visual feedback of the
     * level completed event.
     */
    public void gameWon(){
        renderScoreScreen();
        menuElementsWon[0].gainedFocus();
        wonButtonContainer.setOpacity(1);
    }

    /**
     * Method that renders the players shield to the game screen.
     */
    public void renderShield() {
        graphics.clearRect(gs.player.getX()-10, gs.player.getY()-30, gs.player.getOldWidth()+35, gs.player.getOldHeight()+70);
        graphics.drawImage(gs.player.getShieldSprite(), gs.player.getX(), gs.player.getY()-1);
    }

    /**
     * Method that clears all graphics from all layers
     */
    public void clearAllGraphics(){
        graphics.clearRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        hud.clearRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        bulletLayer.clearRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        enemyLayer.clearRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        playerLayer.clearRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        player2Layer.clearRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
    }
    /**
     * Method that renders the scorescreen when a level is completed.
     * Shows the score, shots fired, enemies hit, enemies killed and accuracy.
     * May or may not be called from a thread, so the method is surrounded by Platform.runLater
     */
    public void renderScoreScreen() {

        Platform.runLater(() -> {
            float bullets = gs.player.getBulletCount();
            float hits = gs.player.getBulletsHit();
            float accuracy = (hits / bullets) * 100;
            DecimalFormat accuracyFormat = new DecimalFormat("#.00");
            levelComplete.setText("Level " + gs.getLevelIterator() + " Cleared!");
            levelComplete.setX((VIEW_WIDTH/2) - 140);
            levelComplete.setY((VIEW_HEIGHT/2) - 230);

            scoreT.setText("Score:   " + Integer.toString(gs.player.getScore()));
            scoreT.setX((VIEW_WIDTH/2) - 300);
            scoreT.setY((VIEW_HEIGHT/2) - 140);

            shotsFired.setText("Shots fired:   " + Float.toString(bullets));
            shotsFired.setX((VIEW_WIDTH/2) - 300);
            shotsFired.setY((VIEW_HEIGHT/2) - 90);

            enemiesHit.setText("Enemies hit:   " + Float.toString(hits));
            enemiesHit.setX((VIEW_WIDTH/2) - 300);
            enemiesHit.setY((VIEW_HEIGHT/2) - 40);

            enemiesKilled.setText("Enemies killed:   " + Integer.toString(gs.player.getEnemiesKilled()));
            enemiesKilled.setX((VIEW_WIDTH/2) - 300);
            enemiesKilled.setY((VIEW_HEIGHT/2) + 10);

            hitPercent.setText("Accuracy:   " + accuracyFormat.format(accuracy) + "%");
            hitPercent.setX((VIEW_WIDTH/2) - 300);
            hitPercent.setY((VIEW_HEIGHT/2) + 60);

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

            hud.drawImage(new Image("assets/image/scorescreen.png"), (VIEW_WIDTH/2) - 360, (VIEW_HEIGHT/2) - 275);
        });
    }

    /**
     * Clears the scorescreen
     * May or may not be called from a thread, so the method is surrounded by Platform.runLater
     */
    public void clearScoreScreen() {
        Platform.runLater(() -> root.getChildren().removeAll(levelComplete, scoreT, shotsFired, enemiesHit, enemiesKilled, hitPercent));
    }

    /**
     * Method that renders the Heads Up Display.
     * This includes Player Hit points, shield charges, current weapon type,
     * boss health bar, score and current level.
     */
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
        levelText.setText("Level " + Integer.toString(gs.getLevelIterator()));
        weaponType.setText(h.weaponType());
    }

    /**
     * Method for rendering the PowerUp text that shows a descriptive text
     * of the PowerUp when the player picks it up which floats upwards and
     * fades out over time.
     */
    void renderPowerUpText(String powerUp, int x, int y, float opacity) {
        hud.clearRect(x-10, y-10, 300, 100);
        hud.setFill(new Color(1, 1, 1, opacity));
        hud.setFont(powerUpFont);
        hud.fillText(powerUp, x, y);
    }

    /**
     * Clears the powerUp text
     */
    void clearPowerUpText(int x, int y) {
        hud.clearRect(x-10, y-50, 300, 300);
    }

    /**
     * @return gets the MenuButtons for the Game-Over event.
     */
    public MenuButton[] getMenuElementsLost(){return menuElementsLost;}

    /**
     * @return gets the MenuButtons for the Level completed event.
     */
    public MenuButton[] getMenuElementsWon(){return menuElementsWon;}

    /**
     * @param opacity sets the opacity for the Buttons in the Level completed event.
     */
    public void setWinButtonOpacity(int opacity) {
        wonButtonContainer.setOpacity(opacity);
    }
}