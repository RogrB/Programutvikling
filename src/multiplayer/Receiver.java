package multiplayer;


import java.net.*;
import java.io.*;

public class Receiver extends Thread {

    private int localPort;
    private DatagramSocket socket;
    private boolean looping = false;
    private final static int MAX_PACKET_SIZE = 8192;
    private Protocol protocol = new Protocol();

    
    public Receiver(int localPort) {
        this.localPort = localPort;

        try {
            socket = new DatagramSocket(localPort);
            looping = true;
        } 
        catch(SocketException e) {
            System.err.println(e);
            // Trenger en måte å outputte feilmelding i UIen
        }
    }
    
    public Receiver() {
        this.localPort = 2001;
        
        try {
            socket = new DatagramSocket(localPort);
            looping = true;
        } 
        catch(SocketException e) {
            System.err.println(e);
            // Trenger en måte å outputte feilmelding i UIen
        }        
    }
    
    public void closeSocket() {
        try {
            socket.close();
        }
        catch(Exception e){
            System.err.println(e);
        }
    }    

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
                System.err.println(e);
            }
        } 
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }
}