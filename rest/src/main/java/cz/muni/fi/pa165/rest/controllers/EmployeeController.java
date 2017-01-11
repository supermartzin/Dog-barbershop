package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.EmployeeDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.EmployeeFacade;
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
@RequestMapping("/employees")
public class EmployeeController {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeFacade employeeFacade;

    public EmployeeController(EmployeeFacade employeeFacade) {
        if (employeeFacade == null)
            throw new IllegalArgumentException("EmployeeFacade is null");

        this.employeeFacade = employeeFacade;
    }

    /**
     * Get all employees
     *
     * @return list of UserDTOs
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<Collection<EmployeeDTO>> getAll() throws FacadeException {
        LOGGER.debug("Get all Employees");

        return new Response<>(employeeFacade.getAll(), "success", HttpStatus.OK);
    }

    /**
     * Get employee by given id
     * 
     * @param id user identifier
     * @return EmployeeDTO
     * @throws EntityNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<EmployeeDTO> getById(@PathVariable("id") long id) throws FacadeException {
        LOGGER.debug("Get Employee by ID {}", id);

        EmployeeDTO employeeDTO = employeeFacade.getById(id);

        if (employeeDTO == null)
            throw new EntityNotFoundException("Employee");

        return new Response<>(employeeDTO, "success", HttpStatus.OK);
    }

    /**
     * Create new employeeDTO for given data
     *
     * @param employeeDTO Employee data
     * @return EmployeeDTO
     * @throws EntityNotFoundException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<EmployeeDTO> create(@RequestBody EmployeeDTO employeeDTO) {
        LOGGER.debug("Create new Employee");

        try {
            employeeFacade.create(employeeDTO);

            return new Response<>(employeeFacade.getById(employeeDTO.getId()), "success", HttpStatus.OK);
        } catch (FacadeException fEx) {
            throw new EntityAlreadyExistsException("Employee", fEx);
        }
    }

    /**
     * Update information about employeeDTO
     *
     * @param id Id of employeeDTO
     * @param employeeDTO Employee
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<EmployeeDTO> update(@PathVariable("id") long id, @RequestBody EmployeeDTO employeeDTO) {
        LOGGER.debug("Update Employee with ID {}", id);

        try {
            employeeDTO.setId(id);
            employeeFacade.update(employeeDTO);

            return new Response<>(employeeFacade.getById(id), "success", HttpStatus.OK);
        } catch (FacadeException fEx) {
            throw new EntityNotFoundException("Employee", fEx);
        }
    }

    /**
     * Delete employee
     *
     * @param id
     * @throws FacadeException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Response<Boolean> delete(@PathVariable("id") long id) throws FacadeException {
        LOGGER.debug("Delete Employee with ID {}", id);

        EmployeeDTO employeeDTO = employeeFacade.getById(id);

        if (employeeDTO == null)
            return new Response<>(false, "employee " + id + " not found", HttpStatus.BAD_REQUEST);

        employeeFacade.delete(employeeDTO);

        return new Response<>(true, "success", HttpStatus.OK);
    }
}
