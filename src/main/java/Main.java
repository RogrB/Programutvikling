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
import Weapons.*;

public class Main extends Application{

    // Stage oppsett
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final double SPEED_MODIFIER = 0.17;
    
    // Oppretter player objekt
    Player player = new Player();
    
    // Arraylist for Enemies og bullets
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int bulletCount = 0; // antall kuler som har blitt fyrt av
    
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
                if (bulletCount > 0) {
                    for (Bullet bullet : bullets) {
                        bullet.travel();
                    }
                    detectHit();
                    drawBullets(gc);
                }

                if (time >= 0.35) { // Hvis vi trenger en funksjon som ikke *må* oppdateres hvert tick av animationtimeren
                    // Do something
                    time = 0;
                }
            }
        };
        timer.start(); // Starter animationtimer  
        return root; // Returnerer root til start()
    }
    
    // Metode for å skyte
    public void shoot() {
        bullets.add(new Bullet(player.getX()+player.getWidth(), player.getY()+(player.getHeight())/2)); // Setter X og Y posisjon for kula til midten foran på player figuren
        bulletCount++;
    }
    
    // Metode for å tegne player - Primitiv rektangel, må byttes ut med sprite
    public void drawPlayer(GraphicsContext gc) {
        gc.setStroke(Color.WHITE); // Player farge
        gc.setLineWidth(3); // Linjebredde
        gc.clearRect(player.getX()-1, player.getOldY()-1, player.getWidth()+3, player.getHeight()+3); // "visker ut" forrige rektangel før det tegnes en ny - Må være større enn forrige frame, ellers blir det igjen litt av forrige rektangel
        gc.strokeRect(player.getX(), player.getY(), player.getWidth(), player.getHeight()); // tegner ny rektangel-frames
        player.setOldY(player.getY()); // Setter oldY verdi, for å viske ut riktig posisjon neste frame
    }
    
    // Metode for å tegne fiender - Primitiv rektangel, må byttes ut med sprites
    public void drawEnemy(GraphicsContext gc) {
        gc.setStroke(Color.RED); // Enemy farge
        gc.setLineWidth(3); // Linjebredde
        for (Enemy enemy : enemies){
            gc.clearRect(enemy.x()-1, enemy.y()-1, 53, 53); // Mangler "old" posisjon for å kunne viske ut forrige frame
            gc.strokeRect(enemy.x(), enemy.y(), 50, 50); // Setter bredde og høyde til 50 for nå
        }      
    }
    
    // Metode for å tegne kuler
    public void drawBullets(GraphicsContext gc) {
        gc.setStroke(Color.YELLOW); // Kulefarge
        gc.setLineWidth(1); // Kulestørrelse (Kun grafisk representasjon)
        for (Bullet bullet : bullets) {
            gc.clearRect(bullet.getOldX()-2, bullet.getOldY()-2, 6, 6); // "visker ut" forrige frame før det tegnes en ny - må være større verdier enn x og y posisjon
            gc.strokeRect(bullet.getX(), bullet.getY(), 2, 2); // tegner ny kule til skjerm
            bullet.setOldX(bullet.getX()); // setter gamle koordinater
            bullet.setOldY(bullet.getOldY());
        }
    }
    
    // Metode for å detecte kollisjon mellom player og enemy - ikke prosjektiler
    public void detectCollision() {
        for (Enemy enemy : enemies) { // Looper igjennom liste med enemyobjekter
            int enemyWidth = 50; // Midlertidig bredde
            int enemyHeight = 50; // Midlertidig høyde
            if (enemy.x() < player.getX()+player.getWidth() && enemy.y() < player.getY()+player.getHeight()) {
                if (player.getX() < enemy.x()+enemyWidth && player.getY() < enemy.y()+enemyHeight) {
                    // Kollisjon detected
                    collisions++;
                    String lText = "Collisions: " + Integer.toString(collisions);
                    lifeText.setText(lText); // Skriver collisions til screen                    
                }
            }
        }
    }  
    
    // Metode for å detecte hit mellom playerBullet og enemy (utestet til enemy systemet er implementert)
    public void detectHit() {
        int enemyWidth = 50; // Midlertidig bredde
        int enemyHeight = 50; // Midlertidig høyde
        for (Bullet bullet : bullets) {
            for (Enemy enemy : enemies) {
                if (bullet.getX() < enemy.x()+enemyWidth && bullet.getY() < enemy.y()+enemyHeight) {
                    if (bullet.getX() > enemy.x() && bullet.getY() > enemy.y()+enemyHeight) { // Troor det her blir riktig..?
                        // Enemy got hit!
                        System.out.println("hit!");
                    }
                }
            }
        }
    }
    
    // Metode for å detecte playerDamage fra enemy bullets - må implementeres
    public void detectDamage() {
        
    }
    
}
