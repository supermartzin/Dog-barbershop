package cz.muni.fi.pa165.exceptions;

/**
 * Exception thrown when validation on entities fails with constraint violation.
 *
 * @author Martin Vrábel
 * @version 19.11.2016 17:36
 */
public class ValidationException extends Exception {

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
