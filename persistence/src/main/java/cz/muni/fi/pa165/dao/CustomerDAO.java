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
     * Creates new entry in database from provided {@link Customer} object.
     *
     * @param customer                  {@link Customer} object to save
     * @throws IllegalArgumentException when {@code customer} is {@code null}
     * @throws DAOException             when {@code customer} is in invalid state or the object cannot be saved to database
     */
    void create(Customer customer) throws DAOException;

    /**
     * Retrieves a {@link Customer} object with provided <b>ID</b> from database.
     *
     * @param id                        <b>ID</b> number of {@link Customer} to retrieve
     * @return                          found {@link Customer} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException when {@code id} is a negative number
     */
    Customer getById(long id);

    /**
     * Retrieves a {@link Customer} object which has provided <b>username</b> from database.
     *
     * @param username                  username of {@link Customer} to retrieve
     * @return                          found {@link Customer} object or {@link null} if <b>username</b> not found
     * @throws IllegalArgumentException when {@code username} is {@code null}
     * @throws DAOException             when {@link Customer} object cannot be retrieved from database
     */
    Customer getByUsername(String username) throws DAOException;

    /**
     * Retrieves all {@link Customer} objects from database.
     *
     * @return              list with all {@link Customer} objects from database
     *                      or <b>empty list</b> if there is no entry in database
     * @throws DAOException when {@link Customer} objects cannot be retrieved from database
     */
    List<Customer> getAll() throws DAOException;

    /**
     * Updates attributes of an existing {@link Customer} object in database.
     *
     * @param customer                  {@link Customer} object with updated attributes
     * @throws IllegalArgumentException when {@code customer} is {@code null}
     * @throws DAOException             when {@code customer} is in invalid state or the object cannot be updated in database
     */
    void update(Customer customer) throws DAOException;

    /**
     * Deletes an existing {@link Customer} entry from database.
     *
     * @param customer                  {@link Customer} object to delete from database
     * @throws IllegalArgumentException when {@code customer} is {@code null}
     * @throws DAOException             when {@code customer} does not exist or the object cannot be removed from database
     */
    void delete(Customer customer) throws DAOException;
}
