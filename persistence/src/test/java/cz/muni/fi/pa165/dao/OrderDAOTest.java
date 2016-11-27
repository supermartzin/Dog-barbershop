package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.*;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Tests for correct contract implementation defined by {@link OrderDAO} interface
 *
 * @author Denis Richtarik
 * @version 31.10.2016
 */
@Transactional
@Rollback(false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-configs/main-config.xml"})
public class OrderDAOTest {

    @PersistenceContext
    private EntityManager manager;

    @Inject
    private OrderDAO orderDAO;

    private Order testingOrder;

    @Before
    public void setUp() throws Exception {
        Customer customer = new Customer("testing", "password", "John", "Tester",
                                new Address("Testing Avenue", 25, "Testero", 2356, "Testing Republic"),
                                "tester@mail.com", "+4209658412");
        Employee employee = new Employee("testEmployee", "admin123", "James", "Working",
                                new Address("Test street", 14, "Testovato", 11452, "Testing Republic"),
                                "testEmployee@company.biz", "+45-996-352-8965", BigDecimal.valueOf(14500));
        Dog dog = new Dog("Doggo", "ChauChau", 1, customer);
        customer.addDog(dog);
        Service service = new Service("testingService", 55, BigDecimal.valueOf(250));

        testingOrder = new Order(LocalDateTime.of(2016, 11, 20, 15, 40, 23), dog, service);
        testingOrder.setEmployee(employee);

        manager.persist(customer);
        manager.persist(employee);
        manager.persist(service);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_orderNull() throws Exception {
        orderDAO.create(null);
    }

    @Test(expected = DAOException.class)
    public void testCreate_timeNull() throws Exception {
        testingOrder.setTime(null);

        orderDAO.create(testingOrder);
    }

    @Test(expected = DAOException.class)
    public void testCreate_dogNull() throws Exception {
        testingOrder.setDog(null);

        orderDAO.create(testingOrder);
    }

    @Test(expected = DAOException.class)
    public void testCreate_serviceNull() throws Exception {
        testingOrder.setService(null);

        orderDAO.create(testingOrder);
    }

    @Test
    public void testCreate_employeeNull() throws Exception {
        testingOrder.setEmployee(null);

        orderDAO.create(testingOrder);

        Assert.assertTrue(testingOrder.getId() > 0);

        Order foundOrder = manager.find(Order.class, testingOrder.getId());

        Assert.assertNotNull(foundOrder);
        Assert.assertNull(foundOrder.getEmployee());
    }

    @Test
    public void testCreate_employeeValid() throws Exception {
        orderDAO.create(testingOrder);

        Assert.assertTrue(testingOrder.getId() > 0);

        Order foundOrder = manager.find(Order.class, testingOrder.getId());

        Assert.assertNotNull(foundOrder);
        assertDeepEquals(testingOrder, foundOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_idInvalid() throws Exception {
        orderDAO.getById(-10);
    }

    @Test
    public void testGetById_orderValid() throws Exception {
        manager.persist(testingOrder);

        Order retrievedOrder = orderDAO.getById(testingOrder.getId());

        Assert.assertNotNull(retrievedOrder);
        assertDeepEquals(testingOrder, retrievedOrder);
    }


    private void assertDeepEquals(Order expected, Order actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getTime(), actual.getTime());
        Assert.assertEquals(expected.getDog(), actual.getDog());
        Assert.assertEquals(expected.getService(), actual.getService());
        Assert.assertEquals(expected.getEmployee(), actual.getEmployee());
    }
}