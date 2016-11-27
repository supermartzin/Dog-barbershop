package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
@Service
public class DogServiceImpl implements DogService {

    @Override
    public void create(Dog dog) {

    }

    @Override
    public Dog getById(long id) {
        return null;
    }

    @Override
    public List<Dog> getAll() {
        return null;
    }

    @Override
    public List<Dog> getByCustomer(Customer customer) {
        return null;
    }

    @Override
    public void update(Dog dog) {

    }

    @Override
    public void delete(Dog dog) {

    }
}
