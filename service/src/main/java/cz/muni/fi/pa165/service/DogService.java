package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;

import java.util.List;

/**
 * Service for retrieving {@link Dog} objects.
 *
 * @author Dominik Gmiterko
 */
public interface DogService {

    /**
     * Creates new entry from provided {@link Dog} object.
     *
     * @param dog                       {@link Dog} object to save
     * @throws IllegalArgumentException when {@code dog} is {@code null}
     */
    void create(Dog dog);

    /**
     * Retrieves a {@see Dog} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link Dog} to retrieve
     * @return                          found {@link Dog} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException when {@code id} is negative number
     */
    Dog getById(long id);

    /**
     * Retrieves all {@link Dog} objects.
     *
     * @return              list with all {@link Dog} objects
     *                      or <b>empty list</b> if there is no entry in database
     */
    List<Dog> getAll();

    /**
     * Retrieves all {@link Dog} objects for {@link Customer}.
     *
     * @param customer      {@link Customer} object
     * @return              list with all {@link Dog} objects
     *                      or <b>empty list</b> if there is no entry in database
     */
    List<Dog> getByCustomer(Customer customer);

    /**
     * Updates attributes of an existing {@link Dog} object.
     *
     * @param dog                       {@link Dog} object with updated attributes
     * @throws IllegalArgumentException when {@code dog} is {@code null}
     */
    void update(Dog dog);

    /**
     * Deletes an existing {@link Dog} entry.
     *
     * @param dog                       {@link Dog} object to delete
     * @throws IllegalArgumentException when {@code dog} is {@code null}
     */
    void delete(Dog dog);
}
