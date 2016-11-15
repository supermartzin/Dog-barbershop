package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @PersistenceUnit
    private EntityManagerFactory managerFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Customer customer) throws DAOException {
        if (customer == null)
            throw new IllegalArgumentException("Customer cannot be null");
        if (customer.getId() > 0)
            throw new DAOException("Customer ID is already set");

        EntityManager manager = null;

        try {
            manager = createManager();

            manager.getTransaction().begin();

            // save Customer to database
            saveAddress(customer.getAddress(), manager);
            manager.persist(customer);

            manager.getTransaction().commit();
        } catch (EntityExistsException eeEx){
            rollbackTransaction(manager);

            throw new DAOException("Customer already exists in database");
        } catch (PersistenceException | IllegalStateException ex) {
            rollbackTransaction(manager);

            throw new DAOException(ex);
        } finally {
            closeManager(manager);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer getById(long id) {
        if (id < 0)
            throw new IllegalArgumentException("ID must be positive integral number");

        EntityManager manager = null;

        try {
            manager = createManager();

            return manager.find(Customer.class, id);
        } finally {
            closeManager(manager);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer getByUsername(String username) throws DAOException {
        if (username == null)
            throw new IllegalArgumentException("Cannot search for null username");

        EntityManager manager = null;

        try {
            manager = createManager();

            return manager.createQuery("select u from Customer u where username = :username", Customer.class)
                          .setParameter("username", username)
                          .getSingleResult();
        } catch (NoResultException nrEx) {
            return null;
        } catch (PersistenceException pEx) {
            throw new DAOException(pEx);
        } finally {
            closeManager(manager);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> getAll() throws DAOException {
        EntityManager manager = null;

        try {
            manager = createManager();

            return manager.createQuery("SELECT u FROM Customer u", Customer.class)
                          .getResultList();
        } catch (PersistenceException pEx) {
            throw new DAOException(pEx);
        } finally {
            closeManager(manager);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Customer customer) throws DAOException {
        if (customer == null)
            throw new IllegalArgumentException("Customer cannot be null");

        EntityManager manager = null;

        try {
            manager = createManager();

            manager.getTransaction().begin();

            Customer existingCustomer = manager.find(Customer.class, customer.getId());
            if (existingCustomer == null)
                throw new DAOException("Customer does not exist in database");

            // update Customer in database
            saveAddress(customer.getAddress(), manager);
            manager.merge(customer);

            manager.getTransaction().commit();
        } catch (PersistenceException pEx) {
            rollbackTransaction(manager);

            throw new DAOException(pEx);
        } finally {
            closeManager(manager);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Customer customer) throws DAOException {
        if (customer == null)
            throw new IllegalArgumentException("Customer cannot be null");

        EntityManager manager = null;

        try {
            manager = createManager();

            manager.getTransaction().begin();

            Customer existingCustomer = manager.find(Customer.class, customer.getId());
            if (existingCustomer == null)
                throw new DAOException("Customer does not exist in database");

            // delete Customer in database
            manager.remove(existingCustomer);

            manager.getTransaction().commit();
        } catch (PersistenceException pEx) {
            rollbackTransaction(manager);

            throw new DAOException(pEx);
        } finally {
            closeManager(manager);
        }
    }


    private EntityManager createManager() {
        return managerFactory.createEntityManager();
    }

    private void rollbackTransaction(EntityManager manager) {
        if (manager == null)
            return;

        // rollback if needed
        if (manager.getTransaction().isActive())
            manager.getTransaction().rollback();
    }

    private void closeManager(EntityManager manager) {
        if (manager == null)
            return;

        if (manager.isOpen())
            manager.close();
    }

    private void saveAddress(Address address, EntityManager manager) {
        if (address == null)
            return;

        if (address.getId() > 0) {
            manager.merge(address);
        } else {
            manager.persist(address);
        }
    }
}
