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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class MultiplayerView extends ViewUtil{

    public static MultiplayerView inst = new MultiplayerView();
    public static MultiplayerView getInst(){
        return inst;
    }

    private MultiplayerHandler mp = MultiplayerHandler.getInstance();
    private GameModel gm = GameModel.getInstance();

    private static final String BG_IMG = "assets/image/background.jpg";

    private TextField hostnameField;
    private TextField remotePortField;
    private TextField localPortField;

    public static Stage stage;

    public Parent initScene(){
        root = initBaseScene(BG_IMG);
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

        setErrorFieldPosition();

        MenuButton connectButton = new MenuButton("CONNECT");
        MenuButton backButton = new MenuButton("BACK");
        Button helpButton = new Button("?");

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
        helpButton.setOnMouseClicked(event -> getHelp());
        helpButton.setTranslateX(275);
        helpButton.setTranslateY(-313);
        backButton.setOnMouseClicked(event -> goToView(event, MenuView.getInstance().initScene()));
        multiplayerMenu.getChildren().addAll(hostname, hostnameField, remotePort, remotePortField, localPort, localPortField, connectButton, backButton, helpButton);
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
        root.getChildren().addAll(header, errorField, multiplayerMenu);

        String lastError = "";
        compareErrorMessage(lastError);

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
    
    private boolean testRange(int remote, int local) {
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
    
    private void getHelp() {
        Alert help = new Alert(AlertType.INFORMATION);
        help.setTitle("Multiplayer Help");
        help.setHeaderText(null);
        help.setContentText("Enter Player 2's hostname (ip adress) in hostname Field - For testing use \"localhost\" \n \n"
                + "To find your IP adress open a command prompt and type \"ipconfig\" (windows) you can use your IPv4 \n \n"
                + "Your LocalPort must be Player 2's RemotePort and vice versa. (Example 5566 / 5567)");

        help.showAndWait();
    }

    private void setWarningField(Color color, String str) {
        errorField.setColor(color);
        errorField.changeText(str);
    }
}