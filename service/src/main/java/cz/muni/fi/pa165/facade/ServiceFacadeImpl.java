package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.ServiceDTO;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.ServiceService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
@Transactional
@org.springframework.stereotype.Service
public class ServiceFacadeImpl implements ServiceFacade {

    @Inject
    private ServiceService serviceService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public void create(ServiceDTO service) throws DAOException {
        if (service == null)
            throw new IllegalArgumentException("Service is null");

        Service serviceEntity = beanMappingService.mapTo(service, Service.class);
        serviceService.create(serviceEntity);
    }

    @Override
    public ServiceDTO getById(long id) {
        return beanMappingService.mapTo(serviceService.getById(id), ServiceDTO.class);
    }

    @Override
    public List<ServiceDTO> getAll() throws DAOException {
        return beanMappingService.mapTo(serviceService.getAll(), ServiceDTO.class);
    }

    @Override
    public void update(ServiceDTO service) throws DAOException {
        Service serviceEntity = beanMappingService.mapTo(service, Service.class);
        serviceService.update(serviceEntity);
    }

    @Override
    public void delete(ServiceDTO service) throws DAOException {
        Service serviceEntity = beanMappingService.mapTo(service, Service.class);
        serviceService.delete(serviceEntity);
    }
}
