package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Employee;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.*;
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
        if (employee == null){
            throw new IllegalArgumentException("employee is null");
        } else {
            manager.persist(employee);
        }
    }

    /**
     * Retrieves a {@see Employee} object with provided <b>ID</b> from database
     *
     * @param id <b>ID</b> number of {@link Employee} to retrieve
     * @return found {@link Employee} object or {@link null} if <b>ID</b> not found
     */
    @Override
    public Employee getById(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("id is incorrect. Must be >= 0");
        } else {
            return manager.find(Employee.class, id);
        }
    }

    /**
     * Retrieves all {@link Employee} objects from database
     *
     * @return list of all {@link Employee} objects from database
     */
    @Override
    public List<Employee> getAll() {
        TypedQuery<Employee> query = manager.createQuery("SELECT e FROM Employee e", Employee.class);
        return query.getResultList();
    }

    /**
     * Retrieves a {@see Employee} object which has provided <b>username</b> from database
     *
     * @param username  Username of {@link Employee} to retrieve
     * @return  found {@link Employee} object or {@link null} if <b>username</b> not found
     * @throws  IllegalArgumentException for {@link null} or empty username
     */
    @Override
    public Employee getByUsername(String username) {
        if (username == null)
            throw new IllegalArgumentException("Cannot search for null username");

        try {
            return manager.createQuery("select e from Employee e where username=:username", Employee.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Updates attributes of an existing {@link Employee} object in database
     *
     * @param employee {@link Employee} object with updated attributes
     */
    @Override
    public void update(Employee employee) {
        if (employee == null){
            throw new IllegalArgumentException("employee is null");
        } else {
            manager.persist(employee);
        }
    }

    /**
     * Deletes an existing {@link Employee} entry from database
     *
     * @param employee {@link Employee} object to delete from database
     */
    @Override
    public void delete(Employee employee) {
        if (employee == null){
            throw new IllegalArgumentException("employee is null");
        } else {
            manager.remove(employee);
        }
    }
}
