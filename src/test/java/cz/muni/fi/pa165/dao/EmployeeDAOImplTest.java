package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Employee;
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


    @Before
    public void setUp() throws Exception {
        address = new Address("strt", 32, "Townsville", 91101, "yea");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createNull() throws Exception {
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
    public void getAll() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

}