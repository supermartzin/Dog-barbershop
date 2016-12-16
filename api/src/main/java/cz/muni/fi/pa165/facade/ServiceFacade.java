package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.ServiceDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;

import java.util.List;

/**
 * Facade for processing {@link ServiceDTO} objects.
 *
 * @author Dominik Gmiterko
 */
public interface ServiceFacade {

    /**
     * Creates new entry from provided {@link ServiceDTO} object.
     *
     * @param service                   {@link ServiceDTO} object to save
     * @throws IllegalArgumentException when provided {@code ServiceDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during creation of {@link ServiceDTO} object
     */
    void create(ServiceDTO service) throws FacadeException;

    /**
     * Retrieves a {@see ServiceDTO} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link ServiceDTO} to retrieve
     * @return                          found {@link ServiceDTO} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException if {@code id} is a negative number
     * @throws FacadeException          in case of any underlaying error during retrieving {@link ServiceDTO} object
     */
    ServiceDTO getById(long id) throws FacadeException;

    /**
     * Retrieves all {@link ServiceDTO} objects.
     *
     * @return                  list with all {@link ServiceDTO} objects
     *                          or <b>empty list</b> if there are no entities
     * @throws FacadeException  in case of any underlaying error during retrieving {@link ServiceDTO} objects
     */
    List<ServiceDTO> getAll() throws FacadeException;

    /**
     * Updates attributes of an existing {@link ServiceDTO} object.
     *
     * @param service                   {@link ServiceDTO} object with updated attributes
     * @throws IllegalArgumentException when {@link ServiceDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during updating {@link ServiceDTO} object
     */
    void update(ServiceDTO service) throws FacadeException;

    /**
     * Deletes an existing {@link ServiceDTO} entry.
     *
     * @param service                   {@link ServiceDTO} object to delete
     * @throws IllegalArgumentException when {@link ServiceDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during deleting {@link ServiceDTO} object
     */
    void delete(ServiceDTO service) throws FacadeException;
}
