package model.levels;

import assets.java.Sprite;
import model.PowerUp;
import model.enemy.Enemy;
import model.enemy.EnemyMovementPattern;
import model.enemy.EnemyType;
import model.enemy.Asteroid;

import java.util.ArrayList;

import static view.GameView.GAME_HEIGHT;
import static view.GameView.GAME_WIDTH;
import static controller.GameController.enemies;

public class LevelLoader {

    // Singleton
    private static LevelLoader inst = new LevelLoader();
    private LevelLoader(){}
    public static LevelLoader getInstance(){ return inst; }

    private String[][][] levelData;
    private int increment;
    private final int COLUMN_WIDTH = 70;

    private ArrayList<PowerUp> powerups = new ArrayList<>();

    public void setLevelData(String[][][] levelData){
        this.levelData = levelData;
    }
    
    public void increment(){
        if((increment < (levelData[1].length*COLUMN_WIDTH))) {
            increment++;
            if (increment % COLUMN_WIDTH == 0) {
                int column = (increment / COLUMN_WIDTH);
                loopThroughEnemyRows(getEnemyColumn(column));
            }
        }
    }

    private void loopThroughEnemyRows(String[][] enemies){
        for(int i = 0; i < enemies.length; i++){
            generateEnemy(enemies[i], i);
        }
    }

    private void generateEnemy(String[] enemyData, int yLane){
        int xSpawn = GAME_WIDTH - 1;

        switch(enemyData[0]){
            case "1":
                enemies.add(new Enemy(
                                EnemyType.valueOf(enemyData[1]),
                                new EnemyMovementPattern(enemyData[2]),
                                xSpawn,
                                ((GAME_HEIGHT-EnemyType.valueOf(enemyData[1]).SPRITE.getHeight())/6) * yLane + 1));
                break;
            case "2":
                enemies.add(new Asteroid(
                                new EnemyMovementPattern(enemyData[1]),
                                xSpawn,
                                ((GAME_HEIGHT-Sprite.ASTEROID1.getHeight())/6) * yLane + 1));
                break;
            case "3":
                enemies.add(new Enemy(
                                EnemyType.valueOf(enemyData[1]),
                                new EnemyMovementPattern(enemyData[2]),
                                xSpawn,
                                ((GAME_HEIGHT-EnemyType.valueOf(enemyData[1]).SPRITE.getHeight())/6) * yLane + 1));
                break;
            case "4":
                //powerups.add(new PowerUp(Sprite.valueOf(enemyData[1]), xSpawn, 1 + ySpawn));
                break;
        }
    }

    public String[][] getEnemyColumn(int column){

        int rows = levelData.length;
        String[][] res = new String[rows][3];

        for(int i = 0; i < rows; i++){
            res[i] = levelData[i][column-1];
        }
        return res;
    }

}
