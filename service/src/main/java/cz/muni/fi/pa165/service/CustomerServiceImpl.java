package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
public class CustomerServiceImpl implements CustomerService {

    @Override
    public void create(Customer customer) {

    }

    @Override
    public Customer getById(long id) {
        return null;
    }

    @Override
    public Customer getByUsername(String username) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(Customer customer) {

    }
}
