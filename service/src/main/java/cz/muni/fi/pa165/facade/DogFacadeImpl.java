package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.DogService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Martin Vrábel
 */
@Transactional
public class DogFacadeImpl implements DogFacade {

    private final DogService dogService;
    private final BeanMappingService beanMappingService;

    public DogFacadeImpl(DogService dogService, BeanMappingService beanMappingService) {
        this.dogService = dogService;
        this.beanMappingService = beanMappingService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(DogDTO dogDTO) throws FacadeException {
        if (dogDTO == null)
            throw new IllegalArgumentException("DogDTO is null");

        try {
            Dog dog = beanMappingService.mapTo(dogDTO, Dog.class);
            dogService.create(dog);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DogDTO getById(long id) throws FacadeException {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be less than 0");

        try {
            return beanMappingService.mapTo(dogService.getById(id), DogDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DogDTO> getAll() throws FacadeException {
        try {
            return beanMappingService.mapTo(dogService.getAll(), DogDTO.class);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(DogDTO dogDTO) throws FacadeException {
        if (dogDTO == null)
            throw new IllegalArgumentException("DogDTO is null");

        try {
            Dog dog = beanMappingService.mapTo(dogDTO, Dog.class);
            dogService.update(dog);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(DogDTO dogDTO) throws FacadeException {
        if (dogDTO == null)
            throw new IllegalArgumentException("DogDTO is null");

        try {
            Dog dog = beanMappingService.mapTo(dogDTO, Dog.class);
            dogService.delete(dog);
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }
}
