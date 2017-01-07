package cz.muni.fi.pa165.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason="The resource already exists")
public class ResourceAlreadyExistingException extends RuntimeException {

    public ResourceAlreadyExistingException() {
        super();
    }

    public ResourceAlreadyExistingException(String message) {
        super(message);
    }

    public ResourceAlreadyExistingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceAlreadyExistingException(Throwable cause) {
        super(cause);
    }
}
