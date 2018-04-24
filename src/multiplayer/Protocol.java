package multiplayer;

import java.io.*;

import model.GameState;
import model.player.Player;
import model.player.Player2;
import model.enemy.Enemy;
import controller.GameController;

public class Protocol {
    
    protected synchronized ByteArrayOutputStream sendPrep(String action, int id, int health, boolean alive) {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(bytestream);    
        
        switch(action) {
            case "EnemyUpdate":
                try {
                    for(Enemy enemy: GameState.getInstance().enemies) {
                        if (enemy.getID() == id) {
                            stream.writeChar('E');
                            stream.writeInt(id);
                            stream.writeInt(health);
                            stream.writeBoolean(alive);
                            
                            stream.flush();
                            stream.close();
                        }
                    }
                }
                catch (IOException e) {
                    System.err.println(e);
                }            
        }
        
        return bytestream;
    }
    
    protected synchronized ByteArrayOutputStream sendPrep(String action, int x, int y) {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(bytestream);          
        
        switch(action) {
            case "Update":
                try {
                    stream.writeChar('M');
                    stream.writeInt(x);
                    stream.writeInt(y);

                    stream.flush();
                    stream.close();
                }
                catch (IOException e) {
                    System.err.println(e);
                }
                break;
            case "Shoot":
                try {
                    stream.writeChar('S');
                    stream.writeInt(x);
                    stream.writeInt(y);
                    
                    stream.flush();
                    stream.close();
                }
                catch (IOException e) {
                    System.err.println(e);
                }
                break;
            case "PowerUp":
                try {
                    stream.writeChar('P');
                }
                catch (IOException e) {
                    
                }
        }
        return bytestream;
    }
    
    public void recieve(DataInputStream input) {
        Player2 player2 = Player2.getInst();
        char breaker = 'b';
        try {
            while (input.available() > 0) {
                char action = input.readChar();
                switch(action) {
                    case 'M':
                        player2.setX(input.readInt());
                        player2.setY(input.readInt());
                        break;
                    case 'S':
                        int x = input.readInt();
                        int y = input.readInt();
                        player2.shoot(x, y);
                    case 'E':
                        int id = input.readInt();
                        int health = input.readInt();
                        boolean alive = input.readBoolean();
                        MultiplayerHandler.getInstance().updateEnemies(id, health, alive);
                        break;
                    case 'P':
                        player2.powerUp();
                }
            }
        }
        catch(IOException e) {
            System.err.println(e);
        }
    }
    
}
