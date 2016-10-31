package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Dog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
        if (dog == null){
            throw new IllegalArgumentException("dog is null");
        } else {
            manager.persist(dog);
        }
    }

    @Override
    public Dog getById(long id){
        if (id < 0) {
            throw new IllegalArgumentException("id is incorrect. Must be >= 0");
        } else {
            return manager.find(Dog.class, id);
        }
    }

    @Override
    public List<Dog> getAll() {
        TypedQuery<Dog> query = manager.createQuery("SELECT d FROM Dog d", Dog.class);
        return query.getResultList();
    }

    @Override
    public void update(Dog dog) {
        if (dog == null){
            throw new IllegalArgumentException("dog is null");
        } else {
            manager.persist(dog);
        }
    }

    @Override
    public void delete(Dog dog) {
        if (dog == null){
            throw new IllegalArgumentException("dog is null");
        } else {
            manager.remove(dog);
        }
    }
}
