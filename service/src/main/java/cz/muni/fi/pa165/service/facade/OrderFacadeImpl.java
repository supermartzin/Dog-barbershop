package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.OrderDTO;
import cz.muni.fi.pa165.dto.ServiceDTO;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Order;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.facade.OrderFacade;

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
public class OrderFacadeImpl implements OrderFacade {

    @Override
    public void create(OrderDTO order) {

    }

    @Override
    public OrderDTO getById(long id) {
        return null;
    }

    @Override
    public List<OrderDTO> getAll() {
        return null;
    }

    @Override
    public List<OrderDTO> getByDog(DogDTO dog) {
        return null;
    }

    @Override
    public List<OrderDTO> getByCustomer(Customer customer) {
        return null;
    }

    @Override
    public List<OrderDTO> getAllOrderDTOsForDay(LocalDate date) {
        return null;
    }

    @Override
    public List<OrderDTO> getByService(ServiceDTO service) {
        return null;
    }

    @Override
    public void update(OrderDTO order) {

    }

    @Override
    public void delete(OrderDTO order) {

    }

    @Override
    public BigDecimal getTotalAmountGained(LocalDateTime from, LocalDateTime to) {
        return null;
    }
}
