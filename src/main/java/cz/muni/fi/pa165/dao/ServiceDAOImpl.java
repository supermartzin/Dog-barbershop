package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Martin Vrábel
 * @version 24.10.2016 20:37
 */
public class ServiceDAOImpl implements ServiceDAO {

    private final EntityManagerFactory factory;

    public ServiceDAOImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(Service service) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        // save to db
        manager.persist(service);

        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public Service getById(long id) {
        return null;
    }

    @Override
    public List<Service> getAll() {
        return null;
    }

    @Override
    public void update(Service service) {

    }

    @Override
    public void delete(Service service) {

    }
}
