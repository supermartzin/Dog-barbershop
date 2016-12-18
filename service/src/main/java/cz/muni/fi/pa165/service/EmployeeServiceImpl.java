package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.EmployeeDAO;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.exceptions.ServiceException;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Denis Richtarik
 */
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Employee employee) throws ServiceException {
        if (employee == null)
            throw new IllegalArgumentException("Employee is null");

        try {
            employeeDAO.create(employee);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getById(long id) throws ServiceException {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be less than 0");

        return employeeDAO.getById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAll() throws ServiceException {
        return employeeDAO.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getByUsername(String username) throws ServiceException {
        if (username == null)
            throw new IllegalArgumentException("username is null");

        try {
            return employeeDAO.getByUsername(username);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Employee employee) throws ServiceException {
        if (employee == null)
            throw new IllegalArgumentException("Employee is null");

        try {
            employeeDAO.update(employee);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Employee employee) throws ServiceException {
        if (employee == null)
            throw new IllegalArgumentException("Employee is null");

        try {
            employeeDAO.delete(employee);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }
}
