package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Autowired
    private CustomerDAO customerDAO;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreate() throws Exception {
        Customer customer = new Customer("testing", "password", "John", "Tester", new Address("Testing Avenue", 25, "Testero", 2356, "Testing Republic"), "tester@mail.com", "+4209658412");

        customerDAO.create(customer);

        // testing
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Customer testCustomer = manager.find(Customer.class, customer.getId());

        Assert.assertEquals("Tester", testCustomer.getLastName());

        manager.getTransaction().commit();
        manager.close();
    }

    @Test
    public void getById() throws Exception {

    }

    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

}