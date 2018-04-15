package model.enemy;

import assets.java.Sprite;
import model.Entity;
import model.GameModel;
import view.GameView;
import model.weapons.Bullet;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Enemy extends Entity {

    // MVC-access
    GameModel gm = GameModel.getInstance();
    GameView gv = GameView.getInstance();

    private final EnemyType TYPE;
    private EnemyMovementPattern pattern;

    private int chanceToShoot;
    private int timerToShoot;
    private int animCounter;

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
        if (this.TYPE == TYPE.ASTROID) {
            animateAstroid();
        }
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
    
    private void animateAstroid() {
        Timer blinkTimer = new Timer();
        blinkTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                switch(animCounter) {
                    case 5:
                        getSprite().setImage(Sprite.ASTROID2.getImage());
                        //newSprite(Sprite.ASTROID2);
                        break;
                    case 10:
                        getSprite().setImage(Sprite.ASTROID3.getImage());
                        //newSprite(Sprite.ASTROID3);
                        break;
                    case 15:
                        getSprite().setImage(Sprite.ASTROID4.getImage());
                        //newSprite(Sprite.ASTROID4);
                        break;
                    case 20:
                        getSprite().setImage(Sprite.ASTROID5.getImage());
                        //newSprite(Sprite.ASTROID5);
                        break;
                    case 25:
                        getSprite().setImage(Sprite.ASTROID6.getImage());
                        //newSprite(Sprite.ASTROID6);
                        break;
                    case 30:
                        getSprite().setImage(Sprite.ASTROID7.getImage());
                        //newSprite(Sprite.ASTROID7);
                        break;      
                    case 35:
                        getSprite().setImage(Sprite.ASTROID8.getImage());
                        //newSprite(Sprite.ASTROID8);
                        animCounter = 0;
                        break;                            
                }
                animCounter++;           
            }
        }, 0, 20);        
    }
    
}
