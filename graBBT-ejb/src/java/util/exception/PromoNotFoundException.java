/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Bryan
 */
public class PromoNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>UpdateOutletException</code> without
     * detail message.
     */
    public PromoNotFoundException() {
    }

    /**
     * Constructs an instance of <code>UpdateOutletException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PromoNotFoundException(String msg) {
        super(msg);
    }
}
