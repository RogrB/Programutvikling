package view;

import controller.GameController;
import controller.UserInputs;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.GameModel;
import multiplayer.MultiplayerHandler;
import controller.UserInputs;
import javafx.application.Platform;

public class MultiplayerView extends ViewUtil{

    public static MultiplayerView inst = new MultiplayerView();
    public static MultiplayerView getInst(){
        return inst;
    }

    private MultiplayerView(){}
    
    private MultiplayerHandler mp = MultiplayerHandler.getInstance();
    private GameModel gm = GameModel.getInstance();

    private static final String BG_IMG = "assets/image/background.jpg";

    private TextField hostnameField;
    private TextField remotePortField;
    private TextField localPortField;

    public static Stage stage;
    
    private WarningField errorField = new WarningField();

    public Parent initScene(){
        root = new Pane();
        header.setX(300);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(header.getFont().font(100));
        root.setPrefSize(VIEW_WIDTH, VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(BG_IMG));
        VBox multiplayerMenu = new VBox();
        Label hostname = new Label("OTHER PLAYERS IP");
        hostname.setTextFill(Color.WHITE);
        Label remotePort = new Label("REMOTE PORT");
        remotePort.setTextFill(Color.WHITE);
        Label localPort = new Label("LOCAL PORT");
        localPort.setTextFill(Color.WHITE);

        hostnameField = new TextField();
        remotePortField = new TextField();
        localPortField = new TextField();

        errorField.setTranslateX(475);
        errorField.setTranslateY(550);

        MenuButton connectButton = new MenuButton("CONNECT");
        MenuButton backButton = new MenuButton("BACK");
        connectButton.setOnMouseClicked(event -> {
            System.out.println(hostnameField.getText());
            if(hostnameField.getText() != null && hostnameField.getText() != null && localPortField.getText() != null){
                
                initMultiplayerGame();
                stage = (Stage) ((Node)event.getTarget()).getScene().getWindow();
                mp.startConnection();                
                
            }
            else{
                if(hostnameField.getText().equals("")){ // not sure if these work yet, cba to check
                    System.out.println("Make sure to fill out the hostname field!");
                }
                else if(remotePortField.getText().equals("")){
                    System.out.println("Make sure to fill out the remote port field");
                }
                else if(localPortField.getText().equals("")){
                    System.out.println("Make sure to fill out the local port field!");
                }
            }
        });
        backButton.setOnMouseClicked(event -> goToView(event, MenuView.getInstance().initScene()));
        multiplayerMenu.getChildren().addAll(hostname, hostnameField, remotePort, remotePortField, localPort, localPortField, connectButton, backButton);
        multiplayerMenu.setSpacing(10);
        multiplayerMenu.setTranslateX(450);
        multiplayerMenu.setTranslateY(250);
        multiplayerMenu.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                if(mp.getInitConnection()){
                    mp.cancelConnectAttempt();
                }
                goToView(event, MenuView.getInstance().initScene());
            }
        });
        backButton.setOnMouseClicked(event ->  {
            if (mp.getInitConnection()) {
                mp.cancelConnectAttempt();
            }
            goToView(event, MenuView.getInstance().initScene());
                });
        root.getChildren().addAll(header, errorField, multiplayerMenu);
        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {

    }
    
    private void initMultiplayerGame() {
        String hostname = hostnameField.getText();
        int remoteport = Integer.parseInt(remotePortField.getText());
        int localport = Integer.parseInt(localPortField.getText());
        setWarningField(Color.GRAY, "Attempting to connect to " + hostname);
        mp.init(hostname, remoteport, localport);
        gm.setMultiplayerStatus(true);
    }
    
    public void startMultiplayerGame(Stage stage) {
        
	Platform.runLater(() -> {
            GameController.getInstance().newGame();
            Scene scene = new Scene(GameView.getInstance().initScene());
            stage.setScene(scene);
            UserInputs userInputs = new UserInputs(scene);
            //System.out.println("Totally started a new Multiplayer game");
        });
    }
    
    private void setWarningField(Color color, String str) {
        errorField.setColor(color);
        errorField.changeText(str);
    }
    
    public void setWarningField(String str) {
        errorField.changeText(str);
    }
    
    public WarningField getField() {
        return errorField;
    }
}
