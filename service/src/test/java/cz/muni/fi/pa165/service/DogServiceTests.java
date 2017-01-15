package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.DogDAO;
import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.exceptions.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for correct contract implementation defined by {@link DogService} interface
 *
 * @author Martin Vrábel
 * @version 22.11.2016 13:34
 */
@RunWith(MockitoJUnitRunner.class)
public class DogServiceTests {

    @Mock
    private DogDAO dogDAO;

    private DogService dogService;

    private Dog testingDog;
    private Customer testingCustomer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        dogService = new DogServiceImpl(dogDAO);

        Address address = new Address("Kensington Avenue", 256, "Birmingham", 887152, "Great Britain");
        testingCustomer = new Customer("testing_cust", "passwd1234*-", "George", "Michael", address, "george@email.com", "+40 785 2369");
        testingDog = new Dog("Daisy", "Yorkshire terrier", 6, testingCustomer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_dogNull() throws ServiceException {
        dogService.create(null);
    }

    @Test(expected = ServiceException.class)
    public void testCreate_dogDoesNotHaveCustomer() throws DAOException, ServiceException {
        doAnswer(invocationOnMock -> {
            Dog dog = invocationOnMock.getArgumentAt(0, Dog.class);

            if (dog.getCustomer() == null)
                throw new DAOException("Dog has no customer");

            return null;
        }).when(dogDAO).create(any(Dog.class));

        Dog dog = new Dog("Skippy", "Chivaca", 4, null);

        dogService.create(dog);
    }

    @Test(expected = ServiceException.class)
    public void testCreate_dogDaoError() throws DAOException, ServiceException {
        doThrow(DAOException.class).when(dogDAO).create(any());

        dogService.create(testingDog);
    }

    @Test
    public void testCreate_dogValid() throws DAOException, ServiceException {
        dogService.create(testingDog);

        verify(dogDAO, times(1)).create(testingDog);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_idInvalid() throws ServiceException {
        dogService.getById(-2);
    }

    @Test
    public void testGetById_dogDoesNotExist() throws ServiceException {
        when(dogDAO.getById(15)).thenReturn(null);

        Dog dog = dogService.getById(15);

        assertNull(dog);

        verify(dogDAO, times(1)).getById(15);
    }

    @Test
    public void testGetById_dogValid() throws ServiceException {
        when(dogDAO.getById(anyLong())).thenReturn(testingDog);

        Dog retrievedDog = dogService.getById(1);

        assertNotNull(retrievedDog);
        assertDeepEquals(testingDog, retrievedDog);

        verify(dogDAO, times(1)).getById(1);
    }

    @Test
    public void testGetAll_noDogs() throws DAOException, ServiceException {
        when(dogDAO.getAll()).thenReturn(Collections.emptyList());

        List<Dog> dogs = dogService.getAll();

        assertNotNull(dogs);
        assertTrue(dogs.isEmpty());

        verify(dogDAO, times(1)).getAll();
    }

    @Test(expected = ServiceException.class)
    public void testGetAll_dogDaoError() throws DAOException, ServiceException {
        doThrow(DAOException.class).when(dogDAO).getAll();

         dogService.getAll();
    }

    @Test
    public void testGetAll_dogsValid() throws DAOException, ServiceException {
        Dog dog1 = new Dog("Debbie", "Slavic pitbull", 3, testingCustomer);
        Dog dog2 = new Dog("Azaria", "German puddle", 1, testingCustomer);

        when(dogDAO.getAll()).thenReturn(Arrays.asList(testingDog, dog1, dog2));

        List<Dog> retrievedDogs = dogService.getAll();

        assertNotNull(retrievedDogs);
        assertFalse(retrievedDogs.isEmpty());
        assertEquals(3, retrievedDogs.size());
        assertThat(retrievedDogs, hasItems(testingDog, dog1, dog2));

        verify(dogDAO, times(1)).getAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_dogNull() throws ServiceException {
        dogService.update(null);
    }

    @Test(expected = ServiceException.class)
    public void testUpdate_dogDoesNotExist() throws DAOException, ServiceException {
        doThrow(DAOException.class).when(dogDAO).update(testingDog);

        dogService.update(testingDog);
    }

    @Test(expected = ServiceException.class)
    public void testUpdate_dogDaoError() throws DAOException, ServiceException {
        doThrow(DAOException.class).when(dogDAO).update(any());

        dogService.update(testingDog);
    }

    @Test
    public void testUpdate_dogValid() throws DAOException, ServiceException {
        testingDog.setAge(5);
        testingDog.setCustomer(new Customer());

        dogService.update(testingDog);

        verify(dogDAO, times(1)).update(testingDog);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_dogNull() throws ServiceException {
        dogService.delete(null);
    }

    @Test(expected = ServiceException.class)
    public void testDelete_dogDoesNotExist() throws DAOException, ServiceException {
        doThrow(DAOException.class).when(dogDAO).delete(testingDog);

        dogService.delete(testingDog);
    }

    @Test(expected = ServiceException.class)
    public void testDelete_dogDaoError() throws DAOException, ServiceException {
        doThrow(DAOException.class).when(dogDAO).delete(any(Dog.class));

        dogService.delete(testingDog);
    }

    @Test
    public void testDelete_dogValid() throws DAOException, ServiceException {
        dogService.delete(testingDog);

        verify(dogDAO, times(1)).delete(testingDog);
    }


    private void assertDeepEquals(Dog expected, Dog actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getBreed(), actual.getBreed());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getCustomer(), actual.getCustomer());
    }
}