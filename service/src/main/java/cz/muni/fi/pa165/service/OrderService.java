package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Order;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
     * Retrieves all {@link Order} objects relating to provided {@link Dog} object
     *
     * @param customer      {@link Customer} object which relates to searched {@link Order} objects
     * @return              list of all {@link Order} objects relating to provided {@link Dog} object
     */
    List<Order> getByCustomer(Customer customer);

    /**
     * Retrieves all {@link Order} objects for given date
     *
     * @param date      date to retrieve orders for
     * @return          list of all {@link Order} objects for given date
     */
    List<Order> getAllOrdersForDay(LocalDate date);

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

    /**
     * Returns sum of prices of all order in given time range
     *
     * @param from      from time
     * @param to        to time
     * @return          sum of order prices
     */
    BigDecimal getTotalAmountGained(LocalDateTime from, LocalDateTime to);
}
