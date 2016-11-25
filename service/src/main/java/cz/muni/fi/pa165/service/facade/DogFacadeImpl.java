package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.facade.DogFacade;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Martin Vrábel
 */
public class DogFacadeImpl implements DogFacade {


    @Autowired
    private DogService dogService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void create(DogDTO dog) throws DAOException {
        Dog dogEntity = beanMappingService.mapTo(dog, Dog.class);
        dogService.create(dogEntity);
    }

    @Override
    public DogDTO getById(long id) {
        return beanMappingService.mapTo(dogService.getById(id), DogDTO.class);
    }

    @Override
    public List<DogDTO> getAll() throws DAOException {
        return beanMappingService.mapTo(dogService.getAll(), DogDTO.class);
    }

    @Override
    public List<DogDTO> getByCustomer(Customer customer) {
        return beanMappingService.mapTo(dogService.getByCustomer(beanMappingService.mapTo(customer, Customer.class)), DogDTO.class);
    }

    @Override
    public void update(DogDTO dog) throws DAOException {
        Dog dogEntity = beanMappingService.mapTo(dog, Dog.class);
        dogService.update(dogEntity);
    }

    @Override
    public void delete(DogDTO dog) throws DAOException {
        Dog dogEntity = beanMappingService.mapTo(dog, Dog.class);
        dogService.delete(dogEntity);
    }
}
