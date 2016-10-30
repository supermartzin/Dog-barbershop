package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Order;
import cz.muni.fi.pa165.entities.Service;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
     * @param order {@link Order} object to save
     */
    @Override
    public void create(Order order) {
        if (order == null)
            throw new IllegalArgumentException("order is null");
    }

    /**
     * Retrieves an {@link Order} object with provided <b>ID</b> from database
     *
     * @param id <b>ID</b> number of {@link Order} to retrieve
     * @return found {@link Order} object or {@link null} if <b>ID</b> not found
     */
    @Override
    public Order getById(long id) {
        if (id < 0)
            throw new IllegalArgumentException("ID must be positive integral number");

        return null;
    }

    /**
     * Retrieves all {@link Order} objects from database
     *
     * @return list of all {@link Order} objects from database
     */
    @Override
    public List<Order> getAll() {
        return null;
    }

    /**
     * Retrieves all {@link Order} objects from database relating to provided {@link Dog} object
     *
     * @param dog {@link Dog} object which relates to searched {@link Order} objects
     * @return list of all {@link Order} objects from database relating to provided {@link Dog} object
     */
    @Override
    public List<Order> getByDog(Dog dog) {
        if (dog == null)
            throw new IllegalArgumentException("dog is null");

        return null;
    }

    /**
     * Retrieves all {@link Order} objects from database relating to provided {@link Service} object
     *
     * @param service {@link Service} object which relates to searched {@link Order} objects
     * @return list of all {@link Order} objects from database relating to provided {@link Service} object
     */
    @Override
    public List<Order> getByService(Service service) {
        if (service == null)
            throw new IllegalArgumentException("service is null");

        return null;
    }

    /**
     * Updates attributes of an existing {@link Order} object in database
     *
     * @param order {@link Order} object with updated attributes}
     */
    @Override
    public void update(Order order) {
        if (order == null)
            throw new IllegalArgumentException("order is null");
    }

    /**
     * Deletes an existing {@link Order} entry from database
     *
     * @param order {@link Order} object to delete from database
     */
    @Override
    public void delete(Order order) {
        if (order == null)
            throw new IllegalArgumentException("order is null");
    }
}
