package multiplayer;

import controller.GameController;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import model.GameState;
import model.enemy.Enemy;
import model.enemy.Asteroid;
import view.MultiplayerView;

import static controller.GameController.gs;

import java.util.Timer;
import java.util.TimerTask;

import model.GameModel;
import view.GameView;
import static view.MultiplayerView.stage;

/**
 * <h1>The base for handling all data transmission between players</h1>
 * This class acts as a layer between the Game logic and
 * multiplayer events
 * @author Roger Birkenes Solli
 */
public class MultiplayerHandler {
    
    /**
     * {@code Protocol} object
     * @see Protocol
     */       
    private Protocol protocol;
    
    /**
     * {@code Sender} object
     * @see Sender
     */       
    private Sender sender;
    
    /**
     * Sets whether or not a connection attempt
     * has been started
     */       
    private boolean initiateConnection = false;
    
    /**
     * Sets whether or not a connection has
     * been established
     */       
    private boolean connected = false;
    
    /**
     * Boolean to set if the game has been started.
     * To make sure multiple instances of the game
     * is not started at once
     */       
    private boolean gameStarted = false;
    
    /**
     * If a connection attempt has been started
     * and then cancelled
     */       
    private boolean cancel = false;
    
    /**
     * If player2 has requested to start the next level.
     * To make sure the request is only processed once
     */      
    private boolean nextGameRequest = false;
    
    /**
     * The singleton object.
     */
    private static MultiplayerHandler inst = new MultiplayerHandler();
    
    /**
     * Private constructor (singleton).
     */    
    private MultiplayerHandler() { }
    
    /**
     * Method to access singleton class.
     * @return Returns a reference to the singleton object.
     */    
    public static MultiplayerHandler getInstance() { return inst; }
    
    /**
     * Initiates the object.
     * @param hostname InetAdress to connect to
     * @param remoteport Sets port to sent data transmissions
     * @param localport Sets port to receive data transmissions
     */
    public void init(String hostname, int remoteport, int localport) {
        protocol = new Protocol();
        sender = new Sender(hostname, remoteport);
        Receiver receiver = new Receiver(localport);
        cancel = false;
        connected = false;
        gameStarted = false;
        nextGameRequest = false;
        initiateConnection = true;

        Thread receiveActivity = new Thread(receiver);
        receiveActivity.start();         
    }

    /**
     * Method for sending data to player2
     * @param action Defines the action for the {@code Protocol} to interpret
     * @param x Sets an int value to be transmitted
     * @param y Sets an int value to be transmitted
     */
    public void send(String action, int x, int y) {
        if(connected)
        sender.send(protocol.sendPrep(action, x, y));
    }
    
    /**
     * Method for sending data to player2 - Specifically enemy information
     * @param action Defines the action for the {@code Protocol} to interpret
     * @param id The ID of the enemy that is being updated between clients
     * @param health New health of the enemy
     * @param alive If the enemy is currently alive or needs to be set dead
     */    
    public void send(String action, int id, int health, boolean alive) {
        if(connected)
        sender.send(protocol.sendPrep(action, id, health , alive));
    }
    
    /**
     * Method for receiving data from Player2
     * @param input takes in DataInputStream for the {@code Protocol} to interpret
     */     
    public void recieveProtocol(DataInputStream input) {
        if(connected)
        protocol.recieve(input);
    }

    /**
     * Returns {@code Protocol} object
     * @return {@code Protocol} object
     */        
    public Protocol getProtocol() {
        return this.protocol;
    }
    
    /**
     * Updates enemies based on data received from Player2
     * @param id The ID of the enemy that is to be updated
     * @param health New enemy health value, if it has been changed
     * @param alive If the enemy is still alive or needs to be set dead
     * Asteroids are handled differently than regular enemies, as they
     * spawn two smaller asteroids upon death
     */       
    void updateEnemies(int id, int health, boolean alive) {
        // System.out.println("trying to find enemyid to apply update");
        ArrayList<Enemy> tempEnemies = new ArrayList<>();
        for(Iterator<Enemy> enemyIterator = GameState.enemies.iterator(); enemyIterator.hasNext();){
            Enemy enemy = enemyIterator.next();
            if (enemy.getID() == id) {
                if(health < enemy.getHealth()) {
                    enemy.setHealth(health);
                    //System.out.println("Setting health to " + health);
                }
                if(!alive && enemy.isAlive()) {
                    enemy.isDead();
                    if (enemy instanceof Asteroid) {
                        tempEnemies.add(enemy);
                    }
                }
            }
        }
        for(Enemy e : tempEnemies){
            GameController.getInstance().spawnSmallAsteroids(e.getX(), e.getY());
        }
    }
    
    /**
     * Creates a thread that attempts to connect to Player2
     * This is done in a thread so as not to freeze the
     * game while connection is established
     */       
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(!connected && !cancel) {
                sender.send(protocol.sendPrep("Connect", 0, 0));
            }           
        } 
    });
    
    /**
     * Starts the thread that attempts to connect to Player2
     */       
    public void startConnection() {
        thread.start();
    }
    
    /**
     * Method for replying to a connection attempt from Player2
     */       
    void replyConnection() {
        sender.send(protocol.sendPrep("Reply", 0, 0));
    }    
    
    /**
     * Method for starting the game when the
     * connection attempt has received a response
     */       
    void establishConnection() {
        setConnected(true);
        thread.interrupt();
        if (!gameStarted) {
            gameStarted = true;
            MultiplayerView.getInst().startMultiplayerGame(stage);
        }
    }
    
    /**
     * Sets {@code connected}
     */       
    private void setConnected(boolean connected) {
        this.connected = connected;
    }
    
    /**
     * Gets {@code connected}
     * @return connected
     */       
    public boolean getConnected() {
        return this.connected;
    }
    
    /**
     * Sends a Disconnection attempt to Player2
     * Timer functions as a buffer to make sure the
     * disconnection request has been sent before calling
     * {@code disconnect} and closing sockets
     */       
    public void sendDisconnect() {
        sender.send(protocol.sendPrep("Disconnect", 0, 0));
        
        Timer dcTimer = new Timer();
        dcTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                if (connected) {
                    disconnect();
                    System.out.println("closing connection");
                }
            }
        }, 0, 50);            
    }
    
    /**
     * Closes connection and unsets multiplayer status
     */       
    public void disconnect() {
        if (connected) {
            GameModel.getInstance().setMultiplayerStatus(false);
            // System.out.println("setting mp to " + GameModel.getInstance().getMultiplayerStatus());
            gs.player2.unsetSprite();
            GameView.getInstance().renderPlayer2();
            sender.closeSocket();
            // receiver.closeSocket();
            GameView.getInstance().getField().changeText("Player 2 disconnected"); 
            System.out.println("Player 2 disconnected");      
            setConnected(false);
        }
    }
    
    /**
     * Cancels an ongoing connection attempt
     */       
    public void cancelConnectAttempt() {
        thread.interrupt();
        cancel = true;
        setConnected(false);
        GameModel.getInstance().setMultiplayerStatus(false);
        sender.closeSocket();
        initiateConnection = false;
    }
    
    /**
     * Sets {@code cancel}
     * @param state sets the cancelstate true or false
     */       
    public void setCancel(boolean state) {
        this.cancel = state;
    }
    
    /**
     * Sends a request for the Player2 client
     * to start the next level
     */       
    public void nextGame() {
        if (connected) {
            if (!nextGameRequest) {
                sender.send(protocol.sendPrep("NextGame", 0, 0));
            }
        }
    }
    
    /**
     * Returns {@code nextGameRequest}
     * @return {@code nextGameRequest}
     */       
    public boolean getNextGameRequest() {
        return this.nextGameRequest;
    }
    
    /**
     * Sets {@code nextGameRequest}
     */       
    public void setNextGameRequest(boolean state) {
        this.nextGameRequest = state;
    }
    
    /**
     * Method for starting the next level
     * after receiving a next level request
     * from Player2
     */       
    void startNextLevel() {
        setNextGameRequest(true);
        GameController.getInstance().nextGame();
        GameView.getInstance().clearScoreScreen();
        GameView.getInstance().setWinButtonOpacity(0);
        GameView.getInstance().getField().changeText("Player 2 started next level");
        System.out.println("Player 2 started next level");
    }
    
    /**
     * Returns {@code initiateConnection}
     * @return {@code initiateConnection}
     */       
    public boolean getInitConnection() {
        return this.initiateConnection;
    }

    /**
     * Sets {@code initiateConnection}
     */       
    public void setInitConnection(boolean b) {
        this.initiateConnection = b;
    }
    
}
