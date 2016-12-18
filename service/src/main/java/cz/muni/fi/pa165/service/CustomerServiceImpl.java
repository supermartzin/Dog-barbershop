package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CustomerDAO;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.exceptions.ServiceException;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Customer customer) throws ServiceException {
        if (customer == null)
            throw new IllegalArgumentException("Customer is null");

        try {
            customerDAO.create(customer);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer getById(long id) throws ServiceException {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be less than 0");

        return customerDAO.getById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer getByUsername(String username) throws ServiceException {
        if (username == null)
            throw new IllegalArgumentException("username is null");

        try {
            return customerDAO.getByUsername(username);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> getAll() throws ServiceException {
        try {
            return customerDAO.getAll();
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Customer customer) throws ServiceException {
        if (customer == null)
            throw new IllegalArgumentException("Customer is null");

        try {
            customerDAO.update(customer);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Customer customer) throws ServiceException {
        if (customer == null)
            throw new IllegalArgumentException("Customer is null");

        try {
            customerDAO.delete(customer);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }
}
