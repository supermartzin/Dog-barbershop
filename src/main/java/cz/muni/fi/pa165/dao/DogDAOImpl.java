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

    /**
     * Creates new entry in database from provided {@link Dog} object
     *
     * @param dog {@link Dog} object to save
     */
    @Override
    public void create(Dog dog) {
        if (dog == null){
            throw new IllegalArgumentException("dog is null");
        } else {
            manager.persist(dog);
        }
    }

    /**
     * Retrieves a {@see Dog} object with provided <b>ID</b> from database
     *
     * @param id <b>ID</b> number of {@link Dog} to retrieve
     * @return found {@link Dog} object or {@link null} if <b>ID</b> not found
     */
    @Override
    public Dog getById(long id){
        if (id < 0) {
            throw new IllegalArgumentException("id is incorrect. Must be >= 0");
        } else {
            return manager.find(Dog.class, id);
        }
    }

    /**
     * Retrieves all {@link Dog} objects from database
     *
     * @return list of all {@link Dog} objects from database
     */
    @Override
    public List<Dog> getAll() {
        TypedQuery<Dog> query = manager.createQuery("SELECT d FROM Dog d", Dog.class);
        return query.getResultList();
    }
    
    /**
     * Updates attributes of an existing {@link Dog} object in database
     *
     * @param dog {@link Dog} object with updated attributes
     */
    @Override
    public void update(Dog dog) {
        if (dog == null){
            throw new IllegalArgumentException("dog is null");
        } else {
            manager.persist(dog);
        }
    }

    /**
     * Deletes an existing {@link Dog} entry from database
     *
     * @param dog {@link Dog} object to delete from database
     */
    @Override
    public void delete(Dog dog) {
        if (dog == null){
            throw new IllegalArgumentException("dog is null");
        } else {
            manager.remove(dog);
        }
    }
}
