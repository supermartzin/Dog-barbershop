package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.*;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Tests for correct contract implementation defined by {@link OrderDAO} interface
 *
 * @author Denis Richtarik
 * @version 31.10.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-configs/main-config.xml"})
public class OrderDAOTest {

    @PersistenceUnit(name = "testing")
    private EntityManagerFactory factory;

    @Inject
    private OrderDAO orderDAO;

    private Order testingOrder;
    private Dog doge;
    private Service service;
    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = new Customer("testing", "password", "John", "Tester", new Address("Testing Avenue", 25, "Testero", 2356, "Testing Republic"), "tester@mail.com", "+4209658412");
        doge = new Dog("Doggo", "ChauChau", 1, customer);
        service = new Service("testingService", 12, BigDecimal.TEN);
        testingOrder = new Order(LocalDateTime.of(2016, 11, 20, 15, 40, 23), doge, service);

        // TODO persist
        // manager.per
    }

    @Test(expected = DAOException.class)
    public void testCreate() throws Exception {
        orderDAO.create(testingOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_orderNull() throws Exception {
        orderDAO.create(null);
    }

    @Test
    public void getById() throws Exception {

        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        manager.persist(testingOrder);

        manager.getTransaction().commit();
        manager.close();


        Order foundService = orderDAO.getById(testingOrder.getId());

        Assert.assertEquals(testingOrder, foundService);
    }

}