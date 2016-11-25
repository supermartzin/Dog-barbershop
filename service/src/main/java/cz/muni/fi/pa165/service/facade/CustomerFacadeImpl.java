package cz.muni.fi.pa165.customer.facade;

import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.facade.CustomerFacade;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CustomerService;
import cz.muni.fi.pa165.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
public class CustomerFacadeImpl implements CustomerFacade {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void create(CustomerDTO customer) throws DAOException {
        Customer customerEntity = beanMappingService.mapTo(customer, Customer.class);
        customerService.create(customerEntity);
    }

    @Override
    public CustomerDTO getById(long id) {
        return beanMappingService.mapTo(customerService.getById(id), CustomerDTO.class);
    }

    @Override
    public CustomerDTO getByUsername(String username) throws DAOException {
        return beanMappingService.mapTo(customerService.getByUsername(username), CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getAll() throws DAOException {
        return beanMappingService.mapTo(customerService.getAll(), CustomerDTO.class);
    }

    @Override
    public void update(CustomerDTO customer) throws DAOException {
        Customer customerEntity = beanMappingService.mapTo(customer, Customer.class);
        customerService.update(customerEntity);
    }

    @Override
    public void delete(CustomerDTO customer) throws DAOException {
        Customer customerEntity = beanMappingService.mapTo(customer, Customer.class);
        customerService.delete(customerEntity);
    }
}
