package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Order;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Martin Vrábel
 * @version 31.10.2016 0:18
 */
@Repository
public class OrderDAOImpl implements OrderDAO {

    @PersistenceUnit
    private EntityManagerFactory managerFactory;

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

        EntityManager manager = managerFactory.createEntityManager();

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

        EntityManager manager = managerFactory.createEntityManager();

        return manager.find(Order.class, id);
    }

    /**
     * Retrieves all {@link Order} objects from database
     *
     * @return              list of all {@link Order} objects from database
     * @throws DAOException when some error occurs during getting {@link Order} objects from database
     */
    @Override
    public List<Order> getAll() throws DAOException {
        EntityManager manager = managerFactory.createEntityManager();

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

        EntityManager manager = managerFactory.createEntityManager();

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
            throw new IllegalArgumentException("facade is null");

        EntityManager manager = managerFactory.createEntityManager();

        try {
            return manager.createQuery("SELECT ord FROM Order ord WHERE service = :service", Order.class)
                          .setParameter("service", service)
                          .getResultList();
        } catch (PersistenceException pEx) {
            throw new DAOException(pEx);
        }
    }

    /**
     * Retrieves all {@link Order} objects from database relating to provided {@link Customer} object
     *
     * @param customer       {@link Service} object which relates to searched {@link Order} objects
     * @return              list of all {@link Order} objects from database relating to provided {@link Customer} object
     * @throws DAOException when some error occurs during getting {@link Order} objects from database
     */
    @Override
    public List<Order> getByCustomer(Customer customer) throws DAOException {
        if (customer == null) throw new IllegalArgumentException("Customer cannot be null");

        EntityManager manager = managerFactory.createEntityManager();

        try {
            return manager.createQuery("SELECT o FROM Order o WHERE customer = :service", Order.class)
                    .setParameter("customer", customer)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Order> getAllOrdersForDay(LocalDate date) throws DAOException {
        if (date == null) throw new IllegalArgumentException("Date cannot be null");

        EntityManager manager = managerFactory.createEntityManager();
        return null;
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

        EntityManager manager = managerFactory.createEntityManager();

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

        EntityManager manager = managerFactory.createEntityManager();

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
