package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.OrderDAO;
import cz.muni.fi.pa165.entities.*;
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
import java.time.LocalDateTime;
import java.util.List;

/**
 * Tests for correct contract implementation defined by {@link OrderService} interface
 *
 * @author Denis Richtarik
 * @version 22.11.2016 13:36
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:api-config.xml"})
public class OrderServiceTest {

    @Mock
    private OrderDAO orderDAO;

    @Inject
    @InjectMocks
    private OrderService orderService;

    private Order testingOrder;
    private Order testingOrder2;
    private Dog testingDog1;
    private Dog testingDog2;
    private Customer testingCustomer;
    private Address testingAddress;
    private Service testingService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testingAddress = new Address("Boxing", 12, "Philadelphia", 55504, "Pennsylvania");
        testingCustomer = new Customer("micky", "12345", "Mr", "T", testingAddress, "rocky@balboa.com", "+4209658412");
        testingDog1 = new Dog("Rocky", "Balboa", 70, testingCustomer);
        testingDog2 = new Dog("Johny", "Rambo", 20, testingCustomer);
        testingService = new Service("Longcut", 50, new BigDecimal("5000"));
        testingOrder = new Order((LocalDateTime.of(2016, 11, 20, 15, 40, 23)), testingDog1, testingService);
        testingOrder2 = new Order((LocalDateTime.of(2016, 12, 13, 12, 00, 00)), testingDog2, testingService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_OrderNull() throws Exception {
        orderService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_IdInvalid() throws Exception {
        orderService.getById(-1);
    }

    @Test
    public void testGetById_orderDoesNotExist() throws Exception {
        Order order = orderService.getById(50);

        Assert.assertNull(order);
    }

    @Test
    public void testGetAll_noOrders() throws Exception {
        List<Order> allOrderss = orderService.getAll();

        Assert.assertNotNull(allOrderss);
        Assert.assertTrue(allOrderss.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_OrderNull() throws Exception {
        orderService.update(null);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_OrderDoesNotExist() throws Exception {
        orderService.update(testingOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_orderNull() throws Exception {
        orderService.delete(null);
    }

    @Test(expected = DAOException.class)
    public void testDelete_orderDoesNotExist() throws Exception {
        orderService.delete(testingOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByDog_dogNull() throws Exception {
        orderService.getByDog(null);
    }


    @Test(expected = IllegalStateException.class)
    public void testGetByDog_dogDoesNotExist() throws Exception {
        orderService.getByDog(testingDog2);
    }

    @Test
    public void getAllOrdersForDay() throws Exception {

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByService_serviceNull() throws Exception {
        orderService.getByService(null);
    }


    private void assertDeepEquals(Order expected, Order actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getTime(), actual.getTime());
        Assert.assertEquals(expected.getDog(), actual.getDog());
        Assert.assertEquals(expected.getEmployee(), actual.getEmployee());
        Assert.assertEquals(expected.getService(), actual.getService());
    }
}