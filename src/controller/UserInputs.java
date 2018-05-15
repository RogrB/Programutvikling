package controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import static controller.GameController.gs;
import view.GameView;

/**
 * Class used for handling key events in GameView.
 *
 * @author Jonas Ege Carlsen, Åsmund Røst Wien
 */
public class UserInputs {

    /**
     * Public constructor. Initializes the class and
     * fires another function.
     * @param s Scene to bind inputs to.
     */
    public UserInputs(Scene s){
        setKeyListeners(s);
    }

    /**
     * Reference to the GameView instance.
     */
    private GameView gv = GameView.getInstance();

    /**
     * Method to bind inputs to certain actions based off of
     * whether the player is alive, playing or both.
     * @param s Scene to bind inputs to.
     */
    private void setKeyListeners(Scene s){
        s.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE && gs.player.getPlaying() && gs.player.isAlive()) {
                gs.player.isShooting();
            }
            if(event.getCode() == KeyCode.SPACE && !gs.player.getPlaying() && gs.player.isAlive() ||
                    event.getCode() == KeyCode.ENTER && !gs.player.getPlaying() && gs.player.isAlive())
            {
                gv.select(gv.getMenuElementsWon()[gv.getElementCounter()].getText(), event);
            }
            if (event.getCode() == KeyCode.SPACE  && !gs.player.getPlaying() && !gs.player.isAlive()||
                    event.getCode() == KeyCode.ENTER && !gs.player.getPlaying() && !gs.player.isAlive())
            {
                gv.select(gv.getMenuElementsLost()[gv.getElementCounter()].getText(), event);
            }
            if(event.getCode() == KeyCode.SPACE && gs.player.getPlaying() && !gs.player.isAlive()||
                    event.getCode() == KeyCode.ENTER && gs.player.getPlaying() && !gs.player.isAlive())
            {
                System.out.println(GameController.getInstance().getLastGameLost());
                gv.select(gv.getMenuElementsWon()[1].getText(), event);
            }
            if (event.getCode() == KeyCode.W  && gs.player.getPlaying() && gs.player.isAlive()||
                    event.getCode() == KeyCode.UP && gs.player.getPlaying() && gs.player.isAlive())
            {
                gs.player.move("UP");
            }
            if (event.getCode() == KeyCode.UP && !gs.player.getPlaying() && !gs.player.isAlive() ||
                    event.getCode() == KeyCode.DOWN && !gs.player.getPlaying() && !gs.player.isAlive())
            {
                gv.getMenuElementsLost()[gv.getElementCounter()].lostFocus();
                gv.traverseMenu(event.getCode(), gv.getMenuElementsLost());
                gv.getMenuElementsLost()[gv.getElementCounter()].gainedFocus();
            }
            if(event.getCode() == KeyCode.UP && !gs.player.getPlaying() && gs.player.isAlive() ||
                    event.getCode() == KeyCode.DOWN && !gs.player.getPlaying() && gs.player.isAlive())
            {
                gv.getMenuElementsWon()[gv.getElementCounter()].lostFocus();
                gv.traverseMenu(event.getCode(), gv.getMenuElementsWon());
                gv.getMenuElementsWon()[gv.getElementCounter()].gainedFocus();
            }
            if (event.getCode() == KeyCode.S && gs.player.getPlaying() && gs.player.isAlive() ||
                    event.getCode() == KeyCode.DOWN && gs.player.getPlaying() && gs.player.isAlive())
            {
                gs.player.move("DOWN");
            }
            if (event.getCode() == KeyCode.E && gs.player.getPlaying() && gs.player.isAlive()) {
                gs.player.powerUp();
            }
            if (event.getCode() == KeyCode.Q && gs.player.getPlaying() && gs.player.isAlive()) {
                gs.player.setShield();
            }
        });

        s.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE && gs.player.getPlaying()) {
                gs.player.isNotShooting();
            }
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP && gs.player.getPlaying()) {
                gs.player.move("STOP");
            }
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN && gs.player.getPlaying()) {
                gs.player.move("STOP");
            }
        });
    }
}
