package multiplayer;

import controller.GameController;
import java.io.DataInputStream;
import model.GameModel;
import model.enemy.Enemy;
import model.player.Player;

public class MultiplayerHandler {
    
    Protocol protocol;
    Receiver receiver;
    Sender sender;
    public Thread receiveActivity;
    
    // Singleton
    private static MultiplayerHandler inst = new MultiplayerHandler();
    private MultiplayerHandler() { }
    public static MultiplayerHandler getInstance() { return inst; }
    
    public void init(String hostname, int remoteport, int localport) {
        protocol = new Protocol();
        sender = new Sender(hostname, remoteport);
        receiver = new Receiver(localport);

        receiveActivity = new Thread(receiver);
        receiveActivity.start();         
    }
    
    public void send(String toSend) {   
        sender.sendPrep(toSend);
    }
    
    /*
    public void send(Player player) {
        sender.send(protocol.sendPrep(player));
    }*/
    
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
        System.out.println("trying to find enemyid to apply update");
        for(Enemy enemy: GameController.getInstance().getEnemies()) {        
            if (enemy.getID() == id) {
                System.out.println("applying enemy update to enemy " + id);
                if(health < enemy.getHealth()) {
                    enemy.setHealth(health);
                }
                if(!alive && enemy.isAlive()) {
                    enemy.isDead();
                }
            }
        }
    }
    
}
