package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Address;
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
        testEmployee = new Employee("tester", "testpass", "Den", "Rich", address, "some@one", "555444333", new BigDecimal("2000"));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createNull() throws Exception, DAOException {
        employeeDAO.create(null);
    }

    @Test
    public void getById() throws Exception {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        Employee employee = new Employee();
        employee.setUsername("usr");
        employee.setPassword("pass");
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        entityManager.close();
        employeeDAO.getById(employee.getId());

        Assert.assertEquals("usr", employee.getUsername());
    }

    @Test
    public void testGetByUsername_employeeDoesNotExist() throws Exception {
        String username = "testing";

        Employee employee = employeeDAO.getByUsername(username);

        Assert.assertNull(employee);
    }

    @Test
    public void testCreate_addressNull() throws Exception, DAOException {
        Employee employee = new Employee();
        employee.setUsername("testing");
        employee.setPassword("password");
        employee.setFirstName("John");
        employee.setLastName("Tester");
        employee.setAddress(null);
        employee.setEmail("testing@mail.com");
        employee.setPhone("+4209658412");

        employeeDAO.create(employee);

    }

    @Test
    public void testCreate_customerValid() throws Exception, DAOException {
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

    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

}