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

    GameView gv = GameView.getInstance();

    public void setKeyListeners(Scene s){
        s.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE && GameController.gs.player.isAlive())
                gs.player.isShooting();
            if (event.getCode() == KeyCode.SPACE  && !GameController.gs.player.isAlive()|| event.getCode() == KeyCode.ENTER && !GameController.gs.player.isAlive())
                gv.select(gv.getMenuElements()[gv.getElementCounter()].getText(), event);
            if (event.getCode() == KeyCode.W  && GameController.gs.player.isAlive()|| event.getCode() == KeyCode.UP && GameController.gs.player.isAlive())
                gs.player.move("UP");
            if (event.getCode() == KeyCode.UP && !GameController.gs.player.isAlive() || event.getCode() == KeyCode.DOWN && !GameController.gs.player.isAlive())
                gv.getMenuElements()[gv.elementCounter].lostFocus();
                gv.traverseMenu(event.getCode(), gv.getMenuElements());
                gv.getMenuElements()[gv.elementCounter].gainedFocus();
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN && GameController.gs.player.isAlive())
                gs.player.move("DOWN");
            if (event.getCode() == KeyCode.E && GameController.gs.player.isAlive())
                gs.player.powerUp();
            if (event.getCode() == KeyCode.Q && GameController.gs.player.isAlive())
                gs.player.setShield();
            if (event.getCode() == KeyCode.T && GameController.gs.player.isAlive())
                GameController.getInstance().getHUD().renderPowerUpText("Test");
            if (event.getCode() == KeyCode.R && GameController.gs.player.isAlive())
                GameView.getInstance().renderScoreScreen();
        });

        s.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE && GameController.gs.player.isAlive())
                gs.player.isNotShooting();
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP && GameController.gs.player.isAlive())
                gs.player.move("STOP");
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN && GameController.gs.player.isAlive())
                gs.player.move("STOP");
        });
    }
}
