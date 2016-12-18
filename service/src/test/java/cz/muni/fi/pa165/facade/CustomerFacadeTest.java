package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AddressDTO;
import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for correct implementation of {@link CustomerFacadeImpl} class
 *
 * @author Martin Vrábel
 * @version 22.11.2016 14:08
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerFacadeTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private BeanMappingService mappingService;

    private CustomerFacade customerFacade;

    private CustomerDTO testingCustomerDTO;
    private Customer testingCustomer;

    @Before
    public void setUp() throws Exception {
        // init Mockito
        MockitoAnnotations.initMocks(this);

        customerFacade = new CustomerFacadeImpl(customerService, mappingService);

        testingCustomerDTO = new CustomerDTO("testingUsr", "passwd225", "George", "Michael",
                new AddressDTO("Testing Street", 25, "Sacramento", 22569, "Testingland"),
                "georgem@testing.com", "555684123");
        testingCustomer = new Customer("testingUsr", "passwd225", "George", "Michael",
                new Address("Testing Street", 25, "Sacramento", 22569, "Testingland"),
                "georgem@testing.com", "555684123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_null() throws FacadeException {
        customerFacade.create(null);
    }

    @Test
    public void testCreate_valid() throws FacadeException {
    }

    @Test
    public void getById() throws FacadeException {

    }

    @Test
    public void getByUsername() throws FacadeException {

    }

    @Test
    public void getAll() throws FacadeException {

    }

    @Test
    public void update() throws FacadeException {

    }

    @Test
    public void delete() throws FacadeException {

    }
}