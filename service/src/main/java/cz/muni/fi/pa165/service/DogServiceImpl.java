package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CustomerDAO;
import cz.muni.fi.pa165.dao.DogDAO;
import cz.muni.fi.pa165.entities.Customer;
import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.exceptions.DAOException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * {@inheritDoc}
 *
 * @author Denis Richtarik
 */
@Service
public class DogServiceImpl implements DogService {

    private final DogDAO dogDAO;
    private final CustomerDAO customerDAO;

    @Inject
    public DogServiceImpl(DogDAO dogDAO, CustomerDAO customerDAO){
        this.dogDAO = dogDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public void create(Dog dog) throws DAOException {
        this.dogDAO.create(dog);
    }

    @Override
    public Dog getById(long id) {
        return this.dogDAO.getById(id);
    }

    @Override
    public List<Dog> getAll() throws DAOException {
        return dogDAO.getAll();
    }

    @Override
    public Set<Dog> getByCustomer(Customer customer) {
        Customer myCustomer = customerDAO.getById(customer.getId());
        return myCustomer.getDogs();
    }

    @Override
    public void update(Dog dog) throws DAOException {
        dogDAO.update(dog);
    }

    @Override
    public void delete(Dog dog) throws DAOException {
        dogDAO.delete(dog);
    }
}
