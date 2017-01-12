package cz.muni.fi.pa165.facade;

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
public class OrderFacadeTest {

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
    public void testGetById_orderValid() throws ServiceException, FacadeException {
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

        verify(orderService, times(1)).getAll();
        verify(mappingService, times(1)).mapTo(anyCollectionOf(Order.class), eq(OrderDTO.class));
    }

    @Test
    public void getByDog() {
        // TODO implement
    }

    @Test
    public void getByCustomer( ) {
        // TODO implement
    }

    @Test
    public void getAllOrdersForDay( ) {
        // TODO implement
    }

    @Test
    public void getByService( ) {
        // TODO implement
    }

    @Test
    public void getByState( ) {
        // TODO implement
    }

    @Test
    public void getTotalAmountGained( ) {
        // TODO implement
    }
    
    @Test
    public void update( ) {
        // TODO implement
    }

    @Test
    public void delete( ) {
        // TODO implement
    }

    @Test
    public void orderCompleted( ) {
        // TODO implement
    }
}