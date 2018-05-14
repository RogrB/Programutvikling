package view;

import assets.java.SoundManager;
import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuButton extends StackPane{
    private Text text;
    private Rectangle bg;
    private int value;

    MenuButton(String name){
        text = new Text(name);
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
        this.setOnMouseClicked(event -> SoundManager.getInst().navSelect());
    }

    public String getText(){
        return text.getText();
    }

    public void gainedFocus(){
        bg.setFill(Color.WHITE);
        text.setFill(Color.BLACK);
    }

    public void lostFocus(){
        bg.setFill(Color.BLACK);
        text.setFill(Color.WHITE);
    }

    void setColor(Color color){
        bg.setStroke(color);
        text.setFill(Color.GREY);
    }

    void setText(String text){
        this.text.setText(text);
    }

    public void setValue(int value){this.value = value; }

    public int getValue(){return value; }
}
