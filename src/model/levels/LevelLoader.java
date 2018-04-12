package model.levels;

import model.enemy.Enemy;
import model.enemy.EnemyMovementPattern;
import model.enemy.EnemyType;

import java.util.ArrayList;

public class LevelLoader {

    private ArrayList<Enemy> enemies = new ArrayList<>();

    public LevelLoader(String[] level){
        initEnemies(level);
    }

    private void initEnemies(String[] level){
        for(int i = 0; i < level.length; i++){
            String line = level[i];
            for(int j = 0; j < line.length(); j++){
                switch(line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        EnemyMovementPattern pattern = EnemyMovementPattern.COS;
                        //Enemy e = new Enemy(EnemyType.SHIP, pattern, 120 * j, 140 * i);
                        Enemy e = new Enemy(EnemyType.SHIP, pattern, 200 * j, 140 * i);
                        System.out.format("Enemy created at x: %d, y: %d \n", e.getX(), e.getY());
                        enemies.add(e);

                        break;
                    case '2':
                        System.out.println("Detected a 2");


                }
            }
        }
    }

    public ArrayList getEnemies(){
        return enemies;
    }


}
