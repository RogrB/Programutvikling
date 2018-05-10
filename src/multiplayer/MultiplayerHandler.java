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
public class MultiplayerHandler {
    
    private Protocol protocol;
    private Sender sender;
    private boolean initiateConnection = false;
    private boolean connected = false;
    private boolean gameStarted = false;
    private boolean cancel = false;
    private boolean nextGameRequest = false;
    
    // Singleton
    private static MultiplayerHandler inst = new MultiplayerHandler();
    private MultiplayerHandler() { }
    public static MultiplayerHandler getInstance() { return inst; }
    
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

    public void send(String action, int x, int y) {
        if(connected)
        sender.send(protocol.sendPrep(action, x, y));
    }
    
    public void send(String action, int id, int healt, boolean alive) {
        if(connected)
        sender.send(protocol.sendPrep(action, id, healt, alive));
    }
    
    public void recieveProtocol(DataInputStream input) {
        if(connected)
        protocol.recieve(input);
    }
    
    public Protocol getProtocol() {
        return this.protocol;
    }
    
    void updateEnemies(int id, int health, boolean alive) {
        // System.out.println("trying to find enemyid to apply update");
        //Iterator<Enemy> enemyIterator = GameState.enemies.iterator();
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
    
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(!connected && !cancel) {
                sender.send(protocol.sendPrep("Connect", 0, 0));
            }           
        } 
    });
    
    public void startConnection() {
        thread.start();
    }
    
    void establishConnection() {
        setConnected(true);
        thread.interrupt();
        if (!gameStarted) {
            gameStarted = true;
            MultiplayerView.getInst().startMultiplayerGame(stage);
        }
    }
    
    void replyConnection() {
        sender.send(protocol.sendPrep("Reply", 0, 0));
    }
    
    private void setConnected(boolean connected) {
        this.connected = connected;
    }
    
    public boolean getConnected() {
        return this.connected;
    }
    
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
    
    public void disconnect() {
        if (connected) {
            GameModel.getInstance().setMultiplayerStatus(false);
            System.out.println("setting mp to " + GameModel.getInstance().getMultiplayerStatus());
            gs.player2.unsetSprite();
            GameView.getInstance().renderPlayer2();
            sender.closeSocket();
            // receiver.closeSocket();
            GameView.getInstance().getField().changeText("Player 2 disconnected"); 
            System.out.println("Player 2 disconnected");      
            setConnected(false);
        }
    }
    
    public void cancelConnectAttempt() {
        thread.interrupt();
        cancel = true;
        setConnected(false);
        GameModel.getInstance().setMultiplayerStatus(false);
        sender.closeSocket();
        initiateConnection = false;
    }
    
    public void setCancel(boolean state) {
        this.cancel = state;
    }
    
    public void nextGame() {
        if (connected) {
            if (!nextGameRequest) {
                sender.send(protocol.sendPrep("NextGame", 0, 0));
            }
        }
    }
    
    public boolean getNextGameRequest() {
        return this.nextGameRequest;
    }
    
    public void setNextGameRequest(boolean state) {
        this.nextGameRequest = state;
    }
    
    void startNextLevel() {
        setNextGameRequest(true);
        GameController.getInstance().nextGame();
        GameView.getInstance().clearScoreScreen();
        GameView.getInstance().setWinButtonOpacity(0);
        GameView.getInstance().getField().changeText("Player 2 started next level");
        System.out.println("Player 2 started next level");
    }
    
    public boolean getInitConnection() {
        return this.initiateConnection;
    }
    
    public void setInitConnection(boolean b) {
        this.initiateConnection = b;
    }
    
}
