package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.*;
import cz.muni.fi.pa165.exceptions.DAOException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Service for retrieving {@link Order} objects.
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
    void create(Order order) throws DAOException;

    /**
     * Retrieves an {@link Order} object with provided <b>ID</b>
     *
     * @param id            <b>ID</b> number of {@link Order} to retrieve
     * @return              found {@link Order} object
     */
    Order getById(long id) throws DAOException;

    /**
     * Retrieves all {@link Order} objects
     *
     * @return              list of all {@link Order} objects
     */
    List<Order> getAll() throws DAOException;

    /**
     * Retrieves all {@link Order} objects relating to provided {@link Dog} object
     *
     * @param dog           {@link Dog} object which relates to searched {@link Order} objects
     * @return              list of all {@link Order} objects relating to provided {@link Dog} object
     */
    List<Order> getByDog(Dog dog) throws DAOException;

    /**
     * Retrieves all {@link Order} objects relating to provided {@link Dog} object
     *
     * @param customer      {@link Customer} object which relates to searched {@link Order} objects
     * @return              list of all {@link Order} objects relating to provided {@link Dog} object
     */
    List<Order> getByCustomer(Customer customer) throws DAOException;

    /**
     * Retrieves all {@link Order} objects for given date
     *
     * @param dateTime      date to retrieve orders for
     * @return              list of all {@link Order} objects for given date
     */
    List<Order> getAllOrdersForDay(LocalDateTime dateTime) throws DAOException;

    /**
     * Retrieves all {@link Order} objects relating to provided {@link Service} object
     *
     * @param service       {@link Service} object which relates to searched {@link Order} objects
     * @return              list of all {@link Order} objects relating to provided {@link Service} object
     */
    List<Order> getByService(Service service) throws DAOException;

    /**
     * Updates attributes of an existing {@link Order} object
     *
     * @param order         {@link Order} object with updated attributes}
     */
    void update(Order order) throws DAOException;

    /**
     * Deletes an existing {@link Order} entry
     *
     * @param order         {@link Order} object to delete
     */
    void delete(Order order) throws DAOException;

    /**
     * Returns sum of prices of all order in given time range
     *
     * @param from      from time
     * @param to        to time
     * @return          sum of order prices
     */
    BigDecimal getTotalAmountGained(LocalDateTime from, LocalDateTime to) throws DAOException;


    /**
     * Returns sum of prices of all order in given time range for each employee
     *
     * @param from      from time
     * @param to        to time
     * @return          sum of order prices
     */
    Map<Employee, BigDecimal> getTotalAmountGainedByEmployee(LocalDateTime from, LocalDateTime to) throws DAOException;
}
