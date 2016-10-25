package cz.muni.fi.pa165.exceptions;

/**
 * Exception for invalid or error states in <b>Data Access Object</b> classes
 *
 * @author Martin Vrábel
 * @version 25.10.2016 11:10
 */
public class DAOException extends Throwable {

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
