package cz.muni.fi.pa165.exceptions;

/**
 * Exception thrown if any error occurs on Service layer
 *
 * @author Martin Vrábel
 * @version 16.12.2016 0:18
 */
public class ServiceException extends Exception {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
