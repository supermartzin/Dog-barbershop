package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.ServiceDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

/**
 * @author Dominik Gmiterko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-configs/main-config.xml"})
public class ServiceFacadeTest {

    @Inject
    private ServiceFacade serviceFacade;

    private ServiceDTO testingService;

    @Before
    public void setUp() throws Exception {
        testingService = new ServiceDTO("testingService", 45, BigDecimal.valueOf(150));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull() throws Exception {
        serviceFacade.create(null);
    }

    @Test
    public void create() throws Exception {
        serviceFacade.create(testingService);
    }

    @Test
    public void getById() throws Exception {
        ServiceDTO result = serviceFacade.getById(1);

        assertNull(result);

        serviceFacade.create(testingService);
        result = serviceFacade.getById(1);

        assertDeepEquals(testingService, result);
    }

    @Test
    public void getAll() throws Exception {
        List<ServiceDTO> result = serviceFacade.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        ServiceDTO testingService1 = new ServiceDTO("ServiceService", 18, new BigDecimal("8.21"));
        ServiceDTO testingService2 = new ServiceDTO("ServiceService2", 21, new BigDecimal("4.00"));
        serviceFacade.create(testingService);
        serviceFacade.create(testingService1);
        serviceFacade.create(testingService2);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertThat(result, hasItems(testingService, testingService1, testingService2));
    }

    @Test
    public void update() throws Exception {
        serviceFacade.create(testingService);

        ServiceDTO result = serviceFacade.getById(1);
        assertDeepEquals(testingService, result);

        ServiceDTO updateService = new ServiceDTO("Changed service name", 30, new BigDecimal("12.99"));
        updateService.setId(1);
        serviceFacade.update(updateService);

        result = serviceFacade.getById(1);
        assertDeepEquals(updateService, result);
    }

    @Test
    public void delete() throws Exception {
        serviceFacade.create(testingService);

        ServiceDTO result = serviceFacade.getById(1);
        assertDeepEquals(testingService, result);

        serviceFacade.delete(testingService);

        result = serviceFacade.getById(1);
        assertNull(result);
    }

    private void assertDeepEquals(ServiceDTO expected, ServiceDTO actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getLength(), actual.getLength());
        Assert.assertEquals(0, expected.getPrice().compareTo(actual.getPrice()));
    }
}
