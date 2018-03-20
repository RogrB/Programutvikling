package controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import model.GameModel;

public class UserInputs {

    private Scene s;

    public UserInputs(Scene s){
        this.s = s;
        setKeyListeners(s);
    }

    // MVC-access
    GameModel gm = GameModel.getInstance();

    public void setKeyListeners(Scene s){
        s.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE)
                gm.player.shoot();
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
                gm.player.move("UP");
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
                gm.player.move("DOWN");
        });

        s.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
                gm.player.move("STOP");
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
                gm.player.move("STOP");
        });
    }
}
