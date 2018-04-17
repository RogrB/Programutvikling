package model.levels;

import assets.java.Sprite;
import model.PowerUp;
import model.enemy.Enemy;
import model.enemy.EnemyMovementPattern;
import model.enemy.EnemyType;

import java.util.ArrayList;

public class LevelLoader {

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<PowerUp> powerups = new ArrayList<>();

    public LevelLoader(String[][][] level){
        initEnemies(level);
    }

    private void initEnemies(String[][][] level){
        for(int i = 0; i < level.length; i++){
            for(int j = 0; j < level[i].length; j++){
                switch(level[i][j][0]){
                    case "0":
                        break;
                    case "1":
                        enemies.add(new Enemy(EnemyType.valueOf(level[i][j][1]), new EnemyMovementPattern(level[i][j][2]),  1 + (j * 100), 1 + (i * 120)));
                        break;
                    case "2":
                        enemies.add(new Enemy(EnemyType.ASTEROID, new EnemyMovementPattern(level[i][j][1]), 1 + (j * 100), 1 + (i * 120)));
                        break;
                    case "3":
                        enemies.add(new Enemy(EnemyType.valueOf(level[i][j][1]), new EnemyMovementPattern(level[i][j][2]), 200 * j, 140 * i));
                        break;
                    case "4":
                        System.out.println("Adding powerup");
                        powerups.add(new PowerUp(Sprite.valueOf(level[i][j][1]), 1 + (j * 100), 1 + (i * 120)));



                }
            }
        }
    }

    public ArrayList getEnemies(){
        return enemies;
    }

    public ArrayList getPowerups(){ return powerups;}


}
