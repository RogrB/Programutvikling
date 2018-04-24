package multiplayer;

import java.io.*;
import model.player.Player;
import model.player.Player2;
import model.enemy.Enemy;
import controller.GameController;

public class Protocol {
    
    /*
    protected synchronized ByteArrayOutputStream sendPrep(String input) {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(bytestream);    

        try {
        stream.writeBytes(input);
        /*
        if(toSend.equals("New Game") {
         // do new game things
        }
        stream.writeInt(1);
        stream.writeChar(a);

        stream.flush();
        stream.close();        
        }
        catch (IOException e) {
            System.err.println(e);
        }
        return bytestream;
    } */
    
    /*
    protected synchronized ByteArrayOutputStream sendPrep(Player player) {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(bytestream);    

        try {
            stream.writeChar('M');
            stream.writeInt(player.getX());
            stream.writeChar('b');
            stream.writeInt(player.getY());
        /*
        stream.writeBytes(toSend);

        if(toSend.equals("New Game") {
         // do new game things
        }
        stream.writeInt(1);
        stream.writeChar(a);

        stream.flush();
        stream.close();   
        
        }
        catch (IOException e) {
            System.err.println(e);
        }
        return bytestream;
    }   */
    
    protected synchronized ByteArrayOutputStream sendPrep(String action, int id, int health, boolean alive) {
        System.out.println("Sending enemyupdate from protocol");
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(bytestream);    
        
        switch(action) {
            case "EnemyUpdate":
                try {
                    for(Enemy enemy: GameController.getInstance().getEnemies()) {
                        if (enemy.getID() == id) {
                            System.out.println("sending update for enemy id " + id);
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
                    System.out.println("Writing shot");
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
                        System.out.println("recieving enemyupdate");
                        int id = input.readInt();
                        int health = input.readInt();
                        boolean alive = input.readBoolean();
                        MultiplayerHandler.getInstance().updateEnemies(id, health, alive);
                }
            }
        }
        catch(IOException e) {
            System.err.println(e);
        }
    }
    
}
