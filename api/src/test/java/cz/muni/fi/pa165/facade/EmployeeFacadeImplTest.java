package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.PersistenceUnit;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;

/**
 * Tests for {@link EmployeeDAOImplTest} class
 *
 * @author Denis Richtarik
 * @version 25.10.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-configs/main-config.xml"})
public class EmployeeDAOImplTest {

    @PersistenceUnit(name = "testing")
    private EntityManagerFactory factory;

    @Inject
    private EmployeeDAO employeeDAO;

    private Address address;
    private Employee testEmployee;


    @Before
    public void setUp() throws Exception {
        address = new Address("Street", 32, "Townsville", 91101, "Island");
        testEmployee = new Employee("testerUsr", "testpass", "Den", "Rich", address, "some@one.you", "555444333", new BigDecimal("2000"));
    }

    @After
    public void tearDown() throws Exception {
        EntityManager manager = createManager();

        // delete all entries created by test
        manager.createNativeQuery("DELETE FROM Employee c").executeUpdate();
        manager.createNativeQuery("DELETE FROM Address a").executeUpdate();

        closeManager(manager);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNull() throws Exception {
        employeeDAO.create(null);
        List<Employee> allEmployees = employeeDAO.getAll();
        assertNull(allEmployees);
    }

    @Test
    public void getById() throws Exception {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(testEmployee);
        entityManager.getTransaction().commit();
        entityManager.close();

        assertDeepEquals(testEmployee, employeeDAO.getById(testEmployee.getId()));
    }

    @Test
    public void testGetByUsername_employeeDoesNotExist() throws Exception {
        String username = "testing";

        Employee employee = employeeDAO.getByUsername(username);

        Assert.assertNull(employee);
    }

    @Test
    public void testCreate_employeeValid() throws Exception, DAOException {
        employeeDAO.create(testEmployee);

        Assert.assertTrue(testEmployee.getId() >= 0);

        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Employee retrievedEmployee = manager.find(Employee.class, testEmployee.getId());

        Assert.assertNotNull(retrievedEmployee);
        assertEquals(testEmployee.getId(), retrievedEmployee.getId());

        manager.getTransaction().commit();
        manager.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_employeeNull() throws Exception {
        employeeDAO.create(null);
    }

    @Test(expected = DAOException.class)
    public void testCreate_usernameNotSet() throws Exception {
        Employee employee = new Employee();
        employee.setPassword("password");
        employee.setFirstName("John");
        employee.setLastName("Tester");
        employee.setAddress(address);
        employee.setEmail("tester@mail.com");
        employee.setPhone("+4209658412");

        employeeDAO.create(employee);
    }

    @Test(expected = DAOException.class)
    public void testCreate_passwordNotSet() throws Exception {
        Employee employee = new Employee();
        employee.setUsername("testing");
        employee.setFirstName("John");
        employee.setLastName("Tester");
        employee.setAddress(address);
        employee.setEmail("tester@mail.com");
        employee.setPhone("+4209658412");

        employeeDAO.create(employee);
    }

    @Test(expected = DAOException.class)
    public void testCreate_passwordInvalid() throws Exception {
        String password = "pass";
        testEmployee.setPassword(password);
        employeeDAO.create(testEmployee);
    }

    @Test
    public void testCreate_addressNull() throws Exception {
        testEmployee.setAddress(null);

        employeeDAO.create(testEmployee);

        // get persisted customer from database
        EntityManager manager = createManager();
        Employee retrievedEmployee = manager.find(Employee.class, testEmployee.getId());

        Assert.assertNotNull(retrievedEmployee);
        Assert.assertNull(retrievedEmployee.getAddress());

        closeManager(manager);
    }

    @Test(expected = DAOException.class)
    public void testCreate_emailInvalid() throws Exception {
        String email = "this is invalid @mail";
        testEmployee.setEmail(email);
        employeeDAO.create(testEmployee);
    }

    @Test(expected = DAOException.class)
    public void testCreate_idAlreadySet() throws Exception {
        testEmployee.setId(15);
        employeeDAO.create(testEmployee);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_idInvalid() throws Exception {
        long id = -1;

        employeeDAO.getById(id);
    }

    @Test
    public void testGetById_employeeDoesNotExists() throws Exception {
        long id = 100;

        Employee retrievedEmployee = employeeDAO.getById(id);

        Assert.assertNull(retrievedEmployee);
    }

    @Test
    public void testGetById_employeeValid() throws Exception {
        // create employee in database
        persistEmployees(testEmployee);

        // retrieve employee
        Employee retrievedEmployee = employeeDAO.getById(testEmployee.getId());

        Assert.assertNotNull(retrievedEmployee);
        assertDeepEquals(testEmployee, retrievedEmployee);
    }


    @Test
    public void testGetAll_noEmployees() throws Exception {
        List<Employee> allEmployees = employeeDAO.getAll();

        Assert.assertNotNull(allEmployees);
        Assert.assertTrue(allEmployees.isEmpty());
    }

    @Test
    public void testGetAll_employeesExist() throws Exception {
        Employee employee = new Employee("testmaster", "masterpassword", "Albert", "Master",
                new Address("Botanicka", 68, "Brno", 62000, "Czech Republic"),
                "testmaster@mail.com", "+421910325478", new BigDecimal("4200"));

        // add some employees into database
        persistEmployees(testEmployee, employee);

        List<Employee> allEmployees = employeeDAO.getAll();

        Assert.assertNotNull(allEmployees);
        Assert.assertFalse(allEmployees.isEmpty());
        Assert.assertEquals(2, allEmployees.size());
        Assert.assertThat(allEmployees, hasItems(testEmployee, employee));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_employeeNull() throws Exception {
        employeeDAO.update(null);
    }

    @Test
    public void testUpdate_employeeDoesNotExist() throws Exception {
        employeeDAO.update(testEmployee);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_usernameInvalid() throws Exception {
        // create employee in database
        persistEmployees(testEmployee);

        // update username
        testEmployee.setUsername(null);

        employeeDAO.update(testEmployee);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_passwordNull() throws Exception {
        // create employee in database
        persistEmployees(testEmployee);

        // update username
        testEmployee.setPassword(null);

        employeeDAO.update(testEmployee);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_passwordInvalid() throws Exception {
        // create employee in database
        persistEmployees(testEmployee);

        // update username
        testEmployee.setPassword("asdfg");

        employeeDAO.update(testEmployee);
    }

    @Test
    public void testUpdate_addressNull() throws Exception {
        // create employee in database
        persistEmployees(testEmployee);

        // update username
        testEmployee.setAddress(null);

        employeeDAO.update(testEmployee);

        // get updated employee from database
        EntityManager manager = createManager();
        Employee updatedEmployee = manager.find(Employee.class, testEmployee.getId());
        closeManager(manager);

        Assert.assertNull(updatedEmployee.getAddress());
    }

    @Test(expected = DAOException.class)
    public void testUpdate_emailInvalid() throws Exception {
        // create employee in database
        persistEmployees(testEmployee);

        // update username
        testEmployee.setEmail("dddddddd@");

        employeeDAO.update(testEmployee);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_phoneInvalid() throws Exception {
        // create employee in database
        persistEmployees(testEmployee);

        // update username
        testEmployee.setPhone("-456 +985-8965aaa");

        employeeDAO.update(testEmployee);
    }

    @Test
    public void testUpdate_employeeValid() throws Exception {
        // create employee in database
        persistEmployees(testEmployee);

        // update username
        testEmployee.setUsername("new_username");
        testEmployee.setPassword("new_pass_12345");
        //testEmployee.setAddress(new Address("New testing St.", 25, "New Testing City", 96523, "Zanzibar"));
        testEmployee.setEmail("newmail@mail.com");
        testEmployee.setPhone("999888777");
        testEmployee.setSalary(new BigDecimal("400"));
        employeeDAO.update(testEmployee);

        // get updated employee from database
        EntityManager manager = createManager();
        Employee updatedEmployee = manager.find(Employee.class, testEmployee.getId());
        closeManager(manager);

        Assert.assertNotNull(updatedEmployee);
        assertDeepEquals(testEmployee, updatedEmployee);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_employeeNull() throws Exception {
        employeeDAO.delete(null);
    }

    @Test(expected = DAOException.class)
    public void testDelete_employeeDoesNotExist() throws Exception {
        employeeDAO.delete(testEmployee);
    }

    @Test
    public void testDelete_rightEmployeeDeleted() throws Exception {
        // create a new employee in database
        Employee employee2 = new Employee("secondUSR", "secondPSW", "npc", "character", new Address("Krenova", 8, "Brno", 62000, "Czech Republic"), "vendor@mail.com", "+421910325478", new BigDecimal("4200"));

        persistEmployees(testEmployee, employee2);

        employeeDAO.delete(testEmployee);

        // get all employees to see if the right one has been deleted
        EntityManager manager = createManager();
        List<Employee> remainingEmployees = manager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();

        Assert.assertThat(remainingEmployees, hasItem(employee2));
        Assert.assertThat(remainingEmployees, not(hasItem(testEmployee)));
    }

    private EntityManager createManager() {
        // create new manager
        EntityManager manager = factory.createEntityManager();

        // start transaction
        manager.getTransaction().begin();

        return manager;
    }

    private void closeManager(EntityManager manager) {
        if (manager == null)
            return;

        // commit current transaction
        manager.getTransaction().commit();

        // close the manager
        manager.close();
    }

    private void persistEmployees(Employee... employees){
        EntityManager manager = createManager();

        for (Employee employee : employees) {
            if (employee.getAddress() != null) {
                Address address = employee.getAddress();

                if (address.getId() > 0) {
                    manager.merge(address);
                } else {
                    manager.persist(address);
                }
            }
            manager.persist(employee);
        }
        closeManager(manager);
    }

    private void assertDeepEquals(Employee expected, Employee actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());
        Assert.assertEquals(expected.getAddress(), actual.getAddress());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getPhone(), actual.getPhone());
        Assert.assertEquals(0, expected.getSalary().compareTo(actual.getSalary()));
    }
}