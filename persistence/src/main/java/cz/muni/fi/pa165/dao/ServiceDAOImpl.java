package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.exceptions.ValidationException;
import cz.muni.fi.pa165.validation.EntityValidator;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
@Repository
public class ServiceDAOImpl implements ServiceDAO {

    @PersistenceContext
    private EntityManager manager;

    private final EntityValidator validator;

    @Inject
    public ServiceDAOImpl(EntityValidator entityValidator) {
        if (entityValidator == null)
            throw new IllegalArgumentException("Entity Validator is null");

        this.validator = entityValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Service service) throws DAOException {
        if (service == null)
            throw new IllegalArgumentException("Service cannot be null");
        if (service.getId() > 0)
            throw new DAOException("Service ID is already set");

        try {
            // validate
            validator.validate(service);

            // save to database
            manager.persist(service);
        } catch (ValidationException | PersistenceException | IllegalStateException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Service getById(long id) {
        if (id < 0)
            throw new IllegalArgumentException("ID must be positive integer number");

        return manager.find(Service.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Service> getAll() throws DAOException {
        return manager.createQuery("SELECT service FROM Service service", Service.class)
                      .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Service service) throws DAOException {
        if (service == null)
            throw new IllegalArgumentException("Service cannot be null");

        try {
            // validate
            validator.validate(service);

            Service existingService = manager.find(Service.class, service.getId());
            if (existingService == null)
                throw new DAOException("Service does not exist in database");

            // update Service in database
            manager.merge(service);
        } catch (ValidationException | PersistenceException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Service service) throws DAOException {
        if (service == null)
            throw new IllegalArgumentException("Service cannot be null");

        try {
            Service existingService = manager.find(Service.class, service.getId());
            if (existingService == null)
                throw new DAOException("Service does not exist in database");

            // delete Service in database
            manager.remove(service);
        } catch (PersistenceException pEx) {
            throw new DAOException(pEx);
        }
    }
}
