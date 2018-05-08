package multiplayer;

import controller.GameController;
import java.io.DataInputStream;
import model.GameState;
import model.enemy.Enemy;
import model.enemy.Asteroid;
import view.MultiplayerView;
import javafx.stage.Stage;

import static controller.GameController.gs;
import java.util.Iterator;
import model.GameModel;
import static view.MultiplayerView.stage;
public class MultiplayerHandler {
    
    Protocol protocol;
    Receiver receiver;
    Sender sender;
    public Thread receiveActivity;
    private boolean connected = false;
    private boolean gameStarted = false;
    private boolean cancel = false;
    
    // Singleton
    private static MultiplayerHandler inst = new MultiplayerHandler();
    private MultiplayerHandler() { }
    public static MultiplayerHandler getInstance() { return inst; }
    
    public void init(String hostname, int remoteport, int localport) {
        protocol = new Protocol();
        sender = new Sender(hostname, remoteport);
        receiver = new Receiver(localport);
        cancel = false;
        connected = false;
        gameStarted = false;

        receiveActivity = new Thread(receiver);
        receiveActivity.start();         
    }

    public void send(String action, int x, int y) {
        sender.send(protocol.sendPrep(action, x, y));
    }
    
    public void send(String action, int id, int healt, boolean alive) {
        sender.send(protocol.sendPrep(action, id, healt, alive));
    }
    
    public void recieveProtocol(DataInputStream input) {
        protocol.recieve(input);
    }
    
    public Protocol getProtocol() {
        return this.protocol;
    }
    
    protected void updateEnemies(int id, int health, boolean alive) {
        // System.out.println("trying to find enemyid to apply update");
        for (Iterator<Enemy> it = gs.enemies.iterator(); it.hasNext();) {
            Enemy enemy = it.next();
            if (enemy.getID() == id) {
                if(health < enemy.getHealth()) {
                    enemy.setHealth(health);
                    System.out.println("Setting health to " + health);
                }
                if(!alive && enemy.isAlive()) {
                    enemy.isDead();
                    if (enemy instanceof Asteroid) {
                        GameController.getInstance().spawnSmallAsteroids(enemy.getX(), enemy.getY());
                    }
                }
            }
        }
    }
    
    Thread thread = new Thread(new Runnable() {
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
    
    public void establishConnection() {
        setConnected(true);
        thread.interrupt();
        if (!gameStarted) {
            gameStarted = true;
            MultiplayerView.getInst().startMultiplayerGame(stage);
        }
    }
    
    public void replyConnection() {
        sender.send(protocol.sendPrep("Reply", 0, 0));
    }
    
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    
    public boolean getConnected() {
        return this.connected;
    }
    
    public void disconnect() {
        setConnected(false);
        GameModel.getInstance().setMultiplayerStatus(false);
        gs.player2.unsetSprite();
        sender.closeSocket();
    }
    
    public void cancelConnectAttempt() {
        thread.interrupt();
        cancel = true;
        setConnected(false);
        GameModel.getInstance().setMultiplayerStatus(false);
        sender.closeSocket();
    }
    
    public void setCancel(boolean state) {
        this.cancel = state;
    }
    
}
