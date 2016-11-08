package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

/**
 * Tests for {@link CustomerDAOImpl} class
 *
 * @author Dominik Gmiterko
 * @version
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-configs/main-config.xml"})
public class DogDAOImplTest {

    @PersistenceUnit(name = "testing")
    private EntityManagerFactory factory;

    @Inject
    private DogDAO dogDAO;

    @Inject
    private CustomerDAO customerDAO;

    private Customer customer;

    @Before
    public void setUp() throws Exception, DAOException {
        customer = new Customer("testing", "password", "John", "Tester", new Address("Testing Avenue", 25, "Testero", 2356, "Testing Republic"), "tester@mail.com", "+4209658412");
        customerDAO.create(customer);
        customer = customerDAO.getById(customer.getId());
    }

    @After
    public void tearDown() throws Exception {
        EntityManager manager = createManager();

        // delete all entries created by test
        manager.createNativeQuery("DELETE FROM Dog d").executeUpdate();
        manager.createNativeQuery("DELETE FROM Customer c").executeUpdate();
        manager.createNativeQuery("DELETE FROM Address a").executeUpdate();

        closeManager(manager);
    }

    @Test
    public void testCreate() throws Exception {
        Dog dog = new Dog("Linda", "testingBreed", 2);
        dog.setCustomer(customer);

        dogDAO.create(dog);

        // testing
        EntityManager manager = createManager();

        Dog testDog = manager.find(Dog.class, dog.getId());

        Assert.assertNotNull(testDog);
        Assert.assertEquals("Linda", testDog.getName());
        Assert.assertEquals("testingBreed", testDog.getBreed());
        Assert.assertEquals(2, testDog.getAge());
        Assert.assertEquals(customer, testDog.getCustomer());

        manager.getTransaction().commit();
        manager.close();
    }

    @Test(expected = DAOException.class)
    public void testCreate_customerNotPersisted() throws Exception {
        Dog dog = new Dog("Linda", "testingBreed", 2);
        dog.setCustomer(new Customer());

        dogDAO.create(dog);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_dogNull() throws DAOException {
        dogDAO.create(null);
    }

    @Test
    public void testGetById() throws Exception {
        Dog dog = new Dog("Linda", "testingBreed", 2);
        dog.setCustomer(customer);

        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        manager.persist(dog);

        manager.getTransaction().commit();
        manager.close();


        Dog foundDog = dogDAO.getById(dog.getId());

        Assert.assertEquals(dog, foundDog);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGet_illegalId() {
        dogDAO.getById(-1);
    }

    @Test
    public void testGetAll() throws Exception {
        Dog dog1 = new Dog("Linda", "testingBreed", 2);
        dog1.setCustomer(customer);
        Dog dog2 = new Dog("Miau", "cat", 3);
        dog2.setCustomer(customer);

        List<Dog> originalDogs = new ArrayList<>();
        originalDogs.add(dog1);
        originalDogs.add(dog2);

        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        manager.persist(dog1);
        manager.persist(dog2);

        manager.getTransaction().commit();
        manager.close();

        List<Dog> foundDogs = dogDAO.getAll();

        Assert.assertEquals(originalDogs, foundDogs);
    }

    @Test
    public void testUpdate() throws Exception {
        Dog dog1 = new Dog("Linda", "testingBreed", 2);
        dog1.setCustomer(customer);
        Dog dog2 = new Dog("Miau", "cat", 3);
        dog2.setCustomer(customer);

        persistDogs(dog1, dog2);

        dog1.setName("Chan-ge'd Name");
        dog1.setAge(7);

        dogDAO.update(dog1);

        // testing
        EntityManager manager = createManager();

        Dog testDog1 = manager.find(Dog.class, dog1.getId());
        Dog testDog2 = manager.find(Dog.class, dog2.getId());

        Assert.assertEquals("Chan-ge'd Name", testDog1.getName());
        Assert.assertEquals("testingBreed", testDog1.getBreed());
        Assert.assertEquals(7, testDog1.getAge());

        Assert.assertEquals("Miau", testDog2.getName());
        Assert.assertEquals(3, testDog2.getAge());

        closeManager(manager);
    }

    @Test(expected = DAOException.class)
    public void testDelete_dogDoesNotExist() throws Exception {
        Dog dog = new Dog("Linda", "testingBreed", 2);
        dogDAO.delete(dog);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_dogNull() {
        dogDAO.update(null);
    }

    @Test
    public void testDelete() throws Exception {
        Dog dog = new Dog("Linda", "testingBreed", 2);
        dog.setCustomer(customer);
        Dog dog2 = new Dog("Miau", "cat", 3);
        dog2.setCustomer(customer);

        persistDogs(dog, dog2);

        dogDAO.delete(dog);

        // get all customers to see if the right one has been deleted
        EntityManager manager = createManager();
        List<Dog> remainingDogs = manager.createQuery("SELECT c FROM Dog c", Dog.class)
                .getResultList();
        Assert.assertThat(remainingDogs, hasItems(dog2));
        Assert.assertThat(remainingDogs, not(hasItem(dog)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_nullDog() throws DAOException {
        dogDAO.delete(null);
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

    private void persistDogs(Dog... dogs){
        EntityManager manager = createManager();

        for (Dog dog : dogs) {
            manager.persist(dog);
        }

        closeManager(manager);
    }

    private void assertDeepEquals(Dog expected, Dog actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getAge(), actual.getAge());
        Assert.assertEquals(expected.getBreed(), actual.getBreed());
        Assert.assertEquals(expected.getCustomer(), actual.getCustomer());
    }
}