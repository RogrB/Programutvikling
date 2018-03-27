package model.enemy;

import model.Entity;
import model.GameModel;
import model.weapons.EnemyBulletBasic;

import java.util.Random;

public class Enemy extends Entity {

    // MVC-access
    GameModel gm = GameModel.getInstance();

    private final EnemyType TYPE;
    private EnemyMovementPattern pattern;
    int i = 100;

    private final int CHANCE_TO_SHOOT = 2000;
    private int chanceToShoot = CHANCE_TO_SHOOT;

    public Enemy(EnemyType enemyType, EnemyMovementPattern pattern, int x, int y){
        super(
                enemyType.SPRITE,
                x,
                y,
                enemyType.MAX_HEALTH
        );

        this.TYPE = enemyType;
        this.pattern = pattern;

        height = (int) sprite.getHeight();
        width = (int) sprite.getWidth();

        canShoot = TYPE.canShoot();
        weapon = TYPE.WEAPON;
        health = TYPE.MAX_HEALTH;

        sprite.getView().relocate(x, y);
    }

    public void updatePatternStartingPoint(double x, double y){
        pattern.x = x;
        pattern.y = y;
    }

    // GET
    public int getHealth() {
        return health;
    }

    public EnemyMovementPattern getPattern(){
        return pattern;
    }

    @Override
    public void update(){
        pattern.nextFrame();
        x = (int)pattern.x;
        y = (int)pattern.y;
        sprite.getView().relocate(x, y);
    }

    @Override
    public void shoot() {
        Random random = new Random();
        if(canShoot()) {
            if(random.nextInt(CHANCE_TO_SHOOT) < chanceToShoot) {
                chanceToShoot--;
            }
            else {
                chanceToShoot = CHANCE_TO_SHOOT;
                gm.getEnemyBullets().add(new EnemyBulletBasic(x + 10, y + (this.height / 2) - 8));
                bulletCount++;
            }
        }
    }

}
