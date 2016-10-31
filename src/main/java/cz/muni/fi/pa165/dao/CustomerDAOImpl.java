package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Order;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.utils.Constants;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @PersistenceContext
    private EntityManager manager;

    /**
     * Creates new entry in database from provided {@link Customer} object
     *
     * @param customer {@link Customer} object to save
     */
    @Override
    public void create(Customer customer) throws DAOException {
        if (customer == null)
            throw new IllegalArgumentException("Customer cannot be null");
        if (customer.getId() > 0)
            throw new DAOException("Customer ID is already set");

        validateCustomer(customer);

        try {
            manager.getTransaction().begin();

            // save Customer to database
            manager.persist(customer);

            manager.getTransaction().commit();
        } catch (EntityExistsException eeEx){
            // rollback if needed
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();

            throw new DAOException("Provided Customer already exists in database");
        } catch (PersistenceException | IllegalStateException ex) {
            // rollback if needed
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();

            throw new DAOException(ex);
        } finally {
            if (manager.isOpen())
                manager.close();
        }
    }

    /**
     * Retrieves a {@see Customer} object with provided <b>ID</b> from database
     *
     * @param id <b>ID</b> number of {@link Customer} to retrieve
     * @return found {@link Customer} object
     * @throws DAOException if {@link Customer} with provided <b>ID</b> not found in database
     */
    @Override
    public Customer getById(long id) throws DAOException {
        if (id < 0)
            throw new IllegalArgumentException("ID must be positive integral number");

        return manager.find(Customer.class, id);
    }

    /**
     * Retrieves a {@see Customer} object which has provided <b>username</b> from database
     *
     * @param username  Username of {@link Customer} to retrieve
     * @return  found {@link Customer} object or {@link null} if <b>username</b> not found
     * @throws  IllegalArgumentException for {@link null} or empty username
     */
    @Override
    public Customer getByUsername(String username) throws DAOException {
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException("Cannot search for null username");

        try {
            return manager.createQuery("select u from Customer u where username=:username", Customer.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Retrieves all {@link Customer} objects from database
     *
     * @return list of all {@link Customer} objects from database
     */
    @Override
    public List<Customer> getAll() throws DAOException {
        try {
            return manager.createQuery("SELECT u FROM Customer u",
                    Customer.class).getResultList();
        } catch (PersistenceException pEx) {
            throw new DAOException(pEx);
        }
    }

    /**
     * Updates attributes of an existing {@link Customer} object in database
     *
     * @param customer {@link Customer} object with updated attributes
     */
    @Override
    public void update(Customer customer) throws DAOException {
        if (customer == null)
            throw new IllegalArgumentException("Customer cannot be null");

        validateCustomer(customer);

        try {
            manager.getTransaction().begin();

            // update Customer in database
            manager.merge(customer);

            manager.getTransaction().commit();
        } catch (PersistenceException pEx) {
            // rollback if needed
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();

            throw new DAOException(pEx);
        } finally {
            if (manager.isOpen())
                manager.close();
        }
    }

    /**
     * Deletes an existing {@link Customer} entry from database
     *
     * @param customer {@link Customer} object to delete from database
     */
    @Override
    public void delete(Customer customer) throws DAOException {
        if (customer == null)
            throw new IllegalArgumentException("Customer cannot be null");

        try {
            manager.getTransaction().begin();

            // delete Customer in database
            manager.remove(customer);

            manager.getTransaction().commit();
        } catch (PersistenceException pEx) {
            // rollback if needed
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();

            throw new DAOException(pEx);
        } finally {
            if (manager.isOpen())
                manager.close();
        }
    }

    private void validateCustomer(Customer customer) throws DAOException {
        if(customer.getUsername() == null) {
            throw new DAOException("Username not set");
        }
        if(customer.getPhone() == null) {
            throw new DAOException("Phone not set");
        }
        if(!customer.getPhone().matches(Constants.PHONE_NUMBER_REGEX_PATTERN)) {
            throw new DAOException("Phone is invalid");
        }
        if(customer.getEmail() == null) {
            throw new DAOException("Email not set");
        }
        if(!customer.getEmail().matches(Constants.EMAIL_REGEX_PATTERN)) {
            throw new DAOException("Email is invalid");
        }
        if(customer.getPassword() == null) {
            throw new DAOException("Password not set");
        }
    }
}
