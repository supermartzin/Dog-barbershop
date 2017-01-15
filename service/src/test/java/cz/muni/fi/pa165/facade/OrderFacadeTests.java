package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.OrderDTO;
import cz.muni.fi.pa165.dto.ServiceDTO;
import cz.muni.fi.pa165.entities.*;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for correct contract implementation defined by {@link OrderFacade} interface
 *
 * @author Martin Vrábel
 * @version 22.11.2016 14:10
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderFacadeTests {

    @Mock
    private OrderService orderService;

    @Mock
    private BeanMappingService mappingService;

    private OrderFacade orderFacade;

    private OrderDTO testingOrderDTO;
    private Order testingOrder;

    @Before
    public void setUp( ) {
        MockitoAnnotations.initMocks(this);

        orderFacade = new OrderFacadeImpl(orderService, mappingService);

        testingOrderDTO = new OrderDTO(LocalDateTime.of(1995, 11, 28, 14, 22, 36),
                                       new DogDTO("Charlie", "Terrier", 4),
                                       new ServiceDTO("Furcutting", 20, BigDecimal.valueOf(250)));
        Customer customer = new Customer("testing_cust", "passwd125*", "Peter", "Thiel",
                                         new Address("Loving Terrace", 42, "Kensington", 44587, "Georgia"),
                                        "customer@mail.com", "+855 698 23 45 78");
        testingOrder = new Order(LocalDateTime.of(1995, 11, 28, 14, 22, 36),
                                 new Dog("Charlie", "Terrier", 4, customer),
                                 new Service("Furcutting", 20, BigDecimal.valueOf(250)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_orderNull() throws FacadeException {
        orderFacade.create(null);
    }

    @Test(expected = FacadeException.class)
    public void testCreate_orderServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(orderService).create(any(Order.class));

        orderFacade.create(testingOrderDTO);
    }

    @Test
    public void testCreate_orderValid() throws ServiceException, FacadeException {
        when(mappingService.mapTo(testingOrderDTO, Order.class)).thenReturn(testingOrder);

        orderFacade.create(testingOrderDTO);

        verify(mappingService, times(1)).mapTo(testingOrderDTO, Order.class);
        verify(orderService, times(1)).create(testingOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_idInvalid() throws FacadeException {
        orderFacade.getById(-5);
    }

    @Test
    public void testGetById_orderDoesNotExist() throws ServiceException, FacadeException {
        when(orderService.getById(anyLong())).thenReturn(null);
        when(mappingService.mapTo(any(Order.class), eq(OrderDTO.class))).thenReturn(null);

        OrderDTO retrievedOrderDTO = orderFacade.getById(120);

        assertNull(retrievedOrderDTO);

        verify(orderService, times(1)).getById(120);
        verify(mappingService, times(1)).mapTo(any(Order.class), eq(OrderDTO.class));
    }

    @Test(expected = FacadeException.class)
    public void testGetById_orderServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(orderService).getById(anyLong());

        orderFacade.getById(15);
    }

    @Test
    public void testGetById_idValid() throws ServiceException, FacadeException {
        when(orderService.getById(anyLong())).thenReturn(testingOrder);
        when(mappingService.mapTo(testingOrder, OrderDTO.class)).thenReturn(testingOrderDTO);

        OrderDTO retrievedOrderDTO = orderFacade.getById(15);

        assertNotNull(retrievedOrderDTO);
        assertEquals(testingOrderDTO, retrievedOrderDTO);

        verify(orderService, times(1)).getById(15);
        verify(mappingService, times(1)).mapTo(testingOrder, OrderDTO.class);
    }

    @Test
    public void testGetAll_noOrders() throws ServiceException, FacadeException {
        when(orderService.getAll()).thenReturn(Collections.emptyList());
        when(mappingService.mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class))).thenReturn(Collections.emptyList());

        List<OrderDTO> retrievedOrders = orderFacade.getAll();

        assertNotNull(retrievedOrders);
        assertTrue(retrievedOrders.isEmpty());

        verify(orderService, times(1)).getAll();
        verify(mappingService, times(1)).mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class));
    }

    @Test(expected = FacadeException.class)
    public void testGetAll_orderServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(orderService).getAll();

        orderFacade.getAll();
    }

    @Test
    public void testGetAll_ordersValid() throws ServiceException, FacadeException {
        OrderDTO orderDTO1 = new OrderDTO(LocalDateTime.of(2016, 2, 13, 10, 0), new DogDTO("Lessie", "Ordinary", 12), new ServiceDTO("Testing service", 12, BigDecimal.valueOf(125)));
        OrderDTO orderDTO2 = new OrderDTO(LocalDateTime.of(2014, 8, 29, 3, 15, 22), new DogDTO("Winkle", "Retriever", 2), new ServiceDTO("'As usual' service", 25, BigDecimal.valueOf(490)));

        when(mappingService.mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class))).thenReturn(Arrays.asList(testingOrderDTO, orderDTO1, orderDTO2));

        List<OrderDTO> retrievedOrders = orderFacade.getAll();

        assertNotNull(retrievedOrders);
        assertFalse(retrievedOrders.isEmpty());
        assertEquals(3, retrievedOrders.size());
        assertThat(retrievedOrders, hasItems(testingOrderDTO, orderDTO1, orderDTO2));

        verify(orderService, times(1)).getAll();
        verify(mappingService, times(1)).mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByDog_dogNull() throws FacadeException {
        orderFacade.getByDog(null);
    }

    @Test
    public void testGetByDog_noOrders() throws ServiceException, FacadeException {
        when(mappingService.mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class))).thenReturn(Collections.emptyList());
        when(orderService.getByDog(any(Dog.class))).thenReturn(Collections.emptyList());

        List<OrderDTO> retrievedOrders = orderFacade.getByDog(new DogDTO());

        assertNotNull(retrievedOrders);
        assertTrue(retrievedOrders.isEmpty());

        verify(mappingService, times(1)).mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class));
        verify(mappingService, times(1)).mapTo(any(DogDTO.class), eq(Dog.class));
        verify(orderService, times(1)).getByDog(any(Dog.class));
    }

    @Test(expected = FacadeException.class)
    public void testGetByDog_orderServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(orderService).getByDog(any(Dog.class));

        orderFacade.getByDog(new DogDTO());
    }

    @Test
    public void testGetByDog_dogValid() throws ServiceException, FacadeException {
        OrderDTO orderDTO1 = new OrderDTO(LocalDateTime.of(2016, 2, 13, 10, 0), new DogDTO("Lessie", "Ordinary", 12), new ServiceDTO("Testing service", 12, BigDecimal.valueOf(125)));
        OrderDTO orderDTO2 = new OrderDTO(LocalDateTime.of(2014, 8, 29, 3, 15, 22), new DogDTO("Winkle", "Retriever", 2), new ServiceDTO("'As usual' service", 25, BigDecimal.valueOf(490)));

        when(mappingService.mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class))).thenReturn(Arrays.asList(testingOrderDTO, orderDTO1, orderDTO2));

        List<OrderDTO> retrievedOrders = orderFacade.getByDog(new DogDTO());

        assertNotNull(retrievedOrders);
        assertFalse(retrievedOrders.isEmpty());
        assertEquals(3, retrievedOrders.size());
        assertThat(retrievedOrders, hasItems(testingOrderDTO, orderDTO1, orderDTO2));

        verify(mappingService, times(1)).mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class));
        verify(mappingService, times(1)).mapTo(any(DogDTO.class), eq(Dog.class));
        verify(orderService, times(1)).getByDog(any(Dog.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByCustomer_customerNull() throws FacadeException {
        orderFacade.getByCustomer(null);
    }

    @Test
    public void testGetByCustomer_noOrders() throws ServiceException, FacadeException {
        when(orderService.getByCustomer(any(Customer.class))).thenReturn(Collections.emptyList());
        when(mappingService.mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class))).thenReturn(Collections.emptyList());

        List<OrderDTO> retrievedOrders = orderFacade.getByCustomer(new CustomerDTO());

        assertNotNull(retrievedOrders);
        assertTrue(retrievedOrders.isEmpty());

        verify(orderService, times(1)).getByCustomer(any(Customer.class));
        verify(mappingService, times(1)).mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class));
        verify(mappingService, times(1)).mapTo(any(CustomerDTO.class), eq(Customer.class));
    }

    @Test(expected = FacadeException.class)
    public void testGetByCustomer_orderServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(orderService).getByCustomer(any(Customer.class));

        orderFacade.getByCustomer(new CustomerDTO());
    }

    @Test
    public void testGetByCustomer_customerValid() throws ServiceException, FacadeException {
        OrderDTO orderDTO1 = new OrderDTO(LocalDateTime.of(2016, 2, 13, 10, 0), new DogDTO("Lessie", "Ordinary", 12), new ServiceDTO("Testing service", 12, BigDecimal.valueOf(125)));
        OrderDTO orderDTO2 = new OrderDTO(LocalDateTime.of(2014, 8, 29, 3, 15, 22), new DogDTO("Winkle", "Retriever", 2), new ServiceDTO("'As usual' service", 25, BigDecimal.valueOf(490)));

        when(mappingService.mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class))).thenReturn(Arrays.asList(testingOrderDTO, orderDTO1, orderDTO2));

        List<OrderDTO> retrievedOrders = orderFacade.getByCustomer(new CustomerDTO());

        assertNotNull(retrievedOrders);
        assertFalse(retrievedOrders.isEmpty());
        assertEquals(3, retrievedOrders.size());
        assertThat(retrievedOrders, hasItems(testingOrderDTO, orderDTO1, orderDTO2));

        verify(orderService, times(1)).getByCustomer(any(Customer.class));
        verify(mappingService, times(1)).mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class));
        verify(mappingService, times(1)).mapTo(any(CustomerDTO.class), eq(Customer.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAllOrdersForDay_nullDateTime() throws FacadeException {
        orderFacade.getAllOrdersForDay(null);
    }

    @Test
    public void testGetAllOrdersForDay_noOrders() throws ServiceException, FacadeException {
        when(orderService.getAllOrdersForDay(any(LocalDateTime.class))).thenReturn(Collections.emptyList());
        when(mappingService.mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class))).thenReturn(Collections.emptyList());

        List<OrderDTO> retrievedOrders = orderFacade.getAllOrdersForDay(LocalDateTime.of(2017, 2, 25, 12, 0));

        assertNotNull(retrievedOrders);
        assertTrue(retrievedOrders.isEmpty());

        verify(orderService, times(1)).getAllOrdersForDay(LocalDateTime.of(2017, 2, 25, 12, 0));
        verify(mappingService, times(1)).mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class));
    }

    @Test(expected = FacadeException.class)
    public void testGetAllOrdersForDay_orderServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(orderService).getAllOrdersForDay(any(LocalDateTime.class));

        orderFacade.getAllOrdersForDay(LocalDateTime.of(2014, 5, 6, 10, 22));
    }

    @Test
    public void testGetAllOrdersForDay_dateTimeValid() throws ServiceException, FacadeException {
        OrderDTO orderDTO1 = new OrderDTO(LocalDateTime.of(2016, 2, 13, 10, 0), new DogDTO("Lessie", "Ordinary", 12), new ServiceDTO("Testing service", 12, BigDecimal.valueOf(125)));
        OrderDTO orderDTO2 = new OrderDTO(LocalDateTime.of(2014, 8, 29, 3, 15, 22), new DogDTO("Winkle", "Retriever", 2), new ServiceDTO("'As usual' service", 25, BigDecimal.valueOf(490)));

        when(mappingService.mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class))).thenReturn(Arrays.asList(testingOrderDTO, orderDTO1, orderDTO2));

        List<OrderDTO> retrievedOrders = orderFacade.getAllOrdersForDay(LocalDateTime.of(2015, 5, 5, 10, 10));

        assertNotNull(retrievedOrders);
        assertFalse(retrievedOrders.isEmpty());
        assertEquals(3, retrievedOrders.size());
        assertThat(retrievedOrders, hasItems(testingOrderDTO, orderDTO1, orderDTO2));

        verify(mappingService, times(1)).mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class));
        verify(orderService, times(1)).getAllOrdersForDay(any(LocalDateTime.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByService_serviceNull() throws FacadeException {
        orderFacade.getByService(null);
    }

    @Test
    public void testGetByService_noOrders() throws ServiceException, FacadeException {
        when(orderService.getByService(any(Service.class))).thenReturn(Collections.emptyList());
        when(mappingService.mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class))).thenReturn(Collections.emptyList());

        List<OrderDTO> retrievedOrders = orderFacade.getByService(new ServiceDTO());

        assertNotNull(retrievedOrders);
        assertTrue(retrievedOrders.isEmpty());

        verify(mappingService, times(1)).mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class));
        verify(mappingService, times(1)).mapTo(any(ServiceDTO.class), eq(Service.class));
        verify(orderService, times(1)).getByService(any(Service.class));
    }

    @Test(expected = FacadeException.class)
    public void testGetByService_orderServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(orderService).getByService(any(Service.class));

        orderFacade.getByService(new ServiceDTO());
    }

    @Test
    public void testGetByService_serviceValid() throws ServiceException, FacadeException {
        OrderDTO orderDTO1 = new OrderDTO(LocalDateTime.of(2016, 2, 13, 10, 0), new DogDTO("Lessie", "Ordinary", 12), new ServiceDTO("Testing service", 12, BigDecimal.valueOf(125)));
        OrderDTO orderDTO2 = new OrderDTO(LocalDateTime.of(2014, 8, 29, 3, 15, 22), new DogDTO("Winkle", "Retriever", 2), new ServiceDTO("'As usual' service", 25, BigDecimal.valueOf(490)));

        when(mappingService.mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class))).thenReturn(Arrays.asList(testingOrderDTO, orderDTO1, orderDTO2));

        List<OrderDTO> retrievedOrders = orderFacade.getByService(new ServiceDTO());

        assertNotNull(retrievedOrders);
        assertFalse(retrievedOrders.isEmpty());
        assertEquals(3, retrievedOrders.size());
        assertThat(retrievedOrders, hasItems(testingOrderDTO, orderDTO1, orderDTO2));

        verify(mappingService, times(1)).mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class));
        verify(mappingService, times(1)).mapTo(any(ServiceDTO.class), eq(Service.class));
        verify(orderService, times(1)).getByService(any(Service.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByState_stateNull() throws FacadeException {
        orderFacade.getByState(null);
    }

    @Test
    public void testGetByState_noOrders() throws ServiceException, FacadeException {
        when(orderService.getByStatus(any(Boolean.class))).thenReturn(Collections.emptyList());
        when(mappingService.mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class))).thenReturn(Collections.emptyList());

        List<OrderDTO> retrievedOrders = orderFacade.getByState(false);

        assertNotNull(retrievedOrders);
        assertTrue(retrievedOrders.isEmpty());

        verify(mappingService, times(1)).mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class));
        verify(orderService, times(1)).getByStatus(any(Boolean.class));
    }

    @Test(expected = FacadeException.class)
    public void testGetByState_orderServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(orderService).getByStatus(any(Boolean.class));

        orderFacade.getByState(true);
    }

    @Test
    public void testGetByState_stateValid() throws ServiceException, FacadeException {
        OrderDTO orderDTO1 = new OrderDTO(LocalDateTime.of(2016, 2, 13, 10, 0), new DogDTO("Lessie", "Ordinary", 12), new ServiceDTO("Testing service", 12, BigDecimal.valueOf(125)));
        orderDTO1.setStatus(false);
        OrderDTO orderDTO2 = new OrderDTO(LocalDateTime.of(2014, 8, 29, 3, 15, 22), new DogDTO("Winkle", "Retriever", 2), new ServiceDTO("'As usual' service", 25, BigDecimal.valueOf(490)));
        orderDTO2.setStatus(false);

        when(mappingService.mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class))).thenReturn(Arrays.asList(testingOrderDTO, orderDTO1, orderDTO2));

        List<OrderDTO> retrievedOrders = orderFacade.getByState(false);

        assertNotNull(retrievedOrders);
        assertFalse(retrievedOrders.isEmpty());
        assertEquals(3, retrievedOrders.size());
        assertThat(retrievedOrders, hasItems(testingOrderDTO, orderDTO1, orderDTO2));

        verify(mappingService, times(1)).mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class));
        verify(orderService, times(1)).getByStatus(false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTotalAmountGained_fromDateTimeNull() throws FacadeException {
        orderFacade.getTotalAmountGained(null, LocalDateTime.of(2000, 9, 11, 5, 24));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTotalAmountGained_toDateTimeNull() throws FacadeException {
        orderFacade.getTotalAmountGained(LocalDateTime.of(2000, 9, 11, 5, 24), null);
    }

    @Test
    public void getTotalAmountGained_noOrders() throws ServiceException, FacadeException {
        when(orderService.getTotalAmountGained(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(BigDecimal.ZERO);

        BigDecimal totalAmountGained = orderFacade.getTotalAmountGained(LocalDateTime.of(2017, 1, 12, 20, 0), LocalDateTime.of(2017, 1, 15, 20, 0));

        assertNotNull(totalAmountGained);
        assertEquals(BigDecimal.ZERO, totalAmountGained);

        verify(orderService, times(1)).getTotalAmountGained(LocalDateTime.of(2017, 1, 12, 20, 0), LocalDateTime.of(2017, 1, 15, 20, 0));
    }

    @Test(expected = FacadeException.class)
    public void getTotalAmountGained_orderServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(orderService).getTotalAmountGained(any(LocalDateTime.class), any(LocalDateTime.class));

        orderFacade.getTotalAmountGained(LocalDateTime.of(1995, 12, 1, 10, 5), LocalDateTime.of(2000, 12, 31, 23, 59));
    }

    @Test
    public void getTotalAmountGained_dateTimesValid() throws ServiceException, FacadeException {
        when(orderService.getTotalAmountGained(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(BigDecimal.valueOf(12500));

        BigDecimal totalAmountGained = orderFacade.getTotalAmountGained(LocalDateTime.of(2017, 1, 12, 20, 0), LocalDateTime.of(2017, 1, 15, 20, 0));

        assertNotNull(totalAmountGained);
        assertEquals(BigDecimal.valueOf(12500), totalAmountGained);

        verify(orderService, times(1)).getTotalAmountGained(LocalDateTime.of(2017, 1, 12, 20, 0), LocalDateTime.of(2017, 1, 15, 20, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_orderNull() throws FacadeException {
        orderFacade.update(null);
    }

    @Test(expected = FacadeException.class)
    public void testUpdate_orderDoesNotExist() throws ServiceException, FacadeException {
        when(mappingService.mapTo(testingOrderDTO, Order.class)).thenReturn(testingOrder);
        doThrow(ServiceException.class).when(orderService).update(testingOrder);

        orderFacade.update(testingOrderDTO);
    }

    @Test(expected = FacadeException.class)
    public void testUpdate_orderServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(orderService).update(any(Order.class));

        orderFacade.update(testingOrderDTO);
    }

    @Test
    public void testUpdate_orderValid() throws ServiceException, FacadeException {
        when(mappingService.mapTo(any(OrderDTO.class), eq(Order.class))).thenReturn(testingOrder);

        orderFacade.update(testingOrderDTO);

        verify(mappingService, times(1)).mapTo(testingOrderDTO, Order.class);
        verify(orderService, times(1)).update(testingOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_orderNull() throws FacadeException {
        orderFacade.delete(null);
    }

    @Test(expected = FacadeException.class)
    public void testDelete_orderDoesNotExist() throws ServiceException, FacadeException {
        when(mappingService.mapTo(testingOrderDTO, Order.class)).thenReturn(testingOrder);
        doThrow(ServiceException.class).when(orderService).delete(testingOrder);

        orderFacade.delete(testingOrderDTO);
    }

    @Test(expected = FacadeException.class)
    public void testDelete_orderServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(orderService).delete(any(Order.class));

        orderFacade.delete(testingOrderDTO);
    }

    @Test
    public void testDelete_orderValid() throws ServiceException, FacadeException {
        when(mappingService.mapTo(any(OrderDTO.class), eq(Order.class))).thenReturn(testingOrder);

        orderFacade.delete(testingOrderDTO);

        verify(mappingService, times(1)).mapTo(testingOrderDTO, Order.class);
        verify(orderService, times(1)).delete(testingOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderCompleted_orderNull() throws FacadeException {
        orderFacade.orderCompleted(null);
    }

    @Test(expected = FacadeException.class)
    public void testOrderCompleted_orderDoesNotExist() throws ServiceException, FacadeException {
        when(mappingService.mapTo(testingOrderDTO, Order.class)).thenReturn(testingOrder);
        doThrow(ServiceException.class).when(orderService).orderCompleted(testingOrder);

        orderFacade.orderCompleted(testingOrderDTO);
    }

    @Test(expected = FacadeException.class)
    public void testOrderCompleted_orderServiceError() throws ServiceException, FacadeException {
        doThrow(ServiceException.class).when(orderService).orderCompleted(any(Order.class));

        orderFacade.orderCompleted(testingOrderDTO);
    }

    @Test
    public void testOrderCompleted_orderValid() throws ServiceException, FacadeException {
        when(mappingService.mapTo(any(OrderDTO.class), eq(Order.class))).thenReturn(testingOrder);

        orderFacade.orderCompleted(testingOrderDTO);

        verify(mappingService, times(1)).mapTo(testingOrderDTO, Order.class);
        verify(orderService, times(1)).orderCompleted(testingOrder);
    }
}