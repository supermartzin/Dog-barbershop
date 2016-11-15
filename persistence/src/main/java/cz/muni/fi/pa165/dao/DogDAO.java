package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.exceptions.DAOException;

import java.util.List;

/**
 * Data access object for retrieving {@link Dog} objects from database.
 *
 * @author Dominik Gmiterko
 */
public interface DogDAO {

    /**
     * Creates new entry in database from provided {@link Dog} object.
     *
     * @param dog                       {@link Dog} object to save
     * @throws IllegalArgumentException when {@code dog} is {@code null}
     * @throws DAOException             when {@code dog} is in invalid state or the object cannot be saved to database
     */
    void create(Dog dog) throws DAOException;

    /**
     * Retrieves a {@see Dog} object with provided <b>ID</b> from database.
     *
     * @param id                        <b>ID</b> number of {@link Dog} to retrieve
     * @return                          found {@link Dog} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException when {@code id} is negative number
     */
    Dog getById(long id);

    /**
     * Retrieves all {@link Dog} objects from database.
     *
     * @return              list with all {@link Dog} objects from database
     *                      or <b>empty list</b> if there is no entry in database
     * @throws DAOException when {@link Dog} objects cannot be retrieved from database
     */
    List<Dog> getAll() throws DAOException;

    /**
     * Updates attributes of an existing {@link Dog} object in database.
     *
     * @param dog                       {@link Dog} object with updated attributes
     * @throws IllegalArgumentException when {@code dog} is {@code null}
     * @throws DAOException             when {@code dog} is in invalid state or the object cannot be updated in database
     */
    void update(Dog dog) throws DAOException;

    /**
     * Deletes an existing {@link Dog} entry from database.
     *
     * @param dog                       {@link Dog} object to delete from database
     * @throws IllegalArgumentException when {@code dog} is {@code null}
     * @throws DAOException             when {@code dog} does not exist or the object cannot be removed from database
     */
    void delete(Dog dog) throws DAOException;
}
