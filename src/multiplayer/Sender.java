package multiplayer;

import java.net.*;
import java.io.*;
import view.GameView;
import view.MultiplayerView;
import view.ViewUtil;

/**
 * <h1>Sends data transmissions to Player2</h1>
 * This class sends data packets to a specified InetAdress and port.
 * @author Roger Birkenes Solli
 */
public class Sender {
    
    /**
     * DatagramSocket - to send and receive datagram packets
     */      
    private DatagramSocket socket;
    
    /**
     * Specifies the port to send packets to
     */      
    private int remotePort;
    
    /**
     * Specifies the InetAdress to send packets to
     */      
    private InetAddress hostname;
    
    /**
     * <b>Constructor: </b>creates a DatagramSocket that
     * is ready to send packets to the specified port and InetAdress
     * @param hostname InetAdress to send to
     * @param remoteport port to send to
     */     
    Sender(String hostname, int remoteport) {
        try {
            this.hostname = InetAddress.getByName(hostname);
        }
        catch (UnknownHostException e) {
            System.err.println(e);
            ViewUtil.setError("Unknown Hostname: " + hostname);
            MultiplayerHandler.getInstance().cancelConnectAttempt();
        }
        this.remotePort = remoteport;
        
        try {
            socket = new DatagramSocket();
        }
        catch(SocketException e) {
            System.err.println(e);
            ViewUtil.setError(e.toString());
        }
    }
    
    /**
     * Method to close the DatagramSocket
     */        
    public void closeSocket() {
        
        try {
            socket.close();
        }
        catch(Exception e) {
            System.err.println(e);
            ViewUtil.setError(e.toString());
        }
    }       
    
    /**
     * Method to send the data to Player2
     * @param stream The encoded data from {@code Protocol} to be sent
     */        
    synchronized void send(ByteArrayOutputStream stream) {
        try {
            byte[]data = stream.toByteArray();
            DatagramPacket packet = new DatagramPacket(data, data.length, hostname, remotePort);
            socket.send(packet);            
        }
        catch(IOException e) {
            if (e instanceof UnknownHostException) {
                ViewUtil.setError("Unknown Hostname: " + hostname);
                System.err.println(e);
            }
            else {
                System.err.println(e);
                ViewUtil.setError(e.toString());
            }
        }
    }
}
