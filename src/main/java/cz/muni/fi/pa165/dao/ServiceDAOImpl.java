package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Service;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
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
