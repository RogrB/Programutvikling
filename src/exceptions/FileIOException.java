package exceptions;

import view.ViewUtil;

/**
 * <h1>Exception for file inputs and outputs.</h1>
 * The class {@code FileIOException} extends the class {@code Exception}
 * and is thrown during reading and writing to files.
 *
 * <p><b>Note: </b>Whenever this class is thrown, the error is also
 * printed out to the user for them to know what has gone wrong.
 *
 * @author  Åsmund Røst Wien
 */
public class FileIOException extends Exception {

    /**
     * This constructor is called whenever a message is passed
     * through the exception. This message will be displayed
     * to the user.
     * @param message The message to be passed to the user.
     */
    public FileIOException(String message){
        super(message);
        System.err.println(message);
        ViewUtil.setError(message);
    }

}
