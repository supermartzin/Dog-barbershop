package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CustomerDAO;
import cz.muni.fi.pa165.dao.OrderDAO;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Order;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;

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
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderDAO orderDAO;

    @Inject
    private CustomerDAO customerDAO;

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
        return orderDAO.getByCustomer(customer);
    }

    @Override
    public List<Order> getAllOrdersForDay(LocalDate date) throws DAOException {
        return orderDAO.getAllOrdersForDay(date);
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
    public BigDecimal getTotalAmountGained(LocalDateTime from, LocalDateTime to) {
        return null;
    }
}
