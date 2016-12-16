package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.ServiceDTO;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.exceptions.ServiceException;
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

    private final ServiceService serviceService;
    private final BeanMappingService beanMappingService;

    @Inject
    public ServiceFacadeImpl(ServiceService serviceService, BeanMappingService beanMappingService) {
        this.serviceService = serviceService;
        this.beanMappingService = beanMappingService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(ServiceDTO serviceDTO) throws FacadeException {
        if (serviceDTO == null)
            throw new IllegalArgumentException("Service is null");

        try {
            Service service = beanMappingService.mapTo(serviceDTO, Service.class);

            serviceService.create(service);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceDTO getById(long id) throws FacadeException {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be less than 0");

        try {
            return beanMappingService.mapTo(serviceService.getById(id), ServiceDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ServiceDTO> getAll() throws FacadeException {
        try {
            return beanMappingService.mapTo(serviceService.getAll(), ServiceDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(ServiceDTO serviceDTO) throws FacadeException {
        if (serviceDTO == null)
            throw new IllegalArgumentException("Service is null");

        try {
            Service service = beanMappingService.mapTo(serviceDTO, Service.class);

            serviceService.update(service);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(ServiceDTO serviceDTO) throws FacadeException {
        if (serviceDTO == null)
            throw new IllegalArgumentException("Service is null");

        try {
            Service service = beanMappingService.mapTo(serviceDTO, Service.class);

            serviceService.delete(service);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }
}
