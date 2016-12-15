package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.EmployeeDAO;
import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.*;

/**
 * Tests for correct contract implementation defined by {@link EmployeeService} interface
 *
 * @author Dominik Gmiterko
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:api-config.xml"})
public class EmployeeServiceTest {

    @Mock
    private EmployeeDAO employeeDAO;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee testingEmployee;
    private Address address;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        address = new Address("Testing Avenue", 25, "Testero", 2356, "Testing Republic");
        testingEmployee = new Employee("testing", "password", "John", "Tester", address,
                "testing.employee@mail.com", "755468236", new BigDecimal("4200"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_employeeNull() throws Exception {

        doThrow(new IllegalArgumentException()).when(employeeDAO).create(null);

        employeeService.create(null);
    }

    @Test
    public void testCreate_employeeValid() throws Exception {
        employeeService.create(testingEmployee);

        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeDAO, times(1)).create(employeeCaptor.capture());
        assertDeepEquals(testingEmployee, employeeCaptor.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_idInvalid() throws Exception {

        when(employeeDAO.getById(-1)).thenThrow(new IllegalArgumentException());

        employeeService.getById(-1);
    }

    @Test
    public void testGetById_employeeDoesNotExists() throws Exception {

        when(employeeDAO.getById(100)).thenReturn(null);

        Employee retrievedEmployee = employeeService.getById(100);

        Assert.assertNull(retrievedEmployee);
    }

    @Test
    public void testGetById_employeeValid() throws Exception {

        when(employeeDAO.getById(1)).thenReturn(testingEmployee);

        // retrieve employee
        Employee retrievedEmployee = employeeService.getById(1);

        Assert.assertNotNull(retrievedEmployee);
        assertDeepEquals(testingEmployee, retrievedEmployee);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByUsername_usernameNull() throws Exception {

        when(employeeDAO.getByUsername(null)).thenThrow(new IllegalArgumentException());

        employeeService.getByUsername(null);
    }

    @Test
    public void testGetByUsername_employeeDoesNotExist() throws Exception {
        String username = "testing";

        when(employeeDAO.getByUsername(username)).thenReturn(null);

        Employee employee = employeeService.getByUsername(username);

        Assert.assertNull(employee);
    }

    @Test
    public void testGetByUsername_employeeExists() throws Exception {
        // create employee in database
        when(employeeDAO.getByUsername(testingEmployee.getUsername())).thenReturn(testingEmployee);

        Employee employee = employeeService.getByUsername(testingEmployee.getUsername());

        Assert.assertNotNull(employee);
        assertDeepEquals(testingEmployee, employee);
    }

    @Test
    public void testGetAll_noEmployees() throws Exception {

        when(employeeDAO.getAll()).thenReturn(new ArrayList<Employee>());

        List<Employee> allEmployees = employeeService.getAll();

        Assert.assertNotNull(allEmployees);
        Assert.assertTrue(allEmployees.isEmpty());
    }

    @Test
    public void testGetAll_employeesExist() throws Exception {
        ArrayList<Employee> mockedEmployees = new ArrayList<Employee>();
        mockedEmployees.add(testingEmployee);
        Employee employee = new Employee("testmaster", "masterpassword", "Albert", "Master",
                new Address("Botanicka", 68, "Brno", 62000, "Czech Republic"),
                "testmaster@mail.com", "+421910325478", new BigDecimal("2100"));
        mockedEmployees.add(employee);

        when(employeeDAO.getAll()).thenReturn(mockedEmployees);

        // get all employees from database
        List<Employee> allEmployees = employeeService.getAll();

        Assert.assertNotNull(allEmployees);
        Assert.assertFalse(allEmployees.isEmpty());
        Assert.assertEquals(2, allEmployees.size());
        Assert.assertThat(allEmployees, hasItems(testingEmployee, employee));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_employeeNull() throws Exception {

        doThrow(new IllegalArgumentException()).when(employeeDAO).update(null);

        employeeService.update(null);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_employeeDoesNotExist() throws Exception {

        doThrow(new DAOException()).when(employeeDAO).update(testingEmployee);

        employeeService.update(testingEmployee);
    }

    @Test
    public void testUpdate_employeeValid() throws Exception {

        // update username
        testingEmployee.setUsername("new_username");
        testingEmployee.setPassword("new_pass_12345");
        testingEmployee.setAddress(new Address("New testing St.", 25, "New Testing City", 96523, "Zanzibar"));
        testingEmployee.setEmail("newmail@mail.com");
        testingEmployee.setPhone("9963215698");

        employeeService.update(testingEmployee);

        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeDAO, times(1)).update(employeeCaptor.capture());
        assertDeepEquals(testingEmployee, employeeCaptor.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_employeeNull() throws Exception {

        doThrow(new IllegalArgumentException()).when(employeeDAO).delete(null);

        employeeService.delete(null);
    }

    @Test(expected = DAOException.class)
    public void testDelete_employeeDoesNotExist() throws Exception {

        doThrow(new DAOException()).when(employeeDAO).delete(testingEmployee);

        employeeService.delete(testingEmployee);
    }

    @Test
    public void testDelete_correctEmployeeDeleted() throws Exception {

        employeeService.delete(testingEmployee);

        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeDAO, times(1)).delete(employeeCaptor.capture());
        assertDeepEquals(testingEmployee, employeeCaptor.getValue());
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
        Assert.assertEquals(expected.getSalary(), actual.getSalary());
    }
}