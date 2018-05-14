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
    private Label hostname;
    private Label remotePort;
    private Label localPort;

    private MenuButton connectButton;
    private MenuButton backButton;
    private Button helpButton;

    public static Stage stage;

    private void createLabels(){
        hostname = createBaseLabel("HOSTNAME (OTHER PLAYERS IP");
        remotePort = createBaseLabel("REMOTE PORT");
        localPort = createBaseLabel("LOCAL PORT");
    }

    private void createTextFields(){
        hostnameField = new TextField();
        remotePortField = new TextField();
        localPortField = new TextField();
    }

    private void createButtons(){
        connectButton = new MenuButton("CONNECT");
        backButton = new MenuButton("BACK");
        helpButton = new Button("?");

        helpButton.setTranslateX(275);
        helpButton.setTranslateY(-313);
    }


    @Override
    void setButtonClickEvents() {
        connectButton.setOnMouseClicked(event -> {
            //if (user_name.getText() == null || user_name.getText().trim().isEmpty())
            if (hostnameField.getText() == null || hostnameField.getText().trim().isEmpty()) {
                errorField.changeText("Enter Hostname");
            }
            else if (remotePortField.getText() == null || remotePortField.getText().trim().isEmpty()) {
                errorField.changeText("Enter RemotePort");
            }
            else if (localPortField.getText() == null || localPortField.getText().trim().isEmpty()) {
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
                    System.err.println(e.getMessage());
                    errorField.changeText(e.toString());
                }
            }
        });
        helpButton.setOnMouseClicked(event -> getHelp());
        backButton.setOnMouseClicked(event -> goToView(event, MenuView.getInstance().initScene()));
        backButton.setOnMouseClicked(event ->  {
            if (mp.getInitConnection()) {
                mp.cancelConnectAttempt();
            }
            goToView(event, MenuView.getInstance().initScene());
        });
    }

    @Override
    void setButtonPressEvents(Parent container) {
        container.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                if(mp.getInitConnection()){
                    mp.cancelConnectAttempt();
                }
                goToView(event, MenuView.getInstance().initScene());
            }
        });
    }

    private void createUI(){
        createLabels();
        createTextFields();
        createButtons();
        setErrorFieldPosition();
    }

    private void setEvents(Parent container){
        setButtonClickEvents();
        setButtonPressEvents(container);
    }

    public Parent initScene(){
        root = initBaseScene(BG_IMG);
        VBox menuContainer = createMenuContainer(450, 250, 10);
        createUI();
        setEvents(menuContainer);
        menuContainer.getChildren().addAll(hostname, hostnameField, remotePort, remotePortField, localPort, localPortField, connectButton, backButton, helpButton);
        root.getChildren().addAll(header, errorField, menuContainer);
        compareErrorMessage();
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