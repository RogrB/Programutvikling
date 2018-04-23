package multiplayer;

import java.io.*;
import model.player.Player;
import model.player.Player2;

public class Protocol {
    
    private Player2 player2 = new Player2();
    
    protected synchronized ByteArrayOutputStream sendPrep(String input) {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(bytestream);    

        try {
        stream.writeBytes(input);
        /*
        if(toSend.equals("New Game") {
         // do new game things
        }
        stream.writeInt(1);
        stream.writeChar(a); */

        stream.flush();
        stream.close();        
        }
        catch (IOException e) {
            System.err.println(e);
        }
        return bytestream;
    }
    
    protected synchronized ByteArrayOutputStream sendPrep(Player player) {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(bytestream);    

        try {
            stream.writeChar('M');
            stream.writeInt(player.getX());
            stream.writeChar('b');
            stream.writeInt(player.getY());
        /*
        stream.writeBytes(toSend);

        if(toSend.equals("New Game") {
         // do new game things
        }
        stream.writeInt(1);
        stream.writeChar(a);
*/
        stream.flush();
        stream.close();   
        
        }
        catch (IOException e) {
            System.err.println(e);
        }
        return bytestream;
    }   
    
    protected synchronized ByteArrayOutputStream sendPrep(String action, int x, int y) {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(bytestream);          
        
        switch(action) {
            case "Update":
                try {
                    stream.writeChar('M');
                    stream.writeInt(x);
                    stream.writeChar('b');
                    stream.writeInt(y);

                    stream.flush();
                    stream.close();
                }
                catch (IOException e) {
                    System.err.println(e);
                }
                break;
        }
        return bytestream;
    }
    
    public void recieve(DataInputStream input) {
        System.out.println("recieving");
        char breaker = 'b';
        /*
        try {
        System.out.println(input.readLine());
        }
        catch (IOException e) {
            System.out.println(e);
        }*/
        try {
            // System.out.println(input);
            char action = input.readChar();
            if(action == 'M') {
                player2.setX(input.readInt());
                breaker = input.readChar();
                player2.setY(input.readInt());
                System.out.println("Updating player2");
            }
            else {
                System.out.println("neida");
            }
            if(action == 'O') {
                System.out.println("O detected");
                int number = input.readInt();
                System.out.println("Recieved number " + number);
                System.out.println("Recieved char " + input.readChar());
            }
        }
        catch(IOException e) {
            System.err.println(e);
        }
    }
    
    public Player2 getPlayer2() {
        return this.player2;
    }
    
}
