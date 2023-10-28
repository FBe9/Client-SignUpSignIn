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
public class WrongPasswordFormatException extends Exception {

    /**
     * Creates a new instance of <code>WrongPasswordFormatException</code>
     * without detail message.
     */
    public WrongPasswordFormatException() {
    }

    /**
     * Constructs an instance of <code>WrongPasswordFormatException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public WrongPasswordFormatException(String msg) {
        super(msg);
    }
}
