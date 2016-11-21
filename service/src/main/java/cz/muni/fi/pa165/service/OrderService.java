package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Order;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;

import java.util.List;

/**
 * Data access object for retrieving {@link Order} objects.
 *
 * @author Martin Vrábel
 * @version 31.10.2016 0:03
 */
public interface OrderService {

    /**
     * Creates new entry from provided {@link Order} object
     *
     * @param order         {@link Order} object to save
     */
    void create(Order order);

    /**
     * Retrieves an {@link Order} object with provided <b>ID</b>
     *
     * @param id            <b>ID</b> number of {@link Order} to retrieve
     * @return              found {@link Order} object
     */
    Order getById(long id);

    /**
     * Retrieves all {@link Order} objects
     *
     * @return              list of all {@link Order} objects
     */
    List<Order> getAll();

    /**
     * Retrieves all {@link Order} objects relating to provided {@link Dog} object
     *
     * @param dog           {@link Dog} object which relates to searched {@link Order} objects
     * @return              list of all {@link Order} objects relating to provided {@link Dog} object
     */
    List<Order> getByDog(Dog dog);

    /**
     * Retrieves all {@link Order} objects relating to provided {@link Service} object
     *
     * @param service       {@link Service} object which relates to searched {@link Order} objects
     * @return              list of all {@link Order} objects relating to provided {@link Service} object
     */
    List<Order> getByService(Service service);

    /**
     * Updates attributes of an existing {@link Order} object
     *
     * @param order         {@link Order} object with updated attributes}
     */
    void update(Order order);

    /**
     * Deletes an existing {@link Order} entry
     *
     * @param order         {@link Order} object to delete
     */
    void delete(Order order);
}
