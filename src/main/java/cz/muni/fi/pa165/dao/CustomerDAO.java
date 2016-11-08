package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.exceptions.DAOException;

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
     * @throws DAOException When some problem with database occurs
     */
    void create(Customer customer) throws DAOException;

    /**
     * Retrieves a {@link Customer} object with provided <b>ID</b> from database
     *
     * @param id    <b>ID</b> number of {@link Customer} to retrieve
     * @return      found {@link Customer} object or {@link null} if <b>ID</b> not found
     * @throws DAOException When some problem with database occurs
     */
    Customer getById(long id) throws DAOException;

    /**
     * Retrieves a {@link Customer} object which has provided <b>username</b> from database
     *
     * @param username Username of {@link Customer} to retrieve
     * @return found {@link Customer} object or {@link null} if <b>username</b> not found
     * @throws IllegalArgumentException for {@link null} or empty username
     * @throws DAOException When some problem with database occurs
     */
    Customer getByUsername(String username) throws DAOException;

    /**
     * Retrieves all {@link Customer} objects from database
     *
     * @return  list of all {@link Customer} objects from database
     * @throws DAOException When some problem with database occurs
     */
    List<Customer> getAll() throws DAOException;

    /**
     * Updates attributes of an existing {@link Customer} object in database
     *
     * @param customer  {@link Customer} object with updated attributes
     * @throws DAOException When some problem with database occurs
     */
    void update(Customer customer) throws DAOException;

    /**
     * Deletes an existing {@link Customer} entry from database
     *
     * @param customer  {@link Customer} object to delete from database
     * @throws DAOException When some problem with database occurs
     */
    void delete(Customer customer) throws DAOException;
}
