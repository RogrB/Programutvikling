package exceptions;


import view.ViewUtil;

public class FileIOException extends Exception {

    public FileIOException(String message){
        super(message);
        ViewUtil.setError(message);
    }

}
