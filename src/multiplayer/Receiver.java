package multiplayer;

import java.net.*;
import java.io.*;

import view.ViewUtil;

/**
 * <h1>Receives data transmissions from Player2</h1>
 * This class listens to and receives data packets on a specified port.
 * Since the Receiver is constantly listening to packets it runs on its own thread
 * 
 * @author Roger Birkenes Solli
 */
public class Receiver extends Thread {

    /**
     * DatagramSocket - to send and receive datagram packets
     */    
    private DatagramSocket socket;
    
    /**
     * If the receiver is active and accepting packets
     */    
    private boolean looping = false;
    
    /**
     * Max packet size
     */    
    private final static int MAX_PACKET_SIZE = 8192;
    
    /**
     * {@code Protocol} object to interpret received data
     * @see Protocol
     */    
    private Protocol protocol = new Protocol();

    /**
     * <b>Constructor: </b>creates a DatagramSocket that listens
     * to packets on the specified port.
     * @param localPort specifies port
     */    
    Receiver(int localPort) {
        try {
            socket = new DatagramSocket(localPort);
            looping = true;
        } 
        catch(SocketException e) {
            System.err.println(e.getMessage());
            ViewUtil.setError("Could not bind to port: " + localPort);
        }
    }

    /**
     * Method for closing the DatagramSocket
     */    
    public void closeSocket() {
        try {
            socket.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            ViewUtil.setError("Failed to close ReceiverSocket");
        }
    }    

    /**
     * Method to receive DatagramPackets and
     * send them to the {@code Protocol} to be interpreted and processed
     */    
    @Override
    public void run() {
        byte[] buffer = new byte[MAX_PACKET_SIZE];
        while(looping) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                InputStream in = new ByteArrayInputStream(packet.getData(), packet.getOffset(), packet.getLength());
                try (DataInputStream stream = new DataInputStream(in)) {
                    protocol.recieve(stream);
                }
            } 
            catch(IOException e) {
                System.err.println(e.getMessage());
                ViewUtil.setError("Failed to receive datagram packet");
            }
        } 
    }

    /**
     * @param looping sets looping variable
     * if the receiver is active or not
     */    
    public void setLooping(boolean looping) {
        this.looping = looping;
    }
}