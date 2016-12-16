package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EmployeeDTO;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Denis Richtarik
 * @version 25.10.2016
 */
@Service
@Transactional
public class EmployeeFacadeImpl implements EmployeeFacade {

    private final EmployeeService employeeService;
    private final BeanMappingService beanMappingService;

    @Inject
    public EmployeeFacadeImpl(EmployeeService employeeService, BeanMappingService beanMappingService) {
        this.employeeService = employeeService;
        this.beanMappingService = beanMappingService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(EmployeeDTO employeeDTO) throws FacadeException {
        if (employeeDTO == null)
            throw new IllegalArgumentException("EmployeeDTO is null");

        try {
            Employee employee = beanMappingService.mapTo(employeeDTO, Employee.class);
            employeeService.create(employee);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeDTO getById(long id) throws FacadeException {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be less than 0");

        try {
            return beanMappingService.mapTo(employeeService.getById(id), EmployeeDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeDTO getByUsername(String username) throws FacadeException {
        if (username == null)
            throw new IllegalArgumentException("username is null");

        try {
            return beanMappingService.mapTo(employeeService.getByUsername(username), EmployeeDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EmployeeDTO> getAll() throws FacadeException {
        try {
            return beanMappingService.mapTo(employeeService.getAll(), EmployeeDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(EmployeeDTO employeeDTO) throws FacadeException {
        if (employeeDTO == null)
            throw new IllegalArgumentException("EmployeeDTO is null");

        try {
            Employee employee = beanMappingService.mapTo(employeeDTO, Employee.class);
            employeeService.update(employee);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(EmployeeDTO employeeDTO) throws FacadeException {
        if (employeeDTO == null)
            throw new IllegalArgumentException("EmployeeDTO is null");

        try {
            Employee employee = beanMappingService.mapTo(employeeDTO, Employee.class);
            employeeService.delete(employee);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }
}
