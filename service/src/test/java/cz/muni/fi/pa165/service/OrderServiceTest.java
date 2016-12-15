package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.OrderDAO;
import cz.muni.fi.pa165.entities.*;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.*;

/**
 * Tests for correct contract implementation defined by {@link OrderService} interface
 *
 * @author Denis Richtarik
 * @version 22.11.2016 13:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:api-config.xml"})
public class OrderServiceTest {

    @Mock
    private OrderDAO orderDAO;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order testingOrder;
    private Order testingOrder2;
    private Dog testingDog1;
    private Dog testingDog2;
    private Customer testingCustomer;
    private Address testingAddress;
    private Service testingService;
    private Employee testingEmployee1;
    private Employee testingEmployee2;

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
        testingEmployee1 = new Employee("Joey", "123456", "Joe", "Mutt", testingAddress, "joe@mail.cz", "+4209658412", new BigDecimal("20000"));
        testingEmployee2 = new Employee("Janish", "123456", "Jane", "Mutt", testingAddress, "jane@mail.cz", "+4209658413", new BigDecimal("25000"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_OrderNull() throws Exception {
        doThrow(new IllegalArgumentException()).when(orderDAO).create(null);
        orderService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_IdInvalid() throws Exception {
        doThrow(new IllegalArgumentException()).when(orderDAO).getById(-1);
        orderService.getById(-1);
    }

    @Test
    public void testGetById_IdValid() throws Exception {
        when(orderDAO.getById(1)).thenReturn(testingOrder);

        Order retrievedOrder = orderService.getById(1);

        Assert.assertNotNull(retrievedOrder);
        assertDeepEquals(testingOrder, retrievedOrder);
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

    @Test
    public void testGetAll_ordersValid() throws Exception {
        List<Order> mockedOrders = new ArrayList<>();
        mockedOrders.add(testingOrder);
        mockedOrders.add(testingOrder2);

        when(orderDAO.getAll()).thenReturn(mockedOrders);

        List<Order> allServices = orderService.getAll();

        Assert.assertNotNull(allServices);
        Assert.assertFalse(allServices.isEmpty());
        Assert.assertEquals(2, allServices.size());
        Assert.assertThat(allServices, hasItems(testingOrder, testingOrder2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_OrderNull() throws Exception {
        doThrow(new IllegalArgumentException()).when(orderDAO).update(null);
        orderService.update(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_OrderDoesNotExist() throws Exception {
        doThrow(new IllegalArgumentException()).when(orderDAO).update(testingOrder);
        orderService.update(testingOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_orderNull() throws Exception {
        doThrow(new IllegalArgumentException()).when(orderDAO).delete(null);
        orderService.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_orderDoesNotExist() throws Exception {
        doThrow(new IllegalArgumentException()).when(orderDAO).delete(testingOrder);
        orderService.delete(testingOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByDog_dogNull() throws Exception {
        doThrow(new IllegalArgumentException()).when(orderDAO).getByDog(null);
        orderService.getByDog(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetByDog_dogDoesNotExist() throws Exception {
        doThrow(new IllegalArgumentException()).when(orderDAO).getByDog(testingDog1);
        orderService.getByDog(testingDog1);
    }

    @Test
    public void getAllOrdersForDay() throws Exception {
        List<Order> mockedOrders = new ArrayList<>();
        mockedOrders.add(testingOrder2);

        when(orderDAO.getAllOrdersInTimeRange(any(), any())).thenReturn(mockedOrders);

        List<Order> wantedOrders = orderService.getAllOrdersForDay(LocalDateTime.of(2016, 12, 13, 01, 00, 00));

        Assert.assertNotNull(wantedOrders);
        Assert.assertFalse(wantedOrders.isEmpty());
        Assert.assertEquals(1, wantedOrders.size());
        Assert.assertThat(wantedOrders, hasItems(testingOrder2));
    }

    @Test
    public void testGetTotalAmountGained() throws Exception {
        List<Order> mockedOrders = new ArrayList<>();
        mockedOrders.add(testingOrder);
        mockedOrders.add(testingOrder2);

        when(orderDAO.getAllOrdersInTimeRange(any(), any())).thenReturn(mockedOrders);

        BigDecimal wantedPrice = orderService.getTotalAmountGained(LocalDateTime.of(2016, 11, 20, 01, 00, 00), LocalDateTime.of(2016, 12, 14, 01, 00, 00));

        Assert.assertEquals(0, testingOrder.getService().getPrice().add(testingOrder2.getService().getPrice()).compareTo(wantedPrice));
    }


    @Test
    public void testGetTotalAmountGainedByEmployee() throws Exception {
        Map<Employee, BigDecimal> result = new HashMap<>();
        result.put(testingEmployee1, new BigDecimal("5000.0"));
        result.put(testingEmployee2, new BigDecimal("5000.0"));

        List<Order> mockedOrders = new ArrayList<>();

        testingOrder.setEmployee(testingEmployee1);
        testingOrder2.setEmployee(testingEmployee2);

        mockedOrders.add(testingOrder);
        mockedOrders.add(testingOrder2);

        when(orderDAO.getAllOrdersInTimeRange(any(), any())).thenReturn(mockedOrders);

        Map<Employee, BigDecimal> wantedHashMap = orderService.getTotalAmountGainedByEmployee(LocalDateTime.of(2016, 11, 20, 01, 00, 00), LocalDateTime.of(2016, 12, 14, 01, 00, 00));

        Assert.assertNotNull(wantedHashMap);
        Assert.assertFalse(wantedHashMap.isEmpty());
        Assert.assertEquals(result, wantedHashMap);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByService_serviceNull() throws Exception {
        doThrow(new IllegalArgumentException()).when(orderDAO).getByService(null);
        orderService.getByService(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByService_serviceDoesNotExist() throws Exception {
        doThrow(new IllegalArgumentException()).when(orderDAO).getByService(testingService);
        orderService.getByService(testingService);
    }

    @Test
    public void testOrderCompleted() throws Exception{
        Assert.assertFalse(testingOrder.getStatus());
        orderService.orderCompleted(testingOrder);

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderDAO, times(1)).update(orderArgumentCaptor.capture());
        Assert.assertTrue(orderArgumentCaptor.getValue().getStatus());
        assertDeepEquals(testingOrder, orderArgumentCaptor.getValue());
    }


    private void assertDeepEquals(Order expected, Order actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getTime(), actual.getTime());
        Assert.assertEquals(expected.getDog(), actual.getDog());
        Assert.assertEquals(expected.getEmployee(), actual.getEmployee());
        Assert.assertEquals(expected.getService(), actual.getService());
        Assert.assertEquals(expected.getStatus(), actual.getStatus());
    }
}