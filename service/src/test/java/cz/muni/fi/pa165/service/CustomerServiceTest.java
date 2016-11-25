package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CustomerDAO;
import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.*;


/**
 * Tests for correct contract implementation defined by {@link CustomerService} interface
 *
 * @author Dominik Gmiterko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-configs/main-config.xml"})
public class CustomerServiceTest {

    @Mock
    private CustomerDAO customerDAO;

    @Autowired
    @InjectMocks
    private CustomerService customerService;

    private Customer testingCustomer;
    private Address address;
    private Dog buddy;
    private Dog charlie;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        address = new Address("Testing Avenue", 25, "Testero", 2356, "Testing Republic");
        testingCustomer = new Customer("testing", "password", "John", "Tester", address,
                "testing.customer@mail.com", "755468236");
        buddy = new Dog("Buddy", "American Foxhound", 5, testingCustomer);
        charlie = new Dog("Charlie", "Neapolitan Mastiff", 9, testingCustomer);

        // add dogs
        testingCustomer.addDog(buddy);
        testingCustomer.addDog(charlie);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_customerNull() throws Exception {

        doThrow(new IllegalArgumentException()).when(customerDAO).create(null);

        customerService.create(null);
    }

    @Test
    public void testCreate_customerValid() throws Exception {
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDAO, times(1)).create(customerCaptor.capture());

        customerService.create(testingCustomer);

        assertDeepEquals(testingCustomer, customerCaptor.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_idInvalid() throws Exception {

        when(customerDAO.getById(-1)).thenThrow(new IllegalArgumentException());

        customerService.getById(-1);
    }

    @Test
    public void testGetById_customerDoesNotExists() throws Exception {

        when(customerDAO.getById(100)).thenReturn(null);

        Customer retrievedCustomer = customerService.getById(100);

        Assert.assertNull(retrievedCustomer);
    }

    @Test
    public void testGetById_customerValid() throws Exception {

        when(customerDAO.getById(1)).thenReturn(testingCustomer);

        // retrieve customer
        Customer retrievedCustomer = customerService.getById(1);

        Assert.assertNotNull(retrievedCustomer);
        assertDeepEquals(testingCustomer, retrievedCustomer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByUsername_usernameNull() throws Exception {

        when(customerDAO.getByUsername(null)).thenThrow(new IllegalArgumentException());

        customerService.getByUsername(null);
    }

    @Test
    public void testGetByUsername_customerDoesNotExist() throws Exception {
        String username = "testing";

        when(customerDAO.getByUsername(username)).thenReturn(null);

        Customer customer = customerService.getByUsername(username);

        Assert.assertNull(customer);
    }

    @Test
    public void testGetByUsername_customerExists() throws Exception {
        // create customer in database
        when(customerDAO.getByUsername(testingCustomer.getUsername())).thenReturn(testingCustomer);

        Customer customer = customerService.getByUsername(testingCustomer.getUsername());

        Assert.assertNotNull(customer);
        assertDeepEquals(testingCustomer, customer);
    }

    @Test
    public void testGetAll_noCustomers() throws Exception {

        when(customerDAO.getAll()).thenReturn(new ArrayList<Customer>());

        List<Customer> allCustomers = customerService.getAll();

        Assert.assertNotNull(allCustomers);
        Assert.assertTrue(allCustomers.isEmpty());
    }

    @Test
    public void testGetAll_customersExist() throws Exception {
        ArrayList<Customer> mockedCustomers = new ArrayList<Customer>();
        mockedCustomers.add(testingCustomer);
        Customer customer = new Customer("testmaster", "masterpassword", "Albert", "Master",
                new Address("Botanicka", 68, "Brno", 62000, "Czech Republic"),
                "testmaster@mail.com", "+421910325478");
        customer.addDog(buddy);
        mockedCustomers.add(customer);

        when(customerDAO.getAll()).thenReturn(mockedCustomers);

        // get all customers from database
        List<Customer> allCustomers = customerService.getAll();

        Assert.assertNotNull(allCustomers);
        Assert.assertFalse(allCustomers.isEmpty());
        Assert.assertEquals(2, allCustomers.size());
        Assert.assertThat(allCustomers, hasItems(testingCustomer, customer));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_customerNull() throws Exception {

        doThrow(new IllegalArgumentException()).when(customerDAO).update(null);

        customerService.update(null);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_customerDoesNotExist() throws Exception {

        doThrow(new DAOException()).when(customerDAO).update(testingCustomer);

        customerService.update(testingCustomer);
    }

    @Test
    public void testUpdate_customerValid() throws Exception {
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDAO, times(1)).update(customerCaptor.capture());

        // update username
        testingCustomer.setUsername("new_username");
        testingCustomer.setPassword("new_pass_12345");
        testingCustomer.setAddress(new Address("New testing St.", 25, "New Testing City", 96523, "Zanzibar"));
        testingCustomer.setEmail("newmail@mail.com");
        testingCustomer.setPhone("9963215698");

        customerService.update(testingCustomer);

        assertDeepEquals(testingCustomer, customerCaptor.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_customerNull() throws Exception {

        doThrow(new IllegalArgumentException()).when(customerDAO).delete(null);

        customerService.delete(null);
    }

    @Test(expected = DAOException.class)
    public void testDelete_customerDoesNotExist() throws Exception {

        doThrow(new DAOException()).when(customerDAO).delete(testingCustomer);

        customerService.delete(testingCustomer);
    }

    @Test
    public void testDelete_correctCustomerDeleted() throws Exception {
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDAO, times(1)).delete(customerCaptor.capture());

        customerService.delete(testingCustomer);

        assertDeepEquals(testingCustomer, customerCaptor.getValue());
    }


    private void assertDeepEquals(Customer expected, Customer actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());
        Assert.assertEquals(expected.getAddress(), actual.getAddress());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getPhone(), actual.getPhone());
        for (Dog dog : expected.getDogs()) {
            Assert.assertThat(actual.getDogs(), hasItem(dog));
        }
    }
}