package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 * @version
 */
public class ServiceDAOImpl implements ServiceDAO {

    private final EntityManagerFactory factory;

    public ServiceDAOImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(Service service) {
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
