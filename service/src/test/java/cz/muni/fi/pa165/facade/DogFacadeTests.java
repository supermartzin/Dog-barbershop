package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.DogService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for correct contract implementation defined by {@link DogFacade} interface
 *
 * @author Denis Richtarik
 * @version 22.11.2016 14:09
 */
public class DogFacadeTests {

    @Mock
    private DogService dogService;

    @Mock
    private BeanMappingService mappingService;

    private DogFacadeImpl dogFacade;
    private DogDTO dogDTO;
    private Dog testingDog;
    private Customer testingCustomer;
    private Address address;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dogFacade = new DogFacadeImpl(dogService, mappingService);
        dogDTO = new DogDTO("Doge", "pepe", 20);
        address = new Address("Testing Avenue", 25, "Testero", 2356, "Testing Republic");
        testingCustomer = new Customer("testing", "password", "John", "Tester", address,
                "testing.employee@mail.com", "755468236");
        testingDog = new Dog("Doge", "pepe", 20, testingCustomer);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_null() throws Exception {
        dogFacade.create(null);
    }

    @Test
    public void testCreate() throws Exception {
        when(mappingService.mapTo(dogDTO, Dog.class)).thenReturn(testingDog);

        dogFacade.create(dogDTO);

        verify(mappingService, times(1)).mapTo(dogDTO, Dog.class);
        verify(dogService, times(1)).create(testingDog);
    }

    @Test
    public void testGetById() throws Exception {
        when(mappingService.mapTo(testingDog, DogDTO.class)).thenReturn(dogDTO);
        when(dogService.getById(anyLong())).thenReturn(testingDog);

        DogDTO result = dogFacade.getById(1);

        assertNotNull(result);
        assertEquals(dogDTO, result);

        verify(mappingService, times(1)).mapTo(testingDog, DogDTO.class);
    }

    @Test
    public void testGetAll() throws Exception {
        Dog testingDog1 = new Dog("Doggo", "lele", 22, testingCustomer);
        DogDTO dogDTO1 = new DogDTO("Doggo", "lele", 22);

        when(dogService.getAll()).thenReturn(Arrays.asList(testingDog, testingDog1));
        when(mappingService.mapTo(Arrays.asList(testingDog, testingDog1), DogDTO.class))
                .thenReturn(Arrays.asList(dogDTO, dogDTO1));

        List<DogDTO> result = dogFacade.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result, hasItems(dogDTO, dogDTO1));

        verify(dogService, times(1)).getAll();
        verify(mappingService, times(1)).mapTo(anyCollectionOf(Dog.class), eq(DogDTO.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_null() throws FacadeException {
        dogFacade.update(null);
    }

    @Test
    public void testUpdate() throws Exception {
        Dog updatedDog = new Dog("Doge", "pepe", 22, testingCustomer);
        DogDTO updatedDogDTO = new DogDTO("Doge", "pepe", 22);

        when(mappingService.mapTo(updatedDogDTO, Dog.class)).thenReturn(updatedDog);
        when(mappingService.mapTo(updatedDog, DogDTO.class)).thenReturn(updatedDogDTO);
        when(dogService.getById(anyLong())).thenReturn(updatedDog);

        dogFacade.update(updatedDogDTO);

        DogDTO updatedEmployeeDTO = dogFacade.getById(1);

        assertNotNull(updatedEmployeeDTO);
        assertEquals(updatedDogDTO, updatedEmployeeDTO);

        verify(mappingService, times(1)).mapTo(updatedDogDTO, Dog.class);
        verify(mappingService, times(1)).mapTo(updatedDog, DogDTO.class);
        verify(dogService, times(1)).update(updatedDog);
        verify(dogService, times(1)).getById(anyLong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_null() throws FacadeException {
        dogFacade.delete(null);
    }

    @Test
    public void testDelete() throws Exception {
        when(mappingService.mapTo(dogDTO, Dog.class)).thenReturn(testingDog);
        when(mappingService.mapTo(anyCollectionOf(Dog.class), eq(DogDTO.class))).thenReturn(Collections.emptyList());
        when(dogService.getAll()).thenReturn(Collections.emptyList());

        dogFacade.delete(dogDTO);

        List<DogDTO> remainingServices = dogFacade.getAll();

        assertNotNull(remainingServices);
        assertTrue(remainingServices.isEmpty());
        assertThat(remainingServices, not(hasItem(dogDTO)));
    }
    
}