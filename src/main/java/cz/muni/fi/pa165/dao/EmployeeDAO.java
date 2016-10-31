package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.DAOException;

import java.util.List;

/**
 * Data access object for retrieving {@link Employee} objects from database.
 *
 * @author Denis Richtarik
 */
public interface EmployeeDAO {

    /**
     * Creates new entry in database from provided {@link Employee} object
     *
     * @param employee {@link Employee} object to save
     */
    void create(Employee employee) throws DAOException;

    /**
     * Retrieves a {@see Employee} object with provided <b>ID</b> from database
     *
     * @param id <b>ID</b> number of {@link Employee} to retrieve
     * @return found {@link Employee} object or {@link null} if <b>ID</b> not found
     */
    Employee getById(long id);

    /**
     * Retrieves all {@link Employee} objects from database
     *
     * @return list of all {@link Employee} objects from database
     */
    List<Employee> getAll();

    /**
     * Retrieves a {@link Employee} object which has provided <b>username</b> from database
     *
     * @param username Username of {@link Employee} to retrieve
     * @return found {@link Employee} object or {@link null} if <b>username</b> not found
     * @throws IllegalArgumentException for {@link null} or empty username
     */
    Employee getByUsername(String username);

    /**
     * Updates attributes of an existing {@link Employee} object in database
     *
     * @param employee {@link Employee} object with updated attributes
     */
    void update(Employee employee) throws DAOException;

    /**
     * Deletes an existing {@link Employee} entry from database
     *
     * @param employee {@link Employee} object to delete from database
     */
    void delete(Employee employee) throws DAOException;
}
