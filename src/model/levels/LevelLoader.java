package model.levels;

import assets.java.Sprite;
import model.enemy.Enemy;
import model.enemy.EnemyMovementPattern;
import model.enemy.EnemyType;
import model.enemy.Asteroid;
import view.ViewUtil;

import static model.GameState.enemies;
import static model.GameState.bossType;

/**
 * <h1>Class for loading and handling levels</h1>
 *
 * @author Jonas Ege Carlsen
 */
public class LevelLoader {

    /**
     * The singleton object.
     */
    private static LevelLoader inst = new LevelLoader();

    /**
     * Private constructor.
     */
    private LevelLoader(){}

    /**
     * Method to access the singleton object.
     * @return Returns a reference to the singleton object.
     */
    public static LevelLoader getInstance(){ return inst; }

    /**
     * Three-dimensional array containing level data.
     */
    private String[][][] levelData;

    /**
     * Sets the Level to the desired LevelData.
     * @param levelData Desired level.
     */
    public void setLevelData(String[][][] levelData){
        bossType = null;
        this.levelData = levelData;
    }

    /**
     * Loads a column of leveldata.
     * <b>Note: </b>The column width is set to 70, which means
     * that once the {@code levelIncrement % 70 == 0}, a new
     * column is loaded.
     * @param increment The levels increment progression.
     * @return Returns The levels increment progression +1.
     */
    public int increment(int increment){
        int COLUMN_WIDTH = 70;
        if((increment < (levelData[1].length* COLUMN_WIDTH))) {
            if (increment % COLUMN_WIDTH == 0) {
                int column = (increment / COLUMN_WIDTH);
                loopThroughEnemyRows(getEnemyColumn(column));
            }
        }
        return ++increment;
    }

    /**
     * Loops through the inner row enemy definitions.
     * @param enemies A single column (2d array) returned
     *                by {@code getEnemyColumn()}.
     */
    private void loopThroughEnemyRows(String[][] enemies){
        for(int i = 0; i < enemies.length; i++){
            generateEnemy(enemies[i], i);
        }
    }

    /**
     * Creates enemies based off of the level data passed in.
     * @param enemyData Array consisting of EnemyType and EnemyMovementPattern
     * @param yLane Which lane to place the enemy in.
     */
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

    /**
     * Returns a column of LevelData
     * @param column Column to return
     * @return Returns a 2D array.
     */
    private String[][] getEnemyColumn(int column){

        int rows = levelData.length;
        String[][] res = new String[rows][3];

        for(int i = 0; i < rows; i++){
            res[i] = levelData[i][column];
        }
        return res;
    }

}
