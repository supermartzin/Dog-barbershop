package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EmployeeDTO;
import cz.muni.fi.pa165.exceptions.DAOException;

import java.util.List;

/**
 * Facade for acessing {@link EmployeeDTO} objects.
 *
 * @author Denis Richtarik
 */
public interface EmployeeFacade {

    /**
     * Creates new entry from provided {@link EmployeeDTO} object
     *
     * @param employee {@link EmployeeDTO} object to save
     */
    void create(EmployeeDTO employee);

    /**
     * Retrieves a {@see EmployeeDTO} object with provided <b>ID</b>
     *
     * @param id <b>ID</b> number of {@link EmployeeDTO} to retrieve
     * @return found {@link EmployeeDTO} object or {@link null} if <b>ID</b> not found
     */
    EmployeeDTO getById(long id);

    /**
     * Retrieves all {@link EmployeeDTO} objects
     *
     * @return list of all {@link EmployeeDTO} objects
     */
    List<EmployeeDTO> getAll();

    /**
     * Retrieves a {@link EmployeeDTO} object which has provided <b>username</b>
     *
     * @param username Username of {@link EmployeeDTO} to retrieve
     * @return found {@link EmployeeDTO} object or {@link null} if <b>username</b> not found
     * @throws IllegalArgumentException for {@link null} or empty username
     */
    EmployeeDTO getByUsername(String username);

    /**
     * Updates attributes of an existing {@link EmployeeDTO} object
     *
     * @param employee {@link EmployeeDTO} object with updated attributes
     */
    void update(EmployeeDTO employee);

    /**
     * Deletes an existing {@link EmployeeDTO} entry
     *
     * @param employee {@link EmployeeDTO} object to delete
     */
    void delete(EmployeeDTO employee);
}
