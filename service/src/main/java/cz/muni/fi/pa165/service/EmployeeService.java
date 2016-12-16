package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.ServiceException;

import java.util.List;

/**
 * Service for working with {@link Employee}s.
 *
 * @author Denis Richtarik
 */
public interface EmployeeService {

    /**
     * Creates new entry from provided {@link Employee} object.
     *
     * @param employee                  {@link Employee} object to creste
     * @throws IllegalArgumentException if provided {@link Employee} is {@code null}
     * @throws ServiceException         if error occurs during creation of {@link Employee}
     */
    void create(Employee employee) throws ServiceException;

    /**
     * Retrieves {@link Employee} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link Employee} to retrieve
     * @return                          found {@link Employee} object or {@code null} if <b>ID</b> not found
     * @throws IllegalArgumentException if provided {@code id} is less than 0
     * @throws ServiceException         if error occurs during retrieving {@link Employee}
     */
    Employee getById(long id) throws ServiceException;

    /**
     * Retrieves all {@link Employee} objects.
     *
     * @return                  list of all {@link Employee} objects
     * @throws ServiceException if error occurs during retrieving of {@link Employee}s
     */
    List<Employee> getAll() throws ServiceException;

    /**
     * Retrieves a {@link Employee} object which has provided <b>username</b>.
     *
     * @param username                  Username of {@link Employee} to retrieve
     * @return                          found {@link Employee} object or {@link null} if <b>username</b> not found
     * @throws IllegalArgumentException for {@code null} or empty username
     * @throws ServiceException         if error occurs during retrieving of {@link Employee}
     */
    Employee getByUsername(String username) throws ServiceException;

    /**
     * Updates attributes of an existing {@link Employee} object.
     *
     * @param employee          {@link Employee} object with updated attributes
     * @throws ServiceException if error occurs during updating of {@link Employee}
     */
    void update(Employee employee) throws ServiceException;

    /**
     * Deletes an existing {@link Employee} entry.
     *
     * @param employee          {@link Employee} object to delete
     * @throws ServiceException if error occurs during deleting {@link Employee}
     */
    void delete(Employee employee) throws ServiceException;
}
