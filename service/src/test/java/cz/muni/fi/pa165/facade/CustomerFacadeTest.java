package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AddressDTO;
import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

        Dog dog = new Dog("Ellie", "stafbull", 6, testingCustomer);
        testingCustomer.addDog(dog);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_customerNull() throws FacadeException {
        customerFacade.create(null);
    }

    @Test(expected = FacadeException.class)
    public void testCreate_customerServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(customerService).create(any(Customer.class));

        customerFacade.create(testingCustomerDTO);
    }

    @Test
    public void testCreate_customerValid() throws ServiceException, FacadeException {
        when(mappingService.mapTo(testingCustomerDTO, Customer.class)).thenReturn(testingCustomer);

        customerFacade.create(testingCustomerDTO);

        verify(mappingService, times(1)).mapTo(testingCustomerDTO, Customer.class);
        verify(customerService, times(1)).create(testingCustomer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_idInvalid() throws FacadeException {
        customerFacade.getById(-1);
    }

    @Test
    public void testGetById_customerDoesNotExist() throws ServiceException, FacadeException {
        when(customerService.getById(anyLong())).thenReturn(null);
        when(mappingService.mapTo(any(Customer.class), eq(CustomerDTO.class))).thenReturn(null);

        CustomerDTO retrievedCustomerDTO = customerFacade.getById(15);

        assertNull(retrievedCustomerDTO);

        verify(customerService, times(1)).getById(15);
        verify(mappingService, times(1)).mapTo(any(Customer.class), eq(CustomerDTO.class));
    }

    @Test(expected = FacadeException.class)
    public void testGetById_customerServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(customerService).getById(anyLong());

        customerFacade.getById(15);
    }

    @Test
    public void testGetById_customerValid() throws ServiceException, FacadeException {
        when(customerService.getById(1)).thenReturn(testingCustomer);
        when(mappingService.mapTo(testingCustomer, CustomerDTO.class)).thenReturn(testingCustomerDTO);

        CustomerDTO retrievedCustomerDTO = customerFacade.getById(1);

        assertNotNull(retrievedCustomerDTO);
        assertEquals(testingCustomerDTO, retrievedCustomerDTO);

        verify(customerService, times(1)).getById(1);
        verify(mappingService, times(1)).mapTo(testingCustomer, CustomerDTO.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByUsername_usernameNull() throws FacadeException {
        customerFacade.getByUsername(null);
    }

    @Test
    public void testGetByUsername_customerWithUsernameDoesNotExist() throws ServiceException, FacadeException {
        String username = testingCustomer.getUsername();

        when(customerService.getByUsername(username)).thenReturn(null);
        when(mappingService.mapTo(any(Customer.class), eq(CustomerDTO.class))).thenReturn(null);

        CustomerDTO retrievedCustomerDTO = customerFacade.getByUsername(username);

        assertNull(retrievedCustomerDTO);

        verify(customerService, times(1)).getByUsername(username);
        verify(mappingService, times(1)).mapTo(any(Customer.class), eq(CustomerDTO.class));
    }

    @Test(expected = FacadeException.class)
    public void testGetByUsername_customerServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(customerService).getByUsername(anyString());

        customerFacade.getByUsername("testing");
    }

    @Test
    public void testGetByUsername_customerValid() throws ServiceException, FacadeException {
        String username = "testingUsr";

        when(customerService.getByUsername(username)).thenReturn(testingCustomer);
        when(mappingService.mapTo(testingCustomer, CustomerDTO.class)).thenReturn(testingCustomerDTO);

        CustomerDTO retrievedCustomerDTO = customerFacade.getByUsername(username);

        assertNotNull(retrievedCustomerDTO);
        assertEquals(testingCustomerDTO, retrievedCustomerDTO);

        verify(customerService, times(1)).getByUsername(username);
        verify(mappingService, times(1)).mapTo(testingCustomer, CustomerDTO.class);
    }

    @Test
    public void testGetAll_noCustomers() throws ServiceException, FacadeException {
        when(customerService.getAll()).thenReturn(Collections.emptyList());
        when(mappingService.mapTo(anyCollectionOf(Customer.class), eq(CustomerDTO.class))).thenReturn(Collections.emptyList());

        List<CustomerDTO> retrievedCustomers = customerFacade.getAll();

        assertNotNull(retrievedCustomers);
        assertTrue(retrievedCustomers.isEmpty());

        verify(customerService, times(1)).getAll();
        verify(mappingService, times(1)).mapTo(anyCollectionOf(Customer.class), eq(CustomerDTO.class));
    }

    @Test(expected = FacadeException.class)
    public void testGetAll_customerServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(customerService).getAll();

        customerFacade.getAll();
    }

    @Test
    public void testGetAll_validCustomers() throws ServiceException, FacadeException {
        CustomerDTO customerDTO1 = new CustomerDTO("customer1", "passwdcust1", "Testing 1", "Customer", new AddressDTO(), "customer1@mail.com", "111 -555- 789");
        CustomerDTO customerDTO2 = new CustomerDTO("customer2", "passwdcust2", "Testing 2", "Customer", new AddressDTO(), "customer2@mail.com", "+426 222-10 5474");

        when(mappingService.mapTo(anyCollectionOf(Customer.class), eq(CustomerDTO.class))).thenReturn(Arrays.asList(testingCustomerDTO, customerDTO1, customerDTO2));

        List<CustomerDTO> retrievedCustomers = customerFacade.getAll();

        assertNotNull(retrievedCustomers);
        assertFalse(retrievedCustomers.isEmpty());
        assertEquals(3, retrievedCustomers.size());
        assertThat(retrievedCustomers, hasItems(testingCustomerDTO, customerDTO1, customerDTO2));

        verify(customerService, times(1)).getAll();
        verify(mappingService, times(1)).mapTo(anyCollectionOf(Customer.class), eq(CustomerDTO.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_customerNull() throws FacadeException {
        customerFacade.update(null);
    }

    @Test(expected = FacadeException.class)
    public void testUpdate_customerDoesNotExist() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(customerService).update(testingCustomer);
        when(mappingService.mapTo(testingCustomerDTO, Customer.class)).thenReturn(testingCustomer);

        customerFacade.update(testingCustomerDTO);
    }

    @Test(expected = FacadeException.class)
    public void testUpdate_customerServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(customerService).update(any(Customer.class));

        customerFacade.update(testingCustomerDTO);
    }

    @Test
    public void testUpdate_customerValid() throws ServiceException, FacadeException {
        when(mappingService.mapTo(testingCustomerDTO, Customer.class)).thenReturn(testingCustomer);

        customerFacade.update(testingCustomerDTO);

        verify(customerService, times(1)).update(testingCustomer);
        verify(mappingService, times(1)).mapTo(testingCustomerDTO, Customer.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_customerNull() throws FacadeException {
        customerFacade.delete(null);
    }

    @Test(expected = FacadeException.class)
    public void testDelete_customerDoesNotExist() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(customerService).delete(testingCustomer);
        when(mappingService.mapTo(testingCustomerDTO, Customer.class)).thenReturn(testingCustomer);

        customerFacade.delete(testingCustomerDTO);
    }

    @Test(expected = FacadeException.class)
    public void testDelete_customerServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(customerService).delete(any(Customer.class));
        when(mappingService.mapTo(any(CustomerDTO.class), eq(Customer.class))).thenReturn(testingCustomer);

        customerFacade.delete(testingCustomerDTO);
    }

    @Test
    public void testDelete_customerValid() throws ServiceException, FacadeException {
        when(mappingService.mapTo(testingCustomerDTO, Customer.class)).thenReturn(testingCustomer);

        customerFacade.delete(testingCustomerDTO);

        verify(customerService, times(1)).delete(testingCustomer);
        verify(mappingService, times(1)).mapTo(testingCustomerDTO, Customer.class);
    }
}