package multiplayer;

import java.io.*;

import model.GameState;
import model.player.Player2;
import model.enemy.Enemy;
import view.MultiplayerView;

import static controller.GameController.gs;

public class Protocol {
    
    protected synchronized ByteArrayOutputStream sendPrep(String action, int id, int health, boolean alive) {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(bytestream);    
        
        switch(action) {
            case "EnemyUpdate":
                try {
                    for(Enemy enemy: gs.enemies) {
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
                break;
        }
        
        return bytestream;
    }
    
    protected synchronized ByteArrayOutputStream sendPrep(String action, int x, int y) {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(bytestream);          
        
        switch(action) {
            case "Connect":
                try {
                    stream.writeChar('C');
                    stream.flush();
                    stream.close();
                }
                catch (IOException e) {
                    System.err.println(e);
                }
                break;
            case "Reply":
                try {
                    stream.writeChar('R');
                }
                catch (IOException e) {
                    System.err.println(e);
                }
                break;
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
                    System.err.println(e);
                }
                break;
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
                        break;
                    case 'E':
                        int id = input.readInt();
                        int health = input.readInt();
                        boolean alive = input.readBoolean();
                        MultiplayerHandler.getInstance().updateEnemies(id, health, alive);
                        break;
                    case 'P':
                        player2.powerUp();
                        break;
                    case 'C':
                        MultiplayerHandler.getInstance().replyConnection();
                        break;
                    case 'R':
                        MultiplayerHandler.getInstance().establishConnection();
                        break;
                }
            }
        }
        catch(IOException e) {
            System.err.println(e);
        }
    }
    
}
