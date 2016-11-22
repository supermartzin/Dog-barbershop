package cz.muni.fi.pa165.validation;

import cz.muni.fi.pa165.exceptions.ValidationException;

/**
 * Validator for validating constraints on entity properties
 *
 * @author Martin Vrábel
 * @version 19.11.2016 17:41
 */
public interface EntityValidator {

    /**
     * Validates provided entity {@code object} properties and throws {@link ValidationException}
     * if any of the constraints is violated.
     *
     * @param object                entity object to validate
     * @param <T>                   type of provided entity {@code object}
     * @throws ValidationException  when constraints on entity are violated
     */
    <T> void validate(T object) throws ValidationException;
}
