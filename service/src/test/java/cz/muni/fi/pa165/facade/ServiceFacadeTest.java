package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.ServiceDTO;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.ServiceService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Tests for correct implementation of {@link ServiceFacadeImpl} class
 *
 * @author Dominik Gmiterko
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceFacadeTest {

    @Mock
    private ServiceService serviceService;

    @Mock
    private BeanMappingService mappingService;

    @InjectMocks
    private ServiceFacadeImpl serviceFacade;

    private ServiceDTO testingServiceDTO;
    private Service testingService;

    @Before
    public void setUp() throws Exception {
        // init Mockito
        MockitoAnnotations.initMocks(this);

        testingServiceDTO = new ServiceDTO("testingService", 45, BigDecimal.valueOf(150));
        testingService = new Service("testingService", 45, BigDecimal.valueOf(150));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_null() throws Exception {
        serviceFacade.create(null);
    }

    @Test
    public void testCreate_valid() throws Exception {
        when(mappingService.mapTo(testingServiceDTO, Service.class)).thenReturn(testingService);

        serviceFacade.create(testingServiceDTO);

        verify(mappingService, times(1)).mapTo(testingServiceDTO, Service.class);
        verify(serviceService, times(1)).create(testingService);
    }

    @Test
    public void testGetById_noService() throws Exception {
        when(serviceService.getById(anyInt())).thenReturn(null);
        when(mappingService.mapTo(null, ServiceDTO.class)).thenReturn(null);

        ServiceDTO result = serviceFacade.getById(1);

        assertNull(result);
    }

    @Test
    public void testGetById_validService() throws Exception {
        when(mappingService.mapTo(testingService, (ServiceDTO.class))).thenReturn(testingServiceDTO);
        when(serviceService.getById(anyLong())).thenReturn(testingService);

        ServiceDTO result = serviceFacade.getById(1);

        assertNotNull(result);
        assertEquals(testingServiceDTO, result);

        verify(mappingService, times(1)).mapTo(any(), eq(ServiceDTO.class));
    }

    @Test
    public void getAll() throws Exception {
        List<ServiceDTO> result = serviceFacade.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        ServiceDTO testingService1 = new ServiceDTO("ServiceService", 18, new BigDecimal("8.21"));
        ServiceDTO testingService2 = new ServiceDTO("ServiceService2", 21, new BigDecimal("4.00"));
        serviceFacade.create(testingServiceDTO);
        serviceFacade.create(testingService1);
        serviceFacade.create(testingService2);

        result = serviceFacade.getAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertThat(result, hasItems(testingServiceDTO, testingService1, testingService2));
    }

    @Test
    public void update() throws Exception {
        serviceFacade.create(testingServiceDTO);

//        System.out.println(serviceFacade.getAll());

        ServiceDTO result = serviceFacade.getById(1);
        assertDeepEquals(testingServiceDTO, result);

        ServiceDTO updateService = new ServiceDTO("Changed service name", 30, new BigDecimal("12.99"));
        updateService.setId(1);
        serviceFacade.update(updateService);

        result = serviceFacade.getById(1);
        assertDeepEquals(updateService, result);
    }

    @Test
    public void delete() throws Exception {
        serviceFacade.create(testingServiceDTO);

        ServiceDTO result = serviceFacade.getById(1);
        assertDeepEquals(testingServiceDTO, result);

        serviceFacade.delete(testingServiceDTO);

        result = serviceFacade.getById(1);
        assertNull(result);
    }

    private void assertDeepEquals(ServiceDTO expected, ServiceDTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getLength(), actual.getLength());
        assertEquals(0, expected.getPrice().compareTo(actual.getPrice()));
    }
}
