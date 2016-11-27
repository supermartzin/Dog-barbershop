package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EmployeeDTO;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 *
 *
 * @author Denis Richtarik
 * @version 25.10.2016
 */
@Service
@Transactional
public class EmployeeFacadeImpl implements EmployeeFacade {

    @Inject
    private EmployeeService employeeService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public void create(EmployeeDTO employee) throws DAOException {
        Employee employeeEntity = beanMappingService.mapTo(employee, Employee.class);
        employeeService.create(employeeEntity);
    }

    @Override
    public EmployeeDTO getById(long id) {
        return beanMappingService.mapTo(employeeService.getById(id), EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getByUsername(String username) throws DAOException {
        return beanMappingService.mapTo(employeeService.getByUsername(username), EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAll() throws DAOException {
        return beanMappingService.mapTo(employeeService.getAll(), EmployeeDTO.class);
    }

    @Override
    public void update(EmployeeDTO employee) throws DAOException {
        Employee employeeEntity = beanMappingService.mapTo(employee, Employee.class);
        employeeService.update(employeeEntity);
    }

    @Override
    public void delete(EmployeeDTO employee) throws DAOException {
        Employee employeeEntity = beanMappingService.mapTo(employee, Employee.class);
        employeeService.delete(employeeEntity);
    }
}
