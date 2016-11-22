package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.facade.DogFacade;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Martin Vrábel
 */
public class DogFacadeImpl implements DogFacade {

    @Override
    public void create(DogDTO dog) {

    }

    @Override
    public DogDTO getById(long id) {
        return null;
    }

    @Override
    public List<DogDTO> getAll() {
        return null;
    }

    @Override
    public List<DogDTO> getByCustomer(Customer customer) {
        return null;
    }

    @Override
    public void update(DogDTO dog) {

    }

    @Override
    public void delete(DogDTO dog) {

    }
}
