package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.*;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;

/**
 * Tests for correct contract implementation defined by {@link OrderDAO} interface
 *
 * @author Denis Richtarik
 * @version 31.10.2016
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:persistence-config.xml"})
public class OrderDAOTest {

    @PersistenceContext
    private EntityManager manager;

    @Inject
    private OrderDAO orderDAO;

    private Order testingOrder;

    @Before
    public void setUp() throws Exception {
        Customer customer = new Customer("testing", "password", "John", "Tester",
                new Address("Testing Avenue", 25, "Testero", 2356, "Testing Republic"),
                "tester@mail.com", "+4209658412");
        Employee employee = new Employee("testEmployee", "admin123", "James", "Working",
                new Address("Test street", 14, "Testovato", 11452, "Testing Republic"),
                "testEmployee@company.biz", "+45-996-352-8965", BigDecimal.valueOf(14500));
        Dog dog = new Dog("Doggo", "ChauChau", 1, customer);
        customer.addDog(dog);
        Service service = new Service("testingService", 55, BigDecimal.valueOf(250));

        testingOrder = new Order(LocalDateTime.of(2016, 11, 20, 15, 40, 23), dog, service);
        testingOrder.setEmployee(employee);

        manager.persist(customer);
        manager.persist(employee);
        manager.persist(service);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_orderNull() throws DAOException {
        orderDAO.create(null);
    }

    @Test(expected = DAOException.class)
    public void testCreate_timeNull() throws DAOException {
        testingOrder.setTime(null);

        orderDAO.create(testingOrder);
    }

    @Test(expected = DAOException.class)
    public void testCreate_dogNull() throws DAOException {
        testingOrder.setDog(null);

        orderDAO.create(testingOrder);
    }

    @Test(expected = DAOException.class)
    public void testCreate_serviceNull() throws DAOException {
        testingOrder.setService(null);

        orderDAO.create(testingOrder);
    }

    @Test
    public void testCreate_employeeNull() throws DAOException {
        testingOrder.setEmployee(null);

        orderDAO.create(testingOrder);

        Assert.assertTrue(testingOrder.getId() > 0);

        Order foundOrder = manager.find(Order.class, testingOrder.getId());

        Assert.assertNotNull(foundOrder);
        Assert.assertNull(foundOrder.getEmployee());
    }

    @Test
    public void testCreate_orderValid() throws DAOException {
        orderDAO.create(testingOrder);

        Assert.assertTrue(testingOrder.getId() > 0);

        Order foundOrder = manager.find(Order.class, testingOrder.getId());

        Assert.assertNotNull(foundOrder);
        assertDeepEquals(testingOrder, foundOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_idInvalid() throws DAOException {
        orderDAO.getById(-10);
    }

    @Test
    public void testGetById_orderDoesNotExist() throws DAOException {
        Order retrievedOrder = orderDAO.getById(100);

        Assert.assertNull(retrievedOrder);
    }

    @Test
    public void testGetById_orderValid() throws DAOException {
        manager.persist(testingOrder);

        Order retrievedOrder = orderDAO.getById(testingOrder.getId());

        Assert.assertNotNull(retrievedOrder);
        assertDeepEquals(testingOrder, retrievedOrder);
    }

    @Test
    public void testGetAll_noOrders() throws DAOException {
        List<Order> orders = orderDAO.getAll();

        Assert.assertNotNull(orders);
        Assert.assertTrue(orders.isEmpty());
    }

    @Test
    public void testGetAll_ordersExist() throws DAOException {
        // prepare data
        Customer customer = new Customer("tester #568", "secret14", "Jurij", "Aksemov", null, "jurij@email.ru", "+46-8965231452");
        Employee employee = new Employee("TestingEmpl", "PasswordAdmin", "Joseph", "Gordon", null, "joseph@dogbarbers.com", "+8891254 9635", BigDecimal.valueOf(25050));
        Dog dog = new Dog("Cheeki Breeki", "Russian Boxer", 5, customer);
        Service service = new Service("Cutting", 30, BigDecimal.valueOf(155));
        Order order = new Order(LocalDateTime.of(2016, 11, 19, 15, 23, 48), dog, service);
        order.setEmployee(employee);

        // save them to database
        manager.persist(customer);
        manager.persist(employee);
        manager.persist(dog);
        manager.persist(service);
        manager.persist(testingOrder);
        manager.persist(order);

        List<Order> retrievedOrders = orderDAO.getAll();

        Assert.assertNotNull(retrievedOrders);
        Assert.assertFalse(retrievedOrders.isEmpty());
        Assert.assertEquals(2, retrievedOrders.size());
        Assert.assertThat(retrievedOrders, hasItems(testingOrder, order));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByService_serviceNull() throws DAOException {
        orderDAO.getByService(null);
    }

    @Test
    public void testGetByService_noSuchService() throws DAOException {
        Service service = new Service("Testing service", 75, BigDecimal.valueOf(1500));
        manager.persist(service);

        manager.persist(testingOrder);

        List<Order> retrievedOrders = orderDAO.getByService(service);

        Assert.assertNotNull(retrievedOrders);
        Assert.assertTrue(retrievedOrders.isEmpty());
    }

    @Test
    public void testGetByService_serviceValid() throws DAOException {
        Service service = new Service("Testing service", 75, BigDecimal.valueOf(1500));
        manager.persist(service);

        testingOrder.setService(service);
        manager.persist(testingOrder);

        List<Order> retrievedOrders = orderDAO.getByService(service);

        Assert.assertNotNull(retrievedOrders);
        Assert.assertFalse(retrievedOrders.isEmpty());

        Order order = retrievedOrders.get(0);
        assertDeepEquals(testingOrder, order);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByDog_dogNull() throws DAOException {
        orderDAO.getByDog(null);
    }

    @Test
    public void testGetByDog_noSuchOrder() throws DAOException {
        Customer customer = new Customer("jjonasson", "password", "Jonas", "Jonasson", null, "jonas@jonasson.com", "+49852316");
        Dog dog = new Dog("Lucas", "Shepard", 12, customer);
        manager.persist(customer);
        manager.persist(dog);

        manager.persist(testingOrder);

        List<Order> retrievedDogs = orderDAO.getByDog(dog);

        Assert.assertNotNull(retrievedDogs);
        Assert.assertTrue(retrievedDogs.isEmpty());
    }

    @Test
    public void testGetByDog_dogValid() throws DAOException {
        Customer customer = new Customer("jjonasson", "password", "Jonas", "Jonasson", null, "jonas@jonasson.com", "+49852316");
        Dog dog = new Dog("Lucas", "Shepard", 12, customer);
        customer.addDog(dog);
        manager.persist(customer);
        manager.persist(dog);

        testingOrder.setDog(dog);
        manager.persist(testingOrder);

        List<Order> retrievedOrders = orderDAO.getByDog(dog);

        Assert.assertNotNull(retrievedOrders);
        Assert.assertFalse(retrievedOrders.isEmpty());

        Order order = retrievedOrders.get(0);
        assertDeepEquals(testingOrder, order);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByEmployee_employeeNull() throws DAOException {
        orderDAO.getByEmployee(null);
    }

    @Test
    public void testGetByEmployee_noSuchOrder() throws DAOException {
        Employee employee = new Employee("testingEmpl", "passwords", "Testing", "Employee", null, "testing@mail.com", "+42563985412", BigDecimal.valueOf(65000));
        manager.persist(employee);

        manager.persist(testingOrder);

        List<Order> retrievedOrders = orderDAO.getByEmployee(employee);

        Assert.assertNotNull(retrievedOrders);
        Assert.assertTrue(retrievedOrders.isEmpty());
    }

    @Test
    public void testGetByEmployee_employeeValid() throws DAOException {
        Employee employee = new Employee("testingEmpl", "passwords", "Testing", "Employee", null, "testing@mail.com", "+42563985412", BigDecimal.valueOf(65000));
        manager.persist(employee);

        testingOrder.setEmployee(employee);

        manager.persist(testingOrder);

        List<Order> retrievedOrders = orderDAO.getByEmployee(employee);

        Assert.assertNotNull(retrievedOrders);
        Assert.assertFalse(retrievedOrders.isEmpty());

        Order order = retrievedOrders.get(0);
        assertDeepEquals(testingOrder, order);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAllOrdersInTimeRange_dateFromNull() throws DAOException {
        orderDAO.getAllOrdersInTimeRange(null, LocalDateTime.now());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAllOrdersInTimeRange_dateToNull() throws DAOException {
        orderDAO.getAllOrdersInTimeRange(LocalDateTime.now(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAllOrdersInTimeRange_dateToLowerThanDateFrom() throws DAOException {
        orderDAO.getAllOrdersInTimeRange(
                LocalDateTime.of(2016, 11, 25, 12, 0, 25),
                LocalDateTime.of(2016, 10, 23, 9, 0, 12));
    }

    @Test
    public void testGetAllOrdersInTimeRange_noSuchOrders() throws DAOException {
        testingOrder.setTime(LocalDateTime.of(2016, 9, 14, 12, 0, 0));
        manager.persist(testingOrder);

        List<Order> retrievedOrders = orderDAO.getAllOrdersInTimeRange(
                LocalDateTime.of(2016, 10, 25, 12, 0, 25),
                LocalDateTime.of(2016, 11, 23, 9, 0, 12));

        Assert.assertNotNull(retrievedOrders);
        Assert.assertTrue(retrievedOrders.isEmpty());
    }

    @Test
    public void testGetAllOrdersInTimeRange_rangeValid() throws DAOException {
        testingOrder.setTime(LocalDateTime.of(2016, 11, 14, 12, 0, 0));
        manager.persist(testingOrder);

        List<Order> retrievedOrders = orderDAO.getAllOrdersInTimeRange(
                LocalDateTime.of(2016, 10, 25, 12, 0, 25),
                LocalDateTime.of(2016, 11, 23, 9, 0, 12));

        Assert.assertNotNull(retrievedOrders);
        Assert.assertFalse(retrievedOrders.isEmpty());
        Assert.assertEquals(1, retrievedOrders.size());

        Order order = retrievedOrders.get(0);
        assertDeepEquals(testingOrder, order);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_orderNull() throws DAOException {
        orderDAO.update(null);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_timeNull() throws DAOException {
        manager.persist(testingOrder);

        testingOrder.setTime(null);

        orderDAO.update(testingOrder);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_dogNull() throws DAOException {
        manager.persist(testingOrder);

        testingOrder.setDog(null);

        orderDAO.update(testingOrder);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_serviceNull() throws DAOException {
        manager.persist(testingOrder);

        testingOrder.setService(null);

        orderDAO.update(testingOrder);
    }

    @Test
    public void testUpdate_employeeNull() throws DAOException {
        manager.persist(testingOrder);

        testingOrder.setEmployee(null);

        orderDAO.update(testingOrder);

        Order updatedOrder = manager.find(Order.class, testingOrder.getId());

        Assert.assertNotNull(updatedOrder);
        assertDeepEquals(testingOrder, updatedOrder);
    }

    @Test
    public void testUpdate_orderValid() throws DAOException {
        Employee employee = new Employee("testingEmpl", "passwords", "Testing", "Employee", null, "testing@mail.com", "+42563985412", BigDecimal.valueOf(65000));
        Customer customer = new Customer("userCustomer", "pass123456", "Jerry", "Tester", null, "mymail@jerry.com", "+652-58963 21 0");
        Service service = new Service("Testing dog service", 40, BigDecimal.valueOf(450));
        Dog dog = new Dog("Doggy", "Ordinary", 3, customer);
        customer.addDog(dog);

        manager.persist(employee);
        manager.persist(customer);
        manager.persist(service);
        manager.persist(testingOrder);

        testingOrder.setTime(LocalDateTime.of(2016, 11, 15, 14, 0, 0));
        testingOrder.setDog(dog);
        testingOrder.setService(service);
        testingOrder.setEmployee(employee);

        orderDAO.update(testingOrder);

        Order updatedOrder = manager.find(Order.class, testingOrder.getId());

        Assert.assertNotNull(updatedOrder);
        assertDeepEquals(testingOrder, updatedOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_orderNull() throws DAOException {
        orderDAO.delete(null);
    }

    @Test(expected = DAOException.class)
    public void testDelete_orderDoesNotExist() throws DAOException {
        manager.persist(testingOrder);
        manager.remove(testingOrder);

        orderDAO.delete(testingOrder);
    }

    @Test
    public void testDelete_correctOrderDeleted() throws DAOException {
        Employee employee = new Employee("testingEmpl", "passwords", "Testing", "Employee", null, "testing@mail.com", "+42563985412", BigDecimal.valueOf(65000));
        Customer customer = new Customer("userCustomer", "pass123456", "Jerry", "Tester", null, "mymail@jerry.com", "+652-58963 21 0");
        Service service = new Service("Testing dog service", 40, BigDecimal.valueOf(450));
        Dog dog = new Dog("Doggy", "Ordinary", 3, customer);
        customer.addDog(dog);

        Order anotherOrder = new Order(LocalDateTime.of(2015, 10, 26, 13, 9, 27), dog, service);
        anotherOrder.setEmployee(employee);

        manager.persist(employee);
        manager.persist(customer);
        manager.persist(service);
        manager.persist(anotherOrder);
        manager.persist(testingOrder);

        orderDAO.delete(anotherOrder);

        List<Order> remainingOrders = manager.createQuery("SELECT ord FROM Order ord", Order.class)
                                             .getResultList();

        Assert.assertThat(remainingOrders, hasItem(testingOrder));
        Assert.assertThat(remainingOrders, not(hasItem(anotherOrder)));
    }


    private void assertDeepEquals(Order expected, Order actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getTime(), actual.getTime());
        Assert.assertEquals(expected.getDog(), actual.getDog());
        Assert.assertEquals(expected.getService(), actual.getService());
        Assert.assertEquals(expected.getEmployee(), actual.getEmployee());
        Assert.assertEquals(expected.getStatus(), actual.getStatus());
    }
}