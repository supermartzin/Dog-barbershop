package cz.muni.fi.pa165.rest.exceptions;

public class EntityNotFoundException extends RuntimeException {

    private final String entityName;

    public EntityNotFoundException(String entityName) {
        this.entityName = entityName;
    }

    public EntityNotFoundException(String entityName, String message) {
        super(message);

        this.entityName = entityName;
    }

    public EntityNotFoundException(String entityName, String message, Throwable cause) {
        super(message, cause);

        this.entityName = entityName;
    }

    public EntityNotFoundException(String entityName, Throwable cause) {
        super(cause);

        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
