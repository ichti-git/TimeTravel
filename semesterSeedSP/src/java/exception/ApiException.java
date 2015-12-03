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
public class ApiException extends Exception {
    int httpError;
    int errorCode;
    /**
     * Creates a new instance of <code>NotFoundException</code> without detail
     * message.
     */
    public ApiException() {
    }

    /**
     * Constructs an instance of <code>NotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ApiException(String msg) {
        super(msg);
    }
    

    public int getHttpError() {
        return httpError;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
