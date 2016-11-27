package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.EmployeeDAO;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 *
 *
 * @author Denis Richtarik
 * @version 25.10.2016
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    @Inject
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public void create(Employee employee) throws DAOException {
        employeeDAO.create(employee);
    }

    @Override
    public Employee getById(long id) {
        return employeeDAO.getById(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDAO.getAll();
    }

    @Override
    public Employee getByUsername(String username) throws DAOException {
        return employeeDAO.getByUsername(username);
    }

    @Override
    public void update(Employee employee) throws DAOException {
        employeeDAO.update(employee);
    }

    @Override
    public void delete(Employee employee) throws DAOException {
        employeeDAO.delete(employee);
    }
}
