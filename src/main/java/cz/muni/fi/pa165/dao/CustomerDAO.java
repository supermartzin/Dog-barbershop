package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Customer;

import java.util.List;

/**
 * Data access object for retrieving {@link Customer} objects from database.
 *
 * @author Martin Vrábel
 * @version 24.10.2016 20:55
 */
public interface CustomerDAO {

    /**
     * Creates new entry in database from provided {@link Customer} object
     *
     * @param customer  {@link Customer} object to save
     */
    void create(Customer customer);

    /**
     * Retrieves a {@see Customer} object with provided <b>ID</b> from database
     *
     * @param id    <b>ID</b> number of {@link Customer} to retrieve
     * @return      found {@link Customer} object or {@link null} if <b>ID</b> not found
     */
    Customer getById(long id);

    /**
     * Retrieves all {@link Customer} objects from database
     *
     * @return  list of all {@link Customer} objects from database
     */
    List<Customer> getAll();

    /**
     * Updates attributes of an existing {@link Customer} object in database
     *
     * @param customer  {@link Customer} object with updated attributes
     */
    void update(Customer customer);

    /**
     * Deletes an existing {@link Customer} entry from database
     *
     * @param customer  {@link Customer} object to delete from database
     */
    void delete(Customer customer);
}
