package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Service;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 * @version
 */
@Repository
public class ServiceDAOImpl implements ServiceDAO {

    @PersistenceUnit
    private EntityManagerFactory managerFactory;

    /**
     * Creates new entry in database from provided {@link Service} object
     *
     * @param service {@link Service} object to save
     */
    @Override
    public void create(Service service) {
        if (service == null)
            throw new IllegalArgumentException("service is null");

        EntityManager manager = managerFactory.createEntityManager();

        manager.persist(service);
    }

    /**
     * Retrieves a {@see Service} object with provided <b>ID</b> from database
     *
     * @param id <b>ID</b> number of {@link Service} to retrieve
     * @return found {@link Service} object or {@link null} if <b>ID</b> not found
     */
    @Override
    public Service getById(long id) {
        if (id < 0)
            throw new IllegalArgumentException("id is incorrect. Must be >= 0");

        EntityManager manager = managerFactory.createEntityManager();

        return manager.find(Service.class, id);
    }

    /**
     * Retrieves all {@link Service} objects from database
     *
     * @return list of all {@link Service} objects from database
     */
    @Override
    public List<Service> getAll() {
        EntityManager manager = managerFactory.createEntityManager();

        return manager.createQuery("SELECT s FROM Service s", Service.class)
                      .getResultList();
    }

    /**
     * Updates attributes of an existing {@link Service} object in database
     *
     * @param service {@link Service} object with updated attributes
     */
    @Override
    public void update(Service service) {
        if (service == null)
            throw new IllegalArgumentException("service is null");

        EntityManager manager = managerFactory.createEntityManager();

        manager.persist(service);
    }

    /**
     * Deletes an existing {@link Service} entry from database
     *
     * @param service {@link Service} object to delete from database
     */
    @Override
    public void delete(Service service) {
        if (service == null)
            throw new IllegalArgumentException("service is null");

        EntityManager manager = managerFactory.createEntityManager();

        manager.remove(service);
    }
}
