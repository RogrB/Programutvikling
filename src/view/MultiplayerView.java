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

    private Parent[] menuElements;

    private static final String BG_IMG = "assets/image/background.jpg";

    private TextField hostnameField;
    private TextField remotePortField;
    private TextField localPortField;

    public static Stage stage;
    
    private WarningField errorField = new WarningField();
    private WarningField hostErrorField = new WarningField();

    public Parent initScene(){
        root = new Pane();
        header.setX(300);
        header.setY(175);
        header.setFill(Color.WHITE);
        header.setFont(header.getFont().font(100));
        root.setPrefSize(VIEW_WIDTH, VIEW_HEIGHT);
        root.setBackground(getBackGroundImage(BG_IMG));
        VBox multiplayerMenu = new VBox();
        Label hostname = new Label("HOSTNAME (OTHER PLAYERS IP)");
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
        hostErrorField.setTranslateX(475);
        hostErrorField.setTranslateY(590);

        MenuButton connectButton = new MenuButton("CONNECT");
        MenuButton backButton = new MenuButton("BACK");

        menuElements = new Parent[]{hostnameField, remotePortField, localPortField, connectButton, backButton};
        connectButton.setOnMouseClicked(event -> {
            //if (user_name.getText() == null || user_name.getText().trim().isEmpty())
            if (hostnameField.getText() == null || hostnameField.getText().trim().isEmpty()) {
                System.out.println("Enter Hostname");
                errorField.changeText("Enter Hostname");
            }
            else if (remotePortField.getText() == null || remotePortField.getText().trim().isEmpty()) {
                System.out.println("Enter RemotePort");
                errorField.changeText("Enter RemotePort");
            }
            else if (localPortField.getText() == null || localPortField.getText().trim().isEmpty()) {
                System.out.println("Enter LocalPort");
                errorField.changeText("Enter LocalPort");
            }            
            else {
                try {
                    if(testRange(Integer.parseInt(remotePortField.getText()), Integer.parseInt(localPortField.getText()))) {
                        initMultiplayerGame();
                        stage = (Stage) ((Node)event.getTarget()).getScene().getWindow();
                        mp.startConnection();   
                    }    
                }
                catch(Exception e) {
                    System.err.println(e);
                    errorField.changeText(e.toString());
                }
            }
        });
        backButton.setOnMouseClicked(event -> goToView(event, MenuView.getInstance().initScene()));
        multiplayerMenu.getChildren().addAll(hostname, hostnameField, remotePort, remotePortField, localPort, localPortField, connectButton, backButton);
        multiplayerMenu.setSpacing(10);
        multiplayerMenu.setTranslateX(450);
        multiplayerMenu.setTranslateY(250);
        multiplayerMenu.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN){
                System.out.println(elementCounter);
            }
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
        root.getChildren().addAll(header, errorField, hostErrorField, multiplayerMenu);
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
        });
    }
    
    public boolean testRange(int remote, int local) {
        boolean valid = true;
        if (remote < 0 || remote > 100000) {
            valid = false;
            errorField.changeText("Invalid RemotePort Range");
        }
        if (local < 0 || local > 100000) {
            valid = false;
            errorField.changeText("Invalid LocalPort Range");
        }
        return valid;
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
    
    public WarningField getHostnameField() {
        return hostErrorField;
    }
    
}
