package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.OrderDTO;
import cz.muni.fi.pa165.dto.ServiceDTO;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.exceptions.DAOException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Facade for acessing {@link OrderDTO} objects.
 *
 * @author Martin Vrábel
 * @version 31.10.2016 0:03
 */
public interface OrderFacade {

    /**
     * Creates new entry from provided {@link OrderDTO} object
     *
     * @param order         {@link OrderDTO} object to save
     */
    void create(OrderDTO order) throws DAOException;

    /**
     * Retrieves an {@link OrderDTO} object with provided <b>ID</b>
     *
     * @param id            <b>ID</b> number of {@link OrderDTO} to retrieve
     * @return              found {@link OrderDTO} object
     */
    OrderDTO getById(long id) throws DAOException;

    /**
     * Retrieves all {@link OrderDTO} objects
     *
     * @return              list of all {@link OrderDTO} objects
     */
    List<OrderDTO> getAll() throws DAOException;

    /**
     * Retrieves all {@link OrderDTO} objects relating to provided {@link DogDTO} object
     *
     * @param dog           {@link DogDTO} object which relates to searched {@link OrderDTO} objects
     * @return              list of all {@link OrderDTO} objects relating to provided {@link DogDTO} object
     */
    List<OrderDTO> getByDog(DogDTO dog) throws DAOException;

    /**
     * Retrieves all {@link OrderDTO} objects relating to provided {@link DogDTO} object
     *
     * @param customer      {@link Customer} object which relates to searched {@link OrderDTO} objects
     * @return              list of all {@link OrderDTO} objects relating to provided {@link DogDTO} object
     */
    List<OrderDTO> getByCustomer(Customer customer) throws DAOException;

    /**
     * Retrieves all {@link OrderDTO} objects for given date
     *
     * @param date      date to retrieve orders for
     * @return          list of all {@link OrderDTO} objects for given date
     */
    List<OrderDTO> getAllOrdersForDay(LocalDateTime date) throws DAOException;

    /**
     * Retrieves all {@link OrderDTO} objects relating to provided {@link ServiceDTO} object
     *
     * @param service       {@link ServiceDTO} object which relates to searched {@link OrderDTO} objects
     * @return              list of all {@link OrderDTO} objects relating to provided {@link ServiceDTO} object
     */
    List<OrderDTO> getByService(ServiceDTO service) throws DAOException;

    /**
     * Updates attributes of an existing {@link OrderDTO} object
     *
     * @param order         {@link OrderDTO} object with updated attributes}
     */
    void update(OrderDTO order) throws DAOException;

    /**
     * Deletes an existing {@link OrderDTO} entry
     *
     * @param order         {@link OrderDTO} object to delete
     */
    void delete(OrderDTO order) throws DAOException;

    /**
     * Returns sum of prices of all order in given time range
     *
     * @param from      from time
     * @param to        to time
     * @return          sum of order prices
     */
    BigDecimal getTotalAmountGained(LocalDateTime from, LocalDateTime to) throws DAOException;
}
