package cz.muni.fi.pa165.service;

import java.util.Collection;
import java.util.List;

/**
 * Service for mapping between Entity and DTO objects.
 *
 * @author Martin Vrábel
 * @version 11.11.2016 12:48
 */
public interface BeanMappingService {

    /**
     * Maps provided collection of objects to collection of specified {@link Class} instances.
     *
     * @param objects       objects to map
     * @param mapToClass    {@link Class} to which map the objects
     * @param <T>           mapping type
     * @return              collection of mapped objects or {@code null} if provided {@code objects} parameter is null
     */
    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    /**
     * Maps provided object to specified {@link Class} instance.
     *
     * @param object        object to map
     * @param mapToClass    {@link Class} to which map the object
     * @param <T>           mapping type
     * @return              mapped object or {@code null} if provided {@code object} parameter is null
     */
    <T> T mapTo(Object object, Class<T> mapToClass);
}
