package exceptions;

/**
 * Class for wrong city format exception.
 * @author irati
 */
public class WrongCityFormatException extends Exception {

    /**
     * Creates a new instance of <code>WrongEmailFormatException</code> without
     * detail message.
     */
    public WrongCityFormatException() {
    }

    /**
     * Constructs an instance of <code>WrongEmailFormatException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WrongCityFormatException(String msg) {
        super(msg);
    }
}
