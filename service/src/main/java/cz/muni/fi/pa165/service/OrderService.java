package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.*;
import cz.muni.fi.pa165.exceptions.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Service for working with {@link Order}s.
 *
 * @author Martin Vrábel
 * @version 31.10.2016 0:03
 */
public interface OrderService {

    /**
     * Creates new entry from provided {@link Order} object.
     *
     * @param order                     {@link Order} object to save
     * @throws IllegalArgumentException if provided {@link Order} is {@code null}
     * @throws ServiceException         if error occurs during creation of {@link Order}
     */
    void create(Order order) throws ServiceException;

    /**
     * Retrieves an {@link Order} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link Order} to retrieve
     * @return                          found {@link Order} object
     * @throws IllegalArgumentException if provided {@code id} is less than 0
     * @throws ServiceException         if error occurs during retrieveing {@link Order}
     */
    Order getById(long id) throws ServiceException;

    /**
     * Retrieves all {@link Order} objects.
     *
     * @return                  list of all {@link Order} objects
     * @throws ServiceException if error occurs during retrieving {@link Order}s
     */
    List<Order> getAll() throws ServiceException;

    /**
     * Retrieves all {@link Order} objects relating to provided {@link Dog} object.
     *
     * @param dog                       {@link Dog} object which relates to searched {@link Order} objects
     * @return                          list of all {@link Order} objects relating to provided {@link Dog} object
     * @throws IllegalArgumentException if provided {@link Dog} is {@code null}
     * @throws ServiceException         if error occurs during retrieving {@link Order}s
     */
    List<Order> getByDog(Dog dog) throws ServiceException;

    /**
     * Retrieves all {@link Order} objects relating to provided {@link Dog} object.
     *
     * @param customer                  {@link Customer} object which relates to searched {@link Order} objects
     * @return                          list of all {@link Order} objects relating to provided {@link Dog} object
     * @throws IllegalArgumentException if provided {@link Customer} is {@code null}
     * @throws ServiceException         if error occurs during retrieving {@link Order}s
     */
    List<Order> getByCustomer(Customer customer) throws ServiceException;

    /**
     * Retrieves all {@link Order} objects for given date.
     *
     * @param dateTime                  date to retrieve orders for
     * @return                          list of all {@link Order} objects for given date
     * @throws IllegalArgumentException if provided {@link LocalDateTime} is {@code null}
     * @throws ServiceException         if error occurs during retrieving {@link Order}s
     */
    List<Order> getAllOrdersForDay(LocalDateTime dateTime) throws ServiceException;

    /**
     * Retrieves all {@link Order} objects relating to provided {@link Service} object.
     *
     * @param service                   {@link Service} object which relates to searched {@link Order} objects
     * @return                          list of all {@link Order} objects relating to provided {@link Service} object
     * @throws IllegalArgumentException if provided {@link Service} is {@code null}
     * @throws ServiceException         if error occurs during retrieving {@link Order}s
     */
    List<Order> getByService(Service service) throws ServiceException;

    /**
     * Updates attributes of an existing {@link Order} object.
     *
     * @param order                     {@link Order} object with updated attributes}
     * @throws IllegalArgumentException if provided {@link Order} is {@code null}
     * @throws ServiceException         if error occurs during updating of {@link Order}
     */
    void update(Order order) throws ServiceException;

    /**
     * Deletes an existing {@link Order} entry.
     *
     * @param order                     {@link Order} object to delete
     * @throws IllegalArgumentException if provided {@link Order} is {@code null}
     * @throws ServiceException         if error occurs during deleting of {@link Order}
     */
    void delete(Order order) throws ServiceException;

    /**
     * Returns sum of prices of all order in given time range.
     *
     * @param from                      from time
     * @param to                        to time
     * @return                          sum of order prices
     * @throws IllegalArgumentException if either of provided {@link LocalDateTime} parameters is {@code null}
     * @throws ServiceException         if error occurs during retrieving total amount gained
     */
    BigDecimal getTotalAmountGained(LocalDateTime from, LocalDateTime to) throws ServiceException;

    /**
     * Returns sum of prices of all order in given time range for each employee.
     *
     * @param from                      from time
     * @param to                        to time
     * @return                          {@link Map} with total price gained from {@link Order}s for each {@link Employee} in the system
     * @throws IllegalArgumentException if either of provided {@link LocalDateTime} parameters is {@code null}
     * @throws ServiceException         if error occurs during retrieving total amount gained
     */
    Map<Employee, BigDecimal> getTotalAmountGainedForEmployees(LocalDateTime from, LocalDateTime to) throws ServiceException;

    /**
     * Updates {@link Order} status to completed
     *
     * @param order             {@link Order} object to update
     * @throws ServiceException when provided {@link Order} cannot be set completed
     */
    void orderCompleted(Order order) throws ServiceException;
}
