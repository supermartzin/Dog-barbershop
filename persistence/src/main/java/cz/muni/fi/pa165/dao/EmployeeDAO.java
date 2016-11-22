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
     * Creates new entry in database from provided {@link Employee} object.
     *
     * @param employee                  {@link Employee} object to save
     * @throws IllegalArgumentException when {@code employee} is {@code null}
     * @throws DAOException             when some problem with database occurs
     */
    void create(Employee employee) throws DAOException;

    /**
     * Retrieves a {@see Employee} object with provided <b>ID</b> from database.
     *
     * @param id                        <b>ID</b> number of {@link Employee} to retrieve
     * @return                          found {@link Employee} object or {@link null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException when {@code id} is a negative number
     */
    Employee getById(long id);

    /**
     * Retrieves a {@link Employee} object which has provided <b>username</b> from database.
     *
     * @param username                  username of {@link Employee} to retrieve
     * @return found {@link Employee}   object or {@link null} if <b>username</b> not found
     * @throws IllegalArgumentException when {@code username} is {@link null}
     * @throws DAOException             when {@link Employee} object cannot be retrieved from database
     */
    Employee getByUsername(String username) throws DAOException;

    /**
     * Retrieves all {@link Employee} objects from database.
     *
     * @return  list of all {@link Employee} objects from database
     *          or <b>empty list</b> if no employees found in database
     */
    List<Employee> getAll();

    /**
     * Updates attributes of an existing {@link Employee} object in database.
     *
     * @param employee                  {@link Employee} object with updated attributes
     * @throws IllegalArgumentException when {@code employee} is {@code null}
     * @throws DAOException             when {@code employee} is in invalid state or the object cannot be updated in database
     */
    void update(Employee employee) throws DAOException;

    /**
     * Deletes an existing {@link Employee} entry from database.
     *
     * @param employee                  {@link Employee} object to delete from database
     * @throws IllegalArgumentException when {@code employee} is {@code null}
     * @throws DAOException             when {@code employee} does not exist or the object cannot be removed from database
     */
    void delete(Employee employee) throws DAOException;
}
