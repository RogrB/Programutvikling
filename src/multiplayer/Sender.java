package multiplayer;

import java.net.*;
import java.io.*;

public class Sender {
    
    private DatagramSocket socket;
    private int remotePort;
    private InetAddress hostname;
    
    public Sender(String hostname, int remoteport) {
        try {
            this.hostname = InetAddress.getByName(hostname);
        }
        catch (UnknownHostException e) {
            System.err.println(e);
        }
        this.remotePort = remoteport;
        
        try {
            socket = new DatagramSocket();
        }
        catch(SocketException e) {
            System.err.println(e);
        }
    }     
    
    public Sender() {
        this.remotePort = 2000;
        try {
            String address = "localhost";
            this.hostname = InetAddress.getByName(address);        
        }
        catch (UnknownHostException e) {
            System.err.println(e);
        }
        
        try {
            socket = new DatagramSocket();
        }
        catch(SocketException e) {
            System.err.println(e);
        }        
    }
    
    public void closeSocket() {
        
        try {
            socket.close();
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }       
    

    protected synchronized void sendPrep(String toSend) {
    
        try {
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            DataOutputStream stream = new DataOutputStream(bytestream);
            
            //stream.writeBytes(toSend); // String gir IOException
            /*
            if(toSend.equals("New Game") {
             // do new game things
            }*/
            stream.writeChar('O');
            stream.writeInt(1);
            stream.writeChar('a');

            stream.flush();
            stream.close();
            send(bytestream);
        } 
        catch(IOException e) {
            System.err.println(e);
        } 
    } 
    
    protected synchronized void send(ByteArrayOutputStream stream) {
        try {
            byte[]data = stream.toByteArray();
            DatagramPacket packet = new DatagramPacket(data, data.length, hostname, remotePort);
            socket.send(packet);            
        }
        catch(IOException e) {
            System.err.println(e);
        } 
    }
}
