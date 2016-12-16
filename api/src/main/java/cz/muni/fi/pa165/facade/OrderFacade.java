package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.OrderDTO;
import cz.muni.fi.pa165.dto.ServiceDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Facade for processing {@link OrderDTO} objects.
 *
 * @author Martin Vrábel
 * @version 31.10.2016 0:03
 */
public interface OrderFacade {

    /**
     * Creates new entry from provided {@link OrderDTO} object.
     *
     * @param orderDTO                  {@link OrderDTO} object to save
     * @throws IllegalArgumentException if provided {@link OrderDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during creation of {@link OrderDTO} object
     */
    void create(OrderDTO orderDTO) throws FacadeException;

    /**
     * Retrieves an {@link OrderDTO} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link OrderDTO} to retrieve
     * @return                          found {@link OrderDTO} object
     * @throws IllegalArgumentException if <b>ID</b> is negative number
     * @throws FacadeException          in case of any underlaying error during retrieving {@link OrderDTO} object
     */
    OrderDTO getById(long id) throws FacadeException;

    /**
     * Retrieves all {@link OrderDTO} objects.
     *
     * @return                  list of all {@link OrderDTO} objects
     * @throws FacadeException  in case of any underlaying error during retrieving {@link OrderDTO} objects
     */
    List<OrderDTO> getAll() throws FacadeException;

    /**
     * Retrieves all {@link OrderDTO} objects relating to provided {@link DogDTO} object
     *
     * @param dogDTO                    {@link DogDTO} object which relates to searched {@link OrderDTO} objects
     * @return                          list of all {@link OrderDTO} objects relating to provided {@link DogDTO} object
     * @throws IllegalArgumentException if provided {@link DogDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during retrieving {@link OrderDTO} objects
     */
    List<OrderDTO> getByDog(DogDTO dogDTO) throws FacadeException;

    /**
     * Retrieves all {@link OrderDTO} objects relating to provided {@link CustomerDTO} object.
     *
     * @param customerDTO               {@link CustomerDTO} object which relates to searched {@link OrderDTO} objects
     * @return                          list of all {@link OrderDTO} objects relating to provided {@link CustomerDTO} object
     * @throws IllegalArgumentException if provided {@link CustomerDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during retrieving {@link OrderDTO} objects
     */
    List<OrderDTO> getByCustomer(CustomerDTO customerDTO) throws FacadeException;

    /**
     * Retrieves all {@link OrderDTO} objects for given date.
     *
     * @param date                      date to retrieve orders for
     * @return                          list of all {@link OrderDTO} objects for given date
     * @throws IllegalArgumentException if provided {@link LocalDateTime} is {@code null}
     * @throws FacadeException          in case of any underlaying error during retrieving {@link OrderDTO} objects
     */
    List<OrderDTO> getAllOrdersForDay(LocalDateTime date) throws FacadeException;

    /**
     * Retrieves all {@link OrderDTO} objects relating to provided {@link ServiceDTO} object.
     *
     * @param serviceDTO                {@link ServiceDTO} object which relates to searched {@link OrderDTO} objects
     * @return                          list of all {@link OrderDTO} objects relating to provided {@link ServiceDTO} object
     * @throws IllegalArgumentException if provided {@link ServiceDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during retrieving {@link OrderDTO} objects
     */
    List<OrderDTO> getByService(ServiceDTO serviceDTO) throws FacadeException;

    /**
     * Updates attributes of an existing {@link OrderDTO} object.
     *
     * @param orderDTO                  {@link OrderDTO} object with updated attributes
     * @throws IllegalArgumentException if provided {@link OrderDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during updating {@link OrderDTO} objects
     */
    void update(OrderDTO orderDTO) throws FacadeException;

    /**
     * Deletes an existing {@link OrderDTO} entry.
     *
     * @param orderDTO                  {@link OrderDTO} object to delete
     * @throws IllegalArgumentException if provided {@link OrderDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during deleting {@link OrderDTO} objects
     */
    void delete(OrderDTO orderDTO) throws FacadeException;

    /**
     * Returns sum of prices of all order in given time range.
     *
     * @param from                      from time
     * @param to                        to time
     * @return                          sum of order prices
     * @throws IllegalArgumentException if either of provided {@link LocalDateTime} parameters is {@code null}
     * @throws FacadeException          in case of any underlaying error during calculating total money gained
     */
    BigDecimal getTotalAmountGained(LocalDateTime from, LocalDateTime to) throws FacadeException;

    /**
     * Updates Order status do done {@link OrderDTO} entry.
     *
     * @param order                     {@link OrderDTO} object to update
     * @throws IllegalArgumentException if provided {@link OrderDTO} is {@code null}
     * @throws FacadeException          in case of any underlaying error during updating {@link OrderDTO} state
     */
    void orderCompleted(OrderDTO order) throws FacadeException;
}
