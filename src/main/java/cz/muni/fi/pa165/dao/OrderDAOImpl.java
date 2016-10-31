package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Order;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Martin Vrábel
 * @version 31.10.2016 0:18
 */
@Repository
public class OrderDAOImpl implements OrderDAO {

    @PersistenceContext
    private EntityManager manager;

    /**
     * Creates new entry in database from provided {@link Order} object
     *
     * @param order         {@link Order} object to save
     * @throws DAOException if {@link Order} already exists in database or if saving to database fails
     */
    @Override
    public void create(Order order) throws DAOException {
        if (order == null)
            throw new IllegalArgumentException("order is null");
        if (order.getId() > 0)
            throw new DAOException("Order ID is already set");

        try {
            manager.getTransaction().begin();

            // save Order to database
            manager.persist(order);

            manager.getTransaction().commit();
        } catch (EntityExistsException eeEx){
            // rollback if needed
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();

            throw new DAOException("Provided Order already exists in database");
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
     * Retrieves an {@link Order} object with provided <b>ID</b> from database
     *
     * @param id            <b>ID</b> number of {@link Order} to retrieve
     * @return              found {@link Order} object
     * @throws DAOException if {@link Order} with provided <b>ID</b> not found in database
     */
    @Override
    public Order getById(long id) throws DAOException {
        if (id < 0)
            throw new IllegalArgumentException("Order ID must be positive integral number");

        Order order = manager.find(Order.class, id);
        if (order != null)
            return order;

        throw new DAOException("Order with id " + id + " has not been found in database");
    }

    /**
     * Retrieves all {@link Order} objects from database
     *
     * @return              list of all {@link Order} objects from database
     * @throws DAOException when some error occurs during getting {@link Order} objects from database
     */
    @Override
    public List<Order> getAll() throws DAOException {
        try {
            return manager.createQuery("SELECT ord from Order ord", Order.class)
                          .getResultList();
        } catch (PersistenceException pEx) {
            throw new DAOException(pEx);
        }
    }

    /**
     * Retrieves all {@link Order} objects from database relating to provided {@link Dog} object
     *
     * @param dog           {@link Dog} object which relates to searched {@link Order} objects
     * @return              list of all {@link Order} objects from database relating to provided {@link Dog} object
     * @throws DAOException when some error occurs during getting {@link Order} objects from database
     */
    @Override
    public List<Order> getByDog(Dog dog) throws DAOException {
        if (dog == null)
            throw new IllegalArgumentException("dog is null");

        try {
            return manager.createQuery("SELECT ord FROM Order ord WHERE dog = :dog", Order.class)
                          .setParameter("dog", dog)
                          .getResultList();
        } catch (PersistenceException pEx) {
            throw new DAOException(pEx);
        }
    }

    /**
     * Retrieves all {@link Order} objects from database relating to provided {@link Service} object
     *
     * @param service       {@link Service} object which relates to searched {@link Order} objects
     * @return              list of all {@link Order} objects from database relating to provided {@link Service} object
     * @throws DAOException when some error occurs during getting {@link Order} objects from database
     */
    @Override
    public List<Order> getByService(Service service) throws DAOException {
        if (service == null)
            throw new IllegalArgumentException("service is null");

        try {
            return manager.createQuery("SELECT ord FROM Order ord WHERE service = :service", Order.class)
                          .setParameter("service", service)
                          .getResultList();
        } catch (PersistenceException pEx) {
            throw new DAOException(pEx);
        }
    }

    /**
     * Updates attributes of an existing {@link Order} object in database
     *
     * @param order         {@link Order} object with updated attributes}
     * @throws DAOException when update of {@link Order} object in database fails
     */
    @Override
    public void update(Order order) throws DAOException {
        if (order == null)
            throw new IllegalArgumentException("order is null");

        try {
            manager.getTransaction().begin();

            // update Order in database
            manager.merge(order);

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
     * Deletes an existing {@link Order} entry from database
     *
     * @param order         {@link Order} object to delete from database
     * @throws DAOException when deleteing of {@link Order} object in database fails
     */
    @Override
    public void delete(Order order) throws DAOException {
        if (order == null)
            throw new IllegalArgumentException("order is null");

        try {
            manager.getTransaction().begin();

            // delete Order in database
            manager.remove(order);

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
}
