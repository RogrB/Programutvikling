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
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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
import javafx.geometry.Pos;

import java.util.Timer;
import java.util.TimerTask;

import Player.*;
import Weapons.*;
import levels.LevelData;

public class Main extends Application{

    // Stage oppsett
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final double SPEED_MODIFIER = 0.17;

    // Oppretter player objekt
    Player player = new Player();
    Enemy eee = new Enemy(EnemyType.SHIP, MovementPattern.SIN, 400, 400);

    // Arraylist for Enemies og bullets
    private ArrayList<Enemy> enemies = new ArrayList<>();


    // GraphicsContext og Spillvariabler
    private GraphicsContext gc; // Brukes for å tegne primitive rektangler - kan evt byttes ut når vi implementerer sprites?
    private double time;
    private double time2;
    private Text scoreText;
    private Text lifeText;
    private int score = 0;
    private int collisions = 0;
    private int levelWidth;

    // Variabler for SplashScreen
    private int cdCounter = 4;
    private int rectX = 215;
    private int rectY = 590;

    // Bildenoder for Countdown Spashscreen
    StackPane cdPane = new StackPane();
    ImageView imgView = new ImageView();

    // Bakgrunnsbilde
    String imgpath = "image/background.jpg";
    Image img = new Image(imgpath);
    BackgroundImage bg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

    // Metode for å generere fiender
    public void generateEnemies() {
        //Enemy test = new Enemy(EnemyType.SHIP, MovementPattern.SIN);
        //enemies.add(test);
    }

    // Metode for å flytte fiender
    public void moveEnemies(){
        AnimationTimer timer = new AnimationTimer() { // Bør muligens flyttes inn i eksisterende animationtimer i initScene()  ?
            @Override
            public void handle(long now) {
                for (Enemy enemy : enemies){
                    enemy.move();
                    //player.move();
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
        Scene scene = new Scene(startGameCountdown());

        levelWidth = LevelData.LEVEL1[0].length() * 60;
        for(int i = 0; i < LevelData.LEVEL1.length; i++){
            String line = LevelData.LEVEL1[i];
            for(int j = 0; j < line.length(); j++){
                switch(line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        //enemies.add(new Enemy(EnemyType.SHIP, MovementPattern.SIN));
                        break;
                }
            }
        }

        primaryStage.setScene(scene);
        primaryStage.show();

        // Key event handler for scene - For Spillerinput
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) { // Trykk på Space
                    player.shoot();
                }
                if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) { // Trykk på W eller ArrowUp
                    player.moveUp();
                }
                if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) { // Trykk på S eller ArrowDown
                    player.moveDown();
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) { // Trykk på W eller ArrowUp
                    player.moveStop();
                }
                if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) { // Trykk på S eller ArrowDown
                    player.moveStop();
                }
            }
        });
    }

    private Parent startGameCountdown() {
        // Starter countdown før levelen,
        // Oppretter nodes
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

        // Videre oppsett for root - Bakgrunn og størrelse
        root.setPrefSize(WIDTH, HEIGHT);
        root.setBackground(new Background(bg));
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        levelWidth = LevelData.LEVEL1[0].length() * 80;
        for(int i = 0; i < LevelData.LEVEL1.length; i++){
            String line = LevelData.LEVEL1[i];
            for(int j = 0; j < line.length(); j++){
                switch(line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        enemies.add(new Enemy(EnemyType.SHIP, MovementPattern.SIN, j * 60, HEIGHT - (i * 60)));
                        break;
                }
            }
        }

        // Legger nodes til root
        root.getChildren().addAll(canvas, player.getSprite(), scoreText, lifeText);
        for(Enemy e : enemies){
            root.getChildren().add(e.getSprite());
        }

        // Tegner "Black-Bars" under countdown
        gc.setFill(Color.rgb(25, 25, 25));
        gc.fillRect(0, 0, 1200, 225);
        gc.fillRect(0, 575, 1200, 225);

        // Prøver å midtjustere CountDownBildene
        cdPane.getChildren().add(imgView); // Globale variabler opprettet under main for å være tilgjengelige i andre metoder
        imgView.setPreserveRatio(true);
        StackPane.setAlignment(imgView, Pos.CENTER);
        cdPane.setAlignment(Pos.CENTER);

        imgView.setX((WIDTH/2)-200); // Prøver å sette x-verdi for imageview
        imgView.setY((HEIGHT/2)-50);
        imgView.fitWidthProperty().bind(cdPane.widthProperty());
        cdPane.setAlignment(Pos.CENTER);

        // Setter playersize for "zoom in" effekt
        int playerTempWidth = player.getWidth(); // Lagrer player originalstørrelse som definert i player klassen
        int playerTempHeight = player.getHeight();
        player.setWidth(100); // Setter høyere playersize, for "zoom in" effekt
        player.setHeight(100);

        // Oppretter Timer - For å telle sekunder for CountDown
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            public int seconds = 1;
            private final int maxSeconds = 4;
            @Override
            public void run() {
                if (seconds < maxSeconds) {
                    countDown(seconds); // Kaller metode for å bytte bildene på splashscreen
                    seconds++;
                }
                else {
                    cancel(); // Stopper timeren
                    finishCountDown(playerTempWidth, playerTempHeight); // Kaller metode for å avslutte countdown
                }
            }
        };timer.schedule(task, 0, 1000); // Starter timeren (0 = start now, 1000 er tickintervall - 1 sekund)

        // Legger cdPane til root (cdPane definert under main som global variabel)
        root.getChildren().add(cdPane);

        initGame();
        return root;
    }

    public void countDown(int seconds) {
        // Metode for å bytte countdownbilder for splashscreen
        Image countDownImage = new Image("image/countdown/" + seconds + ".png"); // Bør vel ha exceptionhandling her
        imgView.setImage(countDownImage);
    }

    public void finishCountDown(int playerTempWidth, int playerTempHeight) {
        player.setCanShoot(true);
        player.setCanMove(true);
        // Metode for å vise "Fly!" splashscreen på skjermen, fjerne "blackbars", "zoome ut" player, og intitialiserer enemies

        AnimationTimer timer = new AnimationTimer() { // Bruker animationtimer
            @Override
            public void handle(long now) {
                time += 0.05;
                time2 += 0.03;
                player.update();
                for(Enemy e : enemies){
                    e.update();
                }
                if (time >= 0.35) {
                    if (cdCounter < 10) {
                        if (time2 >= 0.26) { // Timer2 - For tregere ticks (egentlig unødvendig - må ryddes opp)
                            Image countDownImage = new Image("image/countdown/fly_" + cdCounter + ".png"); // Looper igjennom bildesekvens for "FLY!" splashscreen
                            imgView.setImage(countDownImage);
                            cdCounter++;
                            time2 = 0;
                        }
                    }
                    time = 0;
                }

                if (cdCounter >= 10) {
                    // Zoomer ut player
                    if (player.getWidth() > playerTempWidth) {
                            player.setWidth(player.getWidth()-2);
                    }
                    if (player.getHeight() > playerTempHeight) {
                            player.setHeight(player.getHeight()-2);
                            player.setY(player.getY()+1);
                    }

                    /* Prøver å fade ut siste splashscreen bilde
                    KeyFrame startFadeOut = new KeyFrame(Duration.seconds(0.2), new KeyValue(imgView.opacityProperty(), 1.0));
                    KeyFrame endFadeOut = new KeyFrame(Duration.seconds(0.5), new KeyValue(imgView.opacityProperty(), 0.0));
                    Timeline timelineOn = new Timeline(startFadeOut, endFadeOut);
                    timelineOn.setAutoReverse(false);
                    timelineOn.play(); */

                    /* Prøver å fade ut med fadetransition
                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), imgView);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.play(); */

                    // Animerer ut Top blackbar
                    gc.clearRect(0, 0, 1200, 230);
                    gc.fillRect(0, 0, 1200, rectX);
                    rectX -= 10;

                    // Animerer ut Nedre blackbar
                    gc.clearRect(0, 560, 1200, 230);
                    gc.fillRect(0, rectY, 1200, 225);
                    rectY += 10;

                    if (player.getWidth() <= playerTempWidth && player.getHeight() <= playerTempHeight) {
                        // Zoom ut animasjon ferdig, stopper timer
                        this.stop();

                        imgView.setImage(null); // Fjerner "Fly!" Bilde fra skjermen
                        gc.clearRect(0, 0, 1200, 800); // clearer hele skjermen

                        // Kaller metoder for Enemies
                        generateEnemies();
                        moveEnemies();
                    }
                }

            }
        }; timer.start(); // Starter animationtimer
    }
    public void initGame() {
        // Metode for å starte spillet etter Countdown


        // Animationtimer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += 0.05;
                player.update();
                for(Enemy e : enemies){
                    e.update();
                }
                detectCollision(); // Sjekker om player kolliderer med enemy
                if (player.getBulletCount() > 0) {
                    for (Bullet bullet : player.getBullets()) { // Hvis det har blitt skutt kuler
                        bullet.update(); // Oppdaterer bulletposisjon
                    }
                    detectHit(); // Sjekker om kulene har truffet noe
                    drawBullets(gc); // Oppdaterer kule tegning
                }

                if (time >= 0.35) { // Hvis vi trenger en funksjon som ikke *må* oppdateres hvert tick av animationtimeren
                    // Do something
                    time = 0;
                }
            }
        }; timer.start(); // Starter animationtimer
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
        for (Bullet bullet : player.getBullets()) {
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
        for (Bullet bullet : player.getBullets()) {
            for (Enemy enemy : enemies) {
                if (bullet.getX() < enemy.x()+enemyWidth && bullet.getY() < enemy.y()+enemyHeight) {
                    if (bullet.getX() > enemy.x() && bullet.getY() > enemy.y()+enemyHeight) { // Troor det her blir riktig..?
                        // Enemy got hit!
                        System.out.println("hit!");
                        score++;
                    }
                }
            }
        }
    }

    // Metode for å detecte playerDamage fra enemy bullets - må implementeres
    public void detectDamage() {

    }

}
