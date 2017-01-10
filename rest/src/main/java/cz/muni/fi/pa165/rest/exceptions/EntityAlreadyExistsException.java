package cz.muni.fi.pa165.rest.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {

    private final String entityName;

    public EntityAlreadyExistsException(String entityName) {
        super();

        this.entityName = entityName;
    }

    public EntityAlreadyExistsException(String entityName, String message) {
        super(message);

        this.entityName = entityName;
    }

    public EntityAlreadyExistsException(String entityName, String message, Throwable cause) {
        super(message, cause);

        this.entityName = entityName;
    }

    public EntityAlreadyExistsException(String entityName, Throwable cause) {
        super(cause);

        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
