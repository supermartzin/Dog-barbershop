package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.exceptions.ValidationException;
import cz.muni.fi.pa165.validation.EntityValidator;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Denis Richtarik
 * @version 25.10.2016
 */
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    @PersistenceContext
    private EntityManager manager;

    private final EntityValidator validator;

    @Inject
    public EmployeeDAOImpl(EntityValidator entityValidator) {
        if (entityValidator == null)
            throw new IllegalArgumentException("Entity Validator is null");

        this.validator = entityValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Employee employee) throws DAOException {
        if (employee == null)
            throw new IllegalArgumentException("Employee cannot be null");
        if (employee.getId() > 0)
            throw new DAOException("Employee ID is already set");

        try {
            // validate
            validator.validate(employee);

            // save to database
            manager.persist(employee);
        } catch (ValidationException | PersistenceException | IllegalStateException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getById(long id) {
        if (id < 0)
            throw new IllegalArgumentException("id is incorrect. Must be >= 0");

        return manager.find(Employee.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getByUsername(String username) throws DAOException {
        if (username == null)
            throw new IllegalArgumentException("Cannot search for null username");

        try {
            return manager.createQuery("select employee from Employee employee where employee.username = :username", Employee.class)
                          .setParameter("username", username)
                          .getSingleResult();
        } catch (NoResultException nrEx) {
            return null;
        } catch (PersistenceException pEx) {
            throw new DAOException(pEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAll() {
        return manager.createQuery("SELECT employee FROM Employee employee", Employee.class)
                      .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Employee employee) throws DAOException {
        if (employee == null)
            throw new IllegalArgumentException("employee is null");
        if (employee.getId() == 0)
            throw new DAOException("Employee ID is not set");

        try {
            // validate
            validator.validate(employee);

            Employee existingEmployee = manager.find(Employee.class, employee.getId());
            if (existingEmployee == null)
                throw new DAOException("Employee does not exist in database");

            // update employee in database
            manager.merge(employee);
        } catch (ValidationException | PersistenceException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Employee employee) throws DAOException {
        if (employee == null)
            throw new IllegalArgumentException("Employee can't be null");

        try {
            Employee em = manager.find(Employee.class, employee.getId());
            if (em == null)
                throw new DAOException("Employee with id: " + employee.getId() + " you want to delete doesn't exist!");

            // delete in database
            manager.remove(em);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }
}
