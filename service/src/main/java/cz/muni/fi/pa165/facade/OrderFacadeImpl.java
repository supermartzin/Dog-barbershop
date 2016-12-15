package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.OrderDTO;
import cz.muni.fi.pa165.dto.ServiceDTO;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Order;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.OrderService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Inject
    private OrderService orderService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public void create(OrderDTO service) throws DAOException {
        Order orderEntity = beanMappingService.mapTo(service, Order.class);
        orderService.create(orderEntity);
    }

    @Override
    public OrderDTO getById(long id) throws DAOException {
        return beanMappingService.mapTo(orderService.getById(id), OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getAll() throws DAOException {
        return beanMappingService.mapTo(orderService.getAll(), OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getByDog(DogDTO dog) throws DAOException {
        return beanMappingService.mapTo(orderService.getByDog(beanMappingService.mapTo(dog, Dog.class)), OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getByCustomer(Customer customer) throws DAOException {
        return beanMappingService.mapTo(orderService.getByCustomer(beanMappingService.mapTo(customer, Customer.class)), OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getAllOrdersForDay(LocalDateTime date) throws DAOException {
        return beanMappingService.mapTo(orderService.getAllOrdersForDay(date), OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getByService(ServiceDTO service) throws DAOException {
        return beanMappingService.mapTo(orderService.getByService(beanMappingService.mapTo(service, Service.class)), OrderDTO.class);
    }

    @Override
    public void update(OrderDTO service) throws DAOException {
        Order orderEntity = beanMappingService.mapTo(service, Order.class);
        orderService.update(orderEntity);
    }

    @Override
    public void delete(OrderDTO service) throws DAOException {
        Order orderEntity = beanMappingService.mapTo(service, Order.class);
        orderService.delete(orderEntity);
    }

    @Override
    public BigDecimal getTotalAmountGained(LocalDateTime from, LocalDateTime to) throws DAOException {
        return orderService.getTotalAmountGained(from, to);
    }
}
