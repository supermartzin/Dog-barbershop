package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ServiceDAO;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.exceptions.ServiceException;

import javax.inject.Inject;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Denis Richtarik
 */
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Inject
    private ServiceDAO serviceDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Service service) throws ServiceException {
        if (service == null)
            throw new IllegalArgumentException("Service is null");

        try {
            serviceDAO.create(service);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Service getById(long id) throws ServiceException {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be less than 0");

        return serviceDAO.getById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Service> getAll() throws ServiceException {
        try {
            return serviceDAO.getAll();
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Service service) throws ServiceException {
        try {
            serviceDAO.update(service);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Service service) throws ServiceException {
        try {
            serviceDAO.delete(service);
        } catch (DAOException daoEx) {
            throw new ServiceException(daoEx);
        }
    }
}
