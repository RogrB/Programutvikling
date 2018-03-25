package model.enemy;

import java.util.ArrayList;
import model.Entity;
import model.weapons.EnemyBulletBasic;

import java.util.Random;

public class Enemy extends Entity {

    private final EnemyType TYPE;
    private EnemyMovementPattern pattern;
    int i = 100;

    private final int CHANCE_TO_SHOOT = 2000;
    private int chanceToShoot = CHANCE_TO_SHOOT;
    
    protected ArrayList<EnemyBulletBasic> ebullets = new ArrayList<>();

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
        canShoot = true;

        health = this.TYPE.MAX_HEALTH;

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
        //pattern.resetCoords();
        sprite.getView().relocate(x, y);
        shoot();
        updateBullets();
    }

    @Override
    public void move(String dir){
        /*sprite.getView().relocate(i, i);
        i += 10;
        System.out.println(i);*/
    }

    @Override
    public void shoot() {
        Random random = new Random();
        if(canShoot()) {
            if(random.nextInt(CHANCE_TO_SHOOT) < chanceToShoot) {
                chanceToShoot--;
            }
            else {
                System.out.println("Shoot");
                chanceToShoot = CHANCE_TO_SHOOT;
                ebullets.add(new EnemyBulletBasic(x + 10, y + (this.height / 2) - 8));
                bulletCount++;
            }
        }
    }

    private void updateBullets(){
        for (EnemyBulletBasic b : ebullets) {
            b.setX(b.getX() - 12);
        }
    }
    
    public ArrayList<EnemyBulletBasic> getBullet() { // Kan ikke override metoden i superklassen - forskjellige datatyper
        return ebullets;
    }
}
