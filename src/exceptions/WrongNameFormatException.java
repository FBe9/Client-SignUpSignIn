package exceptions;

/**
 * Class for Wrong name format exception.
 *
 * @author Nerea
 */
public class WrongNameFormatException extends Exception {

    /**
     * Creates a new instance of <code>WrongNameFormatException</code> without
     * detail message.
     */
    public WrongNameFormatException() {
    }

    /**
     * Constructs an instance of <code>WrongNameFormatException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WrongNameFormatException(String msg) {
        super(msg);
    }
}
