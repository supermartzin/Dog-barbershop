package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.entities.Order;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.exceptions.ValidationException;
import cz.muni.fi.pa165.validation.EntityValidator;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Martin Vrábel
 * @version 31.10.2016 0:18
 */
@Repository
public class OrderDAOImpl implements OrderDAO {

    @PersistenceContext
    private EntityManager manager;

    private final EntityValidator validator;

    @Inject
    public OrderDAOImpl(EntityValidator entityValidator) {
        if (entityValidator == null)
            throw new IllegalArgumentException("Entity Validator is null");

        this.validator = entityValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Order order) throws DAOException {
        if (order == null)
            throw new IllegalArgumentException("order is null");
        if (order.getId() > 0)
            throw new DAOException("Order ID is already set");

        try {
            // validate
            validator.validate(order);

            // save Order to database
            manager.persist(order);
        } catch (ValidationException | PersistenceException | IllegalStateException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order getById(long id) throws DAOException {
        if (id < 0)
            throw new IllegalArgumentException("Order ID must be positive number");

        return manager.find(Order.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getAll() throws DAOException {
        return manager.createQuery("SELECT ord from Order ord", Order.class)
                      .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getByDog(Dog dog) throws DAOException {
        if (dog == null)
            throw new IllegalArgumentException("dog is null");

        return manager.createQuery("SELECT ord FROM Order ord WHERE ord.dog = :dog", Order.class)
                      .setParameter("dog", dog)
                      .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getByService(Service service) throws DAOException {
        if (service == null)
            throw new IllegalArgumentException("service is null");

        return manager.createQuery("SELECT ord FROM Order ord WHERE ord.service = :service", Order.class)
                      .setParameter("service", service)
                      .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getByEmployee(Employee employee) throws DAOException {
        if (employee == null)
            throw new IllegalArgumentException("employee is null");

        return manager.createQuery("SELECT ord FROM Order ord WHERE ord.employee = :employee", Order.class)
                      .setParameter("employee", employee)
                      .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getAllOrdersInTimeRange(LocalDateTime from, LocalDateTime to) throws DAOException {
        if (from == null || to == null)
            throw new IllegalArgumentException("Date cannot be null");
        if (to.isBefore(from))
            throw new IllegalArgumentException("Date to is lower than date from");

        return manager.createQuery("SELECT ord FROM Order ord WHERE ord.time > :dateFrom AND ord.time < :dateTo", Order.class)
                      .setParameter("dateFrom", from)
                      .setParameter("dateTo", to)
                      .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Order order) throws DAOException {
        if (order == null)
            throw new IllegalArgumentException("order is null");

        try {
            // validate
            validator.validate(order);

            Order existingOrder = manager.find(Order.class, order.getId());
            if (existingOrder == null)
                throw new DAOException("Order with id: " + order.getId() + " you want to update doesn't exist!");

            // update Order in database
            manager.merge(order);
        } catch (ValidationException | PersistenceException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Order order) throws DAOException {
        if (order == null)
            throw new IllegalArgumentException("order is null");

        try {
            Order orderToRemove = manager.find(Order.class, order.getId());
            if (orderToRemove == null)
                throw new DAOException("Order with id: " + order.getId() + " you want to delete doesn't exist!");

            // delete Order in database
            manager.remove(order);
        } catch (PersistenceException ex) {
            throw new DAOException(ex);
        }
    }
}
