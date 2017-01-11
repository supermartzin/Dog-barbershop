package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;

/**
 * Tests for correct contract implementation defined by {@link EmployeeDAO} interface
 *
 * @author Denis Richtarik
 * @version 25.10.2016
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:persistence-test-config.xml")
public class EmployeeDAOTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private EmployeeDAO employeeDAO;

    private Employee testEmployee;

    @Before
    public void setUp() throws DAOException {
        testEmployee = new Employee("testerUsr", "testpass", "Den", "Rich",
                new Address("Street", 32, "Townsville", 91101, "Island"),
                "some@one.you", "555444333", new BigDecimal("2000"));
    }

    @Test
    public void testGetByUsername_employeeDoesNotExist() throws DAOException {
        String username = "testing";

        Employee employee = employeeDAO.getByUsername(username);

        Assert.assertNull(employee);
    }

    @Test
    public void testCreate_employeeValid() throws DAOException {
        employeeDAO.create(testEmployee);

        Assert.assertTrue(testEmployee.getId() >= 0);

        Employee retrievedEmployee = manager.find(Employee.class, testEmployee.getId());

        Assert.assertNotNull(retrievedEmployee);
        assertEquals(testEmployee.getId(), retrievedEmployee.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_employeeNull() throws DAOException {
        employeeDAO.create(null);
    }

    @Test(expected = DAOException.class)
    public void testCreate_usernameNotSet() throws DAOException {
        testEmployee.setUsername(null);

        employeeDAO.create(testEmployee);
    }

    @Test(expected = DAOException.class)
    public void testCreate_passwordNotSet() throws DAOException {
        testEmployee.setPassword(null);

        employeeDAO.create(testEmployee);
    }

    @Test(expected = DAOException.class)
    public void testCreate_passwordInvalid() throws DAOException {
        String password = "pass";
        testEmployee.setPassword(password);
        employeeDAO.create(testEmployee);
    }

    @Test
    public void testCreate_addressNull() throws DAOException {
        testEmployee.setAddress(null);

        employeeDAO.create(testEmployee);

        // get persisted customer from database
        Employee retrievedEmployee = manager.find(Employee.class, testEmployee.getId());

        Assert.assertNotNull(retrievedEmployee);
        Assert.assertNull(retrievedEmployee.getAddress());
    }

    @Test(expected = DAOException.class)
    public void testCreate_emailInvalid() throws DAOException {
        String email = "this is invalid @mail";
        testEmployee.setEmail(email);
        employeeDAO.create(testEmployee);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_idInvalid() throws DAOException {
        long id = -1;

        employeeDAO.getById(id);
    }

    @Test
    public void testGetById_employeeDoesNotExists() throws DAOException {
        long id = 100;

        Employee retrievedEmployee = employeeDAO.getById(id);

        Assert.assertNull(retrievedEmployee);
    }

    @Test
    public void testGetById_employeeValid() throws DAOException {
        // create employee in database
        manager.persist(testEmployee);

        // retrieve employee
        Employee retrievedEmployee = employeeDAO.getById(testEmployee.getId());

        Assert.assertNotNull(retrievedEmployee);
        assertDeepEquals(testEmployee, retrievedEmployee);
    }

    @Test
    public void testGetAll_noEmployees() throws DAOException {
        List<Employee> allEmployees = employeeDAO.getAll();

        Assert.assertNotNull(allEmployees);
        Assert.assertTrue(allEmployees.isEmpty());
    }

    @Test
    public void testGetAll_employeesExist() throws DAOException {
        Employee employee = new Employee("testmaster", "masterpassword", "Albert", "Master",
                new Address("Botanicka", 68, "Brno", 62000, "Czech Republic"),
                "testmaster@mail.com", "+421910325478", new BigDecimal("4200"));

        // add some employees into database
        manager.persist(testEmployee);
        manager.persist(employee);

        List<Employee> allEmployees = employeeDAO.getAll();

        Assert.assertNotNull(allEmployees);
        Assert.assertFalse(allEmployees.isEmpty());
        Assert.assertEquals(2, allEmployees.size());
        Assert.assertThat(allEmployees, hasItems(testEmployee, employee));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_employeeNull() throws DAOException {
        employeeDAO.update(null);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_employeeDoesNotExist() throws DAOException {
        employeeDAO.update(testEmployee);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_usernameInvalid() throws DAOException {
        // create employee in database
        manager.persist(testEmployee);

        // update username
        testEmployee.setUsername(null);

        employeeDAO.update(testEmployee);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_passwordNull() throws DAOException {
        // create employee in database
        manager.persist(testEmployee);

        // update username
        testEmployee.setPassword(null);

        employeeDAO.update(testEmployee);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_passwordInvalid() throws DAOException {
        // create employee in database
        manager.persist(testEmployee);

        // update username
        testEmployee.setPassword("asdfg");

        employeeDAO.update(testEmployee);
    }

    @Test
    public void testUpdate_addressNull() throws DAOException {
        // create employee in database
        manager.persist(testEmployee);

        // update username
        testEmployee.setAddress(null);

        employeeDAO.update(testEmployee);

        // get updated employee from database
        Employee updatedEmployee = manager.find(Employee.class, testEmployee.getId());

        Assert.assertNull(updatedEmployee.getAddress());
    }

    @Test(expected = DAOException.class)
    public void testUpdate_emailInvalid() throws DAOException {
        // create employee in database
        manager.persist(testEmployee);

        // update username
        testEmployee.setEmail("dddddddd@");

        employeeDAO.update(testEmployee);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_phoneInvalid() throws DAOException {
        // create employee in database
        manager.persist(testEmployee);

        // update username
        testEmployee.setPhone("-456 +985-8965aaa");

        employeeDAO.update(testEmployee);
    }

    @Test
    public void testUpdate_employeeValid() throws DAOException {
        // create employee in database
        manager.persist(testEmployee);

        // update username
        testEmployee.setUsername("new_username");
        testEmployee.setPassword("new_pass_12345");
        testEmployee.setAddress(new Address("New testing St.", 25, "New Testing City", 96523, "Zanzibar"));
        testEmployee.setEmail("newmail@mail.com");
        testEmployee.setPhone("999888777");
        testEmployee.setSalary(new BigDecimal("400"));

        employeeDAO.update(testEmployee);

        // get updated employee from database
        Employee updatedEmployee = manager.find(Employee.class, testEmployee.getId());

        Assert.assertNotNull(updatedEmployee);
        assertDeepEquals(testEmployee, updatedEmployee);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_employeeNull() throws DAOException {
        employeeDAO.delete(null);
    }

    @Test(expected = DAOException.class)
    public void testDelete_employeeDoesNotExist() throws DAOException {
        employeeDAO.delete(testEmployee);
    }

    @Test
    public void testDelete_correctEmployeeDeleted() throws DAOException {
        // create a new employee in database
        Employee employee2 = new Employee("secondUSR", "secondPSW", "npc", "character", new Address("Krenova", 8, "Brno", 62000, "Czech Republic"), "vendor@mail.com", "+421910325478", new BigDecimal("4200"));

        manager.persist(testEmployee);
        manager.persist(employee2);

        employeeDAO.delete(testEmployee);

        // get all employees to see if the right one has been deleted
        List<Employee> remainingEmployees = manager.createQuery("SELECT e FROM Employee e", Employee.class)
                                                   .getResultList();

        Assert.assertThat(remainingEmployees, hasItem(employee2));
        Assert.assertThat(remainingEmployees, not(hasItem(testEmployee)));
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