package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CustomerDAO;
import cz.muni.fi.pa165.dao.OrderDAO;
import cz.muni.fi.pa165.entities.*;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.exceptions.ServiceException;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Order order) throws ServiceException {
        if (order == null)
            throw new IllegalArgumentException("Order is null");

        try {
            orderDAO.create(order);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order getById(long id) throws ServiceException {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be less than 0");

        try {
            return orderDAO.getById(id);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getAll() throws ServiceException {
        try {
            return orderDAO.getAll();
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getByDog(Dog dog) throws ServiceException {
        if (dog == null)
            throw new IllegalArgumentException("dog is null");

        try {
            return orderDAO.getByDog(dog);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getByCustomer(Customer customer) throws ServiceException {
        if (customer == null)
            throw new IllegalArgumentException("customer is null");

        try {
            // get Customer from database
            Customer _customer = customerDAO.getById(customer.getId());

            List<Order> allCustomersOrders = new ArrayList<>();
            for (Dog dog : _customer.getDogs()) {
                // get Orders by Dog
                allCustomersOrders.addAll(orderDAO.getByDog(dog));
            }

            return allCustomersOrders;
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getAllOrdersForDay(LocalDateTime dateTime) throws ServiceException {
        if (dateTime == null)
            throw new IllegalArgumentException("DateTime is null");

        try {
            LocalDateTime from = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 0, 0);
            LocalDateTime to = from.plus(1, ChronoUnit.DAYS);

            return orderDAO.getAllOrdersInTimeRange(from, to);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getByService(Service service) throws ServiceException {
        if (service == null)
            throw new IllegalArgumentException("service is null");

        try {
            return orderDAO.getByService(service);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Order order) throws ServiceException {
        if (order == null)
            throw new IllegalArgumentException("order is null");

        try {
            orderDAO.update(order);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Order order) throws ServiceException {
        if (order == null)
            throw new IllegalArgumentException("order is null");

        try {
            orderDAO.delete(order);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getTotalAmountGained(LocalDateTime from, LocalDateTime to) throws ServiceException {
        if (from == null)
            throw new IllegalArgumentException("From DateTime is null");
        if (to == null)
            throw new IllegalArgumentException("To DateTime is null");

        try {
            // get Orders in range
            List<Order> orders = orderDAO.getAllOrdersInTimeRange(from, to);

            // sum all prices
            return orders.stream()
                         .map(Order::getService)
                         .map(Service::getPrice)
                         .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Employee, BigDecimal> getTotalAmountGainedForEmployees(LocalDateTime from, LocalDateTime to) throws ServiceException {
        if (from == null)
            throw new IllegalArgumentException("From DateTime is null");
        if (to == null)
            throw new IllegalArgumentException("To DateTime is null");

        try {
            // get Orders in range
            List<Order> orders = orderDAO.getAllOrdersInTimeRange(from, to);

            Map<Employee, BigDecimal> totalEmployeeSums = new HashMap<>();
            orders.forEach(order -> {
                Employee employee = order.getEmployee();
                if (employee == null)
                    return;

                BigDecimal totalSum = totalEmployeeSums.get(employee);
                if (totalSum == null)
                    totalSum = BigDecimal.ZERO;

                // add price to total sum
                totalSum = totalSum.add(order.getService().getPrice());

                // save to map
                totalEmployeeSums.put(employee, totalSum);
            });

            return totalEmployeeSums;
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void orderCompleted(Order order) throws ServiceException {
        try {
            order.setStatus(true);

            orderDAO.update(order);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }
}
