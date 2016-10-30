package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Employee;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 *
 * @author Denis Richtarik
 * @version 25.10.2016
 */
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    @PersistenceContext
    private EntityManager manager;

    /**
     * Creates new entry in database from provided {@link Employee} object
     *
     * @param employee {@link Employee} object to save
     */
    @Override
    public void create(Employee employee) {

        
    }

    /**
     * Retrieves a {@see Employee} object with provided <b>ID</b> from database
     *
     * @param id <b>ID</b> number of {@link Employee} to retrieve
     * @return found {@link Employee} object or {@link null} if <b>ID</b> not found
     */
    @Override
    public Employee getById(long id) {
        return null;
    }

    /**
     * Retrieves all {@link Employee} objects from database
     *
     * @return list of all {@link Employee} objects from database
     */
    @Override
    public List<Employee> getAll() {
        return null;
    }

    /**
     * Updates attributes of an existing {@link Employee} object in database
     *
     * @param employee {@link Employee} object with updated attributes
     */
    @Override
    public void update(Employee employee) {

    }

    /**
     * Deletes an existing {@link Employee} entry from database
     *
     * @param employee {@link Employee} object to delete from database
     */
    @Override
    public void delete(Employee employee) {

    }
}
