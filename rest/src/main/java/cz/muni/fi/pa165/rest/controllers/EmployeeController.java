package cz.muni.fi.pa165.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.muni.fi.pa165.dto.EmployeeDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.EmployeeFacade;
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
@RequestMapping("/employee")
public class EmployeeController {
    
    final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Inject
    private EmployeeFacade employeeFacade;

    /**
     * Get all employees
     *
     * @return list of UserDTOs
     * @throws JsonProcessingException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<EmployeeDTO> getAll() throws JsonProcessingException, FacadeException {
        return employeeFacade.getAll();
    }

    /**
     * Get employee by given id
     * 
     * @param id user identifier
     * @return EmployeeDTO
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

    /**
     * Create new employee for given data
     *
     * @param employee Employee data
     * @return EmployeeDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EmployeeDTO create(@RequestBody EmployeeDTO employee) throws FacadeException {

        logger.debug("REST Employee create");

        try {
            employeeFacade.create(employee);
        } catch (FacadeException e) {
            throw new ResourceAlreadyExistingException();
        }

        return employeeFacade.getById(employee.getId());
    }

    /**
     * Delete employee
     *
     * @param id
     * @throws FacadeException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void delete(@PathVariable("id") long id) throws FacadeException {

        logger.debug("REST Employee delete {}", id);

        EmployeeDTO employee = employeeFacade.getById(id);

        if(employee == null) {
            throw new ResourceNotFoundException();
        }

        employeeFacade.delete(employee);
    }

    /**
     * Update information about employee
     *
     * @param id Id of employee
     * @param employee Employee
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EmployeeDTO update(@PathVariable("id") long id, @RequestBody EmployeeDTO employee) throws Exception {

        logger.debug("REST Employee update {}", id);

        employee.setId(id);
        employeeFacade.update(employee);

        return employeeFacade.getById(id);
    }
}
