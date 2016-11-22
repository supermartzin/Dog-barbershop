package cz.muni.fi.pa165.dao;

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
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

/**
 * Tests for correct contract implementation defined by {@link CustomerDAO} interface
 *
 * @author Martin Vrábel
 * @version 24.10.2016 20:55
 */
@Transactional
@Rollback(false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-configs/main-config.xml"})
public class CustomerDAOTest {

    @PersistenceContext
    private EntityManager manager;

    @Inject
    private CustomerDAO customerDAO;

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
        customerDAO.create(null);
    }

    @Test(expected = DAOException.class)
    public void testCreate_usernameNotSet() throws Exception {
        testingCustomer.setUsername(null);

        customerDAO.create(testingCustomer);
    }

    @Test(expected = DAOException.class)
    public void testCreate_passwordNotSet() throws Exception {
        testingCustomer.setPassword(null);

        customerDAO.create(testingCustomer);
    }

    @Test(expected = DAOException.class)
    public void testCreate_passwordInvalid() throws Exception {
        testingCustomer.setPassword("pass");

        customerDAO.create(testingCustomer);
    }

    @Test
    public void testCreate_addressNull() throws Exception {
        testingCustomer.setAddress(null);

        customerDAO.create(testingCustomer);

        // get persisted customer from database
        Customer retrievedCustomer = manager.find(Customer.class, testingCustomer.getId());

        Assert.assertNotNull(retrievedCustomer);
        Assert.assertNull(retrievedCustomer.getAddress());
    }

    @Test(expected = DAOException.class)
    public void testCreate_emailInvalid() throws Exception {
        testingCustomer.setEmail("this is invalid @mail");

        customerDAO.create(testingCustomer);
    }

    @Test(expected = DAOException.class)
    public void testCreate_phoneInvalid() throws Exception {
        testingCustomer.setPhone("-456 +985-8965aaa");

        customerDAO.create(testingCustomer);
    }

    @Test
    public void testCreate_usernameAlreadyExist() throws Exception {
        // create first customer
        manager.persist(testingCustomer);

        Customer customer = new Customer();
        customer.setUsername(testingCustomer.getUsername());
        customer.setPassword("psswd252");
        customer.setFirstName("Emily");
        customer.setLastName("Tester");
        customer.setAddress(new Address("Testing street", 6, "City", 452361, "TSK"));
        customer.setEmail("emily@testers.com");
        customer.setPhone("664895217");
        customer.addDog(charlie);
        customer.addDog(buddy);

        // create second customer with the same username
        exception.expect(JpaSystemException.class);
        customerDAO.create(customer);
    }

    @Test
    public void testCreate_customerValid() throws Exception {
        customerDAO.create(testingCustomer);

        // Assert
        Assert.assertTrue(testingCustomer.getId() > 0);

        Customer retrievedCustomer = manager.find(Customer.class, testingCustomer.getId());

        Assert.assertNotNull(retrievedCustomer);
        assertDeepEquals(testingCustomer, retrievedCustomer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_idInvalid() throws Exception {
        customerDAO.getById(-1);
    }

    @Test
    public void testGetById_customerDoesNotExists() throws Exception {
        Customer retrievedCustomer = customerDAO.getById(100);

        Assert.assertNull(retrievedCustomer);
    }

    @Test
    public void testGetById_customerValid() throws Exception {
        // create customer in database
        manager.persist(testingCustomer);

        // retrieve customer
        Customer retrievedCustomer = customerDAO.getById(testingCustomer.getId());

        Assert.assertNotNull(retrievedCustomer);
        assertDeepEquals(testingCustomer, retrievedCustomer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByUsername_usernameNull() throws Exception {
        customerDAO.getByUsername(null);
    }

    @Test
    public void testGetByUsername_customerDoesNotExist() throws Exception {
        String username = "testing";

        Customer customer = customerDAO.getByUsername(username);

        Assert.assertNull(customer);
    }

    @Test
    public void testGetByUsername_customerExists() throws Exception {
        // create customer in database
        manager.persist(testingCustomer);

        Customer customer = customerDAO.getByUsername(testingCustomer.getUsername());

        Assert.assertNotNull(customer);
        assertDeepEquals(testingCustomer, customer);
    }

    @Test
    public void testGetAll_noCustomers() throws Exception {
        List<Customer> allCustomers = customerDAO.getAll();

        Assert.assertNotNull(allCustomers);
        Assert.assertTrue(allCustomers.isEmpty());
    }

    @Test
    public void testGetAll_customersExist() throws Exception {
        Customer customer = new Customer("testmaster", "masterpassword", "Albert", "Master",
                new Address("Botanicka", 68, "Brno", 62000, "Czech Republic"),
                "testmaster@mail.com", "+421910325478");
        customer.addDog(buddy);

        // add some customers into database
        manager.persist(testingCustomer);
        manager.persist(customer);

        // get all customers from database
        List<Customer> allCustomers = customerDAO.getAll();

        Assert.assertNotNull(allCustomers);
        Assert.assertFalse(allCustomers.isEmpty());
        Assert.assertEquals(2, allCustomers.size());
        Assert.assertThat(allCustomers, hasItems(testingCustomer, customer));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_customerNull() throws Exception {
        customerDAO.update(null);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_customerDoesNotExist() throws Exception {
        customerDAO.update(testingCustomer);
    }

    @Test
    @Rollback
    public void testUpdate_usernameInvalid() throws Exception {
        // create customer in database
        manager.persist(testingCustomer);

        // update username
        testingCustomer.setUsername(null);

        exception.expect(DAOException.class);
        customerDAO.update(testingCustomer);
    }

    @Test
    @Rollback
    public void testUpdate_passwordNull() throws Exception {
        // create customer in database
        manager.persist(testingCustomer);

        // update username
        testingCustomer.setPassword(null);

        exception.expect(DAOException.class);
        customerDAO.update(testingCustomer);
    }

    @Test
    @Rollback
    public void testUpdate_passwordInvalid() throws Exception {
        // create customer in database
        manager.persist(testingCustomer);

        // update username
        testingCustomer.setPassword("pass");

        exception.expect(DAOException.class);
        customerDAO.update(testingCustomer);
    }

    @Test
    public void testUpdate_addressNull() throws Exception {
        // create customer in database
        manager.persist(testingCustomer);

        // update username
        testingCustomer.setAddress(null);

        customerDAO.update(testingCustomer);

        // get updated customer from database
        Customer updatedCustomer = manager.find(Customer.class, testingCustomer.getId());

        Assert.assertNull(updatedCustomer.getAddress());
    }

    @Test
    @Rollback
    public void testUpdate_emailInvalid() throws Exception {
        // create customer in database
        manager.persist(testingCustomer);

        // update username
        testingCustomer.setEmail("this is invalid @mail");

        exception.expect(DAOException.class);
        customerDAO.update(testingCustomer);
    }

    @Test
    @Rollback
    public void testUpdate_phoneInvalid() throws Exception {
        // create customer in database
        manager.persist(testingCustomer);

        // update username
        testingCustomer.setPhone("-456 +985-8965aaa");

        exception.expect(DAOException.class);
        customerDAO.update(testingCustomer);
    }

    @Test
    public void testUpdate_customerValid() throws Exception {
        // create customer in database
        manager.persist(testingCustomer);

        // update username
        testingCustomer.setUsername("new_username");
        testingCustomer.setPassword("new_pass_12345");
        testingCustomer.setAddress(new Address("New testing St.", 25, "New Testing City", 96523, "Zanzibar"));
        testingCustomer.setEmail("newmail@mail.com");
        testingCustomer.setPhone("9963215698");

        customerDAO.update(testingCustomer);

        // get updated customer from database
        Customer updatedCustomer = manager.find(Customer.class, testingCustomer.getId());

        Assert.assertNotNull(updatedCustomer);
        assertDeepEquals(testingCustomer, updatedCustomer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_customerNull() throws Exception {
        customerDAO.delete(null);
    }

    @Test(expected = DAOException.class)
    public void testDelete_customerDoesNotExist() throws Exception {
        customerDAO.delete(testingCustomer);
    }

    @Test
    @Rollback
    public void testDelete_correctCustomerDeleted() throws Exception {
        // create custoemrs in database
        Customer customer1 = new Customer("testmaster", "masterpassword", "Albert", "Master",
                new Address("Botanicka", 68, "Brno", 62000, "Czech Republic"),
                "testmaster@mail.com", "+421910325478");
        Customer customer2 = new Customer("secondtest", "testpass", "Anna", "Testing",
                address, "anna@testing.com", "+421695856321");

        manager.persist(testingCustomer);
        manager.persist(customer1);
        manager.persist(customer2);

        customerDAO.delete(testingCustomer);

        // get all customers to see if the right one has been deleted
        List<Customer> remainingCustomers = manager.createQuery("SELECT c FROM Customer c", Customer.class)
                                                   .getResultList();

        Assert.assertThat(remainingCustomers, hasItems(customer1, customer2));
        Assert.assertThat(remainingCustomers, not(hasItem(testingCustomer)));
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