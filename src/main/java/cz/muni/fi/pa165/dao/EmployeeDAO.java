package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Employee;

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
     * @param customer {@link Employee} object to save
     */
    void create(Employee customer);

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
     * Updates attributes of an existing {@link Employee} object in database
     *
     * @param customer {@link Employee} object with updated attributes
     */
    void update(Employee customer);

    /**
     * Deletes an existing {@link Employee} entry from database
     *
     * @param customer {@link Employee} object to delete from database
     */
    void delete(Employee customer);
}
