package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ServiceDAO;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.*;


/**
 * Tests for correct implementation of {@link ServiceServiceImpl} class
 *
 * @author Denis Richtarik
 * @version 22.11.2016 13:37
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceServiceTest {

    @Mock
    private ServiceDAO serviceDAO;

    @InjectMocks
    private ServiceServiceImpl serviceService;

    private Service testingService;
    private Service testingService2;

    @Before
    public void setUp() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        testingService = new Service("Shortcut", 20, new BigDecimal("1000"));
        testingService2 = new Service("LongCut", 50, new BigDecimal("5000"));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCreate_serviceNull() throws DAOException, ServiceException {
        serviceService.create(null);
    }


    @Test
    public void testCreate_serviceValid() throws DAOException, ServiceException {
        serviceService.create(testingService);

        verify(serviceDAO, times(1)).create(testingService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_IdInvalid() throws ServiceException {
        serviceService.getById(-1);
    }

    @Test
    public void testGetById_serviceDoesNotExist() throws ServiceException {
        Service service = serviceService.getById(50);

        Assert.assertNull(service);
    }

    @Test
    public void testGetById_serviceValid() throws ServiceException {
        when(serviceDAO.getById(1)).thenReturn(testingService);

        Service retrievedService = serviceService.getById(1);

        Assert.assertNotNull(retrievedService);
        assertDeepEquals(testingService, retrievedService);
    }

    @Test
    public void testGetAll_noServices() throws ServiceException {
        List<Service> allServices = serviceService.getAll();

        Assert.assertNotNull(allServices);
        Assert.assertTrue(allServices.isEmpty());
    }

    @Test
    public void testGetAll_servicesValid() throws DAOException, ServiceException {
        List<Service> mockedServices = new ArrayList<>();
        mockedServices.add(testingService);
        mockedServices.add(testingService2);

        when(serviceDAO.getAll()).thenReturn(mockedServices);

        List<Service> allServices = serviceService.getAll();

        Assert.assertNotNull(allServices);
        Assert.assertFalse(allServices.isEmpty());
        Assert.assertEquals(2, allServices.size());
        Assert.assertThat(allServices, hasItems(testingService, testingService2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_ServiceNull() throws DAOException, ServiceException {
        doThrow(new IllegalArgumentException()).when(serviceDAO).update(null);
        serviceService.update(null);
    }

    @Test
    public void testUpdate_serviceValid() throws DAOException, ServiceException {
        serviceService.update(testingService);

        ArgumentCaptor<Service> serviceCaptor = ArgumentCaptor.forClass(Service.class);
        verify(serviceDAO, times(1)).update(serviceCaptor.capture());
        assertDeepEquals(testingService, serviceCaptor.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_ServiceDoesNotExist() throws DAOException, ServiceException {
        doThrow(new IllegalArgumentException()).when(serviceDAO).update(testingService);
        serviceService.update(testingService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_serviceNull() throws DAOException, ServiceException {
        doThrow(new IllegalArgumentException()).when(serviceDAO).delete(null);
        serviceService.delete(null);
    }

    @Test
    public void testDelete_serviceValid() throws DAOException, ServiceException {
        serviceService.delete(testingService);

        ArgumentCaptor<Service> serviceArgumentCaptorCaptor = ArgumentCaptor.forClass(Service.class);
        verify(serviceDAO, times(1)).delete(serviceArgumentCaptorCaptor.capture());
        assertDeepEquals(testingService, serviceArgumentCaptorCaptor.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_serviceDoesNotExist() throws DAOException, ServiceException {
        doThrow(new IllegalArgumentException()).when(serviceDAO).delete(testingService);
        serviceService.delete(testingService);
    }


    private void assertDeepEquals(Service expected, Service actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getLength(), actual.getLength());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
    }
}