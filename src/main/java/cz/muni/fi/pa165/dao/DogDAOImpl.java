package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Dog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 * @version
 */
@Repository
public class DogDAOImpl implements DogDAO {

    @PersistenceContext
    private EntityManager manager;

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
