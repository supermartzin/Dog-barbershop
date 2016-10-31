package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.utils.Constants;
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
    public void create(Employee employee) throws DAOException {
        if (employee == null)
            throw new IllegalArgumentException("Employee cannot be null");
        if (employee.getId() > 0)
            throw new DAOException("Employee ID is already set");

        validateEmployee(employee);

        try {
            manager.getTransaction().begin();

            manager.persist(employee);

            manager.getTransaction().commit();
        } catch (EntityExistsException ex){
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();

            throw new DAOException("Provided Employee already exists in database");
        } catch (PersistenceException | IllegalStateException e) {
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();

            throw new DAOException(e);
        } finally {
            if (manager.isOpen())
                manager.close();
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
        return manager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
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
    public void update(Employee employee) throws DAOException {
        if (employee == null){
            throw new IllegalArgumentException("employee is null");
        }
        validateEmployee(employee);

        try {
            manager.getTransaction().begin();

            manager.merge(employee);

            manager.getTransaction().commit();
        } catch (PersistenceException e) {
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();

            throw new DAOException(e);
        } finally {
            if (manager.isOpen())
                manager.close();
        }
    }

    /**
     * Deletes an existing {@link Employee} entry from database
     *
     * @param employee {@link Employee} object to delete from database
     */
    @Override
    public void delete(Employee employee) throws DAOException {
        if (employee == null){
            throw new IllegalArgumentException("employee is null");
        }

        try {
            manager.getTransaction().begin();

            manager.remove(employee);

            manager.getTransaction().commit();
        } catch (PersistenceException e) {
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();

            throw new DAOException(e);
        } finally {
            if (manager.isOpen())
                manager.close();
        }
    }

    private void validateEmployee(Employee employee) throws DAOException {
        if(employee.getUsername() == null) {
            throw new DAOException("Username not set");
        }
        if(employee.getPhone() == null) {
            throw new DAOException("Phone not set");
        }
        if(!employee.getPhone().matches(Constants.PHONE_NUMBER_REGEX_PATTERN)) {
            throw new DAOException("Phone is invalid");
        }
        if(employee.getEmail() == null) {
            throw new DAOException("Email not set");
        }
        if(!employee.getEmail().matches(Constants.EMAIL_REGEX_PATTERN)) {
            throw new DAOException("Email is invalid");
        }
        if(employee.getPassword() == null) {
            throw new DAOException("Password not set");
        }
        if(employee.getSalary() == null) {
            throw new DAOException("Salary not set");
        }
    }
}
