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

            throw new DAOException("Provided Customer already exists in database");
        } catch (PersistenceException | IllegalStateException ex) {
            rollbackTransaction(manager);

            throw new DAOException(ex);
        } finally {
            closeManager(manager);
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

        EntityManager manager = null;

        try {
            manager = createManager();

            return manager.find(Customer.class, id);
        } finally {
            closeManager(manager);
        }
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
     * Retrieves all {@link Customer} objects from database
     *
     * @return list of all {@link Customer} objects from database
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
     * Updates attributes of an existing {@link Customer} object in database
     *
     * @param customer {@link Customer} object with updated attributes
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
                throw new DAOException("Cannot update non-existing Customer");

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
     * Deletes an existing {@link Customer} entry from database
     *
     * @param customer {@link Customer} object to delete from database
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
                throw new DAOException("Customer with id " + customer.getId() + " does not exist in database");

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
