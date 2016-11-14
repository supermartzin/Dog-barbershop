package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 * @version
 */
@Repository
public class ServiceDAOImpl implements ServiceDAO {

    @PersistenceUnit
    private EntityManagerFactory managerFactory;

    /**
     * Creates new entry in database from provided {@link Service} object
     *
     * @param service {@link Service} object to save
     */
    @Override
    public void create(Service service) throws DAOException {
        if (service == null)
            throw new IllegalArgumentException("Service cannot be null");
        if (service.getId() > 0)
            throw new DAOException("Service ID is already set");

        EntityManager manager = null;

        try {
            manager = createManager();

            manager.getTransaction().begin();

            // save Customer to database
            manager.persist(service);

            manager.getTransaction().commit();
        } catch (EntityExistsException eeEx){
            rollbackTransaction(manager);

            throw new DAOException("Provided service already exists in database", eeEx);
        } catch (PersistenceException | IllegalStateException ex) {
            rollbackTransaction(manager);

            throw new DAOException(ex);
        } finally {
            closeManager(manager);
        }
    }

    /**
     * Retrieves a {@see Service} object with provided <b>ID</b> from database
     *
     * @param id <b>ID</b> number of {@link Service} to retrieve
     * @return found {@link Service} object or {@link null} if <b>ID</b> not found
     */
    @Override
    public Service getById(long id) {
        if (id < 0)
            throw new IllegalArgumentException("ID must be positive integer number");

        EntityManager manager = null;

        try {
            manager = createManager();

            return manager.find(Service.class, id);
        } finally {
            closeManager(manager);
        }
    }

    /**
     * Retrieves all {@link Service} objects from database
     *
     * @return list of all {@link Service} objects from database
     */
    @Override
    public List<Service> getAll() throws DAOException {
        EntityManager manager = null;

        try {
            manager = createManager();

            return manager.createQuery("SELECT u FROM Service u", Service.class)
                    .getResultList();
        } catch (PersistenceException pEx) {
            throw new DAOException(pEx);
        } finally {
            closeManager(manager);
        }
    }

    /**
     * Updates attributes of an existing {@link Service} object in database
     *
     * @param service {@link Service} object with updated attributes
     */
    @Override
    public void update(Service service) throws DAOException {
        if (service == null)
            throw new IllegalArgumentException("Service cannot be null");

        EntityManager manager = null;

        try {
            manager = createManager();

            manager.getTransaction().begin();

            Service existingService = manager.find(Service.class, service.getId());
            if (existingService == null)
                throw new DAOException("Cannot update non-existing Customer");

            // update Customer in database
            manager.merge(service);

            manager.getTransaction().commit();
        } catch (PersistenceException pEx) {
            rollbackTransaction(manager);

            throw new DAOException(pEx);
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            closeManager(manager);
        }
    }

    /**
     * Deletes an existing {@link Service} entry from database
     *
     * @param service {@link Service} object to delete from database
     */
    @Override
    public void delete(Service service) throws DAOException {
        if (service == null)
            throw new IllegalArgumentException("Service cannot be null");

        EntityManager manager = null;

        try {
            manager = createManager();

            manager.getTransaction().begin();

            Service existingService = manager.find(Service.class, service.getId());
            if (existingService == null)
                throw new DAOException("Customer with id " + service.getId() + " does not exist in database");

            // delete Customer in database
            manager.remove(existingService);

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
}
