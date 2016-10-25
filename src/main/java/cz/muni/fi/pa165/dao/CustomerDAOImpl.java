package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Customer;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Martin Vrábel
 * @version 24.10.2016 20:37
 */
public class CustomerDAOImpl implements CustomerDAO {

    private final EntityManagerFactory factory;

    public CustomerDAOImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(Customer customer) {
    }

    @Override
    public Customer getById(long id) {
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
