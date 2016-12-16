package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;

import java.util.List;

/**
 * Facade for processing {@link DogDTO} objects.
 *
 * @author Dominik Gmiterko
 */
public interface DogFacade {

    /**
     * Creates new entry from provided {@link DogDTO} object.
     *
     * @param dogDTO                    {@link DogDTO} object to create
     * @throws IllegalArgumentException when {@code dogDTO} parameter is {@code null}
     * @throws FacadeException          in case of any underlaying error during creation of {@link DogDTO} object
     */
    void create(DogDTO dogDTO) throws FacadeException;

    /**
     * Retrieves a {@link DogDTO} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link DogDTO} to retrieve
     * @return                          found {@link DogDTO} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException when <b>ID</b> is negative number
     * @throws FacadeException          in case of any underlaying error during retrieving {@link DogDTO} object
     */
    DogDTO getById(long id) throws FacadeException;

    /**
     * Retrieves all {@link DogDTO} objects.
     *
     * @return                          list with all {@link DogDTO} objects
     *                                  or <b>empty list</b> if there is no entry in database
     * @throws FacadeException          in case of any underlaying error during retrieving {@link DogDTO} objects
     */
    List<DogDTO> getAll() throws FacadeException;

    /**
     * Updates attributes of an existing {@link DogDTO} object.
     *
     * @param dogDTO                    {@link DogDTO} object with updated attributes
     * @throws IllegalArgumentException when {@code dogDTO} parameter is {@code null}
     * @throws FacadeException          in case of any underlaying error during updating {@link DogDTO} object
     */
    void update(DogDTO dogDTO) throws FacadeException;

    /**
     * Deletes an existing {@link DogDTO} object.
     *
     * @param dogDTO                    {@link DogDTO} object to delete
     * @throws IllegalArgumentException when {@code dogDTO} parameter is {@code null}
     * @throws FacadeException          in case of any underlaying error during deleting {@link DogDTO} object
     */
    void delete(DogDTO dogDTO) throws FacadeException;
}
