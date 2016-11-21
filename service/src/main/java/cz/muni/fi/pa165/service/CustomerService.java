package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Customer;

import java.util.List;

/**
 * Data access object for retrieving {@link Customer} objects from database.
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
     */
    void create(Customer customer);

    /**
     * Retrieves a {@link Customer} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link Customer} to retrieve
     * @return                          found {@link Customer} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException when {@code id} is a negative number
     */
    Customer getById(long id);

    /**
     * Retrieves a {@link Customer} object which has provided <b>username</b>.
     *
     * @param username                  username of {@link Customer} to retrieve
     * @return                          found {@link Customer} object or {@link null} if <b>username</b> not found
     * @throws IllegalArgumentException when {@code username} is {@code null
     */
    Customer getByUsername(String username);

    /**
     * Retrieves all {@link Customer} objects.
     *
     * @return              list with all {@link Customer} objects or <b>empty list</b> if there are no entries
     */
    List<Customer> getAll();

    /**
     * Updates attributes of an existing {@link Customer} object.
     *
     * @param customer                  {@link Customer} object with updated attributes
     * @throws IllegalArgumentException when {@code customer} is {@code null}
     */
    void update(Customer customer);

    /**
     * Deletes an existing {@link Customer} entry from database.
     *
     * @param customer                  {@link Customer} object to delete from database
     * @throws IllegalArgumentException when {@code customer} is {@code null}
     */
    void delete(Customer customer);
}
