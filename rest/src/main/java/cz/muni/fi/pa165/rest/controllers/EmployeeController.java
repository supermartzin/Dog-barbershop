package cz.muni.fi.pa165.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.muni.fi.pa165.dto.EmployeeDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.EmployeeFacade;
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
@RequestMapping(ApiUris.ROOT_URI_EMPLOYEE)
public class EmployeeController {
    
    final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Inject
    private EmployeeFacade employeeFacade;

    /**
     * returns all customers
     *
     * @return list of UserDTOs
     * @throws JsonProcessingException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<EmployeeDTO> getAll() throws JsonProcessingException, FacadeException {
        return employeeFacade.getAll();
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
    public final EmployeeDTO getById(@PathVariable("id") long id) throws Exception {
         EmployeeDTO employee = employeeFacade.getById(id);
         if (employee == null){
            throw new ResourceNotFoundException();
         }
         return employee;
    }
}
