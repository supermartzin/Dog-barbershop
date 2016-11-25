package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.*;
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
        Dog dog = new Dog("Doggo", "ChauChau", 1, customer);
        customer.addDog(dog);
        Service service = new Service("testingService", 55, BigDecimal.valueOf(250));

        testingOrder = new Order(LocalDateTime.of(2016, 11, 20, 15, 40, 23), dog, service);

        manager.persist(customer);
        manager.persist(service);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_orderNull() throws Exception {
        orderDAO.create(null);
    }

    @Test
    public void getById() throws Exception {
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