package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Customer;

import javax.persistence.*;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
public class CustomerDAOImpl implements CustomerDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Creates new entry in database from provided {@link Customer} object
     *
     * @param customer {@link Customer} object to save
     */
    @Override
    public void create(Customer customer) {
        em.persist(customer);
    }

    /**
     * Retrieves a {@see Customer} object with provided <b>ID</b> from database
     *
     * @param id <b>ID</b> number of {@link Customer} to retrieve
     * @return found {@link Customer} object or {@link null} if <b>ID</b> not found
     */
    @Override
    public Customer getById(long id) {
        return em.find(Customer.class, id);
    }

    /**
     * Retrieves a {@see Customer} object with provided <b>ID</b> from database
     *
     * @param username Username of {@link Customer} to retrieve
     * @return found {@link Customer} object or {@link null} if <b>ID</b> not found
     * @throws IllegalArgumentException for {@link null} or empty username
     */
    @Override
    public Customer getByUsername(String username) {
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException("Cannot search for null username");

        try {
            return em.createQuery("select u from Customer u where username=:username", Customer.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Retrieves all {@link Customer} objects from database
     *
     * @return list of all {@link Customer} objects from database
     */
    @Override
    public List<Customer> getAll() {
        TypedQuery<Customer> query = em.createQuery("SELECT u FROM Customer u",
                Customer.class);
        return (List<Customer>) query.getResultList();
    }

    /**
     * Updates attributes of an existing {@link Customer} object in database
     *
     * @param customer {@link Customer} object with updated attributes
     */
    @Override
    public void update(Customer customer) {
        em.persist(customer);
    }

    /**
     * Deletes an existing {@link Customer} entry from database
     *
     * @param customer {@link Customer} object to delete from database
     */
    @Override
    public void delete(Customer customer) {
        em.remove(customer);
    }
}
