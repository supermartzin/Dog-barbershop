package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * Tests for {@link CustomerDAOImpl} class
 *
 * @author Domink Gmiterko
 * @version
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-configs/main-config.xml"})
public class ServiceDAOImplTest {

    @PersistenceUnit(name = "testing")
    private EntityManagerFactory factory;

    @Inject
    private ServiceDAO serviceDAO;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        EntityManager manager = createManager();

        manager.createNativeQuery("DELETE FROM Service s").executeUpdate();

        closeManager(manager);
    }

    @Test
    public void testCreate() throws Exception {
        Service service = new Service("testingService", 12, BigDecimal.TEN);

        serviceDAO.create(service);

        // testing
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Assert.assertNotEquals(0, service.getId());

        Service testService = manager.find(Service.class, service.getId());

        Assert.assertEquals("testingService", testService.getTitle());
        Assert.assertEquals(12, testService.getLength());
        Assert.assertEquals(new BigDecimal("10.00"), testService.getPrice());

        manager.getTransaction().commit();
        manager.close();
    }

    @Test(expected = DAOException.class)
    public void testCreate_idAlreadySet() throws Exception {
        Service service = new Service("testingService", 12, BigDecimal.TEN);
        service.setId(15);

        serviceDAO.create(service);
    }

    @Test
    public void getById() throws Exception {
        Service service = new Service("testingService", 12, BigDecimal.TEN);

        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        manager.persist(service);

        manager.getTransaction().commit();
        manager.close();


        Service foundService = serviceDAO.getById(service.getId());

        Assert.assertEquals(service, foundService);
    }

    @Test
    public void getAll() throws Exception {
        Service service1 = new Service("testingService1", 12, BigDecimal.TEN);
        Service service2 = new Service("testingService2", 9, BigDecimal.ONE);

        List<Service> originalServices = new ArrayList<>();
        originalServices.add(service1);
        originalServices.add(service2);

        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        manager.persist(service1);
        manager.persist(service2);

        manager.getTransaction().commit();
        manager.close();


        List<Service> foundServices = serviceDAO.getAll();

        Assert.assertEquals(originalServices, foundServices);
    }

    @Test
    public void update() throws Exception {
        // Arrange
        Service service1 = new Service("testingService1", 12, BigDecimal.TEN);
        Service service2 = new Service("testingService2", 9, BigDecimal.ONE);

        persistServices(service1, service2);

        service1.setTitle("changedTitle");
        service1.setPrice(BigDecimal.valueOf(12));

        // Act
        serviceDAO.update(service1);

        // Assert
        EntityManager manager = createManager();
        Service testService1 = manager.find(Service.class, service1.getId());
        Service testService2 = manager.find(Service.class, service2.getId());
        closeManager(manager);

        Assert.assertEquals("changedTitle", testService1.getTitle());
        Assert.assertEquals(12, testService1.getLength());
        Assert.assertEquals(0, testService1.getPrice().compareTo(BigDecimal.valueOf(12)));

        Assert.assertEquals("testingService2", testService2.getTitle());
        Assert.assertEquals(9, testService2.getLength());
        Assert.assertEquals(0, testService2.getPrice().compareTo(BigDecimal.ONE));
    }

    @Test
    public void testGetAll_noServices() throws Exception {
        List<Service> allServices = serviceDAO.getAll();

        Assert.assertNotNull(allServices);
        Assert.assertTrue(allServices.isEmpty());
    }

    @Test
    public void delete() throws Exception {
        Service service = new Service("testingService", 12, BigDecimal.TEN);

        serviceDAO.create(service);

        serviceDAO.delete(service);

        // testing
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Assert.assertNotEquals(0, service.getId());

        Service testService = manager.find(Service.class, service.getId());

        Assert.assertNull(testService);

        manager.getTransaction().commit();
        manager.close();
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

    private void persistServices(Service... services){
        EntityManager manager = createManager();

        for (Service service : services) {
            manager.persist(service);
        }

        closeManager(manager);
    }

    private void assertDeepEquals(Service expected, Service actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getLength(), actual.getLength());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
    }

}