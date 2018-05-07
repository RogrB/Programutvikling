package view;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class WarningField extends StackPane {

    private Text text;

    public WarningField(){
        text = new Text("");
        text.setFont(text.getFont().font(15));
        text.setFill(Color.WHITE);

        Rectangle bg = new Rectangle(250, 30);
        bg.setFill(Color.DARKRED);
        bg.setStroke(Color.BLACK);
        bg.setEffect(new GaussianBlur(3.5));
        bg.setArcWidth(30);
        bg.setArcHeight(30);
        this.setOpacity(0);

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);
    }

    public void changeText(String newText){
        text.setText(newText);
        FadeTransition fadeTransitionIn = new FadeTransition(Duration.millis(2000), this);
        fadeTransitionIn.setFromValue(0);
        fadeTransitionIn.setToValue(1);
        fadeTransitionIn.setDuration(Duration.millis(2000));
        fadeTransitionIn.play();
        FadeTransition fadeTransitionOut = new FadeTransition(Duration.millis(2000), this);
        fadeTransitionOut.setDelay(Duration.millis(6000));
        fadeTransitionOut.setFromValue(1);
        fadeTransitionOut.setToValue(0);
        fadeTransitionOut.play();
    }
}
