package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 * @version
 */
public class DogDAOImpl implements DogDAO {

    private final EntityManagerFactory factory;

    public DogDAOImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(Dog dog) {

    }

    @Override
    public Dog getById(long id) {
        return null;
    }

    @Override
    public List<Dog> getAll() {
        return null;
    }

    @Override
    public void update(Dog dog) {

    }

    @Override
    public void delete(Dog dog) {

    }
}
