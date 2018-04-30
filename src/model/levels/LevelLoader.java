package model.levels;

import assets.java.Sprite;
import model.enemy.Enemy;
import model.enemy.EnemyMovementPattern;
import model.enemy.EnemyType;
import model.enemy.Asteroid;
import view.ViewUtil;

import static model.GameState.enemies;
import static model.GameState.bossType;

public class LevelLoader {

    // Singleton
    private static LevelLoader inst = new LevelLoader();
    private LevelLoader(){}
    public static LevelLoader getInstance(){ return inst; }

    private String[][][] levelData;
    private final int COLUMN_WIDTH = 70;

    public void setLevelData(String[][][] levelData){
        bossType = null;
        this.levelData = levelData;
    }
    
    public int increment(int increment){
        if((increment < (levelData[1].length*COLUMN_WIDTH))) {
            if (increment % COLUMN_WIDTH == 0) {
                int column = (increment / COLUMN_WIDTH);
                loopThroughEnemyRows(getEnemyColumn(column));
            }
        }
        return ++increment;
    }

    private void loopThroughEnemyRows(String[][] enemies){
        for(int i = 0; i < enemies.length; i++){
            generateEnemy(enemies[i], i);
        }
    }

    private void generateEnemy(String[] enemyData, int yLane){
        int xSpawn = ViewUtil.VIEW_WIDTH - 1;

        switch(enemyData[0]){
            case "1":
                enemies.add(new Enemy(
                                EnemyType.valueOf(enemyData[1]),
                                new EnemyMovementPattern(enemyData[2]),
                                xSpawn,
                                ((ViewUtil.VIEW_HEIGHT-EnemyType.valueOf(enemyData[1]).SPRITE.getHeight())/6) * yLane + 1));
                break;
            case "2":
                enemies.add(new Asteroid(
                                new EnemyMovementPattern(enemyData[1]),
                                xSpawn,
                                ((ViewUtil.VIEW_HEIGHT-Sprite.ASTEROID1.getHeight())/6) * yLane + 1));
                break;
            case "3":
                bossType = EnemyType.valueOf(enemyData[1]).name();
                enemies.add(new Enemy(
                                EnemyType.valueOf(enemyData[1]),
                                new EnemyMovementPattern(enemyData[2]),
                                xSpawn,
                                ((ViewUtil.VIEW_HEIGHT-EnemyType.valueOf(enemyData[1]).SPRITE.getHeight())/6) * yLane + 1));
                break;
        }
    }

    public String[][] getEnemyColumn(int column){

        int rows = levelData.length;
        String[][] res = new String[rows][3];

        for(int i = 0; i < rows; i++){
            res[i] = levelData[i][column];
        }
        return res;
    }

}
