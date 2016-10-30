package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

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
        buddy = new Dog("Buddy", "American Foxhound", 5);
        charlie = new Dog("Charlie", "Neapolitan Mastiff", 9);

        // add dogs
        testingCustomer.addDog(buddy);
        testingCustomer.addDog(charlie);
    }

    @After
    public void tearDown() throws Exception {
        EntityManager manager = createManager();

        // delete all entries created by test
        manager.createNativeQuery("DELETE FROM Dog d").executeUpdate();
        manager.createNativeQuery("DELETE FROM Customer c").executeUpdate();
        manager.createNativeQuery("DELETE FROM Address a").executeUpdate();

        closeManager(manager);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_customerNull() throws Exception {
        customerDAO.create(null);
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
        customer.addDog(buddy);

        customerDAO.create(customer);
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
        customer.addDog(buddy);

        customerDAO.create(customer);
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
        customer.addDog(charlie);

        customerDAO.create(customer);
    }

    @Test
    public void testCreate_addressNull() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("testing");
        customer.setPassword("password");
        customer.setFirstName("John");
        customer.setLastName("Tester");
        customer.setAddress(null);
        customer.setEmail("testing@mail.com");
        customer.setPhone("+4209658412");
        customer.addDog(charlie);
        customer.addDog(buddy);

        customerDAO.create(customer);

        // get persisted custoemr from database
        EntityManager manager = createManager();
        Customer retrievedCustomer = manager.find(Customer.class, customer.getId());

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
        customer.addDog(buddy);

        customerDAO.create(customer);
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
        customer.addDog(charlie);

        customerDAO.create(customer);
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
        customer.setPhone("+420965841259");
        customer.addDog(buddy);

        customerDAO.create(customer);
    }

    @Test
    public void testCreate_usernameAlreadyExist() throws Exception {
        Customer customer = new Customer();
        customer.setUsername(testingCustomer.getUsername());
        customer.setPassword("psswd252");
        customer.setFirstName("Emily");
        customer.setLastName("Tester");
        customer.setAddress(address);
        customer.setEmail("emily@testers.com");
        customer.setPhone("664895217");
        customer.addDog(charlie);
        customer.addDog(buddy);

        // create first customer
        persistCustomers(testingCustomer);

        // create second customer with the same username
        exception.expect(DAOException.class);
        customerDAO.create(customer);
    }

    @Test
    public void testCreate_customerValid() throws Exception {
        customerDAO.create(testingCustomer);

        // Assert
        Assert.assertTrue(testingCustomer.getId() > 0);

        EntityManager manager = createManager();
        Customer retrievedCustomer = manager.find(Customer.class, testingCustomer.getId());
        closeManager(manager);

        Assert.assertNotNull(retrievedCustomer);
        assertDeepEquals(testingCustomer, retrievedCustomer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_idInvalid() throws Exception {
        long id = -1;

        customerDAO.getById(id);
    }

    @Test
    public void testGetById_customerDoesNotExists() throws Exception {
        long id = 100;

        Customer retrievedCustomer = customerDAO.getById(id);

        Assert.assertNull(retrievedCustomer);
    }

    @Test
    public void testGetById_customerValid() throws Exception {
        // create customer in database
        persistCustomers(testingCustomer);

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
        persistCustomers(testingCustomer);

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
        persistCustomers(testingCustomer, customer);

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

    @Test(expected = DAOException.class)
    public void testUpdate_usernameInvalid() throws Exception {
        // create customer in database
        persistCustomers(testingCustomer);

        // update username
        testingCustomer.setUsername(null);

        customerDAO.update(testingCustomer);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_passwordNull() throws Exception {
        // create customer in database
        persistCustomers(testingCustomer);

        // update username
        testingCustomer.setPassword(null);

        customerDAO.update(testingCustomer);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_passwordInvalid() throws Exception {
        // create customer in database
        persistCustomers(testingCustomer);

        // update username
        testingCustomer.setPassword("pass");

        customerDAO.update(testingCustomer);
    }

    @Test
    public void testUpdate_addressNull() throws Exception {
        // create customer in database
        persistCustomers(testingCustomer);

        // update username
        testingCustomer.setAddress(null);

        customerDAO.update(testingCustomer);

        // get updated customer from database
        EntityManager manager = createManager();
        Customer updatedCustomer = manager.find(Customer.class, testingCustomer.getId());
        closeManager(manager);

        Assert.assertNull(updatedCustomer.getAddress());
    }

    @Test(expected = DAOException.class)
    public void testUpdate_emailInvalid() throws Exception {
        // create customer in database
        persistCustomers(testingCustomer);

        // update username
        testingCustomer.setEmail("this is invalid @mail");

        customerDAO.update(testingCustomer);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_phoneInvalid() throws Exception {
        // create customer in database
        persistCustomers(testingCustomer);

        // update username
        testingCustomer.setPhone("-456 +985-8965aaa");

        customerDAO.update(testingCustomer);
    }

    @Test
    public void testUpdate_customerValid() throws Exception {
        // create customer in database
        persistCustomers(testingCustomer);

        // update username
        testingCustomer.setUsername("new_username");
        testingCustomer.setPassword("new_pass_12345");
        testingCustomer.setAddress(new Address("New testing St.", 25, "New Testing City", 96523, "Zanzibar"));
        testingCustomer.setEmail("newmail@mail.com");
        testingCustomer.setPhone("9963215698");

        customerDAO.update(testingCustomer);

        // get updated customer from database
        EntityManager manager = createManager();
        Customer updatedCustomer = manager.find(Customer.class, testingCustomer.getId());
        closeManager(manager);

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

    @Test(expected = DAOException.class)
    public void testDelete_rightCustomerDeleted() throws Exception {
        // create custoemrs in database
        Customer customer1 = new Customer("testmaster", "masterpassword", "Albert", "Master",
                new Address("Botanicka", 68, "Brno", 62000, "Czech Republic"),
                "testmaster@mail.com", "+421910325478");
        customer1.addDog(buddy);
        Customer customer2 = new Customer("secondtest", "testpass", "Anna", "Testing",
                address, "anna@testing.com", "+421695856321");
        customer2.addDog(charlie);

        persistCustomers(testingCustomer, customer1, customer2);

        customerDAO.delete(testingCustomer);

        // get all customers to see if the right one has been deleted
        EntityManager manager = createManager();
        List<Customer> remainingCustomers = manager.createQuery("SELECT c FROM Customer c", Customer.class)
                                                   .getResultList();
        Assert.assertThat(remainingCustomers, hasItems(customer1, customer2));
        Assert.assertThat(remainingCustomers, not(hasItem(testingCustomer)));
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

    private void persistCustomers(Customer... customers){
        EntityManager manager = createManager();

        for (Customer customer : customers)
            manager.persist(customer);

        closeManager(manager);
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
        Assert.assertThat(actual.getDogs(), is(expected.getDogs()));
    }
}