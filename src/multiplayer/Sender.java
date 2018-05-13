package multiplayer;

import java.net.*;
import java.io.*;
import view.GameView;
import view.MultiplayerView;
import view.ViewUtil;

public class Sender {
    
    private DatagramSocket socket;
    private int remotePort;
    private InetAddress hostname;
    
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
    
    public void closeSocket() {
        
        try {
            socket.close();
        }
        catch(Exception e) {
            System.err.println(e);
            ViewUtil.setError(e.toString());
        }
    }       
    
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
