package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ServiceDAO;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;


/**
 * Tests for correct contract implementation defined by {@link ServiceService} interface
 *
 * @author Denis Richtarik
 * @version 22.11.2016 13:37
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:api-config.xml"})
public class ServiceServiceTest {

    @Mock
    private ServiceDAO serviceDAO;

    @Inject
    @InjectMocks
    private ServiceService serviceService;

    private Service testingService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testingService = new Service("Shortcut", 20, new BigDecimal("1000"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_serviceNull() throws Exception {
        serviceService.create(null);
    }

    /*
    @Test
    public void testCreate_serviceValid() throws Exception {
        serviceService.create(testingService);

        ArgumentCaptor<Service> serviceCaptor = ArgumentCaptor.forClass(Service.class);
        verify(serviceDAO, times(1)).create(serviceCaptor.capture());
        assertDeepEquals(testingService, serviceCaptor.getValue());
    } */

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_IdInvalid() throws Exception {
        serviceService.getById(-1);
    }

    @Test
    public void testGetById_serviceDoesNotExist() throws Exception {
        Service service = serviceService.getById(50);

        Assert.assertNull(service);
    }

    @Test
    public void testGetAll_noServices() throws Exception {
        List<Service> allServices = serviceService.getAll();

        Assert.assertNotNull(allServices);
        Assert.assertTrue(allServices.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_ServiceNull() throws Exception {
        serviceService.update(null);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_ServiceDoesNotExist() throws Exception {
        serviceService.update(testingService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_serviceNull() throws Exception {
        serviceService.delete(null);
    }

    @Test(expected = DAOException.class)
    public void testDelete_serviceDoesNotExist() throws Exception {
        serviceService.delete(testingService);
    }

    private void assertDeepEquals(Service expected, Service actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getLength(), actual.getLength());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
    }
}