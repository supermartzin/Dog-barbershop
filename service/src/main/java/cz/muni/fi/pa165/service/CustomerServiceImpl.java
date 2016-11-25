package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CustomerDAO;
import cz.muni.fi.pa165.dao.EmployeeDAO;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.exceptions.DAOException;

import javax.inject.Inject;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
public class CustomerServiceImpl implements CustomerService {

    @Inject
    private CustomerDAO customerDAO;

    @Override
    public void create(Customer customer) throws DAOException {
        customerDAO.create(customer);
    }

    @Override
    public Customer getById(long id) {
        return customerDAO.getById(id);
    }

    @Override
    public Customer getByUsername(String username) throws DAOException {
        return customerDAO.getByUsername(username);
    }

    @Override
    public List<Customer> getAll() throws DAOException {
        return customerDAO.getAll();
    }

    @Override
    public void update(Customer customer) throws DAOException {
        customerDAO.update(customer);
    }

    @Override
    public void delete(Customer customer) throws DAOException {
        customerDAO.delete(customer);
    }
}
