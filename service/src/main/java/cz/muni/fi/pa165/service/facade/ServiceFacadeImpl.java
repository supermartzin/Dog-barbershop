package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.ServiceDTO;
import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.facade.ServiceFacade;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
@Transactional
public class ServiceFacadeImpl implements ServiceFacade {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void create(ServiceDTO service) {
        Service serviceEntity = beanMappingService.mapTo(service, Service.class);
        serviceService.create(serviceEntity);
    }

    @Override
    public ServiceDTO getById(long id) {
        return beanMappingService.mapTo(serviceService.getById(id), ServiceDTO.class);
    }

    @Override
    public List<ServiceDTO> getAll() {
        return beanMappingService.mapTo(serviceService.getAll(), ServiceDTO.class);
    }

    @Override
    public void update(ServiceDTO service) {
        Service serviceEntity = beanMappingService.mapTo(service, Service.class);
        serviceService.update(serviceEntity);
    }

    @Override
    public void delete(ServiceDTO service) {
        Service serviceEntity = beanMappingService.mapTo(service, Service.class);
        serviceService.delete(serviceEntity);
    }
}
