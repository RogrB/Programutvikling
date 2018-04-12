package model.enemy;

import model.Entity;
import model.GameModel;
import model.weapons.Bullet;

import java.util.Random;

public class Enemy extends Entity {

    // MVC-access
    GameModel gm = GameModel.getInstance();

    private final EnemyType TYPE;
    private EnemyMovementPattern pattern;

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
        pattern.setStartPosition(x, y);

        height = (int) sprite.getHeight();
        width = (int) sprite.getWidth();

        canShoot = TYPE.canShoot();
        weapon = TYPE.WEAPON;
        health = TYPE.MAX_HEALTH;

        sprite.getView().relocate(x, y);
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
        //pattern.nextFrame();
        pattern.updatePosition();
        x = pattern.getX();
        y = pattern.getY();
        sprite.getView().relocate(getX(), getY());
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
                gm.getEnemyBullets().add(new Bullet(x + 10, y + (this.height / 2) - 8, weapon));
                bulletCount++;
            }
        }
    }
    
}
