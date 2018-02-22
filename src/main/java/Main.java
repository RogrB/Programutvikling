package main.java;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import Player.*;

public class Main extends Application{

    // Stage oppsett
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final double SPEED_MODIFIER = 0.17;
    
    // Oppretter player objekt
    Player player = new Player();
    
    // Arraylist for Enemies
    private ArrayList<Enemy> enemies = new ArrayList<>();
    
    // GraphicsContext og Spillvariabler
    private GraphicsContext gc; // Brukes for å tegne primitive rektangler - kan evt byttes ut når vi implementerer sprites?
    private double time;
    private Text scoreText;
    private Text lifeText;
    private int score = 0;
    private int collisions = 0;
    
    // Bakgrunnsbilde
    String imgpath = "image/background.jpg";
    Image img = new Image(imgpath);
    BackgroundImage bg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));    

    // Gammel metode for å generere content - Byttet ut med initScene()
    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        generateEnemies();
        moveEnemies();

        return root;
    }

    // Metode for å generere fiender
    public void generateEnemies() {
        Enemy test = new Enemy(EnemyType.SHIP, MovementPattern.SIN);
        enemies.add(test);
    }

    // Metode for å flytte fiender
    public void moveEnemies(){
        AnimationTimer timer = new AnimationTimer() { // Bør muligens flyttes inn i eksisterende animationtimer i initScene()  ?
            @Override
            public void handle(long now) {
                for (Enemy enemy : enemies){
                    enemy.move();
                    drawEnemy(gc); // Kaller metode for å tegne enemies (primitive representasjoner)
                }
            }
        };

        timer.start();
    }

    // Start metode - Setter opp Scene
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Working Title: Pippi");
        // oppretter rootpane, canvas og scene        
        Scene scene = new Scene(initScene());

        primaryStage.setScene(scene);
        primaryStage.show();

        // Key event handler for scene - For Spillerinput
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) { // Trykk på Space
                    shoot();
                }
                if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) { // Trykk på W eller ArrowUp
                    player.moveUp();
                }
                if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) { // Trykk på S eller ArrowDown
                    player.moveDown();
                }
            }
        });  
    }
    
    private Parent initScene() {
        // Initialize Game - Oppretter nodes
        Pane root = new Pane();
        
        // Oppretter Score og lifeText 
        String scoreT = "Score: " + Integer.toString(score);
        String lifeT = "Collisions: " + Integer.toString(collisions);
        scoreText = new Text(20, 20, scoreT); // Viser score øverst i venstre hjørne
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(Font.font ("Verdana", 20));
        lifeText = new Text(1000, 20, lifeT); // Viser collisions øverst i høyre hjørne
        lifeText.setFill(Color.WHITE);
        lifeText.setFont(Font.font("Verdana", 20));
        
        // Videre oppsett for root - Backgrunn og størrelse
        root.setPrefSize(WIDTH, HEIGHT);
        root.setBackground(new Background(bg));
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Legger nodes til root
        root.getChildren().addAll(canvas, scoreText, lifeText);
        
        // Kaller metoder for Enemies
        generateEnemies();
        moveEnemies();
        
        // Animationtimer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += 0.05;
                drawPlayer(gc);
                detectCollision();

                if (time >= 0.35) { // Hvis vi trenger en funksjon som ikke *må* oppdateres hvert tick av animationtimeren
                    // Do something
                    time = 0;
                }
            }
        };
        timer.start(); // Starter animationtimer  
        return root; // Returnerer root til start()
    }
    
    // Metode for å skyte - trenger implementasjon
    public void shoot() {
        System.out.println("PEWPEWPEW!");
    }
    
    // Metode for å tegne player - Primitiv rektangel, må byttes ut med sprite
    public void drawPlayer(GraphicsContext gc) {
        gc.setStroke(Color.WHITE); // Player farge
        gc.setLineWidth(3); // Linjebredde
        gc.clearRect(player.getX()-1, player.getOldY()-1, player.getWidth()+3, player.getHeight()+3); // "visker ut" forrige rektangel før det tegnes en ny - Må være større enn forrige frame, ellers blir det igjen litt av forrige rektangel
        gc.strokeRect(player.getX(), player.getY(), player.getWidth(), player.getHeight()); // tegner ny rektangel-frames
        player.setOldY(player.getY()); // Setter oldY verdi, for å viske ut riktig posisjon neste frame
    }
    
    public void drawEnemy(GraphicsContext gc) {
        gc.setStroke(Color.RED); // Player farge
        gc.setLineWidth(3); // Linjebredde
        for (Enemy enemy : enemies){
            gc.clearRect(enemy.x()-1, enemy.y()-1, 53, 53); 
            gc.strokeRect(enemy.x(), enemy.y(), 50, 50); // Setter bredde og høyde til 50 for nå
        }      
    }
    
    // Metode for å detecte kollisjon - trenger implementasjon
    public void detectCollision() {
        // DO SOMETHING
    }
    
    
}
