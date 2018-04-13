package model.enemy;

import model.Entity;
import model.GameModel;
import view.GameView;
import model.weapons.Bullet;

import java.util.Random;

public class Enemy extends Entity {

    // MVC-access
    GameModel gm = GameModel.getInstance();
    GameView gv = GameView.getInstance();

    private final EnemyType TYPE;
    private EnemyMovementPattern pattern;

    private int chanceToShoot;
    private int timerToShoot;

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
        chanceToShoot = TYPE.SHOOTING_CHANCE;
        timerToShoot = chanceToShoot;

        if(TYPE.SHOOTING_CHANCE == 0)
            canShoot = false;

        sprite.getImageView().relocate(x, y);
    }

    // GET-SET
    public int getHealth() {
        return health;
    }
    public EnemyMovementPattern getPattern(){
        return pattern;
    }

    public void setChanceToShoot(int chanceToShoot) {
        this.chanceToShoot = chanceToShoot;
    }

    @Override
    public void update(){
        pattern.updatePosition();
        setX(pattern.getX());
        setY(pattern.getY());
        // gv.renderImage(this); // Gir nullpointer .. setter i gamecontroller for now
        // sprite.getImageView().relocate(getX(), getY());
    }

    @Override
    public void shoot() {
        Random random = new Random();
        if(canShoot()) {
            if(random.nextInt(chanceToShoot) < timerToShoot) {
                timerToShoot--;
            }
            else {
                timerToShoot = chanceToShoot;
                gm.getEnemyBullets().add(new Bullet(getX() + 10, getY() + (this.height / 2) - 8, weapon));
                bulletCount++;
            }
        }
    }
    
}
