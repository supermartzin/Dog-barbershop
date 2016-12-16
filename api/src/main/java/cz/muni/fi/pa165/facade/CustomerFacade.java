package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;

import java.util.List;

/**
 * Facade for processing {@link CustomerDTO} objects.
 *
 * @author Martin Vrábel
 * @version 24.10.2016 20:55
 */
public interface CustomerFacade {

    /**
     * Creates new entry from provided {@link CustomerDTO} object.
     *
     * @param customerDTO               {@link CustomerDTO} object to save
     * @throws IllegalArgumentException when {@code customerDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during creation of {@link CustomerDTO} object
     */
    void create(CustomerDTO customerDTO) throws FacadeException;

    /**
     * Retrieves a {@link CustomerDTO} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link CustomerDTO} to retrieve
     * @return                          found {@link CustomerDTO} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException when {@code id} is a negative number
     * @throws FacadeException          in case of any underlaying error during retrieving {@link CustomerDTO} object
     */
    CustomerDTO getById(long id) throws FacadeException;

    /**
     * Retrieves a {@link CustomerDTO} object which has provided <b>username</b>.
     *
     * @param username                  username of {@link CustomerDTO} to retrieve
     * @return                          found {@link CustomerDTO} object or {@link null} if <b>username</b> not found
     * @throws IllegalArgumentException when {@code username} is {@code null}
     * @throws FacadeException          in case of any underlaying error during retrieving {@link CustomerDTO} object
     */
    CustomerDTO getByUsername(String username) throws FacadeException;

    /**
     * Retrieves all {@link CustomerDTO} objects.
     *
     * @return                  list with all {@link CustomerDTO} objects or <b>empty list</b> if there are no entries
     * @throws FacadeException  in case of any underlaying error during retrieving {@link CustomerDTO} objects
     */
    List<CustomerDTO> getAll() throws FacadeException;

    /**
     * Updates attributes of an existing {@link CustomerDTO} object.
     *
     * @param customerDTO               {@link CustomerDTO} object with updated attributes
     * @throws IllegalArgumentException when {@code customerDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during updating {@link CustomerDTO} object
     */
    void update(CustomerDTO customerDTO) throws FacadeException;

    /**
     * Deletes an existing {@link CustomerDTO} entry from database.
     *
     * @param customerDTO               {@link CustomerDTO} object to delete
     * @throws IllegalArgumentException when {@code customerDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during deleting {@link CustomerDTO} object
     */
    void delete(CustomerDTO customerDTO) throws FacadeException;
}
