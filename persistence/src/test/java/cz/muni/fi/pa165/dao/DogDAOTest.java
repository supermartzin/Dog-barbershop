package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
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
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

/**
 * Tests for correct contract implementation defined by {@link DogDAO} interface
 *
 * @author Dominik Gmiterko
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:persistence-config.xml"})
public class DogDAOTest {

    @PersistenceContext
    private EntityManager manager;

    @Inject
    private DogDAO dogDAO;

    private Dog testingDog;
    private Customer customer;

    @Before
    public void setUp() throws DAOException {
        customer = new Customer("testing", "password", "John", "Tester", new Address("Testing Avenue", 25, "Testero", 2356, "Testing Republic"), "tester@mail.com", "+4209658412");
        testingDog = new Dog("Linda", "testingBreed", 2, customer);

        // save Customer
        manager.persist(customer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_dogNull() throws DAOException {
        dogDAO.create(null);
    }

    @Test(expected = DAOException.class)
    public void testCreate_nameInvalid() throws DAOException {
        testingDog.setName(null);

        dogDAO.create(testingDog);
    }

    @Test(expected = DAOException.class)
    public void testCreate_breedInvalid() throws DAOException {
        testingDog.setBreed(null);

        dogDAO.create(testingDog);
    }

    @Test(expected = DAOException.class)
    public void testCreate_ageInvalid() throws DAOException {
        testingDog.setAge(-5);

        dogDAO.create(testingDog);
    }

    @Test(expected = DAOException.class)
    public void testCreate_customerInvalid() throws DAOException {
        testingDog.setCustomer(null);

        dogDAO.create(testingDog);
    }

    @Test(expected = DAOException.class)
    public void testCreate_customerNotSaved() throws DAOException {
        testingDog.setCustomer(new Customer());

        dogDAO.create(testingDog);
    }

    @Test
    public void testCreate_dogValid() throws DAOException {
        // save Dog to database
        dogDAO.create(testingDog);

        Assert.assertTrue(testingDog.getId() > 0);

        Dog retrievedDog = manager.find(Dog.class, testingDog.getId());

        Assert.assertNotNull(retrievedDog);
        assertDeepEquals(testingDog, retrievedDog);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetById_idInvalid() throws DAOException {
        dogDAO.getById(-1);
    }

    @Test
    public void testGetById_dogDoesNotExist() throws DAOException {
        Dog foundDog = dogDAO.getById(100);

        Assert.assertNull(foundDog);
    }

    @Test
    public void testGetById_dogValid() throws DAOException {
        manager.persist(testingDog);

        Dog foundDog = dogDAO.getById(testingDog.getId());

        Assert.assertNotNull(foundDog);
        assertDeepEquals(testingDog, foundDog);
    }

    @Test
    public void testGetAll_noDogs() throws DAOException {
        List<Dog> foundDogs = dogDAO.getAll();

        Assert.assertNotNull(foundDogs);
        Assert.assertTrue(foundDogs.isEmpty());
    }

    @Test
    public void testGetAll_dogsExist() throws DAOException {
        Dog dog = new Dog("Miau", "cat", 3, customer);

        manager.persist(testingDog);
        manager.persist(dog);

        List<Dog> foundDogs = dogDAO.getAll();

        Assert.assertNotNull(foundDogs);
        Assert.assertFalse(foundDogs.isEmpty());
        Assert.assertEquals(2, foundDogs.size());
        Assert.assertThat(foundDogs, hasItems(testingDog, dog));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_dogNull() throws DAOException {
        dogDAO.update(null);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_nameInvalid() throws DAOException {
        testingDog.setName(null);

        dogDAO.update(testingDog);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_breedInvalid() throws DAOException {
        testingDog.setBreed(null);

        dogDAO.update(testingDog);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_ageInvalid() throws DAOException {
        testingDog.setAge(-5);

        dogDAO.update(testingDog);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_customerInvalid() throws DAOException {
        testingDog.setCustomer(null);

        dogDAO.update(testingDog);
    }

    @Test(expected = DAOException.class)
    public void testUpdate_customerNotSaved() throws DAOException {
        testingDog.setCustomer(new Customer());

        dogDAO.update(testingDog);
    }

    @Test
    public void testUpdate_dogValid() throws DAOException {
        Dog dog = new Dog("Miau", "cat", 3, customer);

        manager.persist(testingDog);
        manager.persist(dog);

        dog.setName("Chan-ge'd Name");
        dog.setAge(7);

        dogDAO.update(dog);

        // Assert
        Dog retrievedDog1 = manager.find(Dog.class, testingDog.getId());
        Dog retrievedDog2 = manager.find(Dog.class, dog.getId());

        Assert.assertNotNull(retrievedDog1);
        Assert.assertNotNull(retrievedDog2);
        assertDeepEquals(retrievedDog1, testingDog);
        assertDeepEquals(retrievedDog2, dog);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_dogNull() throws DAOException {
        dogDAO.delete(null);
    }

    @Test(expected = DAOException.class)
    public void testDelete_dogDoesNotExist() throws DAOException {
        dogDAO.delete(testingDog);
    }

    @Test
    public void testDelete_correctDogDeleted() throws DAOException {
        Dog miau = new Dog("Miau", "cat", 3, customer);
        Dog jerry = new Dog("Jerry", "New Hampshire", 8, customer);

        manager.persist(testingDog);
        manager.persist(miau);
        manager.persist(jerry);

        dogDAO.delete(miau);

        // get all dogs to see if the right one has been deleted
        List<Dog> remainingDogs = manager.createQuery("SELECT dog FROM Dog dog", Dog.class)
                                         .getResultList();

        Assert.assertThat(remainingDogs, hasItems(testingDog, jerry));
        Assert.assertThat(remainingDogs, not(hasItem(miau)));
    }


    private void assertDeepEquals(Dog expected, Dog actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getAge(), actual.getAge());
        Assert.assertEquals(expected.getBreed(), actual.getBreed());
        Assert.assertEquals(expected.getCustomer(), actual.getCustomer());
    }
}