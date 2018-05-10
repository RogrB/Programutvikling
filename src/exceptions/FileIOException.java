package exceptions;


public class FileIOException extends Exception {

    public FileIOException(String message){
        super(message);
        //GameView.getInstance().getField().changeText(message);
    }

}
