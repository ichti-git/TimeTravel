/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author ichti (Simon T)
 */
public class NoFlightsFoundException extends ApiException {
    /**
     * Creates a new instance of <code>NotFoundException</code> without detail
     * message.
     */
    public NoFlightsFoundException() {
    }

    /**
     * Constructs an instance of <code>NotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoFlightsFoundException(String msg) {
        super(msg);
        httpError = 400;
        errorCode = 1;
    }
}
