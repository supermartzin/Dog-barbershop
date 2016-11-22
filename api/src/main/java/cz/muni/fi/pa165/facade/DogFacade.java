package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.entities.Customer;

import java.util.List;

/**
 * Facade for acessing {@link DogDTO} objects.
 *
 * @author Dominik Gmiterko
 */
public interface DogFacade {

    /**
     * Creates new entry from provided {@link DogDTO} object.
     *
     * @param dog                       {@link DogDTO} object to save
     * @throws IllegalArgumentException when {@code dog} is {@code null}
     */
    void create(DogDTO dog);

    /**
     * Retrieves a {@see DogDTO} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link DogDTO} to retrieve
     * @return                          found {@link DogDTO} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException when {@code id} is negative number
     */
    DogDTO getById(long id);

    /**
     * Retrieves all {@link DogDTO} objects.
     *
     * @return              list with all {@link DogDTO} objects
     *                      or <b>empty list</b> if there is no entry in database
     */
    List<DogDTO> getAll();

    /**
     * Retrieves all {@link DogDTO} objects for {@link Customer}.
     *
     * @param customer      {@link Customer} object
     * @return              list with all {@link DogDTO} objects
     *                      or <b>empty list</b> if there is no entry in database
     */
    List<DogDTO> getByCustomer(Customer customer);

    /**
     * Updates attributes of an existing {@link DogDTO} object.
     *
     * @param dog                       {@link DogDTO} object with updated attributes
     * @throws IllegalArgumentException when {@code dog} is {@code null}
     */
    void update(DogDTO dog);

    /**
     * Deletes an existing {@link DogDTO} entry.
     *
     * @param dog                       {@link DogDTO} object to delete
     * @throws IllegalArgumentException when {@code dog} is {@code null}
     */
    void delete(DogDTO dog);
}
