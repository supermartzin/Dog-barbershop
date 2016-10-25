package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Service;

import java.util.List;

/**
 * Data access object for retrieving Dog objects from database.
 *
 * @author Martin Vrábel
 * @version 23.10.2016 20:35
 */
public interface ServiceDAO {

    /**
     * Creates new entry in database from provided {@link Service} object
     *
     * @param service   {@link Service} object to save
     */
    void create(Service service);

    /**
     * Retrieves a {@see Service} object with provided <b>ID</b> from database
     *
     * @param id    <b>ID</b> number of {@link Service} to retrieve
     * @return      found {@link Service} object or {@link null} if <b>ID</b> not found
     */
    Service getById(long id);

    /**
     * Retrieves all {@link Service} objects from database
     *
     * @return  list of all {@link Service} objects from database
     */
    List<Service> getAll();

    /**
     * Updates attributes of an existing {@link Service} object in database
     *
     * @param customer  {@link Service} object with updated attributes
     */
    void update(Service customer);

    /**
     * Deletes an existing {@link Service} entry from database
     *
     * @param customer  {@link Service} object to delete from database
     */
    void delete(Service customer);
}
