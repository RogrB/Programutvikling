package view.customElements;

import assets.java.SoundManager;
import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Custom class for a custom button used throughout the application.
 * The class {@code MenuButton} extends {@code StackPane}.
 *
 * @author Jonas Ege Carlsen
 */
public class MenuButton extends StackPane{
    /**
     * The text of the button.
     */
    private Text text;

    /**
     * The background of the button.
     */
    private Rectangle bg;

    /**
     * A value that can be associated with the button. Used when button names cannot be used.
     */
    private int value;

    /**
     * Constructor for the custom menu button.
     * Initializes the object, sets button text and events.
     * @param buttonText The text to place on the button.
     */
    public MenuButton(String buttonText){
        text = new Text(buttonText);
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.WHITE);

        bg = new Rectangle(300, 30);
        bg.setFill(Color.BLACK);
        bg.setStrokeWidth(2);
        bg.setStroke(Color.rgb(255, 255, 255));
        bg.setEffect(new GaussianBlur(3.5));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);
        this.setOnMouseEntered(event -> {
            gainedFocus();
            SoundManager.getInst().nav();
        });
        this.setOnMouseExited(event -> lostFocus());
    }

    /**
     * Returns the button text.
     * @return Returns the text of the button.
     */
    public String getText(){
        return text.getText();
    }

    /**
     *  Changes the background color and the text color to indicate
     *  that the button is targeted.
     */
    public void gainedFocus(){
        bg.setFill(Color.WHITE);
        text.setFill(Color.BLACK);
    }

    /**
     * Changes the background color and the text color to indicate
     * that the button is no longer targeted.
     */
    public void lostFocus(){
        bg.setFill(Color.BLACK);
        text.setFill(Color.WHITE);
    }

    /**
     * Changes the color of the button stroke as well as the button text color.
     * @param color The color you want to change the stroke to.
     */
    public void setColor(Color color){
        bg.setStroke(color);
        text.setFill(color);
    }

    /**
     * Changes the text of the button.
     * @param text The text to change to.
     */
    public void setText(String text){
        this.text.setText(text);
    }

    /**
     * Sets the value of a button.
     * @param value Button value.
     */
    public void setValue(int value){this.value = value; }

    /**
     * Returns the button value.
     * @return Returns the button value.
     */
    public int getValue(){return value; }
}
