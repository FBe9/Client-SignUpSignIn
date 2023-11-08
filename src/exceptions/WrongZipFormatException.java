package exceptions;

/**
 * Class for wrong zip format exception.
 * @author irati
 */
public class WrongZipFormatException extends Exception{
     /**
     * Creates a new instance of <code>WrongEmailFormatException</code> without
     * detail message.
     */
    public WrongZipFormatException () {
    }

    /**
     * Constructs an instance of <code>WrongEmailFormatException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WrongZipFormatException (String msg) {
        super(msg);
    }
}
