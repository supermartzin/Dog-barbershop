package cz.muni.fi.pa165.facade;


import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.exceptions.DAOException;

import java.util.List;

/**
 * Facade for acessing {@link CustomerDTO} objects.
 *
 * @author Martin Vrábel
 * @version 24.10.2016 20:55
 */
public interface CustomerFacade {

    /**
     * Creates new entry from provided {@link CustomerDTO} object.
     *
     * @param customer                  {@link CustomerDTO} object to save
     * @throws IllegalArgumentException when {@code customer} is {@code null}
     */
    void create(CustomerDTO customer) throws DAOException;

    /**
     * Retrieves a {@link CustomerDTO} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link CustomerDTO} to retrieve
     * @return                          found {@link CustomerDTO} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException when {@code id} is a negative number
     */
    CustomerDTO getById(long id);

    /**
     * Retrieves a {@link CustomerDTO} object which has provided <b>username</b>.
     *
     * @param username                  username of {@link CustomerDTO} to retrieve
     * @return                          found {@link CustomerDTO} object or {@link null} if <b>username</b> not found
     * @throws IllegalArgumentException when {@code username} is {@code null
     */
    CustomerDTO getByUsername(String username) throws DAOException;

    /**
     * Retrieves all {@link CustomerDTO} objects.
     *
     * @return              list with all {@link CustomerDTO} objects or <b>empty list</b> if there are no entries
     */
    List<CustomerDTO> getAll() throws DAOException;

    /**
     * Updates attributes of an existing {@link CustomerDTO} object.
     *
     * @param customer                  {@link CustomerDTO} object with updated attributes
     * @throws IllegalArgumentException when {@code customer} is {@code null}
     */
    void update(CustomerDTO customer) throws DAOException;

    /**
     * Deletes an existing {@link CustomerDTO} entry from database.
     *
     * @param customer                  {@link CustomerDTO} object to delete from database
     * @throws IllegalArgumentException when {@code customer} is {@code null}
     */
    void delete(CustomerDTO customer) throws DAOException;
}
