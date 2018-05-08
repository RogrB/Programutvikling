package exceptions;

import view.GameView;

public class FileIOException extends Exception {

    public FileIOException(String message){
        super(message);
        GameView.getInstance().getField().changeText(message);
    }
    
}
