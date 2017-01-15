package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CustomerDAO;
import cz.muni.fi.pa165.dao.OrderDAO;
import cz.muni.fi.pa165.entities.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.*;

/**
 * Tests for correct contract implementation defined by {@link OrderService} interface
 *
 * @author Denis Richtarik
 * @version 22.11.2016 13:36
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTests {

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private CustomerDAO customerDAO;

    private OrderService orderService;

    private Order testingOrder;
    private Order testingOrder2;

    private Dog testingDog;
    private Service testingService;
    private Employee testingEmployee1;
    private Employee testingEmployee2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        orderService = new OrderServiceImpl(orderDAO, customerDAO);

        Customer testingCustomer = new Customer("micky", "12345", "Mr", "T", new Address("Boxing", 12, "Philadelphia", 55504, "Pennsylvania"), "rocky@balboa.com", "+4209658412");
        testingDog = new Dog("Rocky", "Balboa", 70, testingCustomer);
        testingService = new Service("Longcut", 50, new BigDecimal("5000"));
        testingOrder = new Order(LocalDateTime.of(2016, 11, 20, 15, 40, 23), testingDog, testingService);
        testingOrder2 = new Order(LocalDateTime.of(2016, 12, 13, 12, 0, 0), new Dog("Johny", "Rambo", 20, testingCustomer), testingService);
        testingEmployee1 = new Employee("Joey", "123456", "Joe", "Mutt", new Address("Boxing", 12, "Philadelphia", 55504, "Pennsylvania"), "joe@mail.cz", "+4209658412", new BigDecimal("20000"));
        testingEmployee2 = new Employee("Janish", "123456", "Jane", "Mutt", new Address("Boxing", 12, "Philadelphia", 55504, "Pennsylvania"), "jane@mail.cz", "+4209658413", new BigDecimal("25000"));
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
        doThrow(new IllegalArgumentException()).when(orderDAO).getByDog(testingDog);
        orderService.getByDog(testingDog);
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
        result.put(testingEmployee1, BigDecimal.valueOf(5000));
        result.put(testingEmployee2, BigDecimal.valueOf(5000));

        List<Order> mockedOrders = new ArrayList<>();

        testingOrder.setEmployee(testingEmployee1);
        testingOrder2.setEmployee(testingEmployee2);

        mockedOrders.add(testingOrder);
        mockedOrders.add(testingOrder2);

        when(orderDAO.getAllOrdersInTimeRange(any(), any())).thenReturn(mockedOrders);

        Map<Employee, BigDecimal> wantedHashMap = orderService.getTotalAmountGainedForEmployees(LocalDateTime.of(2016, 11, 20, 1, 0), LocalDateTime.of(2016, 12, 14, 1, 0));

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