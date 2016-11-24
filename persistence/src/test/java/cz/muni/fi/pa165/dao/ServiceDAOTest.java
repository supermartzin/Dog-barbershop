package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Service;
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
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;

/**
 * Tests for correct contract implementation defined by {@link ServiceDAO} interface
 *
 * @author Domink Gmiterko
 */
@Rollback(false)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-configs/main-config.xml"})
public class ServiceDAOTest {

    @PersistenceContext
    private EntityManager manager;

    @Inject
    private ServiceDAO serviceDAO;

    private Service testingService;

    @Before
    public void setUp() throws Exception {
        testingService = new Service("testingService", 45, BigDecimal.valueOf(150));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_serviceNull() throws Exception {
        serviceDAO.create(null);
    }

    @Test(expected = DAOException.class)
    public void testCreate_titleInvalid() throws Exception {
        testingService.setTitle(null);

        serviceDAO.create(testingService);
    }

    @Test(expected = DAOException.class)
    public void testCreate_lengthInvalid() throws Exception {
        testingService.setLength(-15);

        serviceDAO.create(testingService);
    }

    @Test(expected = DAOException.class)
    public void testCreate_priceInvalid() throws Exception {
        testingService.setPrice(null);

        serviceDAO.create(testingService);
    }

    @Test(expected = DAOException.class)
    public void testCreate_serviceAlreadyExists() throws Exception {
        serviceDAO.create(testingService);
        serviceDAO.create(testingService);
    }

    @Test
    public void testCreate_serviceValid() throws Exception {
        serviceDAO.create(testingService);

        Assert.assertTrue(testingService.getId() > 0);

        Service testService = manager.find(Service.class, testingService.getId());

        Assert.assertNotNull(testService);
        assertDeepEquals(testingService, testService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_idInvalid() throws Exception {
        serviceDAO.getById(-5);
    }

    @Test
    public void testGetById_serviceDoesNotExist() throws Exception {
        Service service = serviceDAO.getById(500);

        Assert.assertNull(service);
    }

    @Test
    public void testGetById_serviceValid() throws Exception {
        manager.persist(testingService);

        Service service = serviceDAO.getById(testingService.getId());

        Assert.assertNotNull(service);
        assertDeepEquals(testingService, service);
    }

    @Test
    public void testGetAll_noServices() throws Exception {
        List<Service> allServices = serviceDAO.getAll();

        Assert.assertNotNull(allServices);
        Assert.assertTrue(allServices.isEmpty());
    }

    @Test
    public void testGetAll_servicesExist() throws Exception {
        Service service = new Service("Another testing service", 120, BigDecimal.valueOf(550));

        manager.persist(testingService);
        manager.persist(service);

        List<Service> foundServices = serviceDAO.getAll();

        Assert.assertNotNull(foundServices);
        Assert.assertFalse(foundServices.isEmpty());
        Assert.assertEquals(2, foundServices.size());
        Assert.assertThat(foundServices, hasItems(testingService, service));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_serviceNull() throws Exception {
        serviceDAO.update(null);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_serviceDoesNotExist() throws Exception {
        serviceDAO.update(testingService);
    }

    @Rollback
    @Test(expected = DAOException.class)
    public void testUpdate_titleInvalid() throws Exception {
        manager.persist(testingService);

        testingService.setTitle(null);

        serviceDAO.update(testingService);
    }

    @Rollback
    @Test(expected = DAOException.class)
    public void testUpdate_lengthInvalid() throws Exception {
        manager.persist(testingService);

        testingService.setLength(-5);

        serviceDAO.update(testingService);
    }

    @Rollback
    @Test(expected = DAOException.class)
    public void testUpdate_priceInvalid() throws Exception {
        manager.persist(testingService);

        testingService.setPrice(null);

        serviceDAO.update(testingService);
    }

    @Test
    public void testUpdate_serviceValid() throws Exception {
        manager.persist(testingService);

        testingService.setTitle("Another testing service");
        testingService.setLength(15);
        testingService.setPrice(BigDecimal.valueOf(1200));

        serviceDAO.update(testingService);

        Service updatedService = manager.find(Service.class, testingService.getId());

        Assert.assertNotNull(updatedService);
        assertDeepEquals(testingService, updatedService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_serviceNull() throws Exception {
        serviceDAO.delete(null);
    }

    @Test(expected = DAOException.class)
    public void testDelete_serviceDoesNotExist() throws Exception {
        serviceDAO.delete(testingService);
    }

    @Test
    @Rollback
    public void testDelete_correctServiceDeleted() throws Exception {
        Service service = new Service("Another testring service", 70, BigDecimal.valueOf(300));

        manager.persist(testingService);
        manager.persist(service);

        serviceDAO.delete(service);

        List<Service> remainingServices = manager.createQuery("SELECT service FROM Service service", Service.class)
                                                 .getResultList();

        Assert.assertThat(remainingServices, hasItem(testingService));
        Assert.assertThat(remainingServices, not(hasItem(service)));
    }


    private void assertDeepEquals(Service expected, Service actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getLength(), actual.getLength());
        Assert.assertEquals(0, expected.getPrice().compareTo(actual.getPrice()));
    }
}