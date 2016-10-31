package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Service;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
public class ServiceDAOImpl implements ServiceDAO {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void create(Service service) {
        if (service == null){
            throw new IllegalArgumentException("service is null");
        } else {
            manager.persist(service);
        }
    }

    @Override
    public Service getById(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("id is incorrect. Must be >= 0");
        } else {
            return manager.find(Service.class, id);
        }
    }

    @Override
    public List<Service> getAll() {
        TypedQuery<Service> query = manager.createQuery("SELECT s FROM Service s", Service.class);
        return query.getResultList();

    }

    @Override
    public void update(Service service) {
        if (service == null){
            throw new IllegalArgumentException("service is null");
        } else {
            manager.persist(service);
        }
    }

    @Override
    public void delete(Service service) {
        if (service == null){
            throw new IllegalArgumentException("service is null");
        } else {
            manager.remove(service);
        }
    }
}
