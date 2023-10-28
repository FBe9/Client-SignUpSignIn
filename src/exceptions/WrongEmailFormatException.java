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
public class WrongEmailFormatException extends Exception {

    /**
     * Creates a new instance of <code>WrongEmailFormatException</code> without
     * detail message.
     */
    public WrongEmailFormatException() {
    }

    /**
     * Constructs an instance of <code>WrongEmailFormatException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WrongEmailFormatException(String msg) {
        super(msg);
    }
}
