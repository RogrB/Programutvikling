package model.enemy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Entity;

public class Enemy extends Entity {

    private final EnemyType TYPE;
    private EnemyMovementPattern pattern;
    int i = 100;

    public Enemy(EnemyType enemyType, EnemyMovementPattern pattern, int x, int y){
        super(
                enemyType.SPRITE,
                x,
                y,
                enemyType.MAX_HEALTH
        );

        this.TYPE = enemyType;
        this.pattern = pattern;
        height = sprite.getHeight();
        width = sprite.getWidth();

        health = this.TYPE.MAX_HEALTH;

        sprite.getView().relocate(x, y);
    }

    public void updatePosition(double newX, double newY){
        x = (int)newX;
        y = (int)newY;
    }

    public void updatePatternStartingPoint(double x, double y){
        pattern.x = x;
        pattern.y = y;
    }

    // GET
    public EnemyType getTYPE() {
        return TYPE;
    }

    public int getHealth() {
        return health;
    }

    public EnemyMovementPattern getPattern(){
        return pattern;
    }

    @Override
    public void update(){
        sprite.getView().relocate(x, y);
        pattern.nextFrame();
        x = (int)pattern.x;
        y = (int)pattern.y;
        pattern.resetCoords();
    }

    @Override
    public void move(String dir){
        sprite.getView().relocate(i, i);
        i += 10;
        System.out.println(i);
    }

    @Override
    public void shoot() {

    }

    @Override
    public void takeDamage() {

    }
}
