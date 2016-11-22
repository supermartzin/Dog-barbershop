package cz.muni.fi.pa165.validation;

import cz.muni.fi.pa165.exceptions.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Default implementation of {@link EntityValidator} class for validating constraints on entity classes.
 *
 * @author Martin Vrábel
 * @version 19.11.2016 17:39
 */
public class DefaultEntityValidator implements EntityValidator {

    private final Validator validator;

    public DefaultEntityValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void validate(T object) throws ValidationException {
        if (object == null)
            throw new IllegalArgumentException("object to validate is null");

        // validate
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);

        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<T> violation = constraintViolations.iterator().next();

            throw new ValidationException("Provided " + object.getClass().getSimpleName() + " object is not valid: '"
                    + violation.getPropertyPath().toString() + "' -> " + violation.getMessage());
        }
    }
}
