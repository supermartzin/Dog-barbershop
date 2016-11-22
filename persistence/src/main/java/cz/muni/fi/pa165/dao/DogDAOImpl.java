package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.exceptions.ValidationException;
import cz.muni.fi.pa165.validation.EntityValidator;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
@Repository
public class DogDAOImpl implements DogDAO {

    @PersistenceContext
    private EntityManager manager;

    private final EntityValidator validator;

    @Inject
    public DogDAOImpl(EntityValidator entityValidator) {
        if (entityValidator == null)
            throw new IllegalArgumentException("Entity Validator is null");

        this.validator = entityValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Dog dog) throws DAOException {
        if (dog == null)
            throw new IllegalArgumentException("Dog cannot be null");
        if (dog.getId() > 0)
            throw new DAOException("Dog ID is already set");

        try {
            // validate
            validator.validate(dog);

            // save to database
            manager.persist(dog);
        } catch (ValidationException | PersistenceException ex) {
            throw new DAOException(ex);
        } catch (IllegalStateException isEx) {
            throw new DAOException("Cannot persist Dog object, its Customer is not persisted", isEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dog getById(long id){
        if (id < 0)
            throw new IllegalArgumentException("id is incorrect. Must be >= 0");

        return manager.find(Dog.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Dog> getAll() throws DAOException {
        return manager.createQuery("SELECT dog FROM Dog dog", Dog.class)
                      .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Dog dog) throws DAOException {
        if (dog == null)
            throw new IllegalArgumentException("dog is null");

        try {
            // validate
            validator.validate(dog);

            // update Dog in database
            manager.merge(dog);
        } catch (ValidationException | PersistenceException pEx) {
            throw new DAOException(pEx);
        } catch (IllegalStateException isEx) {
            throw new DAOException("Cannot update Dog object, its Customer is not persisted", isEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Dog dog) throws DAOException {
        if (dog == null)
            throw new IllegalArgumentException("Dog cannot be null");

        try {
            Dog existingDog = manager.find(Dog.class, dog.getId());
            if (existingDog == null)
                throw new DAOException("Dog with does not exist in database");

            // delete Dog in database
            manager.remove(existingDog);
        } catch (PersistenceException pEx) {
            throw new DAOException(pEx);
        }
    }
}
