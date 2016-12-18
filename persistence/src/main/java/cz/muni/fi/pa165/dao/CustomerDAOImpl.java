package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.exceptions.ValidationException;
import cz.muni.fi.pa165.validation.EntityValidator;
import org.springframework.dao.DataAccessException;
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
 * @author Dominik Gmiterko
 */
public class CustomerDAOImpl implements CustomerDAO {

    @PersistenceContext
    private EntityManager manager;

    private final EntityValidator validator;

    public CustomerDAOImpl(EntityValidator entityValidator) {
        if (entityValidator == null)
            throw new IllegalArgumentException("Entity Validator is null");

        this.validator = entityValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Customer customer) throws DAOException {
        if (customer == null)
            throw new IllegalArgumentException("Customer cannot be null");
        if (customer.getId() > 0)
            throw new DAOException("Customer ID is already set");

        try {
            // validate
            validator.validate(customer);

            // save to database
            manager.persist(customer);
        } catch (ValidationException | DataAccessException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer getById(long id) {
        if (id < 0)
            throw new IllegalArgumentException("ID must be positive integral number");

        return manager.find(Customer.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer getByUsername(String username) throws DAOException {
        if (username == null)
            throw new IllegalArgumentException("Cannot search for null username");

        try {
            return manager.createQuery("select customer from Customer customer where customer.username = :username", Customer.class)
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
    public List<Customer> getAll() {
        return manager.createQuery("SELECT customer FROM Customer customer", Customer.class)
                      .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Customer customer) throws DAOException {
        if (customer == null)
            throw new IllegalArgumentException("Customer cannot be null");
        if (customer.getId() == 0)
            throw new DAOException("Customer ID is not set");

        try {
            // validate
            validator.validate(customer);

            Customer existingCustomer = manager.find(Customer.class, customer.getId());
            if (existingCustomer == null)
                throw new DAOException("Customer does not exist in database");

            // update customer in database
            manager.merge(customer);
        } catch (ValidationException | PersistenceException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Customer customer) throws DAOException {
        if (customer == null)
            throw new IllegalArgumentException("Customer cannot be null");
        if (customer.getId() == 0)
            throw new DAOException("Customer ID is not set");

        try {
            Customer existingCustomer = manager.find(Customer.class, customer.getId());
            if (existingCustomer == null)
                throw new DAOException("Customer does not exist in database");

            // delete customer in database
            manager.remove(existingCustomer);
        } catch (PersistenceException pEx) {
            throw new DAOException(pEx);
        }
    }
}
