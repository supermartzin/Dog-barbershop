package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.exceptions.DAOException;

import java.util.List;

/**
 * Service for retrieving {@link Customer} objects.
 *
 * @author Martin Vrábel
 * @version 24.10.2016 20:55
 */
public interface CustomerService {

    /**
     * Creates new entry from provided {@link Customer} object.
     *
     * @param customer                  {@link Customer} object to save
     * @throws IllegalArgumentException when {@code customer} is {@code null}
     * @throws DAOException             when problem happens on underlying layer
     */
    void create(Customer customer) throws DAOException;

    /**
     * Retrieves a {@link Customer} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link Customer} to retrieve
     * @return                          found {@link Customer} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException when {@code id} is a negative number
     * @throws DAOException             when problem happens on underlying layer
     */
    Customer getById(long id);

    /**
     * Retrieves a {@link Customer} object which has provided <b>username</b>.
     *
     * @param username                  username of {@link Customer} to retrieve
     * @return                          found {@link Customer} object or {@link null} if <b>username</b> not found
     * @throws IllegalArgumentException when {@code username} is {@code null
     * @throws DAOException             when problem happens on underlying layer
     */
    Customer getByUsername(String username) throws DAOException;

    /**
     * Retrieves all {@link Customer} objects.
     *
     * @return              list with all {@link Customer} objects or <b>empty list</b> if there are no entries
     * @throws DAOException             when problem happens on underlying layer
     */
    List<Customer> getAll() throws DAOException;

    /**
     * Updates attributes of an existing {@link Customer} object.
     *
     * @param customer                  {@link Customer} object with updated attributes
     * @throws IllegalArgumentException when {@code customer} is {@code null}
     * @throws DAOException             when problem happens on underlying layer
     */
    void update(Customer customer) throws DAOException;

    /**
     * Deletes an existing {@link Customer} entry from database.
     *
     * @param customer                  {@link Customer} object to delete from database
     * @throws IllegalArgumentException when {@code customer} is {@code null}
     * @throws DAOException             when problem happens on underlying layer
     */
    void delete(Customer customer) throws DAOException;
}
