package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;

import java.util.List;

/**
 * Service object for retrieving {@link Service} objects.
 *
 * @author Dominik Gmiterko
 */
public interface ServiceService {

    /**
     * Creates new entry from provided {@link Service} object.
     *
     * @param service                   {@link Service} object to save
     * @throws IllegalArgumentException when {@code facade} is {@code null}
     */
    void create(Service service) throws DAOException;

    /**
     * Retrieves a {@see Service} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link Service} to retrieve
     * @return                          found {@link Service} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException if {@code id} is a negative number
     */
    Service getById(long id);

    /**
     * Retrieves all {@link Service} objects.
     *
     * @return              list with all {@link Service} objects
     *                      or <b>empty list</b> if there are no entities
     */
    List<Service> getAll() throws DAOException;

    /**
     * Updates attributes of an existing {@link Service} object.
     *
     * @param service                   {@link Service} object with updated attributes
     * @throws IllegalArgumentException when {@code facade} is {@code null}
     */
    void update(Service service) throws DAOException;

    /**
     * Deletes an existing {@link Service} entry.
     *
     * @param service                   {@link Service} object to delete
     * @throws IllegalArgumentException when {@code facade} is {@code null}
     */
    void delete(Service service) throws DAOException;
}
