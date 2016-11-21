package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.DAOException;

import java.util.List;

/**
 * Facade for acessing {@link Employee} objects.
 *
 * @author Denis Richtarik
 */
public interface EmployeeFacade {

    /**
     * Creates new entry from provided {@link Employee} object
     *
     * @param employee {@link Employee} object to save
     */
    void create(Employee employee);

    /**
     * Retrieves a {@see Employee} object with provided <b>ID</b>
     *
     * @param id <b>ID</b> number of {@link Employee} to retrieve
     * @return found {@link Employee} object or {@link null} if <b>ID</b> not found
     */
    Employee getById(long id);

    /**
     * Retrieves all {@link Employee} objects
     *
     * @return list of all {@link Employee} objects
     */
    List<Employee> getAll();

    /**
     * Retrieves a {@link Employee} object which has provided <b>username</b>
     *
     * @param username Username of {@link Employee} to retrieve
     * @return found {@link Employee} object or {@link null} if <b>username</b> not found
     * @throws IllegalArgumentException for {@link null} or empty username
     */
    Employee getByUsername(String username);

    /**
     * Updates attributes of an existing {@link Employee} object
     *
     * @param employee {@link Employee} object with updated attributes
     */
    void update(Employee employee);

    /**
     * Deletes an existing {@link Employee} entry
     *
     * @param employee {@link Employee} object to delete
     */
    void delete(Employee employee);
}
