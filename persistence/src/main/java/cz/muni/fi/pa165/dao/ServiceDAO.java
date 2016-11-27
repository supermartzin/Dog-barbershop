package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;

import java.util.List;

/**
 * Data access object for retrieving {@link Service} objects from database.
 *
 * @author Dominik Gmiterko
 */
public interface ServiceDAO {

    /**
     * Creates new entry in database from provided {@link Service} object.
     *
     * @param service                   {@link Service} object to save
     * @throws IllegalArgumentException when {@code cz.muni.fi.pa165.facade.facade} is {@code null}
     * @throws DAOException             when {@code cz.muni.fi.pa165.facade.facade} is in invalid state or the object cannot be saved to database
     */
    void create(Service service) throws DAOException;

    /**
     * Retrieves a {@see Service} object with provided <b>ID</b> from database.
     *
     * @param id                        <b>ID</b> number of {@link Service} to retrieve
     * @return                          found {@link Service} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException if {@code id} is a negative number
     */
    Service getById(long id);

    /**
     * Retrieves all {@link Service} objects from database.
     *
     * @return              list with all {@link Service} objects from database
     *                      or <b>empty list</b> if there is no entry in database
     * @throws DAOException when {@link Service} objects cannot be retrieved from database
     */
    List<Service> getAll() throws DAOException;

    /**
     * Updates attributes of an existing {@link Service} object in database.
     *
     * @param service                   {@link Service} object with updated attributes
     * @throws IllegalArgumentException when {@code cz.muni.fi.pa165.facade.facade} is {@code null}
     * @throws DAOException             when {@code cz.muni.fi.pa165.facade.facade} is in invalid state or the object cannot be updated in database
     */
    void update(Service service) throws DAOException;

    /**
     * Deletes an existing {@link Service} entry from database.
     *
     * @param service                   {@link Service} object to delete from database
     * @throws IllegalArgumentException when {@code cz.muni.fi.pa165.facade.facade} is {@code null}
     * @throws DAOException             when {@code cz.muni.fi.pa165.facade.facade} does not exist or the object cannot be deleted from database
     */
    void delete(Service service) throws DAOException;
}
