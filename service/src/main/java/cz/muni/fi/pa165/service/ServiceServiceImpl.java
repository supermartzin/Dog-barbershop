package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ServiceDAO;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;

import javax.inject.Inject;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Denis Richtarik
 */
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceDAO serviceDAO;

    @Inject
    public ServiceServiceImpl(ServiceDAO serviceDAO){
        this.serviceDAO=serviceDAO;
    }

    @Override
    public void create(Service service) throws DAOException {
        serviceDAO.create(service);
    }

    @Override
    public Service getById(long id) {
        return serviceDAO.getById(id);
    }

    @Override
    public List<Service> getAll() throws DAOException {
        return serviceDAO.getAll();
    }

    @Override
    public void update(Service service) throws DAOException {
        serviceDAO.update(service);
    }

    @Override
    public void delete(Service service) throws DAOException {
        serviceDAO.delete(service);
    }
}
