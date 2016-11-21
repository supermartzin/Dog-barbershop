package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
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
    public void update(Dog dog) {

    }

    @Override
    public void delete(Dog dog) {

    }
}
