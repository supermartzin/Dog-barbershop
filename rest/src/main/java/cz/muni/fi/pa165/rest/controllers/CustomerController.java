package cz.muni.fi.pa165.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.CustomerFacade;
import cz.muni.fi.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Dominik Gmiterko
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    
    private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Inject
    private CustomerFacade customerFacade;

    /**
     * Returns all customers
     *
     * @return list of UserDTOs
     * @throws JsonProcessingException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<CustomerDTO> getAll() throws JsonProcessingException, FacadeException {

        logger.debug("REST Customer getAll");

        return customerFacade.getAll();
    }

    /**
     * Return customer according to id
     * 
     * @param id user identifier
     * @return CustomerDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final CustomerDTO getById(@PathVariable("id") long id) throws FacadeException {

        logger.debug("REST Customer getById {}", id);

        CustomerDTO customer = customerFacade.getById(id);
         if (customer == null){
            throw new ResourceNotFoundException();
         }
         return customer;
    }

    /**
     * Create new customer for given data
     *
     * @param customer
     * @return
     * @throws FacadeException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CustomerDTO create(@RequestBody CustomerDTO customer) throws FacadeException {

        logger.debug("REST Customer create");

        try {
            customerFacade.create(customer);
        } catch (FacadeException e) {
            throw new ResourceAlreadyExistingException(e);
        }

        return customerFacade.getById(customer.getId());
    }

    /**
     * Delete customer
     *
     * @param id
     * @throws FacadeException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void delete(@PathVariable("id") long id) throws FacadeException {

        logger.debug("REST Customer delete {}", id);

        CustomerDTO customer = customerFacade.getById(id);

        if(customer == null) {
            throw new ResourceNotFoundException();
        }

        customerFacade.delete(customer);
    }

    /**
     * Update information about customer
     *
     * @param id Id of customer
     * @param customer Customer
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CustomerDTO update(@PathVariable("id") long id, @RequestBody CustomerDTO customer) throws Exception {

        logger.debug("REST Customer update {}", id);

        customer.setId(id);
        customerFacade.update(customer);

        return customerFacade.getById(id);
    }

}
