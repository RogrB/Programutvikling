package view;

import controller.GameController;
import controller.UserInputs;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameModel;
import multiplayer.MultiplayerHandler;
import controller.UserInputs;

public class MultiplayerView extends ViewUtil{

    public static MultiplayerView inst = new MultiplayerView();
    public static MultiplayerView getInst(){
        return inst;
    }

    public MultiplayerView(){}
    
    MultiplayerHandler mp = MultiplayerHandler.getInstance();
    GameModel gm = GameModel.getInstance();

    private static final String BG_IMG = "assets/image/background.jpg";
    public VBox multiplayerMenu;

    public Label hostname;
    public Label remotePort;
    public Label localPort;

    public TextField hostnameField;
    public TextField remotePortField;
    public TextField localPortField;

    public MenuButton connectButton;
    public MenuButton backButton;

    public Text header;

    public Parent initScene(){
        root = new Pane();
        header = new Text("SPACE GAME");
        header.setX(300);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(header.getFont().font(100));
        root.setPrefSize(VIEW_WIDTH, VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(BG_IMG));
        multiplayerMenu = new VBox();
        hostname = new Label("HOSTNAME");
        hostname.setTextFill(Color.WHITE);
        remotePort = new Label("REMOTE PORT");
        remotePort.setTextFill(Color.WHITE);
        localPort = new Label("LOCAL PORT");
        localPort.setTextFill(Color.WHITE);

        hostnameField = new TextField();
        remotePortField = new TextField();
        localPortField = new TextField();

        connectButton = new MenuButton("CONNECT");
        backButton = new MenuButton("BACK");
        connectButton.setOnMouseClicked( event -> {
            System.out.println(hostnameField.getText());
            if(hostnameField.getText() != null && hostnameField.getText() != null && localPortField.getText() != null){
                // ROGER STUFF
                //mp.startConnection();
                initMultiplayerGame();
                
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                startMultiplayerGame(stage); // Trenger å kalle denne fra initMultiplayerGame()
                
            }
            else{
                if(hostnameField.getText() == ""){ // not sure if these work yet, cba to check
                    System.out.println("Make sure to fill out the hostname field!");
                }
                else if(remotePortField.getText() == ""){
                    System.out.println("Make sure to fill out the remote port field");
                }
                else if(localPortField.getText() == ""){
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
                goToView(event, MenuView.getInstance().initScene());
            }
        });
        backButton.setOnMouseClicked(event -> goToView(event, MenuView.getInstance().initScene()));
        root.getChildren().addAll(header, multiplayerMenu);
        return root;
    }

    @Override
    public void select(String buttonName, KeyEvent event) {

    }
    
    public void initMultiplayerGame() {
        String hostname = hostnameField.getText();
        int remoteport = Integer.parseInt(remotePortField.getText());
        int localport = Integer.parseInt(localPortField.getText());
        mp.init(hostname, remoteport, localport);
        gm.setMultiplayerStatus(true);
    }
    
    public void startMultiplayerGame(Stage stage) {
        GameController.getInstance().newGame();
        Scene scene = new Scene(GameView.getInstance().initScene());
        stage.setScene(scene);
        UserInputs userInputs = new UserInputs(scene);
        System.out.println("Totally started a new Multiplayer game");        
    }
}
