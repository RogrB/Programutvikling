package multiplayer;

import java.io.DataInputStream;
import model.GameModel;
import model.player.Player;

public class MultiplayerHandler {
    
    Protocol protocol;
    Receiver receiver;
    Sender sender;
    public Thread receiveActivity;
    
    public MultiplayerHandler(String hostname, int remoteport, int localport) {
        protocol = new Protocol();
        sender = new Sender(hostname, remoteport);
        receiver = new Receiver(localport);

        receiveActivity = new Thread(receiver);
        receiveActivity.start();      
        
    }
    
    public MultiplayerHandler() {
        protocol = new Protocol();
        receiver = new Receiver();
        sender = new Sender();
        
        receiveActivity = new Thread(receiver);
        receiveActivity.start();              
    }
    
    public void send(String toSend) {   
        sender.sendPrep(toSend);
    }
    
    public void send(Player player) {
        sender.send(protocol.sendPrep(player));
    }
    
    public void send(String action, int x, int y) {
        sender.send(protocol.sendPrep(action, x, y));
    }
    
    public void recieveProtocol(DataInputStream input) {
        protocol.recieve(input);
    }
    
    public Protocol getProtocol() {
        return this.protocol;
    }
    
}
