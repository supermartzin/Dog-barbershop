package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Tests for {@link CustomerDAOImpl} class
 *
 * @author Martin Vrábel
 * @version 24.10.2016 20:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-configs/main-config.xml"})
public class CustomerDAOImplTest {

    @PersistenceUnit(name = "testing")
    private EntityManagerFactory factory;

    @Inject
    private CustomerDAO customerDAO;

    private Address address;

    @Before
    public void setUp() throws Exception {
        address = new Address("Testing Avenue", 25, "Testero", 2356, "Testing Republic");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_null() throws Exception {
        customerDAO.create(null);

        Assert.fail("'create()' method should have thrown an exception");
    }

    @Test(expected = DAOException.class)
    public void testCreate_usernameNotSet() throws Exception {
        Customer customer = new Customer();
        customer.setPassword("password");
        customer.setFirstName("John");
        customer.setLastName("Tester");
        customer.setAddress(address);
        customer.setEmail("tester@mail.com");
        customer.setPhone("+4209658412");

        customerDAO.create(customer);

        Assert.fail("'create()' method should have thrown an exception");
    }

    @Test(expected = DAOException.class)
    public void testCreate_passwordNotSet() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("testing");
        customer.setFirstName("John");
        customer.setLastName("Tester");
        customer.setAddress(address);
        customer.setEmail("tester@mail.com");
        customer.setPhone("+4209658412");

        customerDAO.create(customer);

        Assert.fail("'create()' method should have thrown an exception");
    }

    @Test(expected = DAOException.class)
    public void testCreate_passwordInvalid() throws Exception {
        String password = "pass";

        Customer customer = new Customer();
        customer.setUsername("testing");
        customer.setPassword(password);
        customer.setFirstName("John");
        customer.setLastName("Tester");
        customer.setAddress(address);
        customer.setEmail("tester@mail.com");
        customer.setPhone("+4209658412");

        customerDAO.create(customer);

        Assert.fail("'create()' method should have thrown an exception");
    }

    @Test(expected = DAOException.class)
    public void testCreate_emailInvalid() throws Exception {
        String email = "this is invalid @mail";

        Customer customer = new Customer();
        customer.setUsername("testing");
        customer.setPassword("password");
        customer.setFirstName("John");
        customer.setLastName("Tester");
        customer.setAddress(address);
        customer.setEmail(email);
        customer.setPhone("+4209658412");

        customerDAO.create(customer);

        Assert.fail("'create()' method should have thrown an exception");
    }

    @Test(expected = DAOException.class)
    public void testCreate_phoneInvalid() throws Exception {
        String phone = "-456 +985-8965aaa";

        Customer customer = new Customer();
        customer.setUsername("testing");
        customer.setPassword("password");
        customer.setFirstName("John");
        customer.setLastName("Tester");
        customer.setAddress(address);
        customer.setEmail("tester@mail.com");
        customer.setPhone(phone);

        customerDAO.create(customer);

        Assert.fail("'create()' method should have thrown an exception");
    }

    @Test(expected = DAOException.class)
    public void testCreate_idAlreadySet() throws Exception {
        Customer customer = new Customer();
        customer.setId(15);
        customer.setUsername("testing");
        customer.setPassword("password");
        customer.setFirstName("John");
        customer.setLastName("Tester");
        customer.setAddress(address);
        customer.setEmail("tester@mail.com");
        customer.setPhone("+4209658412");

        customerDAO.create(customer);

        Assert.fail("'create()' method should have thrown an exception");
    }

    @Test
    public void testCreate_validCustomer() throws Exception {
        Customer customer = new Customer("testing", "password", "John", "Tester", address, "tester@mail.com", "+4209658412");

        customerDAO.create(customer);

        // Assert
        Assert.assertTrue(customer.getId() > 0);

        EntityManager manager = createManager();
        Customer testCustomer = manager.find(Customer.class, customer.getId());

        Assert.assertNotNull(testCustomer);
        Assert.assertEquals(testCustomer.getId(), customer.getId());
        Assert.assertEquals("testing", testCustomer.getUsername());
        Assert.assertEquals("password", testCustomer.getPassword());
        Assert.assertEquals("John", testCustomer.getFirstName());
        Assert.assertEquals("Tester", testCustomer.getLastName());
        Assert.assertEquals(address, testCustomer.getAddress());
        Assert.assertEquals("tester@mail.com", testCustomer.getEmail());
        Assert.assertEquals("+4209658412", testCustomer.getPhone());

        closeManager(manager);
    }

    @Test
    public void getById() throws Exception {
        Assert.fail("not implemented yet");
    }

    @Test
    public void getAll() throws Exception {
        Assert.fail("not implemented yet");
    }

    @Test
    public void update() throws Exception {
        Assert.fail("not implemented yet");
    }

    @Test
    public void delete() throws Exception {
        Assert.fail("not implemented yet");
    }


    private EntityManager createManager() {
        // create new manager
        EntityManager manager = factory.createEntityManager();

        // start transaction
        manager.getTransaction().begin();

        return manager;
    }

    private void closeManager(EntityManager manager) {
        if (manager == null)
            return;

        // commit current transaction
        manager.getTransaction().commit();

        // close the manager
        manager.close();
    }
}