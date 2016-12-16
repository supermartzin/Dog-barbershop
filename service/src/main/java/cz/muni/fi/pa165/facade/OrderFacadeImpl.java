package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.OrderDTO;
import cz.muni.fi.pa165.dto.ServiceDTO;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Order;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.OrderService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Martin Vrábel
 * @version 31.10.2016 0:18
 */
@org.springframework.stereotype.Service
@Transactional
public class OrderFacadeImpl implements OrderFacade {

    private final OrderService orderService;
    private final BeanMappingService beanMappingService;

    @Inject
    public OrderFacadeImpl(OrderService orderService, BeanMappingService beanMappingService) {
        this.orderService = orderService;
        this.beanMappingService = beanMappingService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(OrderDTO orderDTO) throws FacadeException {
        if (orderDTO == null)
            throw new IllegalArgumentException("OrderDTO is null");

        try {
            Order order = beanMappingService.mapTo(orderDTO, Order.class);
            orderService.create(order);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDTO getById(long id) throws FacadeException {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be less than 0");

        try {
            return beanMappingService.mapTo(orderService.getById(id), OrderDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> getAll() throws FacadeException {
        try {
            return beanMappingService.mapTo(orderService.getAll(), OrderDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> getByDog(DogDTO dogDTO) throws FacadeException {
        if (dogDTO == null)
            throw new IllegalArgumentException("DogDTO is null");

        try {
            return beanMappingService.mapTo(orderService.getByDog(beanMappingService.mapTo(dogDTO, Dog.class)), OrderDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> getByCustomer(CustomerDTO customerDTO) throws FacadeException {
        if (customerDTO == null)
            throw new IllegalArgumentException("CustomerDTO is null");

        try {
            return beanMappingService.mapTo(orderService.getByCustomer(beanMappingService.mapTo(customerDTO, Customer.class)), OrderDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> getAllOrdersForDay(LocalDateTime date) throws FacadeException {
        if (date == null)
            throw new IllegalArgumentException("date is null");

        try {
            return beanMappingService.mapTo(orderService.getAllOrdersForDay(date), OrderDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> getByService(ServiceDTO serviceDTO) throws FacadeException {
        if (serviceDTO == null)
            throw new IllegalArgumentException("ServiceDTO is null");

        try {
            return beanMappingService.mapTo(orderService.getByService(beanMappingService.mapTo(serviceDTO, Service.class)), OrderDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(OrderDTO orderDTO) throws FacadeException {
        if (orderDTO == null)
            throw new IllegalArgumentException("OrderDTO is null");

        try {
            Order orderEntity = beanMappingService.mapTo(orderDTO, Order.class);
            orderService.update(orderEntity);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(OrderDTO orderDTO) throws FacadeException {
        if (orderDTO == null)
            throw new IllegalArgumentException("OrderDTO is null");

        try {
            Order order = beanMappingService.mapTo(orderDTO, Order.class);
            orderService.delete(order);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getTotalAmountGained(LocalDateTime from, LocalDateTime to) throws FacadeException {
        if (from == null)
            throw new IllegalArgumentException("From DateTime is null");
        if (to == null)
            throw new IllegalArgumentException("To DateTime is null");

        try {
            return orderService.getTotalAmountGained(from, to);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void orderCompleted(OrderDTO orderDTO) throws FacadeException {
        if (orderDTO == null)
            throw new IllegalArgumentException("OrderDTO is null");

        try {
            Order order = beanMappingService.mapTo(orderDTO, Order.class);

            orderService.orderCompleted(order);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }
}
