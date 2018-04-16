package model.levels;

import model.enemy.Enemy;
import model.enemy.EnemyMovementPattern;
import model.enemy.EnemyType;

import java.util.ArrayList;

public class LevelLoader {

    private ArrayList<Enemy> enemies = new ArrayList<>();

    public LevelLoader(String[][] level){
        initEnemies(level);
    }

    private void initEnemies(String[][] level){
        for(int i = 0; i < level.length; i++){
            for(int j = 0; j < level[i].length; j++){
                switch(level[i][j]){
                    case "0":
                        break;
                    case "1":
                        enemies.add(new Enemy(EnemyType.BLUE1, new EnemyMovementPattern("LEFT_PULSATING"),  1 + (j * 100), 1 + (i * 120)));
                        break;
                    case "2":
                        enemies.add(new Enemy(EnemyType.ASTROID, new EnemyMovementPattern("COS"), 1 + (j * 100), 1 + (i * 120)));
                        break;
                    case "3":
                        // I fremtiden burde EnemyType og EnemyMovementPattern hos denne bli funnet via levelnavn somehow
                        enemies.add(new Enemy(EnemyType.BOSS01, new EnemyMovementPattern("BOSS_EIGHT"), 200 * j, 140 * i));
                }
            }
        }
    }

    public ArrayList getEnemies(){
        return enemies;
    }


}
