package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CustomerDAO;
import cz.muni.fi.pa165.dao.DogDAO;
import cz.muni.fi.pa165.dao.OrderDAO;
import cz.muni.fi.pa165.entities.*;
import cz.muni.fi.pa165.exceptions.DAOException;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * {@inheritDoc}
 *
 * @author Martin Vrábel
 * @version 31.10.2016 0:18
 */
@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    private final CustomerDAO customerDAO;

    @Inject
    public OrderServiceImpl(OrderDAO orderDAO, CustomerDAO customerDAO) {
        this.orderDAO = orderDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public void create(Order order) throws DAOException {
        orderDAO.create(order);
    }

    @Override
    public Order getById(long id) throws DAOException {
        return orderDAO.getById(id);
    }

    @Override
    public List<Order> getAll() throws DAOException {
        return orderDAO.getAll();
    }

    @Override
    public List<Order> getByDog(Dog dog) throws DAOException {
        return orderDAO.getByDog(dog);
    }

    @Override
    public List<Order> getByCustomer(Customer customer) throws DAOException {
        Customer myCustomer = customerDAO.getById(customer.getId());
        Set<Dog> allCustomersDogs = myCustomer.getDogs();
        List<Order> allCustomersOrders = new ArrayList<>();

        for(Dog dog : allCustomersDogs){
            allCustomersOrders.addAll(orderDAO.getByDog(dog));
        }
        return allCustomersOrders;
    }

    @Override
    public List<Order> getAllOrdersForDay(LocalDateTime dateTime) throws DAOException {
        LocalDateTime from = LocalDateTime.from(dateTime);
        LocalDateTime to = from.plus(1, ChronoUnit.DAYS);
        return orderDAO.getAllOrdersInTimeRange(from, to);
    }

    @Override
    public List<Order> getByService(Service service) throws DAOException {
       return orderDAO.getByService(service);
    }

    @Override
    public void update(Order order) throws DAOException {
        orderDAO.update(order);
    }

    @Override
    public void delete(Order order) throws DAOException {
        orderDAO.delete(order);
    }

    @Override
    public BigDecimal getTotalAmountGained(LocalDateTime from, LocalDateTime to) throws DAOException {
        List<Order> orders = orderDAO.getAllOrdersInTimeRange(from, to);
        BigDecimal total = new BigDecimal("0.0");
        for(Order order : orders) {
            total = total.add(order.getService().getPrice());
        }
        return total;
    }

    @Override
    public Map<Employee, BigDecimal> getTotalAmountGainedByEmployee(LocalDateTime from, LocalDateTime to) throws DAOException {
        List<Order> orders = orderDAO.getAllOrdersInTimeRange(from, to);
        Map<Employee, BigDecimal> total = new HashMap<>();
        for(Order order : orders) {
            Employee e = order.getEmployee();
            if(!total.containsKey(e)) {
                total.put(e, new BigDecimal("0.0"));
            }
            total.put(e, total.get(e).add(order.getService().getPrice()));
        }
        return total;
    }

    @Override
    public void orderCompleted(Order order) {
        try {
            order.setStatus(true);
            orderDAO.update(order);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
