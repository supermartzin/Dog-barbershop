package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Order;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.junit.After;
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
    private Dog doge;
    private LocalDateTime time;
    private Service service;

    @Before
    public void setUp() throws Exception {
        doge = new Dog("Doggo", "ChauChau", 1);
        time = LocalDateTime.now();
        service = new Service("testingService", 12, BigDecimal.TEN);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = DAOException.class)
    public void testCreate() throws Exception, DAOException {
        Order order = new Order(time, doge, service);

        orderDAO.create(order);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_orderNull() throws Exception, DAOException {
        orderDAO.create(null);
    }

    @Test
    public void getById() throws Exception, DAOException {
        Order order = new Order(time, doge, service);

        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        manager.persist(order);

        manager.getTransaction().commit();
        manager.close();


        Order foundService = orderDAO.getById(order.getId());

        Assert.assertEquals(order, foundService);
    }

}