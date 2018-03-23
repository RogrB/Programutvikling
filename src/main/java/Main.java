package main.java;

import controller.GameController;
import controller.UserInputs;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.GameModel;
import view.GameView;

public class Main extends Application {

    public static GameView gv = GameView.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        gv.setup();

        primaryStage.setTitle("Working Title: Pippi");
        Scene scene = new Scene(gv.initGame());

        UserInputs userInputs = new UserInputs(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }













    //private ArrayList<model.enemy> enemies = new ArrayList<>();

    // Start metode - Setter opp Scene
    /*@Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Working Title: Pippi");
        Scene scene = new Scene(initGame());

        //levelWidth = LevelData.LEVEL1[0].length() * 60;
        for(int i = 0; i < LevelData.LEVEL1.length; i++){
            String line = LevelData.LEVEL1[i];
            for(int j = 0; j < line.length(); j++){
                switch(line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        //enemies.add(new model.enemy(EnemyType.SHIP, EnemyMovementPatterns.SIN));
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
                    gl.model.player.shoot();
                }
                if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) { // Trykk på W eller ArrowUp
                    gl.model.player.moveUp();
                }
                if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) { // Trykk på S eller ArrowDown
                    gl.model.player.moveDown();
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) { // Trykk på W eller ArrowUp
                    gl.model.player.moveStop();
                }
                if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) { // Trykk på S eller ArrowDown
                    gl.model.player.moveStop();
                }
            }
        });
    }*/

    /*public Parent initGame() {
        // Metode for å starte spillet etter Countdown
        Pane root = new Pane();
        root.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
        root.setBackground(new Background(bg));
        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        //root.getChildren().addAll(canvas, model.player.getSpriteView(), scoreText, lifeText);
        root.getChildren().addAll(canvas, gl.model.player.getSpriteView());

        // Animationtimer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gl.model.player.update();
                for(model.enemy e : enemies){
                    e.update();
                }
                //detectCollision(); // Sjekker om model.player kolliderer med model.enemy
                if (gl.model.player.getBulletCount() > 0) {
                    for (Bullet bullet : gl.model.player.getBullets()) { // Hvis det har blitt skutt kuler
                        bullet.update(); // Oppdaterer bulletposisjon
                    }
                    //detectHit(); // Sjekker om kulene har truffet noe
                    //drawBullets(gc); // Oppdaterer kule tegning
                }
            }
        }; timer.start(); // Starter animationtimer
        return root;
    }*/

    /*private double time2;
    private Text scoreText;
    private Text lifeText;
    private int score = 0;
    private int collisions = 0;
    private int levelWidth;*/

    // Variabler for SplashScreen
    /*private int cdCounter = 4;
    private int rectX = 215;
    private int rectY = 590;*/

    // Bildenoder for Countdown Spashscreen
    /*StackPane cdPane = new StackPane();
    ImageView imgView = new ImageView();*/

    // Metode for å generere fiender
    /*public void generateEnemies() {
        //model.enemy test = new model.enemy(EnemyType.SHIP, EnemyMovementPatterns.SIN);
        //enemies.add(test);
    }*/

    // Metode for å flytte fiender
    /*public void moveEnemies(){
        AnimationTimer timer = new AnimationTimer() { // Bør muligens flyttes inn i eksisterende animationtimer i initScene()  ?
            @Override
            public void handle(long now) {
                for (model.enemy model.enemy : enemies){
                    model.enemy.move();
                    drawEnemy(gc); // Kaller metode for å tegne enemies (primitive representasjoner)
                }
            }
        };

        timer.start();
    }*/

    /*private Parent startGameCountdown() {
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
        root.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
        root.setBackground(new Background(bg));
        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        levelWidth = LevelData.LEVEL1[0].length() * 80;
        for(int i = 0; i < LevelData.LEVEL1.length; i++){
            String line = LevelData.LEVEL1[i];
            for(int j = 0; j < line.length(); j++){
                switch(line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        enemies.add(new model.enemy(EnemyType.SHIP, EnemyMovementPatterns.SIN, j * 60, GAME_HEIGHT - (i * 60)));
                        break;
                }
            }
        }

        // Legger nodes til root
        root.getChildren().addAll(canvas, model.player.getSpriteView(), scoreText, lifeText);
        for(model.enemy e : enemies){
            root.getChildren().add(e.getSpriteView());
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

        imgView.setX((GAME_WIDTH/2)-200); // Prøver å sette x-verdi for imageview
        imgView.setY((GAME_HEIGHT/2)-50);
        imgView.fitWidthProperty().bind(cdPane.widthProperty());
        cdPane.setAlignment(Pos.CENTER);

        // Setter playersize for "zoom in" effekt
        int playerTempWidth = model.player.getPLAYERWIDTH(); // Lagrer model.player originalstørrelse som definert i model.player klassen
        int playerTempHeight = model.player.getPLAYERHEIGHT();
        model.player.setPLAYERWIDTH(100); // Setter høyere playersize, for "zoom in" effekt
        model.player.setPLAYERHEIGHT(100);

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
    }*/

    /*public void countDown(int seconds) {
        // Metode for å bytte countdownbilder for splashscreen
        Image countDownImage = new Image("assets.image/countdown/" + seconds + ".png"); // Bør vel ha exceptionhandling her
        imgView.setImage(countDownImage);
    }*/

    /*public void finishCountDown(int playerTempWidth, int playerTempHeight) {
        model.player.setCanShoot(true);
        model.player.setCanMove(true);
        // Metode for å vise "Fly!" splashscreen på skjermen, fjerne "blackbars", "zoome ut" model.player, og intitialiserer enemies

        AnimationTimer timer = new AnimationTimer() { // Bruker animationtimer
            @Override
            public void handle(long now) {
                time += 0.05;
                time2 += 0.03;
                model.player.update();
                for(model.enemy e : enemies){
                    e.update();
                }
                if (time >= 0.35) {
                    if (cdCounter < 10) {
                        if (time2 >= 0.26) { // Timer2 - For tregere ticks (egentlig unødvendig - må ryddes opp)
                            Image countDownImage = new Image("assets.image/countdown/fly_" + cdCounter + ".png"); // Looper igjennom bildesekvens for "FLY!" splashscreen
                            imgView.setImage(countDownImage);
                            cdCounter++;
                            time2 = 0;
                        }
                    }
                    time = 0;
                }

                if (cdCounter >= 10) {
                    // Zoomer ut model.player
                    if (model.player.getPLAYERWIDTH() > playerTempWidth) {
                            model.player.setPLAYERWIDTH(model.player.getPLAYERWIDTH()-2);
                    }
                    if (model.player.getPLAYERHEIGHT() > playerTempHeight) {
                            model.player.setPLAYERHEIGHT(model.player.getPLAYERHEIGHT()-2);
                            model.player.setY(model.player.getY()+1);
                    }

                    // Prøver å fade ut siste splashscreen bilde
                    //KeyFrame startFadeOut = new KeyFrame(Duration.seconds(0.2), new KeyValue(imgView.opacityProperty(), 1.0));
                    //KeyFrame endFadeOut = new KeyFrame(Duration.seconds(0.5), new KeyValue(imgView.opacityProperty(), 0.0));
                    //Timeline timelineOn = new Timeline(startFadeOut, endFadeOut);
                    //timelineOn.setAutoReverse(false);
                    //timelineOn.play();

                    // Prøver å fade ut med fadetransition
                    //FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), imgView);
                    //fadeTransition.setFromValue(1.0);
                    //fadeTransition.setToValue(0.0);
                    //fadeTransition.play();

                    // Animerer ut Top blackbar
                    gc.clearRect(0, 0, 1200, 230);
                    gc.fillRect(0, 0, 1200, rectX);
                    rectX -= 10;

                    // Animerer ut Nedre blackbar
                    gc.clearRect(0, 560, 1200, 230);
                    gc.fillRect(0, rectY, 1200, 225);
                    rectY += 10;

                    if (model.player.getPLAYERWIDTH() <= playerTempWidth && model.player.getPLAYERHEIGHT() <= playerTempHeight) {
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
    }*/

    // Metode for å tegne kuler
    /*public void drawBullets(GraphicsContext gc) {
        gc.setStroke(Color.YELLOW); // Kulefarge
        gc.setLineWidth(1); // Kulestørrelse (Kun grafisk representasjon)
        for (Bullet bullet : gl.model.player.getBullets()) {
            gc.clearRect(bullet.getOldX()-2, bullet.getOldY()-2, 6, 6); // "visker ut" forrige frame før det tegnes en ny - må være større verdier enn x og y posisjon
            gc.strokeRect(bullet.getX(), bullet.getY(), 2, 2); // tegner ny kule til skjerm
            bullet.setOldX(bullet.getX()); // setter gamle koordinater
            bullet.setOldY(bullet.getOldY());
        }
    }*/

    // Metode for å tegne fiender - Primitiv rektangel, må byttes ut med sprites
    /*public void drawEnemy(GraphicsContext gc) {
        gc.setStroke(Color.RED); // model.enemy farge
        gc.setLineWidth(3); // Linjebredde
        for (model.enemy model.enemy : enemies){
            gc.clearRect(model.enemy.x()-1, model.enemy.y()-1, 53, 53); // Mangler "old" posisjon for å kunne viske ut forrige frame
            gc.strokeRect(model.enemy.x(), model.enemy.y(), 50, 50); // Setter bredde og høyde til 50 for nå
        }
    }*/

    // Metode for å detecte kollisjon mellom model.player og model.enemy - ikke prosjektiler
    /*public void detectCollision() {
        for (model.enemy model.enemy : enemies) { // Looper igjennom liste med enemyobjekter
            int enemyWidth = 50; // Midlertidig bredde
            int enemyHeight = 50; // Midlertidig høyde
            if (model.enemy.x() < model.player.getX()+model.player.getPLAYERWIDTH() && model.enemy.y() < model.player.getY()+model.player.getPLAYERHEIGHT()) {
                if (model.player.getX() < model.enemy.x()+enemyWidth && model.player.getY() < model.enemy.y()+enemyHeight) {
                    // Kollisjon detected
                    collisions++;
                    String lText = "Collisions: " + Integer.toString(collisions);
                    lifeText.setText(lText); // Skriver collisions til screen
                }
            }
        }
    }*/

    // Metode for å detecte hit mellom playerBullet og model.enemy (utestet til model.enemy systemet er implementert)
    /*public void detectHit() {
        int enemyWidth = 50; // Midlertidig bredde
        int enemyHeight = 50; // Midlertidig høyde
        for (Bullet bullet : model.player.getBullets()) {
            for (model.enemy model.enemy : enemies) {
                if (bullet.getX() < model.enemy.x()+enemyWidth && bullet.getY() < model.enemy.y()+enemyHeight) {
                    if (bullet.getX() > model.enemy.x() && bullet.getY() > model.enemy.y()+enemyHeight) { // Troor det her blir riktig..?
                        // model.enemy got hit!
                        System.out.println("hit!");
                        score++;
                    }
                }
            }
        }
    }*/

}
