package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Order;
import cz.muni.fi.pa165.entities.Service;

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
public class OrderServiceImpl implements OrderService {

    @Override
    public void create(Order order) {

    }

    @Override
    public Order getById(long id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public List<Order> getByDog(Dog dog) {
        return null;
    }

    @Override
    public List<Order> getByCustomer(Customer customer) {
        return null;
    }

    @Override
    public List<Order> getAllOrdersForDay(LocalDate date) {
        return null;
    }

    @Override
    public List<Order> getByService(Service service) {
        return null;
    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public BigDecimal getTotalAmountGained(LocalDateTime from, LocalDateTime to) {
        return null;
    }
}
