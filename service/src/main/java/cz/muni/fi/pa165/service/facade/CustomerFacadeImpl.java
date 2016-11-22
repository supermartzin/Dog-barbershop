package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.facade.CustomerFacade;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
public class CustomerFacadeImpl implements CustomerFacade {

    @Override
    public void create(CustomerDTO customer) {

    }

    @Override
    public CustomerDTO getById(long id) {
        return null;
    }

    @Override
    public CustomerDTO getByUsername(String username) {
        return null;
    }

    @Override
    public List<CustomerDTO> getAll() {
        return null;
    }

    @Override
    public void update(CustomerDTO customer) {

    }

    @Override
    public void delete(CustomerDTO customer) {

    }
}
