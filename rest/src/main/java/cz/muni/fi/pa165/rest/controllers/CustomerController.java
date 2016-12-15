package cz.muni.fi.pa165.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.facade.CustomerFacade;
import cz.muni.fi.pa165.rest.ApiUris;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collection;

/**
 * REST Controller for Users
 * 
 * @author brossi
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_CUSTOMER)
public class CustomerController {
    
    final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Inject
    private CustomerFacade customerFacade;

    /**
     * returns all customers
     *
     * @return list of UserDTOs
     * @throws JsonProcessingException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<CustomerDTO> getAll() throws JsonProcessingException, DAOException {
        return customerFacade.getAll();
    }

    /**
     *
     * getting user according to id
     * 
     * @param id user identifier
     * @return UserDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final CustomerDTO getById(@PathVariable("id") long id) throws Exception {
         CustomerDTO customer = customerFacade.getById(id);
         if (customer == null){
            throw new ResourceNotFoundException();
         }
         return customer;
    }
}
