package view;

import controller.GameController;
import controller.UserInputs;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import view.customElements.MenuButton;

/**
 * <h1>Multiplayer Menu</h1>
 * The class {@code MultiplayerView} extends {@code ViewUtil}.
 *
 * @author Jonas Ege Carlsen, Roger Birkenes Solli
 */
public class MultiplayerView extends ViewUtil{

    /**
     * The singleton object.
     */
    public static MultiplayerView inst = new MultiplayerView();

    /**
     * Method to access the singleton object.
     * @return Returns a reference to the singleton object.
     */
    public static MultiplayerView getInst(){
        return inst;
    }

    /**
     * The MultiplayerHandler singleton object.
     */
    private MultiplayerHandler mp = MultiplayerHandler.getInstance();

    /**
     * The GameModel singleton object.
     */
    private GameModel gm = GameModel.getInstance();


    /**
     * TextField used to type the hostname.
     */
    private TextField hostnameField;

    /**
     * TextField used to type the remote port.
     */
    private TextField remotePortField;

    /**
     * TextField used to type the local port.
     */
    private TextField localPortField;

    /**
     * TextField used to describe the hostname TextField.
     */
    private Label hostname;

    /**
     * TextField used to describe the remote port TextField.
     */
    private Label remotePort;

    /**
     * TextField used to describe the local port TextField.
     */
    private Label localPort;

    /**
     * Button used to launch the game and connect to the other player.
     */
    private MenuButton connectButton;

    /**
     * Button used to go back to {@code MenuView}.
     */
    private MenuButton backButton;

    /**
     * Button used to display help.
     */
    private Button helpButton;

    /**
     * Static reference to the stage.
     */
    public static Stage stage;

    /**
     * Method used to create all the Labels in the view.
     */
    private void createLabels(){
        hostname = createBaseLabel("HOSTNAME (OTHER PLAYERS IP");
        remotePort = createBaseLabel("REMOTE PORT");
        localPort = createBaseLabel("LOCAL PORT");
    }

    /**
     * Method used to create all the TextFields in the view.
     */
    private void createTextFields(){
        hostnameField = new TextField();
        remotePortField = new TextField();
        localPortField = new TextField();
    }

    /**
     * Method used to create all the buttons in the view.
     */
    private void createButtons(){
        connectButton = new MenuButton("CONNECT");
        backButton = new MenuButton("BACK");
        helpButton = new Button("?");

        helpButton.setTranslateX(275);
        helpButton.setTranslateY(-313);
    }

    /**
     * Sets the button click events of the view.
     */
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
                    if(validateIP(hostnameField.getText())) {
                        if(testRange(Integer.parseInt(remotePortField.getText()), Integer.parseInt(localPortField.getText()))) {
                            initMultiplayerGame();
                            hostnameField.setDisable(true);
                            remotePortField.setDisable(true);
                            localPortField.setDisable(true);
                            stage = (Stage) ((Node)event.getTarget()).getScene().getWindow();
                            mp.startConnection();
                        }
                    }
                    else {
                        errorField.changeText("Invalid hostname");
                    }
                }
                catch(Exception e) {
                    System.err.println(e.getMessage());
                    errorField.changeText(e.getMessage());
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

    /**
     * Sets the button press events for the menu container.
     * @param container The menu container of the view.
     */
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

    /**
     * Creates all the user interface elements for the view.
     */
    private void createUI(){
        createLabels();
        createTextFields();
        createButtons();
        setErrorFieldPosition();
    }

    /**
     * Calls other methods and returns a finished root node.
     * @return Returns a root node / Pane.
     */
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

    /**
     * Method for handling selection of menu elements
     * Overridden from {@code ViewUtil}, although it
     * is not used in this view.
     * @param buttonName The name of the button.
     * @param event The event that this function is called from.
     */
    @Override
    public void select(String buttonName, KeyEvent event) {

    }

    /**
     * Method used to initiate a multiplayer game.
     * Attempts to establish connection
     */
    private void initMultiplayerGame() {
        String hostname = hostnameField.getText();
        int remoteport = Integer.parseInt(remotePortField.getText());
        int localport = Integer.parseInt(localPortField.getText());
        setWarningField(Color.GRAY, "Attempting to connect to " + hostname);
        mp.init(hostname, remoteport, localport);
        gm.setMultiplayerStatus(true);
    }

    /**
     * Method used to start a multiplayer game once connection has been established
     * @param stage Stage to start game on.
     */
    public void startMultiplayerGame(Stage stage) {
	Platform.runLater(() -> {
            mp.replyConnection();
            GameController.getInstance().newGame();
            Scene scene = new Scene(GameView.getInstance().initScene());
            stage.setScene(scene);
            UserInputs userInputs = new UserInputs(scene);
        });
    }


    /**
     * Method used to check if remote and local ports are valid.
     * @param remote The remote port.
     * @param local The local port.
     * @return Returns a boolean based on whether or not the ports are valid
     */
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
    
    private boolean validateIP(String hostname) {
        if ("localhost".equals(hostname) || "Localhost".equals(hostname)) {
            return true;
        }
        else {
            String comparePattern = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

            Pattern pattern = Pattern.compile(comparePattern);
            Matcher matcher = pattern.matcher(hostname);
            return matcher.find();        
        }
    }

    /**
     * Method used to display help.
     */
    private void getHelp() {
        Alert help = new Alert(AlertType.INFORMATION);
        help.setTitle("Multiplayer Help");
        help.setHeaderText(null);
        help.setContentText("Enter Player 2's hostname (ip adress) in hostname Field - For testing use \"localhost\" \n \n"
                + "Your LocalPort must be Player 2's RemotePort and vice versa. (Example 5566 / 5567)\n"
                + "For online play make sure the ports are open in your Router\n"
                + "For playing on a Local Area Network use your local adress");

        help.showAndWait();
    }

    /**
     * Method used to set Warning Field color and text.
     * @param color Desired color.
     * @param str Desired text.
     */
    private void setWarningField(Color color, String str) {
        errorField.setColor(color);
        errorField.changeText(str);
    }
}