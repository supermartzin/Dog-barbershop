package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AddressDTO;
import cz.muni.fi.pa165.dto.EmployeeDTO;
import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for correct contract implementation defined by {@link EmployeeFacade} interface
 *
 * @author Denis Richtarik
 * @version 22.11.2016 14:10
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeeFacadeTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private BeanMappingService mappingService;

    private EmployeeFacade employeeFacade;
    private EmployeeDTO employeeDTO;
    private Employee testingEmployee;
    private AddressDTO addressDTO;
    private Address address;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        employeeFacade = new EmployeeFacadeImpl(employeeService, mappingService);

        addressDTO = new AddressDTO("Testing Avenue", 25, "Testero", 2356, "Testing Republic");
        employeeDTO = new EmployeeDTO("testing", "password", "John", "Tester", addressDTO,
                "testing.employee@mail.com", "755468236", new BigDecimal("4200"));
        address = new Address("Testing Avenue", 25, "Testero", 2356, "Testing Republic");
        testingEmployee = new Employee("testing", "password", "John", "Tester", address,
                "testing.employee@mail.com", "755468236", new BigDecimal("4200"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_employeeNull() throws Exception {
        employeeFacade.create(null);
    }

    @Test
    public void testCreate_employeeValid() throws Exception {
        when(mappingService.mapTo(employeeDTO, Employee.class)).thenReturn(testingEmployee);

        employeeFacade.create(employeeDTO);

        verify(mappingService, times(1)).mapTo(employeeDTO, Employee.class);
        verify(employeeService, times(1)).create(testingEmployee);
    }

    @Test
    public void testGetById() throws Exception {
        when(mappingService.mapTo(testingEmployee, EmployeeDTO.class)).thenReturn(employeeDTO);
        when(employeeService.getById(anyLong())).thenReturn(testingEmployee);

        EmployeeDTO result = employeeFacade.getById(1);

        assertNotNull(result);
        assertEquals(employeeDTO, result);

        verify(mappingService, times(1)).mapTo(testingEmployee, EmployeeDTO.class);
    }

    @Test
    public void testGetAll() throws Exception {
        Employee testingEmployee2 = new Employee("tetete", "tititi", "tatata", "tututu", address,
                "tute@toto.com", "755468236", new BigDecimal("2313"));
        EmployeeDTO employeeDTO1 = new EmployeeDTO("tetete", "tititi", "tatata", "tututu", addressDTO,
                "tute@toto.com", "755468236", new BigDecimal("2313"));

        when(employeeService.getAll()).thenReturn(Arrays.asList(testingEmployee, testingEmployee2));
        when(mappingService.mapTo(Arrays.asList(testingEmployee, testingEmployee2), EmployeeDTO.class))
                .thenReturn(Arrays.asList(employeeDTO, employeeDTO1));

        List<EmployeeDTO> result = employeeFacade.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result, hasItems(employeeDTO, employeeDTO1));

        verify(employeeService, times(1)).getAll();
        verify(mappingService, times(1)).mapTo(anyCollectionOf(Employee.class), eq(EmployeeDTO.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByUsername_usernameNull() throws FacadeException {
        employeeFacade.getByUsername(null);
    }
    
    @Test
    public void testGetByUsername_usernameValid() throws Exception {
        when(mappingService.mapTo(testingEmployee, EmployeeDTO.class)).thenReturn(employeeDTO);
        when(employeeService.getByUsername(any())).thenReturn(testingEmployee);

        EmployeeDTO result = employeeFacade.getByUsername("testing");

        assertNotNull(result);
        assertEquals(employeeDTO, result);

        verify(mappingService, times(1)).mapTo(testingEmployee, EmployeeDTO.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_employeeNull() throws FacadeException {
        employeeFacade.update(null);
    }
    
    @Test
    public void testUpdate_employeeValid() throws Exception {
        EmployeeDTO updatedServiceDTO = new EmployeeDTO("tetete", "tititi", "tatata", "tututu", addressDTO,
                "tute@toto.com", "755468236", new BigDecimal("2313"));
        Employee updatedEmployee = new Employee("tetete", "tititi", "tatata", "tututu", address,
                "tute@toto.com", "755468236", new BigDecimal("2313"));

        when(mappingService.mapTo(updatedServiceDTO, Employee.class)).thenReturn(updatedEmployee);
        when(mappingService.mapTo(updatedEmployee, EmployeeDTO.class)).thenReturn(updatedServiceDTO);
        when(employeeService.getById(anyLong())).thenReturn(updatedEmployee);

        employeeFacade.update(updatedServiceDTO);

        EmployeeDTO updatedEmployeeDTO = employeeFacade.getById(1);

        assertNotNull(updatedEmployeeDTO);
        assertEquals(updatedServiceDTO, updatedEmployeeDTO);

        verify(mappingService, times(1)).mapTo(updatedServiceDTO, Employee.class);
        verify(mappingService, times(1)).mapTo(updatedEmployee, EmployeeDTO.class);
        verify(employeeService, times(1)).update(updatedEmployee);
        verify(employeeService, times(1)).getById(anyLong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_employeeNull() throws FacadeException {
        employeeFacade.delete(null);
    }
    
    @Test
    public void testDelete_employeeValid() throws Exception {
        when(mappingService.mapTo(employeeDTO, Employee.class)).thenReturn(testingEmployee);
        when(mappingService.mapTo(anyCollectionOf(Employee.class), eq(EmployeeDTO.class))).thenReturn(Collections.emptyList());
        when(employeeService.getAll()).thenReturn(Collections.emptyList());

        employeeFacade.delete(employeeDTO);

        List<EmployeeDTO> remainingServices = employeeFacade.getAll();

        assertNotNull(remainingServices);
        assertTrue(remainingServices.isEmpty());
        assertThat(remainingServices, not(hasItem(employeeDTO)));
    }
}