package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.CustomerFacade;
import cz.muni.fi.pa165.rest.exceptions.EntityAlreadyExistsException;
import cz.muni.fi.pa165.rest.exceptions.EntityNotFoundException;
import cz.muni.fi.pa165.rest.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author Dominik Gmiterko
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerFacade customerFacade;

    public CustomerController(CustomerFacade customerFacade) {
        if (customerFacade == null)
            throw new IllegalArgumentException("CustomerFacade is null");

        this.customerFacade = customerFacade;
    }

    /**
     * Returns all customers
     *
     * @return list of UserDTOs
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<Collection<CustomerDTO>> getAll() throws FacadeException {
        LOGGER.debug("Get all Customers");

        return new Response<>(customerFacade.getAll(), "success", HttpStatus.OK);
    }

    /**
     * Return customer according to id
     *
     * @param id user identifier
     * @return CustomerDTO
     * @throws EntityNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<CustomerDTO> getById(@PathVariable("id") long id) throws FacadeException {
        LOGGER.debug("Get Customer by ID {}", id);

        CustomerDTO customerDTO = customerFacade.getById(id);

        if (customerDTO == null)
            throw new EntityNotFoundException("Customer");

        return new Response<>(customerDTO, "success", HttpStatus.OK);
    }

    /**
     * Create new customer for given data
     *
     * @param customerDTO
     * @return
     * @throws FacadeException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<CustomerDTO> create(@RequestBody CustomerDTO customerDTO) {
        LOGGER.debug("Create new Customer");

        try {
            customerFacade.create(customerDTO);

            CustomerDTO createdCustomerDTO = customerFacade.getById(customerDTO.getId());

            return new Response<>(createdCustomerDTO, "success", HttpStatus.OK);
        } catch (FacadeException fEx) {
            throw new EntityAlreadyExistsException("Customer", fEx);
        }
    }

    /**
     * Update information about customerDTO
     *
     * @param id       Id of customerDTO
     * @param customerDTO Customer
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<CustomerDTO> update(@PathVariable("id") long id, @RequestBody CustomerDTO customerDTO) {
        LOGGER.debug("Update Customer with ID {}", id);

        try {
            customerDTO.setId(id);
            customerFacade.update(customerDTO);

            return new Response<>(customerFacade.getById(id), "success", HttpStatus.OK);
        } catch (FacadeException fEx) {
            throw new EntityNotFoundException("Customer", fEx);
        }
    }

    /**
     * Delete customer
     *
     * @param id
     * @throws FacadeException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<Boolean> delete(@PathVariable("id") long id) throws FacadeException {
        LOGGER.debug("Delete Customer with ID {}", id);

        CustomerDTO customerDTO = customerFacade.getById(id);

        if (customerDTO == null)
            return new Response<>(false, "customer " + id + " not found", HttpStatus.BAD_REQUEST);

        customerFacade.delete(customerDTO);

        return new Response<>(true, "success", HttpStatus.OK);
    }
}
