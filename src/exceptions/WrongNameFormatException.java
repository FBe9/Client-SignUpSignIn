/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
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
