package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.ServiceException;

import java.util.List;

/**
 * Service object for working with {@link Service}s.
 *
 * @author Dominik Gmiterko
 */
public interface ServiceService {

    /**
     * Creates new entry from provided {@link Service} object.
     *
     * @param service                   {@link Service} object to save
     * @throws IllegalArgumentException when {@code service} is {@code null}
     * @throws ServiceException         when any error occurs during creation of {@link Service}
     */
    void create(Service service) throws ServiceException;

    /**
     * Retrieves a {@see Service} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link Service} to retrieve
     * @return                          found {@link Service} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException if {@code id} is a negative number
     * @throws ServiceException         if error occurs during retrieving {@link Service}
     */
    Service getById(long id) throws ServiceException;

    /**
     * Retrieves all {@link Service} objects.
     *
     * @return                  list with all {@link Service} objects
     *                          or <b>empty list</b> if there are no entities
     * @throws ServiceException when any error occurs getting all {@link Service}s in the system
     */
    List<Service> getAll() throws ServiceException;

    /**
     * Updates attributes of an existing {@link Service} object.
     *
     * @param service                   {@link Service} object with updated attributes
     * @throws IllegalArgumentException when provided {@link Service} is {@code null}
     * @throws ServiceException         when any error occurs during updating of {@link Service}
     */
    void update(Service service) throws ServiceException;

    /**
     * Deletes an existing {@link Service} entry.
     *
     * @param service                   {@link Service} object to delete
     * @throws IllegalArgumentException when provided {@link Service} is {@code null}
     * @throws ServiceException         when any error occurs during deletion of {@link Service}
     */
    void delete(Service service) throws ServiceException;
}
