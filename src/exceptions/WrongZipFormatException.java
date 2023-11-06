/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
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
