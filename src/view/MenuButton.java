package view;

import assets.java.SoundManager;
import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MenuButton extends StackPane{
    private Text text;
    private Rectangle bg;

    MenuButton(String name){
        text = new Text(name);
        text.setFont(text.getFont().font(20));
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

    public void setColor(Color color){
        bg.setStroke(color);
        text.setFill(Color.GREY);
    }

    public void setText(String text){
        this.text.setText(text);
    }
}
