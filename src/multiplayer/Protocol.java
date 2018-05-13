package multiplayer;

import java.io.*;

import model.GameState;
import model.player.Player2;
import model.enemy.Enemy;

import view.GameView;

/**
 * <h1>The Protocol encodes and interprets all data transmissions between clients</h1>
 * This class handles action requests and encodes them accordingly in order to minimize
 * data needed to be transmitted between clients. The data received is then interpreted
 * and processed accordingly.
 * 
 * @author Roger Birkenes Solli
 */
class Protocol {
    
    /**
     * The sendPrep Method prepared data to be transmitted in the {@code Sender} class
     * @see Sender. This method specifically handles the enemy updates when a player
     * shoots an enemy. The data is condensed into chars, ints and booleans to limit
     * transmission size.
     * @param action The action that is to be transmitted
     * @param id The ID of the enemy that is being updated
     * @param health The health of the enemy
     * @param alive If the enemy is still alive after being updated
     * @return a ByteArrayOutputStream ready to be sent in the {@code Sender} class
     */      
    synchronized ByteArrayOutputStream sendPrep(String action, int id, int health, boolean alive) {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(bytestream);    
        
        switch(action) {
            case "EnemyUpdate":
                try {
                    for(Enemy enemy: GameState.enemies) {
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
    
    /**
     * A sendPrep Method with different parameters to handle different actions like
     * updating Player2 position, handling player2 shot events, powerups, connection
     * and disconnection requests etc.
     * The data is condensed into chars, ints and booleans to limit transmission size.
     * @param action The action that is to be transmitted
     * @param x An optional x value that can be transmitted
     * @param y An optional y value that can be transmitted
     * @return a ByteArrayOutputStream ready to be sent in the {@code Sender} class
     */  
    synchronized ByteArrayOutputStream sendPrep(String action, int x, int y) {
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
            case "Disconnect":
                try {
                    stream.writeChar('D');
                }
                catch (IOException e) {
                    System.err.println(e);
                }
                break;
            case "NextGame":
                try {
                    stream.writeChar('N');
                }
                catch (IOException e) {
                    System.err.println(e);
                }
                break;                
        }
        return bytestream;
    }
    
    /**
     * This method receives data transmitted from Player2 and interprets them according
     * to the encoding done in the {@code sendPrep} methods.
     * It reads the starting char and then processes the action requested.
     * @param input The DataInputStream that was received from Player2 through the {@code Receiver} class
     */     
    void recieve(DataInputStream input) {
        Player2 player2 = Player2.getInst();
        //char breaker = 'b';
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
                    case 'D':
                        MultiplayerHandler.getInstance().disconnect();
                        break;
                    case 'N':
                        MultiplayerHandler.getInstance().startNextLevel();
                        break;
                }
            }
        }
        catch(IOException e) {
            System.err.println(e);
        }
    }
    
}
