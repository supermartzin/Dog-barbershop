package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ServiceDAO;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.*;


/**
 * Tests for correct contract implementation defined by {@link ServiceService} interface
 *
 * @author Denis Richtarik
 * @version 22.11.2016 13:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:api-config.xml"})
public class ServiceServiceTest {

    @Mock
    private ServiceDAO serviceDAO;

    @InjectMocks
    private ServiceServiceImpl serviceServiceImpl;

    private Service testingService;
    private Service testingService2;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testingService = new Service("Shortcut", 20, new BigDecimal("1000"));
        testingService2 = new Service("LongCut", 50, new BigDecimal("5000"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_serviceNull() throws Exception {
        doThrow(new IllegalArgumentException()).when(serviceDAO).create(null);
        serviceServiceImpl.create(null);
    }


    @Test
    public void testCreate_serviceValid() throws Exception {
        serviceServiceImpl.create(testingService);

        ArgumentCaptor<Service> serviceCaptor = ArgumentCaptor.forClass(Service.class);
        verify(serviceDAO, times(1)).create(serviceCaptor.capture());
        assertDeepEquals(testingService, serviceCaptor.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_IdInvalid() throws Exception {
        doThrow(new IllegalArgumentException()).when(serviceDAO).getById(-1);
        serviceServiceImpl.getById(-1);
    }

    @Test
    public void testGetById_serviceDoesNotExist() throws Exception {
        Service service = serviceServiceImpl.getById(50);

        Assert.assertNull(service);
    }

    @Test
    public void testGetById_serviceValid() throws Exception {
        when(serviceDAO.getById(1)).thenReturn(testingService);

        Service retrievedService = serviceServiceImpl.getById(1);

        Assert.assertNotNull(retrievedService);
        assertDeepEquals(testingService, retrievedService);
    }

    @Test
    public void testGetAll_noServices() throws Exception {
        List<Service> allServices = serviceServiceImpl.getAll();

        Assert.assertNotNull(allServices);
        Assert.assertTrue(allServices.isEmpty());
    }

    @Test
    public void testGetAll_servicesValid() throws Exception {
        List<Service> mockedServices = new ArrayList<>();
        mockedServices.add(testingService);
        mockedServices.add(testingService2);

        when(serviceDAO.getAll()).thenReturn(mockedServices);

        List<Service> allServices = serviceServiceImpl.getAll();

        Assert.assertNotNull(allServices);
        Assert.assertFalse(allServices.isEmpty());
        Assert.assertEquals(2, allServices.size());
        Assert.assertThat(allServices, hasItems(testingService, testingService2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_ServiceNull() throws Exception {
        doThrow(new IllegalArgumentException()).when(serviceDAO).update(null);
        serviceServiceImpl.update(null);
    }

    @Test
    public void testUpdate_serviceValid() throws Exception {
        serviceServiceImpl.update(testingService);

        ArgumentCaptor<Service> serviceCaptor = ArgumentCaptor.forClass(Service.class);
        verify(serviceDAO, times(1)).update(serviceCaptor.capture());
        assertDeepEquals(testingService, serviceCaptor.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_ServiceDoesNotExist() throws Exception {
        doThrow(new IllegalArgumentException()).when(serviceDAO).update(testingService);
        serviceServiceImpl.update(testingService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_serviceNull() throws Exception {
        doThrow(new IllegalArgumentException()).when(serviceDAO).delete(null);
        serviceServiceImpl.delete(null);
    }

    @Test
    public void testDelete_serviceValid() throws Exception {
        serviceServiceImpl.delete(testingService);

        ArgumentCaptor<Service> serviceArgumentCaptorCaptor = ArgumentCaptor.forClass(Service.class);
        verify(serviceDAO, times(1)).delete(serviceArgumentCaptorCaptor.capture());
        assertDeepEquals(testingService, serviceArgumentCaptorCaptor.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_serviceDoesNotExist() throws Exception {
        doThrow(new IllegalArgumentException()).when(serviceDAO).delete(testingService);
        serviceServiceImpl.delete(testingService);
    }

    private void assertDeepEquals(Service expected, Service actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getLength(), actual.getLength());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
    }
}