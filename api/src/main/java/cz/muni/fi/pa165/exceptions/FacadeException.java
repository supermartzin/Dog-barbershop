package cz.muni.fi.pa165.exceptions;

/**
 * Exception thrown if any error occurs on Facade layer
 *
 * @author Martin Vrábel
 * @version 16.12.2016 0:52
 */
public class FacadeException extends Exception {

    public FacadeException() {
    }

    public FacadeException(String message) {
        super(message);
    }

    public FacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacadeException(Throwable cause) {
        super(cause);
    }
}
