package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EmployeeDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;

import java.util.List;

/**
 * Facade for processing {@link EmployeeDTO} objects.
 *
 * @author Denis Richtarik
 */
public interface EmployeeFacade {

    /**
     * Creates new entry from provided {@link EmployeeDTO} object.
     *
     * @param employeeDTO               {@link EmployeeDTO} object to save
     * @throws IllegalArgumentException if provided {@code employeeDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during creation of {@link EmployeeDTO} object
     */
    void create(EmployeeDTO employeeDTO) throws FacadeException;

    /**
     * Retrieves a {@see EmployeeDTO} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link EmployeeDTO} to retrieve
     * @return                          found {@link EmployeeDTO} object or {@code null} if <b>ID</b> not found
     * @throws IllegalArgumentException provided <b>ID</b> is negative number
     * @throws FacadeException          in case of any underlaying error during retrieving {@link EmployeeDTO} object
     */
    EmployeeDTO getById(long id) throws FacadeException;

    /**
     * Retrieves a {@link EmployeeDTO} object which has provided {@code username}.
     *
     * @param username                  Username of {@link EmployeeDTO} to retrieve
     * @return                          found {@link EmployeeDTO} object or {@code null} if {@code username} not found
     * @throws IllegalArgumentException for {@link null} or empty {@code username}
     * @throws FacadeException          in case of any underlaying error during retrieving {@link EmployeeDTO} object
     */
    EmployeeDTO getByUsername(String username) throws FacadeException;

    /**
     * Retrieves all {@link EmployeeDTO} objects.
     *
     * @return                  list of all {@link EmployeeDTO} objects
     * @throws FacadeException  in case of any underlaying error during retrieving {@link EmployeeDTO} objects
     */
    List<EmployeeDTO> getAll() throws FacadeException;

    /**
     * Updates attributes of an existing {@link EmployeeDTO} object.
     *
     * @param employeeDTO               {@link EmployeeDTO} object with updated attributes
     * @throws IllegalArgumentException if provided {@code employeeDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during updating {@link EmployeeDTO} object
     */
    void update(EmployeeDTO employeeDTO) throws FacadeException;

    /**
     * Deletes an existing {@link EmployeeDTO} entry.
     *
     * @param employee                  {@link EmployeeDTO} object to delete
     * @throws IllegalArgumentException if provided {@code employeeDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during deleting {@link EmployeeDTO} object
     */
    void delete(EmployeeDTO employee) throws FacadeException;
}
