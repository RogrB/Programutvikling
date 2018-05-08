package model.enemy;

import assets.java.SoundManager;
import model.Entity;
import model.GameModel;
import model.IdGen;
import model.weapons.Basic;
import view.OptionsView;

import java.util.Iterator;
import java.util.Random;

import static controller.GameController.gs;
import static model.GameModel.gameSettings;

public class Enemy extends Entity {

    // MVC-access

    private final EnemyType TYPE;
    private EnemyMovementPattern pattern;

    private double chanceToShoot;
    private boolean scoreCount = false;
    private final int enemyID;

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

        canShoot = TYPE.canShoot();
        weapon = TYPE.WEAPON;
        health = TYPE.MAX_HEALTH;
        chanceToShoot = TYPE.SHOOTING_CHANCE;

        if(TYPE.SHOOTING_CHANCE == 0)
            canShoot = false;

        enemyID = IdGen.getInstance().newId();

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
    public void shoot() {
        Random random = new Random();
        if(canShoot()) {
            double shotMod = (double) gameSettings.getDifficultyValue()/3;
            if(random.nextDouble() > 1 - (chanceToShoot * shotMod)) {
                playShotAudio();
                gs.enemyBullets.add(new Basic(getX() + 10, getY() + (this.height / 2) - 8, weapon));
            }
        }
    }

    private void playShotAudio(){
        if(!TYPE.IS_BOSS){
            SoundManager.getInst().shotEnemy();
        } else {
            SoundManager.getInst().shotBoss();
        }
    }

    public void update(Iterator i){
        if(isAlive()) {
            pattern.updatePosition();
            setX(pattern.getX());
            setY(pattern.getY());

            if(TYPE.IS_BOSS){
                SoundManager.getInst().bossTalk();
            }

        } else {
            setOldX(getX());
            setOldY(getY());
            animateDeath();
            addScore();
        }

        if(isOffScreen() || getReadyToPurge()){
            purge(i);
        } else
            shoot();
    }
    
    public void addScore() {
        if (!this.scoreCount) {
            gs.player.setScore(gs.player.getScore() + 100);
            gs.player.setEnemiesKilled(gs.player.getEnemiesKilled() + 1);
            this.scoreCount = true;
        }
    }
    
    public EnemyType getType() {
        return this.TYPE;
    }

    public int getID() {
        return this.enemyID;
    }
    
}
