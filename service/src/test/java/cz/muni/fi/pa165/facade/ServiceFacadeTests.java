package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.ServiceDTO;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.ServiceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Tests for correct implementation of {@link ServiceFacadeImpl} class
 *
 * @author Dominik Gmiterko
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceFacadeTests {

    @Mock
    private ServiceService serviceService;

    @Mock
    private BeanMappingService mappingService;

    private ServiceFacade serviceFacade;

    private ServiceDTO testingServiceDTO;
    private Service testingService;

    @Before
    public void setUp() {
        // init Mockito
        MockitoAnnotations.initMocks(this);

        serviceFacade = new ServiceFacadeImpl(serviceService, mappingService);

        testingServiceDTO = new ServiceDTO("testingService", 45, BigDecimal.valueOf(150));
        testingService = new Service("testingService", 45, BigDecimal.valueOf(150));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_serviceNull() throws FacadeException {
        serviceFacade.create(null);
    }

    @Test
    public void testCreate_serviceValid() throws FacadeException, ServiceException {
        when(mappingService.mapTo(testingServiceDTO, Service.class)).thenReturn(testingService);

        serviceFacade.create(testingServiceDTO);

        verify(mappingService, times(1)).mapTo(testingServiceDTO, Service.class);
        verify(serviceService, times(1)).create(testingService);
    }

    @Test
    public void testGetById_serviceDoesNotExist() throws FacadeException, ServiceException {
        when(serviceService.getById(anyInt())).thenReturn(null);
        when(mappingService.mapTo(null, ServiceDTO.class)).thenReturn(null);

        ServiceDTO result = serviceFacade.getById(1);

        assertNull(result);
    }

    @Test
    public void testGetById_serviceValid() throws FacadeException, ServiceException {
        when(mappingService.mapTo(testingService, ServiceDTO.class)).thenReturn(testingServiceDTO);
        when(serviceService.getById(anyLong())).thenReturn(testingService);

        ServiceDTO result = serviceFacade.getById(1);

        assertNotNull(result);
        assertEquals(testingServiceDTO, result);

        verify(mappingService, times(1)).mapTo(testingService, ServiceDTO.class);
    }

    @Test
    public void testGetAll_noServices() throws FacadeException, ServiceException {
        when(serviceService.getAll()).thenReturn(Collections.emptyList());
        when(mappingService.mapTo(anyCollectionOf(Service.class), eq(ServiceDTO.class))).thenReturn(Collections.emptyList());

        List<ServiceDTO> result = serviceFacade.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(serviceService, times(1)).getAll();
        verify(mappingService, times(1)).mapTo(anyCollectionOf(Service.class), eq(ServiceDTO.class));
    }

    @Test
    public void testGetAll_servicesValid() throws FacadeException, ServiceException {
        // create more services
        Service testingService1 = new Service("ServiceService", 18, new BigDecimal("8.21"));
        Service testingService2 = new Service("ServiceService2", 21, new BigDecimal("4.00"));
        ServiceDTO testingServiceDTO1 = new ServiceDTO("ServiceService", 18, new BigDecimal("8.21"));
        ServiceDTO testingServiceDTO2 = new ServiceDTO("ServiceService2", 21, new BigDecimal("4.00"));

        when(serviceService.getAll()).thenReturn(Arrays.asList(testingService, testingService1, testingService2));
        when(mappingService.mapTo(Arrays.asList(testingService, testingService1, testingService2), ServiceDTO.class))
                           .thenReturn(Arrays.asList(testingServiceDTO, testingServiceDTO1, testingServiceDTO2));

        List<ServiceDTO> result = serviceFacade.getAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertThat(result, hasItems(testingServiceDTO, testingServiceDTO1, testingServiceDTO2));

        verify(serviceService, times(1)).getAll();
        verify(mappingService, times(1)).mapTo(anyCollectionOf(Service.class), eq(ServiceDTO.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_serviceNull() throws FacadeException {
        serviceFacade.update(null);
    }

    @Test
    public void testUpdate_serviceValid() throws FacadeException, ServiceException {
        ServiceDTO serviceDTO = new ServiceDTO("Changed service name", 30, BigDecimal.valueOf(135));
        Service updatedService = new Service("Changed service name", 30, BigDecimal.valueOf(135));

        when(mappingService.mapTo(serviceDTO, Service.class)).thenReturn(updatedService);
        when(mappingService.mapTo(updatedService, ServiceDTO.class)).thenReturn(serviceDTO);
        when(serviceService.getById(anyLong())).thenReturn(updatedService);


        // update Service
        serviceFacade.update(serviceDTO);

        // check if correctly updated
        ServiceDTO updatedServiceDTO = serviceFacade.getById(1);

        assertNotNull(updatedServiceDTO);
        assertEquals(serviceDTO, updatedServiceDTO);

        verify(mappingService, times(1)).mapTo(serviceDTO, Service.class);
        verify(mappingService, times(1)).mapTo(updatedService, ServiceDTO.class);
        verify(serviceService, times(1)).update(updatedService);
        verify(serviceService, times(1)).getById(anyLong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_serviceNull() throws FacadeException {
        serviceFacade.delete(null);
    }

    @Test
    public void testDelete_serviceValid() throws FacadeException, ServiceException {
        when(mappingService.mapTo(testingServiceDTO, Service.class)).thenReturn(testingService);
        when(mappingService.mapTo(anyCollectionOf(Service.class), eq(ServiceDTO.class))).thenReturn(Collections.emptyList());
        when(serviceService.getAll()).thenReturn(Collections.emptyList());

        // delete Service
        serviceFacade.delete(testingServiceDTO);

        // check if correctly deleted
        List<ServiceDTO> remainingServices = serviceFacade.getAll();

        assertNotNull(remainingServices);
        assertTrue(remainingServices.isEmpty());
        assertThat(remainingServices, not(hasItem(testingServiceDTO)));
    }
}
