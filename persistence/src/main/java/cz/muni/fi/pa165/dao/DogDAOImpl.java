package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
@Repository
public class DogDAOImpl implements DogDAO {

    @PersistenceUnit
    private EntityManagerFactory managerFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Dog dog) throws DAOException {
        if (dog == null)
            throw new IllegalArgumentException("Dog cannot be null");
        if (dog.getId() > 0)
            throw new DAOException("Dog ID is already set");

        if (dog.getCustomer() == null)
            throw new DAOException("Dog has to have owner");
        if (dog.getCustomer().getId() == 0)
            throw new DAOException("Dog owner is not saved");

        EntityManager manager = null;

        try {
            manager = managerFactory.createEntityManager();
            manager.getTransaction().begin();

            // save Customer to database
            manager.persist(dog);

            manager.getTransaction().commit();
        } catch (EntityExistsException eeEx){
            rollbackTransaction(manager);

            throw new DAOException("Provided Customer already exists in database");
        } catch (PersistenceException | IllegalStateException ex) {
            rollbackTransaction(manager);

            throw new DAOException(ex);
        } finally {
            closeManager(manager);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dog getById(long id){
        if (id < 0)
            throw new IllegalArgumentException("id is incorrect. Must be >= 0");

        EntityManager manager = null;

        try {
            manager = managerFactory.createEntityManager();

            return manager.find(Dog.class, id);
        } finally {
            closeManager(manager);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Dog> getAll() throws DAOException {
        EntityManager manager = managerFactory.createEntityManager();

        return manager.createQuery("SELECT d FROM Dog d", Dog.class)
                      .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Dog dog) throws DAOException {
        if (dog == null)
            throw new IllegalArgumentException("dog is null");

        EntityManager manager = null;

        try {
            manager = managerFactory.createEntityManager();

            manager.getTransaction().begin();

            // update Dog in database
            manager.merge(dog);

            manager.getTransaction().commit();
        } catch (PersistenceException pEx) {
            rollbackTransaction(manager);

            throw new DAOException(pEx);
        } finally {
            closeManager(manager);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Dog dog) throws DAOException {
        if (dog == null)
            throw new IllegalArgumentException("Dog cannot be null");

        EntityManager manager = null;

        try {
            manager = managerFactory.createEntityManager();

            manager.getTransaction().begin();

            Dog existingDog = manager.find(Dog.class, dog.getId());
            if (existingDog == null)
                throw new DAOException("Dog with does not exist in database");

            // delete Dog in database
            manager.remove(existingDog);

            manager.getTransaction().commit();
        } catch (PersistenceException pEx) {
            rollbackTransaction(manager);

            throw new DAOException(pEx);
        } finally {
            closeManager(manager);
        }
    }

    private void rollbackTransaction(EntityManager manager) {
        if (manager == null)
            return;

        // rollback if needed
        if (manager.getTransaction().isActive())
            manager.getTransaction().rollback();
    }

    private void closeManager(EntityManager manager) {
        if (manager == null)
            return;

        if (manager.isOpen())
            manager.close();
    }
}
