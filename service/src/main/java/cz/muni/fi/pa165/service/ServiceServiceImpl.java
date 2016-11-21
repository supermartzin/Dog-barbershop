package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Service;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
public class ServiceServiceImpl implements ServiceService {

    @Override
    public void create(Service service) {

    }

    @Override
    public Service getById(long id) {
        return null;
    }

    @Override
    public List<Service> getAll() {
        return null;
    }

    @Override
    public void update(Service service) {

    }

    @Override
    public void delete(Service service) {

    }
}
