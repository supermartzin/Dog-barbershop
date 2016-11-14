package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;

import java.util.List;

/**
 * Data access object for retrieving Dog objects from database.
 *
 * @author Dominik Gmiterko
 */
public interface ServiceDAO {

    /**
     * Creates new entry in database from provided {@link Service} object
     *
     * @param service {@link Service} object to save
     * @throws DAOException When some problem with database occurs
     */
    void create(Service service) throws DAOException;

    /**
     * Retrieves a {@see Service} object with provided <b>ID</b> from database
     *
     * @param id <b>ID</b> number of {@link Service} to retrieve
     * @return found {@link Service} object or {@link null} if <b>ID</b> not found
     */
    Service getById(long id);

    /**
     * Retrieves all {@link Service} objects from database
     *
     * @return found {@link Service} object or {@link null} if <b>ID</b> not found
     */
    List<Service> getAll() throws DAOException;

    /**
     * Updates attributes of an existing {@link Service} object in database
     *
     * @param customer {@link Service} object with updated attributes
     * @return found {@link Service} object or {@link null} if <b>ID</b> not found
     */
    void update(Service customer) throws DAOException;

    /**
     * Deletes an existing {@link Service} entry from database
     *
     * @param customer {@link Service} object to delete from database
     * @return found {@link Service} object or {@link null} if <b>ID</b> not found
     */
    void delete(Service customer) throws DAOException;
}
