package multiplayer;


import java.net.*;
import java.io.*;

import view.MultiplayerView;
import view.GameView;

public class Receiver extends Thread {

    private DatagramSocket socket;
    private boolean looping = false;
    private final static int MAX_PACKET_SIZE = 8192;
    private Protocol protocol = new Protocol();

    
    Receiver(int localPort) {

        try {
            socket = new DatagramSocket(localPort);
            looping = true;
        } 
        catch(SocketException e) {
            System.err.println(e);
            // Trenger en måte å outputte feilmelding i UIen
            MultiplayerView.getInst().getField().changeText(e.toString());
            GameView.getInstance().getField().changeText(e.toString());
        }
    }

    public void closeSocket() {
        try {
            socket.close();
        }
        catch(Exception e){
            System.err.println(e);
            MultiplayerView.getInst().getField().changeText(e.toString());
            GameView.getInstance().getField().changeText(e.toString());            
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
                MultiplayerView.getInst().getField().changeText(e.toString());
                GameView.getInstance().getField().changeText(e.toString());                
            }
        } 
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }
}