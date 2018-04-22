package view;

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

public class MultiplayerView extends ViewUtil{

    public static MultiplayerView inst = new MultiplayerView();
    public static MultiplayerView getInst(){
        return inst;
    }

    public MultiplayerView(){}

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
        remotePort = new Label("REMOTE PORT");
        localPort = new Label("LOCAL PORT");

        hostnameField = new TextField();
        remotePortField = new TextField();
        localPortField = new TextField();

        connectButton = new MenuButton("CONNECT");
        backButton = new MenuButton("BACK");
        multiplayerMenu.getChildren().addAll(hostname, hostnameField, remotePort, remotePortField, localPort, localPortField, connectButton, backButton);
        multiplayerMenu.setSpacing(10);
        multiplayerMenu.setTranslateX(450);
        multiplayerMenu.setTranslateY(250);
        multiplayerMenu.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                goToView(event, MenuView.getInstance().initScene());
            }
        });
        root.getChildren().addAll(header, multiplayerMenu);
        return root;
    }
}
