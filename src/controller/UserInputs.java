package controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import model.GameModel;
import model.GameState;

import static controller.GameController.gs;
import view.GameView;

public class UserInputs {

    private Scene s;

    public UserInputs(Scene s){
        this.s = s;
        setKeyListeners(s);
    }

    // MVC-access

    public void setKeyListeners(Scene s){
        s.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE)
                gs.player.isShooting();
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
                gs.player.move("UP");
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
                gs.player.move("DOWN");
            if (event.getCode() == KeyCode.E)
                gs.player.powerUp();
            if (event.getCode() == KeyCode.Q)
                gs.player.setShield();
            if (event.getCode() == KeyCode.T)
                GameController.getInstance().getHUD().renderPowerUpText("Test");
            if (event.getCode() == KeyCode.R)
                GameView.getInstance().renderScoreScreen();
        });

        s.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE)
                gs.player.isNotShooting();
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
                gs.player.move("STOP");
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
                gs.player.move("STOP");
        });
    }
}
