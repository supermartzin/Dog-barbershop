package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Service;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private DogDAO dogDAO;

    @Autowired
    private CustomerDAO customerDAO;

    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = new Customer("testing", "password", "John", "Tester", new Address("Testing Avenue", 25, "Testero", 2356, "Testing Republic"), "tester@mail.com", "+4209658412");
        customerDAO.create(customer);
    }

    @After
    public void tearDown() throws Exception {
        customerDAO.delete(customer);
    }

    @Test
    public void testCreate() throws Exception {
        Dog dog = new Dog("Linda", "testingBreed", 2, customer);

        dogDAO.create(dog);

        // testing
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Dog testDog = manager.find(Dog.class, customer.getId());

        Assert.assertEquals("Linda", testDog.getName());
        Assert.assertEquals("testingBreed", testDog.getBreed());
        Assert.assertEquals(2, testDog.getAge());
        Assert.assertEquals(customer, testDog.getCustomer());

        manager.getTransaction().commit();
        manager.close();
    }

    @Test
    public void getById() throws Exception {
        Dog dog = new Dog("Linda", "testingBreed", 2, customer);

        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        manager.persist(dog);

        manager.getTransaction().commit();
        manager.close();


        Dog foundDog = dogDAO.getById(dog.getId());

        Assert.assertEquals(dog, foundDog);
    }

    @Test
    public void getAll() throws Exception {
        Dog dog1 = new Dog("Linda", "testingBreed", 2, customer);
        Dog dog2 = new Dog("Miau", "cat", 3, customer);

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
    public void update() throws Exception {
        Dog dog1 = new Dog("Linda", "testingBreed", 2, customer);
        Dog dog2 = new Dog("Miau", "cat", 3, customer);

        dogDAO.create(dog1);
        dogDAO.create(dog2);

        dog1.setName("Chan-ge'd Name");
        dog1.setAge(7);

        dogDAO.update(dog1);

        // testing
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Dog testDog1 = manager.find(Dog.class, dog1.getId());
        Dog testDog2 = manager.find(Dog.class, dog2.getId());

        Assert.assertEquals("Chan-ge'd Name", testDog1.getName());
        Assert.assertEquals(7, testDog1.getAge());

        Assert.assertEquals("Miau", testDog2.getName());
        Assert.assertEquals(3, testDog2.getAge());

        manager.getTransaction().commit();
        manager.close();
    }

    @Test
    public void delete() throws Exception {
        Dog dog = new Dog("Linda", "testingBreed", 2, customer);

        dogDAO.create(dog);

        dogDAO.delete(dog);

        // testing
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Assert.assertNotEquals(0, dog.getId());

        Service testService = manager.find(Service.class, dog.getId());

        Assert.assertNull(testService);

        manager.getTransaction().commit();
        manager.close();
    }

}