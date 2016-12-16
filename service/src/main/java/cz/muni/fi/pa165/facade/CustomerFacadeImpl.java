package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
@Service
@Transactional
public class CustomerFacadeImpl implements CustomerFacade {

    private final CustomerService customerService;
    private final BeanMappingService beanMappingService;

    @Inject
    public CustomerFacadeImpl(CustomerService customerService, BeanMappingService beanMappingService) {
        this.customerService = customerService;
        this.beanMappingService = beanMappingService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(CustomerDTO customerDTO) throws FacadeException {
        if (customerDTO == null)
            throw new IllegalArgumentException("CustomerDTO is null");

        try {
            Customer customer = beanMappingService.mapTo(customerDTO, Customer.class);
            customerService.create(customer);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDTO getById(long id) throws FacadeException {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be less than 0");

        try {
            return beanMappingService.mapTo(customerService.getById(id), CustomerDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDTO getByUsername(String username) throws FacadeException {
        if (username == null)
            throw new IllegalArgumentException("Username is null");

        try {
            return beanMappingService.mapTo(customerService.getByUsername(username), CustomerDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CustomerDTO> getAll() throws FacadeException {
        try {
            return beanMappingService.mapTo(customerService.getAll(), CustomerDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(CustomerDTO customerDTO) throws FacadeException {
        if (customerDTO == null)
            throw new IllegalArgumentException("CustomerDTO is null");

        try {
            Customer customer = beanMappingService.mapTo(customerDTO, Customer.class);
            customerService.update(customer);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(CustomerDTO customerDTO) throws FacadeException {
        if (customerDTO == null)
            throw new IllegalArgumentException("CustomerDTO is null");

        try {
            Customer customer = beanMappingService.mapTo(customerDTO, Customer.class);
            customerService.delete(customer);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }
}
